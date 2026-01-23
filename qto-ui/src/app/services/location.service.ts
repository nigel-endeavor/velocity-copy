import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { Location } from "../models/location.model";

@Injectable({
  providedIn: 'root'
})
export class LocationService extends AbstractModelService<Location> {
  override path = '/locations';
}