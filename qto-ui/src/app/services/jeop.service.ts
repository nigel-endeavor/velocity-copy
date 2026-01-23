import { Injectable } from '@angular/core';
import {AbstractModelService} from "./abstract-model.service";
import {Jeop} from "../models/jeop.model";

@Injectable({
  providedIn: 'root'
})
export class JeopService extends AbstractModelService<Jeop>{
  override path = '/jeopPathNotSet';
  public type: string;

  setType(value:string) {
    this.type = value;
    this.path = '/' + this.type + 'Jeops';
  }
}
