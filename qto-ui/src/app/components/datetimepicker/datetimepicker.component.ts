import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatDatepicker } from '@angular/material/datepicker';

@Component({
  selector: 'app-datetimepicker',
  templateUrl: './datetimepicker.component.html',
  styleUrls: ['./datetimepicker.component.scss']
})
export class DatetimepickerComponent implements OnInit {

  @Input() date: Date | null;
  @Output() dateChange = new EventEmitter<Date | null>();

  @Input() name: string;
  @Input() disabled: boolean = false;
  @Input() required: boolean = false;
  @Input() max = '9999-12-31';
  @Input() min = '1000-01-01';

  @ViewChild('picker') picker: MatDatepicker<Date>;

  dirty: boolean = false;

  hour: string | null = null;
  minute: string | null = null;
  ampm: string | null = null;

  hourOpts = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
  minOpts = ['00', '15', '30', '45'];
  amPmOpts = ['AM', 'PM'];

  constructor() { }

  ngOnInit(): void {
    if (this.date) {
      this.date = new Date(this.date);
      if (this.date.getHours() > 12) {
        this.ampm = 'PM';
        this.hour = (this.date.getHours() - 12).toString();
      } else {
        this.ampm = 'AM';
        this.hour = this.date.getHours().toString();
      }
      let minute = this.date.getMinutes().toString();
      if (minute.length == 1) {
        minute = '0' + minute;
      }
      this.minute = minute;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['date'] && !changes['date'].currentValue) {
      this.hour = null;
      this.minute = null;
      this.ampm = null;
    }
    if (changes['max']) {
      this.max = changes['max']?.currentValue;
    }
    if (changes['min'] || changes['max']) {
      this.min = changes['min']?.currentValue;
    }
  }

  onDateSelected(event: any): void {
    this.date = event;
    this.setTime();
  }

  onHourChanged(event: any): void {
    this.hour = event;
    this.setTime();
  }

  onMinuteChanged(event: any): void {

    this.minute = event;
    this.setTime();
  }

  onAmpmChanged(event: any): void {
    this.ampm = event;
    this.setTime();
  }

  setTime() {
    if (this.date) {
      //if date is a string, convert it to a date
      if (typeof this.date === 'string') {
        this.date = new Date(this.date);
      }
      if (this.hour) {
        let hour = this.hour
        if (this.hour.length == 1) {
          hour = '0' + hour;
        }
        if (this.ampm == 'PM' && this.hour != '12') {
          this.date.setHours(parseInt(hour) + 12);
        } else {
          this.date.setHours(parseInt(hour));
        }
      }
      if (this.minute) {
        let minute = this.minute;
        if (this.minute.length == 1) {
          minute = '0' + minute;
        }
        this.date.setMinutes(parseInt(minute));
      }
      this.dateChange.emit(this.date);
    }
  }

  requiredFieldsPopulated(): boolean {
    if (!this.required) {
      return true;
    }
    return !!this.date && !!this.hour && !!this.minute && !!this.ampm;
  }
}
