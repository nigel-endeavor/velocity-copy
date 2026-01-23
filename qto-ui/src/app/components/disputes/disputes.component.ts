import { Component, OnInit } from '@angular/core';
import { disputesTableSelectors } from './configs/table.config';
import { getColumns, getDisputeAssignments, getDisputeTypes, getFilters, getMetaData, getSelectedDispute } from './store/disputes.selectors';
import { Store, select } from '@ngrx/store';
import { Observable, filter } from 'rxjs';
import * as actions from './store/disputes.actions';
import { Dispute } from 'src/app/models/dispute.model';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import { getDisputesCount, getSelectedLocationOrService } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { Service } from 'src/app/models/service.model';
import { Location } from 'src/app/models/location.model';
import { plainToInstance } from 'class-transformer';
import { DisputesService } from 'src/app/services/disputes.service';
import { ActivatedRoute } from '@angular/router';
import { loadUserStatuses, reloadOrder, setActiveTab } from 'src/app/order-detail-page/ngrx/order-details.actions';
import { editEnabled } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { SubjectInterface } from 'src/app/models/subject.model';
import { SubjectService } from 'src/app/services/subject.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';

@Component({
  selector: 'app-disputes',
  templateUrl: './disputes.component.html',
  styleUrls: ['./disputes.component.scss', '../../order-detail-page/form-styles.scss']
})
export class DisputesComponent implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  serviceId: number;
  summaryBill: string | null;
  companyId: number;

  editDisabled = false;
  inputDisabled: boolean;
  userSubject: SubjectInterface;
  disputes: Dispute[];
  internalOnlyDefault: boolean = false;

  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));

  public metaData$: Observable<any>;
  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any[]> = this.store.pipe(select(disputesTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(disputesTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(disputesTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public selectedDispute$ = this.store.pipe(select(getSelectedDispute));
  public disputesCount$ = this.store.pipe(select(getDisputesCount));
  public disputeCount: number;

  selectedDispute: Dispute | null = null;
  selectedDisputeId: number | null = null;
  disputeTypes$ = this.store.pipe(select(getDisputeTypes));
  disputeAssignments$: Observable<any>;

  constructor(
    public store: Store,
    public oes: OrderEditService,
    private disputeService: DisputesService,
    private route: ActivatedRoute,
    private subjectService: SubjectService,
    private securityUtils : SecurityUtilService,
    private companyConfigService: CompanyConfigPropertyService
  ) { }

  ngOnInit(): void {
    this.companyConfigService.getValue('DISPUTE_NOTE_INTERNAL_ONLY_DEFAULT').subscribe((res) => {
      if (res && res.value) {
        this.internalOnlyDefault = JSON.parse(res.value.toLowerCase());
        if (this.selectedDispute && !this.selectedDispute.id) {
          this.selectedDispute.initialNoteInternalOnly = this.internalOnlyDefault;
        }
      }
    });
    this.store.dispatch(loadUserStatuses());
    this.selectedLocationOrService$.subscribe((service: Service | Location | null) => {
      if (service instanceof Service) {
        this.serviceId = service.id;
        this.summaryBill = service.summaryBill;
        this.companyId = this.oes.order!.company.id;
        this.route.queryParams.subscribe(params => {
          this.selectedDisputeId = params['disputeId'];
          this.disputeService.findByServiceId(this.serviceId).subscribe((res: Dispute[]) => {
            this.disputes = plainToInstance(Dispute, res);
            if (this.disputes.length > 0) {
              if (this.selectedDisputeId) {
                this.selectedDispute = this.disputes.find(d => d.id == this.selectedDisputeId) || null;
              } else {
                this.selectedDispute = null;
              }
            }
          });
        });
      }
    });
    this.store.dispatch(actions.updateFilters({ key: 'serviceId', value: this.serviceId }));
    this.store.dispatch(actions.loadDisputeTypes({ companyId: this.companyId }));
    this.store.dispatch(actions.loadMetaData());
    this.metaData$ = this.store.pipe(select(getMetaData));
    this.store.dispatch(actions.loadDisputeAssignments());
    this.disputeAssignments$ = this.store.pipe(select(getDisputeAssignments));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(actions.updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.selectedDisputeId = null;
    this.selectedDispute = null;
    this.store.dispatch(actions.updateFilters({ key, value}));
  }

  onDisputeDblClicked(dispute: Dispute) {
    this.selectedDispute = plainToInstance(Dispute, dispute);
    this.selectedDisputeId = this.selectedDispute.id;
    this.selectedLocationOrService$.subscribe((sOrL: Service | Location | null) => {
          if (sOrL instanceof Location && this.selectedDispute) {
            let parentService = sOrL?.services.find(s => s.id === dispute?.serviceId);
            if (parentService) {
              this.summaryBill = parentService.summaryBill;
            }
          } else if (sOrL instanceof Service && this.selectedDispute) {
            this.summaryBill = sOrL?.summaryBill;
          }
        });
    this.setInputDisabled();
  }

  onAddClicked() {
    this.selectedDispute = new Dispute();
    this.selectedDisputeId = -1;
    this.selectedDispute.serviceId = this.serviceId;
    this.selectedDispute.openDate = new Date();
    this.setInputDisabled();
    this.selectedDispute.disputeAssignment = this.securityUtils.getLoggedInUser()?.name || '';
    this.selectedDispute.initialNoteInternalOnly = this.internalOnlyDefault;
  }

  onMilestoneSave() {
    if (!this.selectedDispute!.id) {
      return;
    }
    
    this.store.dispatch(actions.saveDisputeMilestone({ dispute: this.selectedDispute }));
    
    this.selectedDispute$.subscribe((dispute: Dispute | null) => {
      this.selectedDispute = plainToInstance(Dispute, dispute);
      this.setInputDisabled();
    });
  }

  save() {
    this.store.dispatch(actions.saveDispute({ dispute: this.selectedDispute }));
    this.store.dispatch(reloadOrder());
    this.disputesCount$.subscribe((res) => {
      this.disputeCount = res;
    })
    this.store.dispatch(setActiveTab({
      'tab': 'Disputes (' + this.disputeCount + ')',
      host: 'service'
    }));
    this.selectedDisputeId = null;
    this.selectedDispute = null;
  }

  onCancel() {
    this.selectedDisputeId = null;
    this.selectedDispute = null;
    
    this.store.dispatch(actions.loadMetaData());
  }

  setInputDisabled(): void {
    this.inputDisabled = this.oes.isReadOnly || this.editDisabled || (this.selectedDispute && this.selectedDispute.disputeStatus === 'Dispute Closed' || false);
  }

}
