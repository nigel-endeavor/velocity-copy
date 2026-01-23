import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { ValidatorsModule } from '../../directives/validators.module';
import { CommonModule, DatePipe } from '@angular/common';
import { Contact, ContactType } from 'src/app/models/contact.model';
import { LookupValue } from 'src/app/models/lookup-value.model';

@Component({
  standalone: true,
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    ValidatorsModule,
    DatePipe
  ]
})
export class ContactComponent implements OnInit {
  @Input() contact: Contact;
  @Input() companyId: number;
  @Input() showRoleAndNoteFields = true;
  @Output() contactSaved = new EventEmitter<Contact>();
  @Output() contactDeleted = new EventEmitter<Contact>();
  @Output() contactCancelled = new EventEmitter();
  @ViewChild('contactForm') contactForm: NgForm;

  @ViewChild('dialog') dialog: ElementRef;

  roleOpts: string[];
  readonly ContactType = ContactType;

  constructor(private lookupValueService: LookupValueService) { }

  ngOnInit(): void {
    this.lookupValueService.find('CONTACT_ROLE', this.companyId).subscribe((values: LookupValue[]) => { this.roleOpts = values.map(v => v.display); });
  }

  ngAfterViewInit(): void {
    this.dialog.nativeElement.showModal();
  }

  save(): void {
    this.contactSaved.emit(this.contact);
  }
  onDeleteClicked(): void {
    this.contactForm.resetForm();
    this.contactDeleted.emit(this.contact);
  }
  onCancelClicked(): void {
    this.contactCancelled.emit();
  }

}
