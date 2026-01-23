import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { EngineeringEmailMessagingService } from "../models/engineering-Email-messaging-service.model";


@Injectable({
  providedIn: 'root'
})
export class EngineeringEmailMessagingServiceService extends AbstractModelService<EngineeringEmailMessagingService> {
  override path = '/engineeringEmailMessagingService';
}
