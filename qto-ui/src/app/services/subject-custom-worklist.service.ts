import { Injectable } from "@angular/core";
import { SubjectCustomWorklist } from "../models/subject-custom-worklist.model";
import { AbstractModelService } from "./abstract-model.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SubjectCustomWorklistService extends AbstractModelService<SubjectCustomWorklist> {
  override path = '/subjectCustomWorklists';

  getCustomWorklists(worklistNamed: string): Observable<SubjectCustomWorklist[]> {
    let url = this.getUrl() + '?worklistName=' + worklistNamed;
    return this.http.get<SubjectCustomWorklist[]>(url);
  }

  saveFavoriteWorklist(worklist: SubjectCustomWorklist): Observable<SubjectCustomWorklist> {
    let url = this.getUrl() + '/saveFavoriteWorklist';
    return this.http.put<SubjectCustomWorklist>(url, worklist);
  }

  saveLastViewedDate(worklist: SubjectCustomWorklist): Observable<SubjectCustomWorklist> {
    let url = this.getUrl() + '/saveLastViewedDate';
    return this.http.put<SubjectCustomWorklist>(url, worklist);
  }

  deleteCustomWorklist(id: number): Observable<any> {
    let url = this.getUrl() + '/' + id;
    return this.http.delete(url);
  }
}
