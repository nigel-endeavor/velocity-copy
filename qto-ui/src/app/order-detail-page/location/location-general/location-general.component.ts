import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { plainToClass } from 'class-transformer';
import { Address } from '../../../models/address.model';
import { FileAttachmentType } from '../../../models/file-attachment';
import { Location } from '../../../models/location.model';
import { RequirementTemplate } from '../../../models/requirement-template.model';
import { LookupValueService } from '../../../services/lookup-value.service';
import { RequirementTemplateService } from '../../../services/requirement-template.service';
import { OrderEditService } from '../../order-edit.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { TerminalOrderStatuses } from '../../../models/constants/terminal-order-statuses';
import { LevelOfEffortService } from '../../../services/level-of-effort.service';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { select, Store } from '@ngrx/store';
import { editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly, getSelectedLocationOrService } from '../../ngrx/order-details.selectors';
import { deleteLocation, saveLocation, saveOrder, toggleEdit } from '../../ngrx/order-details.actions';
import { filter, map, take } from 'rxjs';
import { MatLegacyDialog as MatDialog, } from '@angular/material/legacy-dialog';
import { CostHistoryComponent } from 'src/app/components/cost-history/cost-history.component';
import { ComponentType } from '@angular/cdk/portal';
import { LinkLocationComponent } from 'src/app/components/link-location/link-location.component';
import { CANADIAN_PROVINCES, addressToString } from 'src/app/utilities';
import { LocationContact } from 'src/app/models/location-contact.model';
import { ContactType } from 'src/app/models/contact.model';
import { Router } from "@angular/router";
import { TerminalServiceStatuses } from "../../../models/constants/terminal-service-statuses";

@Component({
  selector: 'app-location-general',
  templateUrl: './location-general.component.html',
  styleUrls: ['location-general.component.scss', '../../form-styles.scss']
})
export class LocationGeneralComponent implements OnInit, OnDestroy {

  public isInventory$ = this.store.pipe(select(getIsInventory), map(item => {
    this.isInventory = item;
    return item
  }));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public selectedLocationOrService$ =
    this.store.pipe(select(getSelectedLocationOrService)).pipe(
      filter(selected => !!selected),
      map(selectedLocationOrService => {
        this.location = plainToClass(Location, selectedLocationOrService as Location);
        if (!this.location.lcon) {
          this.location.lcon = new LocationContact(null, ContactType.LCON, this.location.id);
        }
        return selectedLocationOrService as Location;
      })
    );
  location: Location;
  companyId: number;
  parentCompanyId: number;
  public isInventory: boolean;
  showForm: boolean = false;

  @ViewChild('locationForm') locationForm: NgForm;

  readonly fileAttachmentType = FileAttachmentType.LOCATION;

  editingAddress: boolean = false;
  requirementTemplates: RequirementTemplate[];
  timeZoneOpts: string[];
  canadianProvinces = CANADIAN_PROVINCES;
  countryOpts: string[];
  stateOpts: LookupValue[];
  levelOfEffortOpts: string[];
  terminalOrderStatuses: string[] = Object.values(TerminalOrderStatuses);

  public editingLocInfo = false;
  public editingLocRequirements = false;

  // tooltips
  locationStatusTip = 'i90 Status that is determined by order progression of location milestones.';
  clientLocationIdTip = 'Your Custom ID for this location record.';
  quoteLocationIdTip = 'Unique Id from the Provider when this location was quoted in pre-order stage.';
  nameTip = 'Custom, user friendly name for the location.';
  locationPhoneNumberTip = 'Main Phone Number for the location.';
  addressTip = 'Physical address.';
  timeZoneTip = 'Time zone the location recognizes.';
  lconNameTip = 'Local Contact name.';
  lconEmailTip = 'Local Contact email.';
  lconPhoneTip = 'Local Contact Phone Number.';
  levelOfEffortTip = 'The Difficulty, or Complexity, of this locations total service delivery consideration, as assessed by your organization.';
  locationInfoTip = 'Custom grouping identifier for this location.';
  locationTypeTip = 'Secondary Custom grouping identifier for this location.';
  activeTip = 'Indicates if this as an active location for your organization.';
  sourceTip = 'Displays how this record was created ( Manual, Import, API, etc.).';
  requirementsTemplateTip = 'Sets the testing checklist when performing activations.';
  mrcTip = 'Aggregate Monthly Recurring Cost of all services under this location.';
  nrcTip = 'Aggregate Non-Recurring Cost of all services under this location.';
  mrrTip = 'Aggregate Monthly Recurring Revenue of all services under this location.';
  nrrTip = 'Aggregate Non-Recurring Revenue of all services under this location.';
  annualRecurringCostTip = 'Aggregate Annual Recurring Cost of all services under this location.';
  icbUnitCostTip = 'Aggregate Individual Case Basis cost of all services under this location.';
  ospConstructionCostTip = 'Aggregate Outside Plant Construction Cost of all services under this location.';
  descriptionTip = 'Notes or context that about this location you wish to permanently display.';
  deleteLocationTip = 'This will delete this location record.';

  constructor(
    public router: Router,
    public oes: OrderEditService,
    private requirementTemplateService: RequirementTemplateService,
    private lookupValueService: LookupValueService,
    private store: Store,
    private levelOfEffortService: LevelOfEffortService,
    public costHistoryDialog: MatDialog,
    public linkToParentDialog: MatDialog
  ) {
    this.store.pipe(select(getFlagByName('editingLocInfo'))).subscribe(res => {
      this.editingLocInfo = res;
    });
    this.store.pipe(select(getFlagByName('editingLocRequirements'))).subscribe(res => {
      this.editingLocRequirements = res;
    });
  }

  ngOnInit(): void {
    setTimeout(() => {
      // This is a hack to prevent the screen from 'flickering' when general tab is navigated to
      this.showForm = true;
    }, 0);
    this.companyId = this.oes.order!.company.id;
    this.parentCompanyId = this.oes.order!.company?.parentCompany?.id;
    this.isInventory$.subscribe()

    setTimeout(() => {
      this.oes.addForm(this.locationForm);
    }, 1);
    this.requirementTemplateService.getTemplates(this.companyId, null).subscribe((res: RequirementTemplate[]) => {
      this.requirementTemplates = res;
    });
    this.levelOfEffortService.findByCompanyId(this.companyId).subscribe((res: LevelOfEffort[]) => {
      this.levelOfEffortOpts = res.map((loe: LevelOfEffort) => loe.levelOfEffort);
    });
    this.lookupValueService.getValues('NA_TIME_ZONE', this.companyId).subscribe((res: string[]) => {
      this.timeZoneOpts = res;
    });
    this.lookupValueService.find('STATE_PROVINCE', this.companyId).subscribe((values: LookupValue[]) => { this.stateOpts = values });
    this.lookupValueService.getValues('COUNTRY', this.companyId).subscribe((values: string[]) => { this.countryOpts = values });
  }

  ngOnDestroy(): void {
    this.oes.removeForm(this.locationForm);
  }

  onSaveClicked(): void {
    if (this.locationForm.invalid) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    // @ts-ignore
    if ([...this.oes.forms.values()].some(form => form.invalid)) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    if (!this.oes.order!.company) {
      alert('Please select an End Customer for this order.');
      return;
    }
    // @ts-ignore
    this.oes.order!.locations = this.oes.order!.locations.map(item => {
      if ((item.id === this.location.id) || (item.id === undefined)) {
        return this.location
      }
      return item
    });
    if (this.isInventory) {
      this.location.currentInventory = true;
      this.location.recordSource = 'Manual Entry';
    }
    this.store.dispatch(saveLocation({ location: this.location }));
  }

  onAddressClicked(): void {
    if (!this.editingLocInfo) {
      return;
    }
    this.editingAddress = true;
  }

  onAddressSelected(address: Address): void {
    this.location.clientLocationId = address.clientLocationId;
    this.location.address1 = address.address1;
    this.location.address2 = address.address2;
    this.location.city = address.city;
    this.location.state = address.state;
    this.location.postalCode = address.postalCode;
    this.location.country = address.country;
    this.editingAddress = false;
  }

  onAddressSelectCancelled(): void {
    this.editingAddress = false;
  }

  isNew(): boolean {
    return this.location.id ? false : true;
  }

  onStateChanged(value: string): void {
    if (this.canadianProvinces.includes(value)) {
      this.location.country = 'Canada';
    } else {
      this.location.country = 'US';
    }
    this.location.state = value;
  }

  onEditRequirementsClicked() {
    this.store.dispatch(toggleEdit({ key: 'editingRequirementsTemplate' }));
  }

  onCostHistoryClicked(locationId: number): void {
    const component: ComponentType<CostHistoryComponent> = CostHistoryComponent;
    const dialogRef = this.costHistoryDialog.open(component, {
      data: {
        id: locationId,
        type: 'location'
      },
      width: '1500px'
    });
  }

  onLinkToParentClicked(): void {
    const component: ComponentType<LinkLocationComponent> = LinkLocationComponent;
    const dialogRef = this.linkToParentDialog.open(component, {
      data: {
        companyId: this.parentCompanyId,
        locationId: this.location.id
      },
      width: '1500px',
      panelClass: 'modal-dialog'
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      if (result) {
        this.location.parentLocationId = result;
        this.onSaveClicked();
      }
    });
  }

  locationAddressToString(): string {
    return addressToString(this.location.address1, this.location.address2, this.location.city, this.location.state, this.location.postalCode, this.location.country);
  }

  onDeleteLocation(id: number) {

    if (this.location.inventoryLocationId !== null) {
      alert('This location is in inventory. Please delete the inventory location first.');
      return;
    }

    if (!this.location.currentInventory) {
      const allCancelled = this.location.services.every(s =>
        s.status === TerminalServiceStatuses.CANCELLED || s.status === TerminalServiceStatuses.CHANGE_IN_ASSIGNMENT
      );
      if (!allCancelled) {
        alert('You cannot delete a location with services that are not Cancelled.');
        return;
      }
    }

    if (confirm("Are you sure you want to delete this Location? Click OK to Continue.")) {
      this.store.dispatch(deleteLocation({location: this.location, order: this.oes.order!}));
    }
  }
}
