import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { Store, select } from '@ngrx/store';
import * as actions from './store/new-macd.actions';
import { NgForm } from '@angular/forms';
import {
  getDisconnectReasons,
  getFilteredOrderTypes,
  getFilteredSubOrderTypes,
  getMacdSelections,
  getOrderTypes,
  getProjectNames,
  getSaveMacdResult,
  getSubOrderTypes,
  getSubmitMacdsResult,
  selectMacdNote,
  selectCreateLinkedBundled,
  getIsLoading,
  getServiceTypes
} from './store/new-macd.selectors';
import { LookupValue } from '../../../models/lookup-value.model';
import { take } from 'rxjs';
import { Router } from '@angular/router';
import { FileAttachment } from 'src/app/models/file-attachment';
import { MatCheckboxChange } from "@angular/material/checkbox";

@Component({
  selector: 'app-new-macd',
  templateUrl: './new-macd.component.html',
  styleUrls: ['./new-macd.component.scss', '../../../order-detail-page/form-styles.scss']
})
export class NewMacdComponent implements OnInit {
  attachmentMap: Map<any, any[]> = new Map<any, any[]>();
  showForm: boolean = false;
  companyId: number;
  isMultiMacd: boolean = false;
  isLinkedOrBundled: boolean = false;
  showDialog: boolean = false;
  macdSelections$ = this.store.pipe(select(getMacdSelections));
  orderTypes$ = this.store.pipe(select(getOrderTypes));
  subOrderTypes$ = this.store.pipe(select(getSubOrderTypes));
  serviceTypes$ = this.store.pipe(select(getServiceTypes));
  disconnectReasons$ = this.store.pipe(select(getDisconnectReasons));
  projectNames$ = this.store.pipe(select(getProjectNames));
  filteredSubOrderTypes$ = this.store.pipe(select(getFilteredSubOrderTypes));
  filteredOrderTypes$ = this.store.pipe(select(getFilteredOrderTypes));
  saveMacdResult$ = this.store.pipe(select(getSaveMacdResult));
  submitMacdsResult$ = this.store.pipe(select(getSubmitMacdsResult));
  macdNote$ = this.store.pipe(select(selectMacdNote));
  createLinkedBundled$ = this.store.pipe(select(selectCreateLinkedBundled));
  isLoading$ = this.store.pipe(select(getIsLoading));


  currentOrderType: LookupValue | null = null;
  latestMacdSelection: { orderType: LookupValue | undefined, subOrderType: LookupValue | undefined, createDisconnectUponCompletion: boolean, disconnectReason: LookupValue | undefined }
    = {
      orderType: undefined,
      subOrderType: undefined,
      createDisconnectUponCompletion: false,
      disconnectReason: undefined
    };

  @ViewChild('newMacdForm') newMacdForm: NgForm;

  constructor(
    private router: Router,
    private dialogRef: MatDialogRef<NewMacdComponent>,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: {
      serviceIds: number[],
      companyId: number,
      isMultiMacd: boolean,
      linkedOrBundled: boolean
      fileAttachment: any;
    }) {
    this.companyId = data.companyId;
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.isMultiMacd = this.data.isMultiMacd;
    this.isLinkedOrBundled = this.data.linkedOrBundled;
    this.store.dispatch(actions.loadOrderTypes({ companyId: this.companyId }));
    this.store.dispatch(actions.loadSubOrderTypes({ companyId: this.companyId }));
    this.store.dispatch(actions.loadDisconnectReasons({ companyId: this.companyId }));
    this.store.dispatch(actions.loadProjectNames({ companyId: this.companyId }));
    this.store.dispatch(actions.loadServiceTypes({ companyId: this.companyId }));
    this.macdSelections$.subscribe((macdSelections) => {
      this.latestMacdSelection = macdSelections[macdSelections.length - 1];
    });
  }

  onClose() {
    this.store.dispatch(actions.pageDestroyed());
    this.dialogRef.close();
  }

  onSubmit() {
    const fileInput: HTMLInputElement | null = document.getElementById("file-input") as HTMLInputElement | null;

    let file: any = null;

    this.macdSelections$.pipe(take(1)).subscribe((macdSelections) => {
      if (fileInput && fileInput.files && fileInput.files.length > 0) {
        file = new FileAttachment();
        file.content = {data: fileInput.files[0]};
        file.description = "MACD Attachment";
      }
      this.store.dispatch(actions.saveMacd({ serviceIds: this.data.serviceIds, macds: macdSelections, isMultiMacd: this.data.isMultiMacd, fileAttachment: file }));
    });
    const saveSubscription = this.saveMacdResult$.subscribe((service) => {
      if (service) {
        saveSubscription.unsubscribe();
        this.onClose();
        this.router.navigate(['order', service.orderId, 'location', service.locationId, 'service', service.id]);
      }
    });
    const submitSubscription = this.submitMacdsResult$.subscribe((result) => {
      if (result) {
        submitSubscription.unsubscribe();
        this.onClose();
      }
    });
  }

  onAttachmentClicked(event: any, attachment: any): void {
    if (attachment.expanded) {
      return;
    }
    attachment.expanded = true;
    let self = this;
    //adds a click handler to the page to update attachment description when clicking outside attachment element
    const onClickHandler = function(e: any) {
      if (!event.srcElement.contains(e.target)) {
        attachment.expanded = false;
        document.removeEventListener('click', onClickHandler);
      }
    }
    document.addEventListener('click', onClickHandler);
  }

  setDirty(attachment: FileAttachment): void {
    attachment.dirty = true;
  }

  toggleShowDialog(): void {
    this.showDialog = !this.showDialog;
  }

  onPropertyUpdate(key: string, event: any, selection: number) {
    //pass key/value to actions for updating a field
    if (key === 'orderType') {
      this.currentOrderType = event.value;
    }
    this.store.dispatch(actions.updateMacdSelection({ selection, key, value: (event.value ? event.value : event.checked) }));
  }

  addMacd() {
    this.store.dispatch(actions.addMacd());
  }

  removeMacd(index: number) {
    this.store.dispatch(actions.removeMacd({ index }));
  }

  isFormInvalid() {
    //verify that disconnectReason is selected if orderType is Disconnect
    if (this.latestMacdSelection.orderType?.value === 'Disconnect'
      && this.latestMacdSelection.disconnectReason === undefined) {
      return true;
      //verify that disconnectReasons is selected if subOrderType starts with 'New Service' and craete disconnect upon completion is checked
    } else if (this.latestMacdSelection.subOrderType?.value.startsWith('New Service')
      && this.latestMacdSelection.createDisconnectUponCompletion
      && this.latestMacdSelection.disconnectReason === undefined) {
      return true;
      //verify that subOrderType is selected for all other cases
    } else if (!this.latestMacdSelection.subOrderType
      && !(this.latestMacdSelection.orderType?.value === 'Disconnect')) {
      return true;
    } else {
      return false;
    }
  }

  onMacdNoteChange(newMacdNote: any) {
    this.store.dispatch(actions.updateMacdNote({ macdNote: newMacdNote.target.value }));
  }

  onCreateLinkedBundledChange(value: MatCheckboxChange) {
    this.store.dispatch(actions.updateCreateLinkedBundled({ createLinkedBundled: value.checked }));
  }
}
