import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-v-toggle',
  templateUrl: './toggle.component.html',
  styleUrls: ['./toggle.component.scss'],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class ToggleComponent {

  @Input() active: boolean | null;
  @Input() disabled: boolean;
  @Output() activeChange = new EventEmitter<boolean>();

  constructor(
  ) {}

  statusChanged() {
    if (!this.disabled) {
      this.activeChange.emit(!this.active);
    }
  }
}
