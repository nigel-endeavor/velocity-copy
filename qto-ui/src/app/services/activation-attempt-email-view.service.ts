import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { ActivationAttemptEmailView } from '../models/activation-attempt-email-view.model';

@Injectable({
  providedIn: 'root'
})
export class ActivationAttemptEmailViewService extends AbstractModelService<ActivationAttemptEmailView> {
  override path = '/activationAttemptEmailViews';
}
