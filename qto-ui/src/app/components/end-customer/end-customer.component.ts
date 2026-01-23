import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Company } from '../../models/company.model';
import { FormsModule, NgForm } from '@angular/forms';
import { ValidatorsModule } from '../../directives/validators.module';
import { CompanyType } from 'src/app/models/constants/company-type';

@Component({
  standalone: true,
  selector: 'app-end-customer',
  templateUrl: './end-customer.component.html',
  styleUrls: ['./end-customer.component.scss'],
  imports: [
    FormsModule,
    ValidatorsModule
  ]
})
export class EndCustomerComponent implements OnInit {

  @Input() company: Company;
  @Input() companyType: CompanyType;
  @Output() companySave = new EventEmitter<Company>();
  @Output() companyCancel = new EventEmitter<void>();

  @ViewChild('customerDialog') dialog: ElementRef;
  @ViewChild('customerForm') customerForm: NgForm;

  constructor() {
  }

  ngOnInit(): void {
    if (!this.company) {
      this.company = new Company();
      this.company.type = this.companyType;
    }
  }

  ngAfterViewInit(): void {
    this.dialog.nativeElement.showModal();
  }

  saveCustomer(): void {
    if (this.customerForm.invalid) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    if (this.company.name == null) {
      throw Error('Company Name must be filled in');
    }
    if ((this.company.address1 != null || this.company.city != null
        || this.company.state != null || this.company.postalCode != null)
      && (this.company.address1 == null || this.company.city == null
        || this.company.state == null || this.company.postalCode == null)) {
      throw Error('The selected address must have the following fields populated: address1, city, state, postal code');
    }
    this.companySave.emit(this.company);
    this.dialog.nativeElement.close();
  }

  cancelDialog(): void {
    this.companyCancel.emit();
    this.dialog.nativeElement.close();
  }
}
