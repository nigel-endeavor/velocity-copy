import { Component, EventEmitter, Output } from '@angular/core';
import { ServiceMilestoneInstance } from 'src/app/models/milestone-instance.model';
import { Milestone } from 'src/app/models/milestone.model';
import { MilestoneDisplaySetService } from 'src/app/services/milestone-display-set.service';
import { ServiceMilestoneInstanceService } from 'src/app/services/service-milestone-instance.service';
import { AbstractMilestonesComponent } from './abstract-milestones.component';
import { Service } from 'src/app/models/service.model';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { NoteComponent } from '../note/note.component';
import { JeopsComponent } from '../jeops/jeops.component';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { ServiceType } from 'src/app/models/constants/service-type';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { MatDividerModule } from '@angular/material/divider';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { DatetimepickerModule } from '../datetimepicker/datetimepicker.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { Store, select }from '@ngrx/store';
import { loadInventoryLocation, reloadOrder, setMissingServiceField } from '../../order-detail-page/ngrx/order-details.actions';
import { MatLegacyDialog } from '@angular/material/legacy-dialog';
import { JeopService } from 'src/app/services/jeop.service';
import { getInventoryLocation, getIsInventory, getSelectedLocation } from '../../order-detail-page/ngrx/order-details.selectors';
import { Observable, take } from 'rxjs';
import { addressToString } from '../../utilities';
import { MatDialog } from '@angular/material/dialog';
import { OptionsDialogComponent } from './options-dialog.component';

@Component({
  standalone: true,
  selector: 'app-service-milestones',
  templateUrl: './abstract-milestones.component.html',
  styleUrls: ['./abstract-milestones.component.scss'],
  imports: [
    CommonModule,
    NoteComponent,
    JeopsComponent,
    DatePipe,
    FormsModule,
    NgxDaterangepickerMd,
    MatDividerModule,
    DatepickerModule,
    DatetimepickerModule,
    MatProgressSpinnerModule,
  ]
})
export class ServiceMilestonesComponent extends AbstractMilestonesComponent<ServiceMilestoneInstance> {
  private service: Service;
  public isInventory: boolean;
  public inventoryLocation$: Observable<any>;
  public selectedLocation$: Observable<any>;
  @Output() missingServiceField = new EventEmitter<{milestoneName: string, missingFieldName: string, missingFieldDisplayName: string}>();

  constructor(private milestoneDisplaySetService: MilestoneDisplaySetService,
              public oes: OrderEditService,
              private serviceMilestoneInstanceService: ServiceMilestoneInstanceService,
              public securityUtils: SecurityUtilService,
              public store: Store,
              private dialogService: MatLegacyDialog,
              private jeopService: JeopService,
              private dialog: MatDialog
  ) {
    super(milestoneDisplaySetService, oes, securityUtils, store, dialogService, jeopService);
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
  }

  override ngOnInit(): void {
    this.selectedLocationOrService$.subscribe(service => {
      if (service instanceof Service) {
        this.service = service;
        if (service.orderType == 'Disconnect') {
          this.displayGroup = 'DISCONNECT_MILESTONE';
        } else if ((service.orderType == 'Move' || service.orderType == 'Add' || service.orderType == 'Change')
        && !service.subOrderType.includes('New Service')) {
          this.displayGroup = 'EXISTING_MAC_MILESTONE';
        } else {
          switch (service.type) {
            case ServiceType.BROADBAND:
              this.displayGroup = 'BROADBAND_SERVICE_MILESTONE';
              break;
            case ServiceType.DIA:
              this.displayGroup = 'DIA_SERVICE_MILESTONE';
              break;
            case ServiceType.UCAAS:
              this.displayGroup = 'UCAAS_SERVICE_MILESTONE'
              break;
            case ServiceType.G:
              this.displayGroup = '4G5G_SERVICE_MILESTONE';
              break;
            case ServiceType.CROSSCONNECT:
              this.displayGroup = 'CROSS_CONNECT_SERVICE_MILESTONE';
              break;
            case ServiceType.ETHERNET:
              this.displayGroup = 'ETHERNET_SERVICE_MILESTONE';
              break;
            case ServiceType.TELEVISION:
              this.displayGroup = 'TELEVISION_SERVICE_MILESTONE';
              break;
            case ServiceType.MPLS:
              this.displayGroup = 'MPLS_SERVICE_MILESTONE';
              break;
            case ServiceType.THREATMDR:
              this.displayGroup = 'THREATMDR_SERVICE_MILESTONE';
              break;
            case ServiceType.RANSOMMDR:
              this.displayGroup = 'RANSOMMDR_SERVICE_MILESTONE';
              break;
            case ServiceType.RISKMDR:
              this.displayGroup = 'RISKMDR_SERVICE_MILESTONE';
              break;
            case ServiceType.ENGINEERING_IAM:
              this.displayGroup = 'ENGINEERING_IAM_SERVICE_MILESTONE';
              break;
            case ServiceType.ENGINEERING_MDM:
              this.displayGroup = 'ENGINEERING_MDM_SERVICE_MILESTONE';
              break;
            case ServiceType.ENGINEERING_ENDPOINT:
              this.displayGroup = 'ENGINEERING_ENDPOINT_SERVICE_MILESTONE';
              break;
            case ServiceType.ENGINEERING_INFO_PROTECTION:
              this.displayGroup = 'ENGINEERING_INFO_PROTECTION_SERVICE_MILESTONE';
              break;
            case ServiceType.ENGINEERING_EMAIL_MESSAGING:
              this.displayGroup = 'ENGINEERING_EMAIL_MESSAGING_SERVICE_MILESTONE';
              break;
            case ServiceType.CYBER360MXDR:
              this.displayGroup = 'CYBER360_MXDR_SERVICE_MILESTONE';
              break;   
            case ServiceType.MICROSOFTLICENSES:
              this.displayGroup = 'MICROSOFT_LICENSES_SERVICE_MILESTONE';
              break;   
            default:
              window.location.reload();
          }
        }
        if (!this.isInventory) {
          this.store.dispatch(loadInventoryLocation({ serviceId: service.id }));
        }
      }

      if (!this.isInventory) {
        this.inventoryLocation$ = this.store.pipe(select(getInventoryLocation));
        this.selectedLocation$ = this.store.pipe(select(getSelectedLocation));
      }
    })
    super.ngOnInit();
  }

  override getMilestoneInstanceService(): ServiceMilestoneInstanceService {
    return this.serviceMilestoneInstanceService;
  }

  override getNewInstance(milestone: Milestone): ServiceMilestoneInstance {
    let instance = new ServiceMilestoneInstance();
    instance.serviceId = this.parentId;
    instance.milestone = milestone;
    return instance;
  }

  override getMilestoneInstanceClass() {
    return ServiceMilestoneInstance;
  }

  override onSaveClicked(instance: ServiceMilestoneInstance): void {
    if (!this.isInventory) {
      this.inventoryLocation$.pipe(take(1)).subscribe(inventoryLocation => {
        this.selectedLocation$.pipe(take(1)).subscribe(selectedLocation => {
          if (instance.milestone.code == 'PROVIDER_ORDER_SUBMITTED' && this.service.contractTerm == null) {
            this.store.dispatch(setMissingServiceField({
              milestoneName: 'Provider Order Submitted',
              missingFieldName: 'contractTerm',
              missingFieldDisplayName: 'Contract Term'
            }));
            return;
          } else if (instance.milestone.code == 'DATA_PROVISIONING_COMPLETE' && this.service.contractTerm == null) {
            this.store.dispatch(setMissingServiceField({
              milestoneName: 'Data Provisioning Complete',
              missingFieldName: 'contractTerm',
              missingFieldDisplayName: 'Contract Term'
            }));
            return;
          } else if (instance.milestone.code == 'NETWORK_PROVIDER_FOC' && this.service.contractTerm == null) {
            this.store.dispatch(setMissingServiceField({
              milestoneName: 'Network Provider FOC',
              missingFieldName: 'contractTerm',
              missingFieldDisplayName: 'Contract Term'
            }));
            return;
          } else if (inventoryLocation) {
            if (instance.milestone.code == 'COMPLETE' && instance.milestoneDate) {
              let serviceFormattedAddress = addressToString(selectedLocation.address1, selectedLocation.address2, selectedLocation.city, selectedLocation.state, selectedLocation.postalCode, selectedLocation.country);
              let inventoryFormattedAddress = addressToString(inventoryLocation.address1, inventoryLocation.address2, inventoryLocation.city, inventoryLocation.state, inventoryLocation.postalCode, inventoryLocation.country);
              if (serviceFormattedAddress != inventoryFormattedAddress) {
                let message = `The service order you are completing for Client Location ID "${selectedLocation.clientLocationId}" has a different address in inventory from your order.  Please select which address to keep for this inventory record.`;
                const dialogRef = this.dialog.open(OptionsDialogComponent, {
                  data: {
                    title: 'Attention', message: message,
                    options: [
                      {header: 'Your Address', content: serviceFormattedAddress},
                      {header: 'Inventory Address', content: inventoryFormattedAddress}
                    ]
                  },
                  maxWidth: '600px'
                });

                dialogRef.afterClosed().subscribe(result => {
                  if (result) {
                    instance.useExistingInventoryLocationAddress = result.header == 'Inventory Address';
                    super.onSaveClicked(instance);
                  } else {
                    this._store.dispatch(reloadOrder());
                  }
                });
                return;
              }
            }
          }
          super.onSaveClicked(instance);
        });
      });
    } else {
      super.onSaveClicked(instance);
    }
  }
}
