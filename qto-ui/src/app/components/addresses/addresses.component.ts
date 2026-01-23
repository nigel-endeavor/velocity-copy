import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { AddressSearchCriteria } from 'src/app/models/address-search-criteria.model';
import { Address } from 'src/app/models/address.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { AddressService } from 'src/app/services/address.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ValidatorsModule } from "../../directives/validators.module";
import { Subject, debounceTime } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-addresses',
  templateUrl: './addresses.component.html',
  styleUrls: ['./addresses.component.scss'],
  imports: [
    FormsModule,
    CommonModule,
    ValidatorsModule
  ]
})
export class AddressesComponent implements OnInit, AfterViewInit {

  @Input() companyId: number;
  @Input() isLocation: boolean;
  @Output() addressSelected = new EventEmitter<Address>();
  @Output() addressSelectCancelled = new EventEmitter<void>();

  @ViewChild('dialog') dialog: ElementRef;

  addresses: Address[];
  selectedAddress: Address;
  searchText: string;
  searchSubject: Subject<string> = new Subject<string>();

  countryOpts: string[];
  stateOpts: LookupValue[];

  canadianProvinces = ['NL', 'PE', 'NS', 'NB', 'QC', 'ON', 'MB', 'SK', 'AB', 'BC', 'YT', 'NT', 'NU'];

  constructor(
    private addressService: AddressService, 
    private lookupValueService: LookupValueService
  ) { }

  ngOnInit(): void {
    let criteria = new AddressSearchCriteria();
    criteria.companyId = this.companyId;
    criteria.isLocation = this.isLocation;
    this.addressService.search(criteria).subscribe((res: PaginatedResult<Address>) => {
      this.addresses = res.collection;
    });
    this.lookupValueService.getValues('COUNTRY', this.companyId).subscribe((values: string[]) => { this.countryOpts = values });
    this.lookupValueService.find('STATE_PROVINCE', this.companyId).subscribe((values: LookupValue[]) => { this.stateOpts = values });

    this.searchSubject.pipe(debounceTime(500)).subscribe((searchText: string) => {
      let criteria = new AddressSearchCriteria();
      criteria.companyId = this.companyId;
      criteria.search = searchText;
      criteria.isLocation = this.isLocation;
      this.addressService.search(criteria).subscribe((res: PaginatedResult<Address>) => {
        this.addresses = res.collection;
      });
    });
  }

  ngAfterViewInit(): void {
    // Prevent the dialog from closing when the user presses the escape key
    this.dialog.nativeElement.addEventListener('cancel', (event: any) => {
      event.preventDefault();
    });
    // Open the dialog
    this.dialog.nativeElement.showModal();
  }

  isSingleClick: boolean = false;
  prevSelectedRowElement: HTMLTableRowElement;
  onRowClicked(rowElement: HTMLTableRowElement, address: Address): void {
    this.isSingleClick = true;
    setTimeout(() => {
      if (this.isSingleClick) {
        // this.showForm = false;
        this.selectRow(rowElement, address);
      }
    }, 200);
  }

  onRowDblClicked(rowElement: HTMLTableRowElement, address: Address): void {
    this.selectRow(rowElement, address);
    this.onSelectClicked();
  }

  selectRow(rowElement: HTMLTableRowElement, address: Address): void {
    this.selectedAddress = address;
    rowElement.style.backgroundColor = '#5DCADE';
    rowElement.style.color = '#F8F8F8';
    if (this.prevSelectedRowElement) {
      this.prevSelectedRowElement.style.backgroundColor = '';
      this.prevSelectedRowElement.style.color = '';
    }
    this.prevSelectedRowElement = rowElement;
  }

  onSelectClicked(): void {
    this.addressSelected.emit(this.selectedAddress);
    this.dialog.nativeElement.close();
  }

  onCancelClicked(): void {
    this.addressSelectCancelled.emit();
    this.dialog.nativeElement.close();
  }

  onSearchTextChanged(value: any): void {
    this.searchText = value;
    this.searchSubject.next(value);
  }
}
