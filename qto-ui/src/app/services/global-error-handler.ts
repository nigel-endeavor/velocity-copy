import { HttpErrorResponse } from "@angular/common/http";
import { ErrorHandler, Injectable } from "@angular/core";
import { ErrorDialogService } from "../components/error-dialog/error-dialog.service";
import { Router } from "@angular/router";

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(
    private errorDialogService: ErrorDialogService,
    private router: Router
  ) {}

  handleError(error: any): void {
    if (error.message && error.message.includes('interaction_in_progress')) {
      return;
    }
    if (error.message && error.message.includes('NG04002')) { //swallows unknown route error when user cache is cleared
      return;
    }
    if (error.message && error.message.includes('ChunkLoadError')) { //forces a reload of the page when a chunk load error occurs
      window.location.reload();
      return;
    }
    console.error('Error from global error handler', error);
    if (error.error && typeof error.error == "string" && error.error.includes('Row was updated or deleted by another transaction')) {
      error.error = 'Your updates could not be saved because this entity was updated by another user. Please refresh the page and try again.';
    }
    let message = "";
    if (error.status == 400) {
      let vAny: any = error.error.errors;
      for (var val of vAny) {
        let msg = val.message + '\r\n';
        message += msg
      }
    } else {
      message = error.message
      if (error instanceof HttpErrorResponse && error.error) {
        if (error.error.includes(':')) {
          message = error.error.split(':')[1];
        } else {
          message = error.error;
        }
      }
    }

    this.errorDialogService.openDialog(message, error.status);
  }
}
