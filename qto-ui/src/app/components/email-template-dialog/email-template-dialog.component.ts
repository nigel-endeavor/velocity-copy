import { Component, Inject, OnInit } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { Store, select } from '@ngrx/store';
import { ActivationAttemptEmailView } from '../../models/activation-attempt-email-view.model';
import { EmailTemplate } from '../../models/email-template.model';
import { ActivationAttemptEmailViewService } from '../../services/activation-attempt-email-view.service';
import { Permissions, SecurityUtilService } from '../../services/security-util.service';
import { render } from 'velocityjs';
import * as actions from './store/email-template-dialog.actions';
import { getSelectedTemplate, getTemplateList } from './store/email-template-dialog.selectors';
import { map } from 'rxjs';
import { editEnabled, getIsReadOnly } from 'src/app/order-detail-page/ngrx/order-details.selectors';

@Component({
  selector: 'app-email-template-dialog',
  templateUrl: './email-template-dialog.component.html',
  styleUrls: ['./email-template-dialog.component.scss']
})
export class EmailTemplateDialogComponent implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public currentActivationAttemptEmailView: ActivationAttemptEmailView;
  public previewEmailSubject: string | undefined;
  public previewEmailBody: string | undefined;
  public encodedEmailSubject: string | undefined;
  public encodedEmailBody: string | undefined;
  public showTemplateManager: boolean;

  public emailTemplates$ = this.store.pipe(select(getTemplateList));
  public selectedTemplate$ = this.store.pipe(select(getSelectedTemplate),
    map((template: EmailTemplate | undefined) => {
      if (template !== undefined) {
        if (template.subject) {
          this.previewEmailSubject = render(template.subject, this.currentActivationAttemptEmailView);
          this.encodedEmailSubject = encodeURIComponent(this.previewEmailSubject);
        } else {
          this.previewEmailSubject = undefined;
          this.encodedEmailSubject = undefined;
        }
        if (template.body) {
          const clearBody: any = {...this.currentActivationAttemptEmailView};
          Object.keys(clearBody).forEach( (key: string) => {
            if (clearBody[key] === null) {
              clearBody[key] = '"    "'
            }
          })
          this.previewEmailBody = render(template.body, clearBody);
          this.encodedEmailBody = encodeURIComponent(this.previewEmailBody);
        } else {
          this.previewEmailBody = undefined;
          this.encodedEmailBody = undefined;
        }
      } else {
        this.previewEmailSubject = undefined;
        this.previewEmailBody = undefined;
        this.encodedEmailSubject = undefined;
        this.encodedEmailBody = undefined;
      }
      return template;
    }));

  constructor(private dialogRef: MatDialogRef<EmailTemplateDialogComponent>,
    private store: Store,
    private securityUtils: SecurityUtilService,
    private activationAttemptEmailViewService: ActivationAttemptEmailViewService,
    @Inject(MAT_DIALOG_DATA) public data: {
      id: number,
      level: string
    }) { }

  ngOnInit(): void {
    this.activationAttemptEmailViewService.retrieve(this.data.id).subscribe((res: ActivationAttemptEmailView) => {
      this.currentActivationAttemptEmailView = res;
      // following code is to format voip complete and network complete date to MM/DD/YYYY
      let newVoipDate = new Date(res.voipCompleteDate)
      const voipFormattedDate = new Intl.DateTimeFormat('en-US', {month: 'numeric', day: 'numeric', year: 'numeric' }).format(newVoipDate);
      this.currentActivationAttemptEmailView.voipCompleteDate = voipFormattedDate;
      let newNetworkDate = new Date(res.networkCompleteDate)
      const networkFormattedDate = new Intl.DateTimeFormat('en-US', {month: 'numeric', day: 'numeric', year: 'numeric' }).format(newNetworkDate);
      this.currentActivationAttemptEmailView.networkCompleteDate = networkFormattedDate;
    });
    this.store.dispatch(actions.loadEmailTemplateList({ level: this.data.level, id: this.data.id }));
  }

  public loadEmailTemplates(): void {
    this.store.dispatch(actions.loadEmailTemplateList({ level: this.data.level, id: this.data.id }));
  }

  onTemplateSelectionChange(event?: any) {
    this.store.dispatch(actions.setSelectedTemplate({ template: event.value }));
  }

  onClose() {
    this.store.dispatch(actions.clearState());
    this.dialogRef.close();
  }

  openTemplateManager() {
    this.showTemplateManager = true;
  }

  closeTemplateManager() {
    this.previewEmailSubject = undefined;
    this.previewEmailBody = undefined;
    this.encodedEmailSubject = undefined;
    this.encodedEmailBody = undefined;
    this.showTemplateManager = false;
  }

  saveTemplate(template: EmailTemplate): void {
    this.store.dispatch(actions.saveTemplate({ template: template, level: this.data.level, id: this.data.id }));
    this.emailTemplates$.subscribe((templates: EmailTemplate[]) => {
      const initiallySavedTemplate = templates.find(t => t.name === template.name);
      this.store.dispatch(actions.setSelectedTemplate({ template: initiallySavedTemplate }));
      this.showTemplateManager = false;
    });
  }

  deleteTemplate(template: EmailTemplate): void {
    this.store.dispatch(actions.deleteTemplate({ template: template, level: this.data.level, id: this.data.id }));
    this.emailTemplates$.subscribe(() => {
      this.store.dispatch(actions.setSelectedTemplate({ template: undefined }));
      this.showTemplateManager = false;
    });
  }
}
