import { Component, ElementRef, EventEmitter, Input, OnInit, Output, Renderer2, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { EmailTemplate } from '../../../models/email-template.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { TemplateVariable } from '../../../models/template-variable.model';
import { TemplateVariableService } from '../../../services/template-variable.service';
import { render } from 'velocityjs';
import { Store, select } from '@ngrx/store';
import * as actions from '../store/email-template-dialog.actions';
import { getSelectedTemplate, getTemplateList } from '../store/email-template-dialog.selectors';
import { updateTemplateState } from '../store/email-template-dialog.actions';
import { getBaseUrl } from '../../../utilities';

@Component({
  selector: 'app-email-template-management-form',
  templateUrl: './email-template-management-form.component.html',
  styleUrls: ['./email-template-management-form.component.scss']
})
export class EmailTemplateManagementFormComponent implements OnInit {
  // @Input() emailTemplates: Observable<EmailTemplate[]>;
  @Input() context: any;
  @Input() level: string;
  // @Input() inputTemplate: EmailTemplate | undefined | null;
  @Output() close = new EventEmitter();
  @Output() save = new EventEmitter();
  @Output() delete = new EventEmitter();
  @ViewChild('templateForm') templateForm: NgForm;
  @ViewChild('bodyInputTextArea') bodyInputTextArea!: ElementRef<HTMLTextAreaElement>;
  @ViewChild('bodyPreviewTextArea') bodyPreviewTextArea: ElementRef<HTMLTextAreaElement>;
  @ViewChild('nameInput') nameInput: ElementRef<HTMLInputElement>;
  @ViewChild('subjectInput') subjectInput!: ElementRef<HTMLTextAreaElement>;

  workingTemplate: EmailTemplate | undefined;
  previewEmailSubject: string;
  previewEmailBody: string;
  templateVariables: TemplateVariable[];
  bodyValue: string;

  emailTemplates$ = this.store.pipe(select(getTemplateList));
  selectedTemplate$ = this.store.pipe(select(getSelectedTemplate));

  constructor(
    private store: Store,
    private renderer: Renderer2,
    private templateVariableService: TemplateVariableService
    ) { }

  ngOnInit(): void {
    //retrieve available template variables
    this.templateVariableService.retrieveByType(this.level).subscribe((res: PaginatedResult<TemplateVariable>) => {
      this.templateVariables = res.collection;
      //prefix all paths with $
      this.templateVariables.forEach((variable: TemplateVariable) => {
        variable.path = `$${variable.path}`;
      });
      //add template variable for the url of this activation attempt
      this.templateVariables.push({
        id: -1,
        label: 'Activation URL',
        path: `${getBaseUrl()}order/$orderId/location/$locationId/service/$serviceId/activation?activationId=$id`,
        type: 'string',
        templateType: "ACTIVATION",
        version: 1
      });
      //alphabetize the template variables
      this.templateVariables.sort((a, b) => a.label.localeCompare(b.label));
    });
  }

  /**
   * When the user clicks the close button, emit the close event.
   */
  onClose() {
    this.close.emit();
  }

  /**
   * When the user changes the email template subject, update the preview email subject.
   */
  onEmailTemplateSubjectChange(): void {
    if (this.workingTemplate !== undefined) {
      this.previewEmailSubject = render(this.workingTemplate.subject, this.context);
    }
  }

  /**
   * When the user changes the email template body, update the preview email body.
   */
  onEmailTemplateBodyChange(): void {
    if (this.workingTemplate !== undefined) {
      // const updatedTemplate: EmailTemplate = {
      //   ...this.selectedTemplate,
      //   name: this.selectedTemplate,
      //   subject: this.bodyValue,
      //   body: this.bodyValue
      // };
      // this.store.dispatch(updateTemplateState({ template: updatedTemplate }));
      this.previewEmailBody = render(this.bodyValue, this.context);
    }
  }

  updateLocalStore(event?: any) {
    this.store.dispatch(updateTemplateState({
      template: this.templateForm.value as EmailTemplate
    }))
  }

  /**
   * When the user changes the email template, update the preview email subject and body.
   */
  onTemplateSelectionChange(event? : any): void {
    this.store.dispatch(actions.setSelectedTemplate({ template: event.value }));
    this.workingTemplate = { ...event.value };
    if (this.workingTemplate !== undefined) {
      this.bodyValue = this.workingTemplate.body || '';
      this.previewEmailSubject = render(this.workingTemplate.subject, this.context);
      this.previewEmailBody = render(this.workingTemplate.body, this.context);

      // Synchronized scrolling code
      if (this.bodyInputTextArea === undefined || this.bodyPreviewTextArea === undefined) {
        return;
      }
      const textarea1Element = this.bodyInputTextArea.nativeElement;
      const textarea2Element = this.bodyPreviewTextArea.nativeElement;

      textarea1Element.addEventListener('scroll', () => {
        textarea2Element.scrollTop = textarea1Element.scrollTop;
      });

      textarea2Element.addEventListener('scroll', () => {
        textarea1Element.scrollTop = textarea2Element.scrollTop;
      });

      // fixes issue where labels overlap input text
      setTimeout(() => {
        this.nameInput.nativeElement.click();
        this.nameInput.nativeElement.blur();
        this.subjectInput.nativeElement.click();
        this.subjectInput.nativeElement.blur();
      }, 0);
    }
  }

  onAddClicked(): void {
    this.workingTemplate = new EmailTemplate();
    this.workingTemplate.templateType = this.level;
  }

  onSaveClicked(): void {
    if (this.workingTemplate !== undefined) {
      this.workingTemplate.companyId = this.context.companyId;
      this.workingTemplate.body = this.bodyValue;
      this.save.emit(this.workingTemplate);
    }
  }

  onDeleteClicked(): void {
    this.templateForm.reset();
    this.delete.emit(this.workingTemplate);
  }

  onBackClicked(): void {
    this.templateForm.reset();
    this.close.emit();
  }

  onCancelClicked(): void {
    this.templateForm.reset();
    this.store.dispatch(actions.setSelectedTemplate({ template: undefined }));
    this.workingTemplate = undefined;
  }

  private cursorPosition: number = 0;
  private template = '';
  selectedTemplateVariable: string | undefined;
  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  @ViewChild('templateDropdown') templateDropdownRef: ElementRef;
  contextMenuStyle = {};
  onTemplateRightClicked(template: string, event: MouseEvent): void {
    event.preventDefault();
    this.template = template;
    this.selectedTemplateVariable = undefined;
    if ('body' === template) {
      this.cursorPosition = this.bodyInputTextArea.nativeElement.selectionStart;
    } else if ('subject' === template) {
      this.cursorPosition = this.subjectInput.nativeElement.selectionStart;
    }
    console.log('event client x y', event.clientX, event.clientY);
    const scrollX = window.scrollX || window.pageXOffset;
    const scrollY = window.scrollY || window.pageYOffset;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'absolute',
      'left': `${event.clientX + scrollX}px`,
      'top': `${event.clientY + scrollY}px`,
      'padding': '5px',
      'opacity': 1,
      'transition': 'opacity 0.3s ease, transform 0.3s ease',
    };
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement) &&
          !e.target.contains(this.templateDropdownRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        listenerFn(); //removes click listener
      }
    });
  }
  onVariableClicked(event: any): void {
    if (!event.target.value) {
      return;
    }
    this.selectedTemplateVariable = undefined;
    let variable: TemplateVariable = event.target.value;
    if (this.template === 'subject' && this.workingTemplate) {
      if (this.workingTemplate.subject === undefined) {
        this.workingTemplate.subject = '';
      }
      this.workingTemplate.subject = `${this.workingTemplate.subject.slice(0, this.cursorPosition)}${this.cursorPosition < 1 ? '' : ' '}${variable}${this.cursorPosition == this.workingTemplate.subject.length ? '' : ' '}${this.workingTemplate.subject.slice(this.cursorPosition)}`;
      this.onEmailTemplateSubjectChange();
    } else if (this.template === 'body') {
      if (this.bodyValue === undefined) {
        this.bodyValue = '';
      }
      this.bodyValue = `${this.bodyValue.slice(0, this.cursorPosition)}${this.cursorPosition < 1 ? '' : ' '}${variable}${this.cursorPosition == this.bodyValue.length ? '' : ' '}${this.bodyValue.slice(this.cursorPosition)}`;
      this.onEmailTemplateBodyChange();
    }
    // Hide the context menu after a variable is selected
    this.contextMenuStyle = {
        'display': 'none',
    };
    //set the selected index to 0
    this.templateDropdownRef.nativeElement.selectedIndex = 0;
    this.selectedTemplateVariable = undefined;
  }
}
