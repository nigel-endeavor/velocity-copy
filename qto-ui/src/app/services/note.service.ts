import { Injectable } from '@angular/core';
import {AbstractModelService} from "./abstract-model.service";
import {Note} from "../models/note.model";
import { PaginatedResult } from '../models/paginated-result.model';

@Injectable({
  providedIn: 'root'
})
export class NoteService extends AbstractModelService<Note>{
  override path = '/notePathNotSet';
  public type: string;

  setType(type: string) {
    this.type = type;
    this.path = '/' + this.type + 'Notes';
  }

  getAuditNotes(criteria: any) {
    let url = this.getUrl() + '/audit';
    return this.http.get<PaginatedResult<Note>>(url, { params: this.getParams(criteria) });
  }
}
