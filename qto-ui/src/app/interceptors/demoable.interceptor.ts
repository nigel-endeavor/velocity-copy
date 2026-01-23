import {
  HttpClient, HttpErrorResponse, HttpEvent,
  HttpHandler, HttpInterceptor, HttpRequest, HttpResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, of, throwError } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { catchError, delay, mergeMap } from 'rxjs/operators';

import { JsonFile, Payload } from './demoable.interface';
import { DemoStoreState } from '../features/demo-mode/demo-store.reducer';
import { selectDemoStoreState } from '../features/demo-mode/demo-store.selectors';

@Injectable({
  providedIn: 'root'
})
export class DemoInterceptor implements HttpInterceptor {
  public demoConfig?: DemoStoreState;

  constructor(
    private store: Store<DemoStoreState>,
    private http: HttpClient,
  ) {
    // Doing this in the interceptor itself causes issues of caching responses, as well as piled up observables
    // that seem to never get freed. So we will do this once on load.
    this.store.pipe(
      select(selectDemoStoreState)
    ).subscribe((state: DemoStoreState) => {
      this.demoConfig = state;
    });
  }


  /**
   * Check our config and see if we're in demo mode. If not, make requests as usual.
   * If we are, then try to find the json file associated with this api call.
   *
   * @param request
   * @param next
   */
  public intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const regex = new RegExp(/^\/assets/);
    if (!this.demoConfig || !this.demoConfig.enabled || regex.test(request.url)) {
      return next.handle(request);
    }

    try {
      // Don't remove fileType, in case if we add enterprise files we need it
      // this.demoConfig.isEnterprise ? 'ent' : 'mid';
      let requestUrl = request.url;
      const fileType = 'mid';
      const hasHttp = requestUrl.match(/^https?:/) !== null;
      if (!hasHttp) {
        requestUrl = `https:${requestUrl}`;
      }
      const url = new URL(requestUrl);
      if (url.pathname.includes('/assets/')) {
        return next.handle(request);
      }
      const pathname = `/assets/demo-data/json${url.pathname}.${fileType}.json`;
      return this.getPayload(
        url,
        pathname,
        this.demoConfig.slowdownEnabled ? this.demoConfig.slowdown : 0,
        request,
        next,
      );
    } catch(e) {
      return next.handle(request);
    }
  }

  /*
   * If we can get the json file associated with the url in the request, then try to find a payload
   * in that json file based on the method type, and the arguments (body/params) of the request if
   * they're defined.  If all else fails, just go back to making a standard http call.
   */
  public getPayload(
    url: URL,
    pathname: string,
    slowdown: number,
    request: HttpRequest<unknown>,
    next: HttpHandler,
  ): Observable<HttpEvent<unknown>> {
    const slowdownInSeconds = slowdown * 1000;
    return this.http.get<JsonFile>(pathname).pipe(
      delay(slowdownInSeconds),
      mergeMap(json => {
        console.groupCollapsed(`DEMO MODE REQUEST: (${request.method}) ${url.pathname}`);
        console.log('  - Body: ', request.body);

        const payload =
          request.method.toLowerCase() === 'get'
            ? this.findPayloadFromParams(json.payloads, request)
            : this.findPayload(json.payloads, request);
        if (!payload) {
          console.log('  - Response: ', '-- No payload found. --');
          console.groupEnd();
          return next.handle(request);
        }
        if (payload.error) {
          console.log('  - Response: ', '-- Error: ', payload.error);
          console.groupEnd();
          // @ts-ignore
          return throwError(new HttpErrorResponse(payload.error));
        }
        let body = this.massageData(payload.value);
        // eslint-disable-next-line no-console
        console.log('  - Response: ', body);
        // eslint-disable-next-line no-console
        console.groupEnd();
        return of(
          new HttpResponse({
            status: 200,
            body,
          }),
        );
      }),
      catchError(() => next.handle(request)),
    );
  }

  private findPayload(payloads: Payload[], req: HttpRequest<unknown>, params?: Record<string, string>): Payload {
    let asObject = {};
    if (!params && req && req.body) {
      // eslint-disable-next-line
      const requestBody = req.body as any;
      if (requestBody.encoder && requestBody.map) {
        // handle case with body encoded as HttpParams

        try {
          requestBody.map.forEach((value: any, key: string | number) => {
            // @ts-ignore
            asObject[key] = requestBody.encoder.decodeValue(value);
          });
        } catch (e) {
          console.log('request decoding failed');
        }
      }
    }
    const data = params ?? req.body;
    const requestMethod = req.method.toLowerCase();

    // @ts-ignore
    return payloads.find(p => {
      let isMatch = p.method === requestMethod;

      if (isMatch && p.args) {
        isMatch = p.isRequestDecoded ? doesContain(p.args, asObject) : doesContain(p.args, data);
      }

      return isMatch;
    });
  }

  private findPayloadFromParams(payloads: Payload[], req: HttpRequest<unknown>): Payload {
    const params = req.params.keys().reduce(
      // @ts-ignore
      (acc: Record<string, string>, key: string) => ({
        ...acc,
        [key]: req.params.get(key),
      }),
      {},
    );

    // @ts-ignore
    return this.findPayload(payloads, req, params);
  }

  private massageData(item: unknown): unknown {
    if (item === null || item === undefined) {
      return item;
    }
    if (typeof item === 'string') {
      item = this.parseByType(item);
    } else if (Array.isArray(item)) {
      item = item.map(i => this.parseByType(i));
    } else if (typeof item === 'object') {
      const value = { ...item };
      item = Object.keys(value).reduce((acc, k: string) => {
        // @ts-ignore
        acc[k] = this.parseByType(value[k]);
        return acc;
      }, {});
    }
    return item;
  }

  private parseByType(item: unknown): unknown {
    if (typeof item === 'string') {
      return this.parseSpecialDate(item);
    } else if (Array.isArray(item)) {
      return item.map((i: unknown) => this.massageData(i));
    } else if (typeof item === 'object') {
      return this.massageData(item);
    }
    return item;
  }

  private parseSpecialDate(str: string | number): string | number {
    if (typeof str !== 'string') {
      return str;
    }

    return str;
  }
}

function doesContain(base: unknown, derived: unknown): boolean {
  if (isArgumentEmpty(base, derived)) {
    return false;
  }
  if (Array.isArray(base)) {
    // @ts-ignore
    return base.every((k, idx) => doesContain(k, derived[idx]));
  } else if (typeof base === 'object') {
    // @ts-ignore
    return Object.keys(base).every(k => doesContain(base[k], derived[k]));
  }
  // if the base value contains a splat (*), then it should compare to anything
  return base === '*' || base === derived;
}

function isArgumentEmpty(base: unknown, derived: unknown): boolean {
  // prevent casting "" to false and handling bool values
  const castablePrimitiveTypes = ['string', 'boolean'];
  return !castablePrimitiveTypes.includes(typeof base) && !castablePrimitiveTypes.includes(typeof derived) && !derived;
}
