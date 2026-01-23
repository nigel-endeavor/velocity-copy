import { Component } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { HelpDeskService } from 'src/app/services/help-desk.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { catchError, throwError } from 'rxjs';

@Component({
  selector: 'app-help-desk',
  templateUrl: './help-desk.component.html',
  styleUrls: ['./help-desk.component.scss']
})
export class HelpDeskComponent {

  isLoading: boolean = true;
  formData = {
    title: '',
    description: '',
    attachment: [],
    fileName: '',
  };

  submitted: boolean = false;
  showDialog: boolean = false;
  message: string = '';

  constructor(
    private http: HttpClient,
    public securityUtils: SecurityUtilService,
    private helpDeskService: HelpDeskService,
    public dialog: MatDialog,
  ) {
  }

  ngOnInit(): void {
    this.helpDeskService.onHelpDeskRequest.subscribe(response => {
    });
  }

  async onSubmit() {
    this.submitted = true;
    const fileInput = document.getElementById("fileInput") as HTMLInputElement | null;

    if (fileInput && fileInput.files && fileInput.files.length > 0) {
      this.formData.fileName = encodeFilename(fileInput.files[0].name)
      this.formData.attachment = await this.getByteArray(fileInput.files[0]) as any;
    }

    const requestData = {
      description: this.formData.description,
      title: this.formData.title,
      attachment: this.formData.attachment,
      fileName: this.formData.fileName,
    };

    this.helpDeskService.submitFormData(requestData).pipe(
      catchError((error: HttpErrorResponse) => {
        this.openDialog("The was an error creating your ticket.")
        return throwError(() => new Error("error"))
      })
    ).subscribe(
      response => {
        this.openDialog("Ticket created successfully.")
      });
  }

  getByteArray(file: File) {
    return new Promise(function(resolve, reject) {
        let fileReader = new FileReader();
        fileReader.readAsArrayBuffer(file);
        fileReader.onload = function(ev) {
            const array = new Uint8Array(ev.target!.result as ArrayBuffer);
            const fileByteArray = [];
            for (let i = 0; i < array.length; i++) {
                fileByteArray.push(array[i]);
            }
            resolve(array);
        }
        fileReader.onerror = reject;
    })
  }


  openDialog(message: string): void {
    console.log('openDialog', message);
    console.log("location", location)
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '500px',
      data: { message }
    });

  }
}

function encodeFilename(filename: string | number | boolean) {
  return encodeURIComponent(filename);
}
