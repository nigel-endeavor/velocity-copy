import { distinctUntilChanged, from, Observable, of } from 'rxjs';
import { Inject, Injectable } from '@angular/core';
import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { select, Store } from '@ngrx/store';
import {
  catchError,
  filter,
  map,
  switchMap,
  takeUntil,
  tap,
  withLatestFrom,
} from 'rxjs/operators';
import isEqual from 'lodash.isequal';

import { SharedTableEffectsConfigToken, SharedTableServiceToken } from '../tokens';
import { AbstractModelService } from '../../../services/abstract-model.service';
import { SharedTableConfig } from '../interfaces';


export type Effect = ReturnType<typeof createEffect>;

@Injectable()
export class SharedTableEffects<Service extends AbstractModelService<any>> {
  constructor(
    private actions$: Actions,
    private store: Store,
    @Inject(SharedTableServiceToken) private service: {
      [key: string]: (request: any) => Observable<any>
    },
    @Inject(SharedTableEffectsConfigToken) private config: SharedTableConfig,
  ) {}
  public loadTableRequest$ = createEffect(() => this.actions$.pipe(
    ofType(this.config.actions.loadTableData),
    withLatestFrom(this.store.pipe(select(this.config.getReadFilters))),
    map(([_, filters]) => {
      return this.config.actions.startLoadTableData({ filters })
    })
  ));


  public getTableRequest$ = createEffect(() => this.actions$.pipe(
    ofType(this.config.actions.startLoadTableData),
    switchMap((action) => {
        return this.service[this.config.readMethodName](action.filters).pipe(
          takeUntil(this.actions$.pipe(ofType(this.config.destroyAction))),
        )
      }
    ),
    map(records =>
      this.config.actions.loadTableDataSuccess({
        records: records.collection,
        total: records.total
      })
    ),
    catchError(() =>
      from([
        this.config.actions.loadTableDataFailure(),
      ]),
    ),
  ));

  public loadTableOnFilter$ = createEffect(() => this.actions$.pipe(
    withLatestFrom(this.store.pipe(select(this.config.getReadFilters))),
    map(([_, filters]) => filters),
    filter(item => !!item ),
    distinctUntilChanged((a, b) => isEqual(a, b)),
    map((filters) => {
      return this.config.actions.startLoadTableData({ filters })
      }
    )
  ));

  public exportTable$ = createEffect(() =>
  this.actions$.pipe(
    ofType(this.config.actions.exportTable),
    concatLatestFrom(() => [
      this.store.pipe(select(this.config.getReadFilters)),
      this.store.pipe(select(this.config.getColumns))
    ]),
    switchMap(([action, filters, columns]) => {
      let fields = '';
      let headers = '';
      let exportedColumns = [...columns];

      exportedColumns.forEach((col: any) => {
        if (!col.hidden) {
          fields += col.propertyName + ',';
          headers += col.name + ',';
        }
      });
      // Removes trailing commas
      fields = fields.slice(0, -1);
      headers = headers.slice(0, -1);

      return this.service['export']({
        ...filters,
        fields,
        headers,
        format: 'xlsx'
      }).pipe(
        map((res: any) => {
          let downloadLink = document.createElement('a');
          downloadLink.href = window.URL.createObjectURL(new Blob([res], { type: res.type }));
          if (action.fileName) {
            downloadLink.setAttribute('download', action.fileName);
          }
          document.body.appendChild(downloadLink);
          downloadLink.click();
          downloadLink.parentNode?.removeChild(downloadLink);
          return this.config.actions.exportTableSuccess();
        }),
        catchError(() => of(this.config.actions.exportTableFailure())),
      );
    })
  )
);
}
