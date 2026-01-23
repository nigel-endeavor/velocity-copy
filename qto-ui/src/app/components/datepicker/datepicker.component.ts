import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatDatepicker } from '@angular/material/datepicker';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.scss'],
})
export class DatepickerComponent implements OnInit {

  @Input() date: Date | null | undefined;
  @Output() dateChange = new EventEmitter<Date | null>();

  @Input() name: string;
  @Input() disabled: boolean = false;
  @Input() required: boolean = false;
  @Input() max = '9999-12-31';
  @Input() min = '1000-01-01';

  //this property should only be used by the app-datetimepicker component
  //it only exists to support the getDisplayDate() method
  @Input() hasTime = false;

  @ViewChild('picker') picker: MatDatepicker<Date>;

  dirty: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['max']) {
      this.max = changes['max']?.currentValue;
    }
    if (changes['min'] || changes['max']) {
      this.min = changes['min']?.currentValue;
    }
  }

  onDateSelected(event: any): void {
    if (event) {
      const today = new Date(event);
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
      this.dirty = true;
      this.date = today;
      this.dateChange.emit(new Date(dateString));
    } else {
      this.date = undefined;
      this.dateChange.emit(undefined);
    }
  }

  onClear() {
    this.date = null;
    this.dateChange.emit(null);
  }

  //hack to fix an off-by-one error in the angular material datepicker
  getDisplayDate(): string {
    if (!this.date) {
      return '';
    }
    let d = new Date(this.date);
    if (!this.hasTime) {
      d.setMinutes(d.getMinutes() + d.getTimezoneOffset());
    }
    return d.toISOString();
  }

  openPicker(): void {
    this.picker.open();
  }
}
