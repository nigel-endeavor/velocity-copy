import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Observable, Subject, debounceTime, filter } from 'rxjs';
import { importActivityTableActions, importActivityTableSelectors } from './configs/table.config';
import { Store, select } from '@ngrx/store';
import { loadSubjects, toggleColumn, updateFilters, updateSort } from './ngrx/import-activity-worklist.actions';
import { ImportActivity } from 'src/app/models/import-activity.model';
import { CommonColumn } from 'src/app/interfaces/columns.interface';
import { getColumns, getFilters, getUploadedByOptions } from './ngrx/import-activity-worklist.selectors';
import { MatDialog } from '@angular/material/dialog';
import { FileImportDialogComponent } from 'src/app/components/file-import-dialog/file-import-dialog.component';
import { ImportActivityService } from 'src/app/services/import-activity.service';
import { ImportType } from 'src/app/models/constants/import-type';
import { AbstractFileAttachmentService } from 'src/app/services/abstract-file-attachment.service';
import { ActivatedRoute } from '@angular/router';
import { SecurityUtilService, Permissions } from 'src/app/services/security-util.service';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { LookupValueService } from "../../services/lookup-value.service";

@Component({
  selector: 'app-import-activity-worklist',
  templateUrl: './import-activity-worklist.component.html',
  styleUrls: ['../worklist-styles.scss']
})
export class ImportActivityWorklistComponent implements OnInit {

  public tableTotal$: Observable<number> = this.store.pipe(select(importActivityTableSelectors.getTableTotal));
  public isLoading$ = this.store.pipe(select(importActivityTableSelectors.getTableDataIsLoading));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public tableData$: Observable<any[]> = this.store.pipe(select(importActivityTableSelectors.getTableData));
  public uploadedByOptions$: Observable<any[]> = this.store.pipe(select(getUploadedByOptions));

  public columns$ = this.store.pipe(select(getColumns));
  public importTypes: string[] = this.securityUtil.userHasPermission(Permissions.FILE_IMPORT) ? Object.values(ImportType) : ['Order'];

  filteredImportTypes: string[] = [];
  serviceTypes: LookupValue[];

  private subject: Subject<{ key: string, value: any }> = new Subject();

  constructor(
    private store: Store,
    public dialog: MatDialog,
    private importActivityService: ImportActivityService,
    private renderer: Renderer2,
    private attachmentService: AbstractFileAttachmentService,
    private route: ActivatedRoute,
    public securityUtil: SecurityUtilService,
    public lookupValueService: LookupValueService
  ) {
    this.subject.pipe(debounceTime(500)).subscribe(({ key, value }) => {
      this.store.dispatch(updateFilters({ key, value }));
    });
  }

  ngOnInit(): void {
    const keepAlways = ['Master Customer', 'End Customer'];
    this.lookupValueService.find('TENANT_SERVICE_TYPES').subscribe((values: LookupValue[]) => {
      this.serviceTypes = values;
      // Filter the importTypes array
      this.filteredImportTypes = this.importTypes.filter(
      importType => this.serviceTypes.some(service => service.value === importType) || keepAlways.includes(importType));
    });
    this.route.queryParams.subscribe(params => {
      if (params['importType']) {
        this.store.dispatch(updateFilters({ key: 'importType', value: params['importType'] }));
      }
    });
    this.store.dispatch(importActivityTableActions.loadTableData());
    this.store.dispatch(loadSubjects());
    this.importActivityService.connect();
    this.importActivityService.refreshSubject.subscribe(() => {
      this.store.dispatch(importActivityTableActions.loadTableData());
    });
  }

  ngOnDestroy(): void {
    this.importActivityService.disconnect();
  }

  onImportClicked(): void {
    this.dialog.open(FileImportDialogComponent, {
      panelClass: 'file-import-dialog',
    });
  }

  onImportActivityDblClicked(importActivity: ImportActivity): void {
    if (!importActivity.errorFileAttachment) {
      return;
    }
    this.attachmentService.download(importActivity.errorFileAttachment.id).subscribe((res: any) => {
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(res);
      a.href = url;
      a.download = importActivity.errorFileAttachment.name;
      document.body.append(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    })
  }

  downloadUploadedSpreadsheet(importActivity: ImportActivity): void {
    if (!importActivity.fileAttachment) {
      return;
    }
    this.attachmentService.download(importActivity.fileAttachment.id).subscribe((res: any) => {
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(res);
      a.href = url;
      a.download = importActivity.fileAttachment.name;
      document.body.append(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    })
  }

  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(toggleColumn({ columnName: col.propertyName}));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.store.dispatch(updateFilters({ key, value}));
  }

  exportTable(fileName: string): void {
    this.store.dispatch(importActivityTableActions.exportTable({ fileName }));
  }


  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuImportActivity: any;
  onImportActivityRightClicked(data: any) {
    const event = data.event;
    const importActivity = data.row;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    }
    this.contextMenuImportActivity = importActivity;
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        this.contextMenuImportActivity = null;
        listenerFn(); //removes click listener
      }
    });
  }

}
