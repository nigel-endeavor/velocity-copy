import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Service } from '../../../models/service.model';
import { OrderEditService } from '../../../order-detail-page/order-edit.service';
import { select, Store } from '@ngrx/store';
import { cloneService } from '../../../order-detail-page/ngrx/order-details.actions';
import { getOrder } from '../../../order-detail-page/ngrx/order-details.selectors';
import { distinctUntilChanged, map, skip } from 'rxjs';
import isEqual from 'lodash.isequal';
import { LookupValueService } from "../../../services/lookup-value.service";
import { LookupValue } from "../../../models/lookup-value.model";

@Component({
  standalone: true,
  selector: 'app-cancel-clone-dialog',
  templateUrl: './cancel-clone-dialog.component.html',
  styleUrls: ['../../../order-detail-page/form-styles.scss'],
  imports: [
    FormsModule,
    CommonModule
  ]
})
export class CancelCloneDialogComponent implements OnInit {
  @ViewChild('dialog') dialog: ElementRef;
  @Output() done = new EventEmitter();
  @ViewChild('dialogForm') dialogForm: NgForm;
  @Input() service: Service;

  selectedServiceType: string;
  serviceTypes: LookupValue[];

  private order$ = this.store.pipe(
    select(getOrder),
    skip(1),
    distinctUntilChanged((a, b) => isEqual(a, b)),
    map(order => {
      this.dialogForm.resetForm();
      this.done.emit();
    })
  )

  constructor(
    public oes: OrderEditService,
    private store: Store,
    private lookupValueService: LookupValueService
  ) { }

  ngOnInit(): void {
    this.lookupValueService.find('TENANT_SERVICE_TYPES').subscribe((values: LookupValue[]) => { this.serviceTypes = values });
    this.order$.subscribe();
  }

  ngAfterViewInit(): void {
    this.dialog.nativeElement.showModal();
  }


  cloneAndCancel(): void {
    //copy
    //call the target service type's API to create a new service with the source service as the payload
    this.store.dispatch(cloneService({ serviceType: this.selectedServiceType, service: this.service }));

    // this.oes.cloneService(this.selectedServiceType, this.service)?.subscribe({
    //   next: (serviceRes: Service) => {
    //     this.clonedAndCancelledService.emit(serviceRes);
    //     this.dialogForm.resetForm();
    //     this.done.emit();
    //   },
    //   error: (error: any) => {
    //     // Display an error message indicating why the clone and cancel failed
    //     throw Error(`Error cloning and canceling the service: ${error}`);
    //   }
    // });
  }
  onCancelClicked(): void {
    this.dialogForm.resetForm();
    this.done.emit();
  }
}
