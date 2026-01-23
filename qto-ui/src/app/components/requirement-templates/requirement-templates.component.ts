import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Company } from 'src/app/models/company.model';
import { LookupValueSearchCriteria } from 'src/app/models/lookup-value-search-criteria.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { RequirementTemplate } from 'src/app/models/requirement-template.model';
import { Requirement } from 'src/app/models/requirement.model';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { RequirementTemplateService } from 'src/app/services/requirement-template.service';
import { FormsModule } from '@angular/forms';
import {CommonModule} from "@angular/common";
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';

@Component({
  standalone: true,
  selector: 'app-requirement-templates',
  templateUrl: './requirement-templates.component.html',
  styleUrls: ['./requirement-templates.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    MatCheckboxModule
  ]
})
export class RequirementTemplatesComponent implements OnInit, AfterViewInit {

  @Input() company: Company;
  @Output() dialogClosed = new EventEmitter<RequirementTemplate>();

  @ViewChild('dialog') dialog: ElementRef;

  templates: RequirementTemplate[];
  selectedTemplate: RequirementTemplate | null;
  requirements: Requirement[];
  showInactiveTemplates: boolean = false;
  addingNew: boolean = false;
  editingExisting: boolean = false;

  constructor(private requirementTemplateService: RequirementTemplateService, private lookupValueService: LookupValueService) { }

  ngOnInit(): void {
    this.requirementTemplateService.getTemplates(this.company.id, null).subscribe((res: RequirementTemplate[]) => {
      this.templates = res;
    });
    let lookupCriteria = new LookupValueSearchCriteria();
    lookupCriteria.typeCode = 'ACTIVATION_REQUIREMENTS';
    this.lookupValueService.search(lookupCriteria).subscribe((res: PaginatedResult<LookupValue>) => {
      this.requirements = res.collection.map(lookup => {
        let req = new Requirement();
        req.lookupValue = lookup;
        return req;
      });
    })
  }

  ngAfterViewInit(): void {
    this.dialog.nativeElement.showModal();
  }

  onTemplateSelected(event: any): void {
    const value = event.target.value;
    if (value) {
      this.selectedTemplate = this.templates.filter(t => t.id == value)[0];
    }
  }

  toggleShowInactiveTemplates(): void {
    this.showInactiveTemplates = !this.showInactiveTemplates;
    this.requirementTemplateService.getTemplates(this.company.id, this.showInactiveTemplates).subscribe((res: RequirementTemplate[]) => {
      this.templates = res;
    });
  }

  onSaveClicked(): void {
    //verify there is only one default template for this company
    if (this.selectedTemplate?.default) {
      this.templates.forEach(t => {
        if (t.default && t.id != this.selectedTemplate?.id) {
          throw Error('There can only be one default template per company. ' + t.name + ' is currently the default template.');
        }
      });
    }

    this.selectedTemplate!.requirements.forEach((req, index) => {
      req.sortOrder = index;
    })
    this.requirementTemplateService.save(this.selectedTemplate!).subscribe((res: RequirementTemplate) => {
      this.dialog.nativeElement.close();
      this.dialogClosed.emit();
    });
  }

  onCancelClicked(): void {
    this.dialog.nativeElement.close();
    this.dialogClosed.emit();
  }

  onAddClicked(): void {
    this.addingNew = true;
    this.editingExisting = false;
    this.selectedTemplate = new RequirementTemplate();
    this.selectedTemplate.companyId = this.company.id;
    this.selectedTemplate.requirements = [];
    this.selectedTemplate.active = true;
  }

  onEditExistingClicked(): void {
    this.editingExisting = true;
    this.addingNew = false;
    this.selectedTemplate = null;
  }

  //Drag and drop handlers
  @ViewChild('placeholder') placeholder: ElementRef;
  @ViewChild('rightList') rightList: ElementRef;
  placeholderStyles: {} = {'visibility': 'hidden'}
  draggedReq: any;
  draggedEl: any;

  isDraggable(requirement: Requirement): boolean {
    //@ts-ignore
    return this.selectedTemplate && !this.selectedTemplate.requirements.map(r => r.lookupValue.id).includes(requirement.lookupValue.id);
  }

  onLeftDragStart(event: Event, req: Requirement): void {
    this.draggedReq = req;
  }

  onRightDragStart(event: any, req: Requirement): void {
    this.draggedReq = req;
    this.draggedEl = event.target;
    this.draggedEl.style.opacity = '.4';
  }

  onParentDragOver(event: Event): void {
    event.preventDefault();
  }

  onChildDragOver(event: Event, requirement: Requirement): void {
    this.dragLeave = false;
    let target = (event.target as HTMLElement);
    if (target instanceof HTMLParagraphElement && target.parentElement) {
      target = target.parentElement;
    }
    if (!target) {
      return;
    }
    this.placeholderStyles = {'visibility': '', 'height': '14px', 'background-color': '#5DCADE'};
    if (this.isBefore(this.draggedEl!, target)) {
      target.parentElement?.insertBefore(this.placeholder.nativeElement, target);
    } else {
      target.parentElement?.insertBefore(this.placeholder.nativeElement, target.nextSibling);
    }
  }

  isBefore(el1: HTMLElement, el2: HTMLElement): boolean {
    if (el1 && el2 && el1.parentNode === el2.parentNode) {
      for (let current = el1.previousSibling; current && current.nodeType !== 9; current = current.previousSibling) {
        if (current === el2) {
          return true;
        }
      }
    }
    return false;
  }

  onDrop(event: Event): void {
    if (this.draggedReq && this.selectedTemplate) {
      if (this.selectedTemplate.requirements.map(r => r.lookupValue.id).includes(this.draggedReq.lookupValue.id)) {
        const placeholderIndex = [...this.rightList.nativeElement.children].indexOf(this.placeholder.nativeElement);
        this.selectedTemplate.requirements.splice(placeholderIndex, 0, this.draggedReq);
        const draggedIndex = [...this.rightList.nativeElement.children].indexOf(this.draggedEl);
        if (draggedIndex != -1) {
          this.selectedTemplate.requirements.splice(draggedIndex, 1);
        }
      } else {
        const placeholderIndex = [...this.rightList.nativeElement.children].indexOf(this.placeholder.nativeElement);
        this.selectedTemplate.requirements.splice(placeholderIndex, 0, this.draggedReq);
      }
      this.draggedReq = null;
      if(this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = {'visibility': 'hidden'}
    }
  }

  dragLeave: boolean = false;
  counter: number = 0;
  onDragEnter(event: Event): void {
    this.counter++;
  }
  onDragLeave(event: Event): void {
    this.counter--;
  }
  onDragEnd(event: Event): void {
    if (this.counter == 0 && this.selectedTemplate) {
      const draggedIndex = this.selectedTemplate.requirements.indexOf(this.draggedReq);
      this.selectedTemplate.requirements.splice(draggedIndex, 1);
      this.draggedReq = null;
      if(this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = {'visibility': 'hidden'}
    }
    this.counter = 0;
  }
}
