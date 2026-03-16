import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { ErrorDialogService } from './components/error-dialog/error-dialog.service';
import { NotificationService } from './services/notification.service';
import { SecurityUtilService } from './services/security-util.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatLegacyDialog, MatLegacyDialogModule, MatLegacyDialogRef } from '@angular/material/legacy-dialog';
import { ComponentType } from '@angular/cdk/portal';
import { CompanyConfigPropertyService } from './services/company-config-property.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'qto-ui';

  @ViewChild('errorDialog') errorDialogRef: ErrorDialogComponent;

  constructor(
    private errorDialogService: ErrorDialogService,
    private securityUtils: SecurityUtilService,
    private notificationService: NotificationService,
    private companyConfigService: CompanyConfigPropertyService,
    public dialog: MatLegacyDialog
  ) { }

  ngOnInit() {
    try {
      let token = this.securityUtils.getBearerToken();
      this.notificationService.connect(token);

      if (!this.securityUtils.about) {
        this.securityUtils.loadAppAbout();
        this.securityUtils.aboutSubject.subscribe(() => { this.checkForNewRelease(); });
      } else {
        this.checkForNewRelease();
      }
      this.companyConfigService.getValue('TELECOM_CLIENT').subscribe((res: { value: string; }) => {
        if (res && res.value) {
          localStorage.setItem('TELECOM_CLIENT', JSON.parse(res.value.toLowerCase()));
        }
      });
      this.companyConfigService.getValue('CYBER_SECURITY_CLIENT').subscribe((res: { value: string; }) => {
        if (res && res.value) {
          localStorage.setItem('CYBER_SECURITY_CLIENT', JSON.parse(res.value.toLowerCase()));
        }
      });
    } catch (error) {
      console.error('failed to initialize', error);
    }
  }

  ngAfterViewInit(): void {
    this.errorDialogService.setErrorDialogRef(this.errorDialogRef);
  }

  checkForNewRelease() {
    const userVersion = localStorage.getItem('userVersion');
    const currentVersion = this.securityUtils.about.version;
    if ((userVersion !== currentVersion)) {
      console.log('New version detected');
      localStorage.setItem('userVersion', currentVersion);
      const component: ComponentType<NewReleaseDialog> = NewReleaseDialog;
      const dialogRef = this.dialog.open(component, {
        panelClass: 'modal-dialog'
      });
    }
  }
}

@Component({
  selector: 'app-new-release-dialog',
  templateUrl: './new-release-dialog.html',
  styleUrls: ['./app.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, MatLegacyDialogModule]
})
export class NewReleaseDialog {
  constructor(
    public dialogRef: MatLegacyDialogRef<NewReleaseDialog>,
    public securityUtils: SecurityUtilService
  ) {}

  onReleaseNotesClicked(): void {
    let url = this.securityUtils.about.releaseNoteUrl;
    if (url) {
      window.open(url);
    }
  }
}
