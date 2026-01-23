import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Store } from '@ngrx/store';
import { importActivityTableActions } from '../../features/import-activity-worklist/configs/table.config';
import { ImportActivity } from '../../models/import-activity.model';
import { ImportActivityService } from '../../services/import-activity.service';
import { ImportType } from 'src/app/models/constants/import-type';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { LookupValueService } from "../../services/lookup-value.service";

@Component({
  selector: 'app-file-import-dialog',
  templateUrl: './file-import-dialog.component.html',
  styleUrls: ['./file-import-dialog.component.scss']
})
export class FileImportDialogComponent {

  importTypes: string[] = Object.values(ImportType);
  filteredImportTypes: string[] = [];
  serviceTypes: LookupValue[];

  selectedImportType: string;
  selectedFile: File;

  constructor(
    private dialogRef: MatDialogRef<FileImportDialogComponent>,
    private importActivityService: ImportActivityService,
    private store: Store,
    private lookupValueService: LookupValueService
  ) { }

  onClose(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    const keepAlways = ['Master Customer', 'End Customer'];
    this.lookupValueService.find('TENANT_SERVICE_TYPES').subscribe((values: LookupValue[]) => {
      this.serviceTypes = values;
      // Filter the importTypes array
      this.filteredImportTypes = this.importTypes.filter(
      importType => this.serviceTypes.some(service => service.value === importType) || keepAlways.includes(importType));
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onUploadClicked(): void {
    this.importActivityService.uploadFile(this.selectedImportType, this.selectedFile).subscribe(
      (res: ImportActivity) => {
        this.store.dispatch(importActivityTableActions.loadTableData())
        this.dialogRef.close();
      }
    );
  }

  onDownloadClicked(): void {
    this.importActivityService.downloadTemplate(this.selectedImportType).subscribe((res: HttpResponse<Blob>) => {
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(res.body!);
      a.href = url;
      a.download = res.headers.get('Content-Disposition')!.split('=')[1];
      document.body.append(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    }, (err) => {
      let reader = new FileReader();
      reader.onload = () => {
        throw Error(reader.result as string);
      };
      reader.readAsText(err.error);
    });
  }
}
