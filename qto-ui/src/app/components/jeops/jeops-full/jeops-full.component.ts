import { Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { Jeop } from '../../../models/jeop.model';
import { JeopsTableComponent } from './jeops-table.component';
import { LookupValueService } from '../../../services/lookup-value.service';
import { SecurityUtilService } from '../../../services/security-util.service';
import { JeopService } from '../../../services/jeop.service';
import { SubjectService } from '../../../services/subject.service';
import { SubjectInterface } from '../../../models/subject.model';
import { FormsModule, NgForm } from '@angular/forms';
import { plainToClass } from 'class-transformer';
import { CommonModule, DatePipe } from '@angular/common';
import { DatepickerModule } from '../../datepicker/datepicker.module';
import { Store, select } from '@ngrx/store';
import { editEnabled, getIsInventory } from 'src/app/order-detail-page/ngrx/order-details.selectors';

@Component({
  standalone: true,
  selector: 'app-jeops-full',
  templateUrl: './jeops-full.component.html',
  styleUrls: ['./jeops-full.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    DatePipe,
    JeopsTableComponent,
    DatepickerModule
  ]
})
export class JeopsFullComponent implements OnInit {

  @Output() close = new EventEmitter<void>();
  @Input() type: string = ' ';
  @Input() parentId: number;
  @Input() orderId: number;
  @Input() companyId: number;

  public isInventory$ = this.store.pipe(select(getIsInventory));
  public editEnabled$ = this.store.pipe(select(editEnabled));
  @ViewChild('table') table: JeopsTableComponent;
  @ViewChild('jeopForm') jeopForm: NgForm;

  showContent: boolean;
  maxChars: number = 200;

  title: string;
  jeop: Jeop;
  today: any;

  descriptionOptions: string[];
  responsibilityOptions: string[];
  assignedToOptions: string[];

  constructor(public securityUtils: SecurityUtilService,
              private lookupValueService: LookupValueService,
              private subjectService: SubjectService,
              private jeopService: JeopService,
              private store: Store) {}

  ngOnInit(): void {
    this.onTypeChanged();
    this.createNewJeop();
    this.lookupValueService.getValues(this.type + '_jeopardy', this.companyId).subscribe((values: string[]) => { this.descriptionOptions = values; });
    this.lookupValueService.getValues('jeopardy_responsibility', this.companyId).subscribe((values: string[]) => { this.responsibilityOptions = values; });
    this.subjectService.getSubjects(this.orderId).subscribe((subjects: SubjectInterface[]) => {
      this.assignedToOptions = subjects.map(subject => subject.displayName);
    });
    this.today = this.getToday();
  }

  createNewJeop() {
    this.jeop = new Jeop();
    this.jeop[this.type + 'Id'] = this.parentId;
    // this.store.dispatch(loadUserStatuses());
    let originator = this.securityUtils.getLoggedInUser();
    this.jeop.originator = originator.name ? originator.name : originator.username;
    if (this.jeopForm) {
      this.jeopForm.resetForm();
    }
  }

// run this when component inputs change
  ngOnChanges(changes:SimpleChanges): void {
    if (changes['parentId'] || changes['type']) {
      this.onTypeChanged();
    }
  }

  private onTypeChanged() {
    this.title = this.type.charAt(0).toUpperCase() + this.type.slice(1) + ' Jeopardies';
    if (this.table) {
      this.table.getModelService().setType(this.type);
      this.table.searchCriteria[this.type + 'Id'] = this.parentId;
      this.table.fetch();
    }
  }

  openDialog() {
    this.showContent = true;
  }

  closeDialog() {
    this.createNewJeop();
    this.showContent = false;
    this.close.emit();
  }

  save() {
    if (this.jeop.level != this.type) {
      if (this.jeop.level == 'order') {
        throw Error('This is an Order level Jeop that affects multiple locations and services.  Please navigate to the order level to edit or close this Jeop.');
      }
      if (this.jeop.level == 'location') {
        throw Error('This is a Location level Jeop that affects other services.  Please navigate to the location level to edit or close this Jeop.');
      }
    }
    if (this.jeop.startDate && this.jeop.endDate && this.jeop.startDate > this.jeop.endDate) {
      throw Error('Jeop cannot end before it starts');
    }
    if (!this.jeop.startDate || !this.jeop.description || !this.jeop.responsibility) {
      throw Error('Jeop must have a start date, description, and a responsibility.');
    }
    this.jeopService.save(this.jeop)
      .subscribe((res: Jeop) => {
        this.table.fetch();
        this.createNewJeop();
      });
  }

  onJeopClicked(jeop: Jeop) {
    this.jeop = plainToClass(Jeop, structuredClone(jeop));
    this.jeop[this.type + 'Id'] = this.parentId;
  }

  //gets todays date as a string, used for setting the max property on date input
  getToday(): string {
    const today = new Date();
    let dd: any = today.getDate();
    let mm: any = today.getMonth() + 1;
    let yyyy = today.getFullYear();
    if (dd < 10) {
      dd = '0' + dd;
    }
    if (mm < 10) {
      mm = '0' + mm;
    }
    let dateString = yyyy + '-' + mm + '-' + dd + 'T00:00:00.000';
    return dateString;
  }
}
