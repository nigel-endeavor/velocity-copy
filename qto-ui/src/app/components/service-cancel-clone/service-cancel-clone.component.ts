import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Service } from 'src/app/models/service.model';
import { CancelCloneDialogComponent } from "./cancel-clone-dialog/cancel-clone-dialog.component";
import { CommonModule } from '@angular/common';
import { TerminalServiceStatuses } from '../../models/constants/terminal-service-statuses';

@Component({
  standalone: true,
  selector: 'app-service-cancel-clone',
  templateUrl: './service-cancel-clone.component.html',
  styleUrls: ['../../order-detail-page/form-styles.scss'],
  imports: [
    CancelCloneDialogComponent,
    CommonModule
  ]
})
export class ServiceCancelCloneComponent implements OnInit {
  showCloneAndCancelDialog: boolean = false;
  @Input() tooltip: String;
  @Input() service: Service;
  @Input() disabled: boolean;
  @ViewChild('dialogForm') dialogForm: NgForm;

  cloneAndCancelTitleText = 'This action allows you to cancel this existing service order, and open a new one.';

  constructor() { }

  ngOnInit(): void {
  }
  openCloneAndCancelDialog(): void {
    this.showCloneAndCancelDialog = true;
  }
  onDone(): void {
    this.showCloneAndCancelDialog = false;
  }

  isServiceTerminal() {
    const terminalStatuses: string[] = [...Object.values(TerminalServiceStatuses)];
    return terminalStatuses.includes(this.service.status);
  }
}
