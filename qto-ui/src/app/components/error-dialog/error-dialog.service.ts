import { Injectable } from '@angular/core';
import { ErrorDialogComponent } from './error-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ErrorDialogService {

  private errorDialog: ErrorDialogComponent;

  constructor() { }

  openDialog(message: string, status?: number) {
    return this.errorDialog.open(message, status);
  }


  setErrorDialogRef(component: ErrorDialogComponent) {
    this.errorDialog = component;
  }
}
