import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.scss']
})
export class ErrorDialogComponent implements OnInit {

  @ViewChild('dialog') dialog: ElementRef;

  message: string;
  status: number | undefined;
  timestamp: string;

  constructor(private changeDetectorRef: ChangeDetectorRef) { }

  ngOnInit(): void {

  }

  open(message: string, status: number | undefined): void {
    this.message = message;
    if (status != 400) {
      this.status = status;
    }
    this.timestamp = new Date().toLocaleString('en-US', { month: 'long', day: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', hour12: true });
    this.changeDetectorRef.detectChanges();
    if (!this.dialog.nativeElement.open) {
      this.dialog.nativeElement.showModal();
    }
    return this.dialog.nativeElement;
  }

  close(): void {
    this.dialog.nativeElement.close();
  }
}
