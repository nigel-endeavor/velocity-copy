import { EventEmitter, Injectable } from "@angular/core";
import { HttpClient, HttpResponse} from '@angular/common/http';
import { Observable, catchError, map, of, switchMap, throwError} from "rxjs";
import { environment } from "../../environments/environment";


@Injectable({
    providedIn: 'root'
})

export class HelpDeskService {

    constructor(
        protected http: HttpClient,
        ) { }

    public onHelpDeskRequest = new EventEmitter<string>();

    submitFormData(formData: any): Observable<any> {
        let url = environment.qtoUrl + '/helpdesk';
        console.log("ff", formData);
        return this.http.post<any>(url, formData)
    }
}