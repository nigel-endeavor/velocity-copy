import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SecurityUtilService } from '../services/security-util.service';
import { isDemoModeEnabled } from '../features/demo-mode/demo-store.selectors'
import { Store } from '@ngrx/store';
import { toggleDemoMode } from '../features/demo-mode/demo-store.actions';
import { environment } from 'src/environments/environment';
import { NotificationService } from '../services/notification.service';
import { ReportService } from "../services/report.service";
import { TOASTR_TOKEN, Toastr } from '../services/toastr.service';
import { Notification } from '../models/notifications.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatLegacyDialog, MatLegacyDialogModule, MatLegacyDialogRef } from '@angular/material/legacy-dialog';
import { ComponentType } from '@angular/cdk/portal';
import { HttpResponse } from "@angular/common/http";

@Component({
  selector: 'app-application-wrapper',
  templateUrl: './application-wrapper.component.html',
  styleUrls: ['./application-wrapper.component.scss']
})
export class ApplicationWrapperComponent implements OnInit {
  public isDemoModeOn$ = this.store.select(isDemoModeEnabled);
  public isProd = environment.production;

  showDismissedNotifications: boolean = false;

  constructor(
    private store: Store,
    public securityUtils: SecurityUtilService,
    public router: Router,
    public notificationService: NotificationService,
    public reportService: ReportService,
    @Inject(TOASTR_TOKEN) private toastr: Toastr,
    public aboutDialog: MatLegacyDialog,
    public reportDialog: MatLegacyDialog
  ) { }

  ngOnInit(): void {
    let self = this;
    this.toastr.options.onclick = function(event: any) {
      try {
        const notificationId = event.target.innerHTML.substring(event.target.innerHTML.lastIndexOf('notificationId=') + 15, event.target.innerHTML.lastIndexOf('</a>'));
        const notification = self.notificationService.notifications.find(n => n.id == notificationId);
        self.onNotificationClicked(event, notification!);
      } catch (e) {
        console.log(e);
      }
    }
    //load about.json if not already loaded
    if (!this.securityUtils.about) {
      this.securityUtils.loadAppAbout();
    }
  }

  logout() {
    // no-op: auth removed
  }

  onWorklistClicked(): void {
    if (localStorage.getItem('worklist')) {
      this.router.navigate(['/' + localStorage.getItem('worklist')]);
    } else {
      this.router.navigate(['/locations']);
    }
  }

  onDashboardsClicked() {
    this.router.navigate(['/reports']);
  }

  onInventoryClicked(): void {
    if (localStorage.getItem('inventory-worklist')) {
      this.router.navigate(['/inventory/' + localStorage.getItem('inventory-worklist')]);
    } else {
      this.router.navigate(['/inventory/locations']);
    }
  }

  onCustomersClicked(): void {
    if (localStorage.getItem('customers-worklist')) {
      this.router.navigate([`/customers/${localStorage.getItem('customers-worklist')}`]);
    } else {
      this.router.navigate([`/customers/masterCustomers`]);
    }
  }

  onAboutClicked(): void {
    const component: ComponentType<AboutDialog> = AboutDialog;
    const dialogRef = this.aboutDialog.open(component, {
      panelClass: 'modal-dialog'
    });
  }

  onResourceCenterClicked(): void {
    if (this.securityUtils.about?.resourceCenterUrl) {
      window.open(this.securityUtils.about.resourceCenterUrl);
    }
  }

  onHelpDeskClicked(): void {
    let helpDeskLink: string = window.location.protocol + '//' + window.location.host + '/help-desk';
    window.open(helpDeskLink, '_blank');
  }

  public toggleDemoMode(): void {
    this.store.dispatch(toggleDemoMode());
    window.location.reload();
  }

  onNotificationClicked(event: any, notification: Notification): void {
    event.stopPropagation();
    if (notification.linkTo) {
      let route;
      let queryParams = {};
      if (notification.linkTo.indexOf('?') != -1) {
        route = notification.linkTo.substring(0, notification.linkTo.indexOf('?'));
        queryParams = JSON.parse('{"' + decodeURI(notification.linkTo.substring(notification.linkTo.indexOf('?') + 1))?.replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g,'":"') + '"}') || '';
      } else {
        route = notification.linkTo;
      }
      this.router.navigate([route], { queryParams: queryParams }).then(() => {
        window.location.reload();
      });
    }
  }

  onNotificationDismissClicked(event: any, notification: Notification): void {
    event.stopPropagation();
    this.notificationService.dismissNotification(notification);
    this.notificationService.count--;
    this.notificationService.notifications.splice(this.notificationService.notifications.indexOf(notification), 1);
  }

  onShowDismissedNotificationsClicked(): void {
    this.showDismissedNotifications = !this.showDismissedNotifications;
    this.notificationService.poll(this.showDismissedNotifications);
  }

  onDismissAllNotificationsClicked(event: any): void {
    event?.stopPropagation();
    this.notificationService.dismissAllNotifications();
    this.showDismissedNotifications = false;
    this.notificationService.count = 0;
    this.notificationService.notifications = [];
  }

  onCreateReport(type: string): void {
    this.reportService.downloadReport(type).subscribe((res: HttpResponse<Blob>) => {
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(res.body!);
      a.href = url;
      a.download = res.headers.get('Content-Disposition')!.split('=')[1];
      document.body.append(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    }, (err) => {
      let reader = new FileReader();
      reader.onload = () => {
        throw Error(reader.result as string);
      };
      reader.readAsText(err.error);
    });

    const component: ComponentType<ReportDialog> = ReportDialog;
    const dialogRef = this.reportDialog.open(component, {
      panelClass: 'modal-dialog'
    });
  }
}


@Component({
  selector: 'app-about-dialog',
  templateUrl: './about-dialog.html',
  styleUrls: ['./application-wrapper.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, MatLegacyDialogModule]
})
export class AboutDialog {
  constructor(
    public dialogRef: MatLegacyDialogRef<AboutDialog>,
    public securityUtils: SecurityUtilService
  ) {}

  onReleaseNotesClicked(): void {
    let url = this.securityUtils.about.releaseNoteUrl;
    if (url) {
      window.open(url);
    }
  }
}

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.html',
  styleUrls: ['./application-wrapper.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, MatLegacyDialogModule]
})
export class ReportDialog {
  constructor(
    public dialogRef: MatLegacyDialogRef<ReportDialog>,
    public securityUtils: SecurityUtilService
  ) {}

}
