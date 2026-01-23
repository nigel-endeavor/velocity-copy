import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { plainToInstance } from 'class-transformer';
import { ActivationIssue } from 'src/app/models/activation-issue.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { ActivationIssueService } from 'src/app/services/activation-issue.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { editEnabled } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { select, Store } from "@ngrx/store";
import { loadUserStatuses } from 'src/app/order-detail-page/ngrx/order-details.actions';

@Component({
  selector: 'app-activation-issues',
  templateUrl: './activation-issues.component.html',
  styleUrls: ['./activation-issues.component.scss']
})
export class ActivationIssuesComponent implements OnInit, AfterViewInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  editEnabled: boolean;
  @Input() attemptId: number;
  @Input() isReadOnly: boolean | null;
  @Output() dialogClosed = new EventEmitter<void | number>();

  @ViewChild('dialog') dialog: ElementRef;

  issues: ActivationIssue[];
  primaryCauses: LookupValue[];
  secondaryCauses: LookupValue[];
  tertiaryCauses: LookupValue[];
  secondaryCauseMap: Map<string, LookupValue[]> = new Map();
  tertiaryCauseMap: Map<string, LookupValue[]> = new Map();;

  constructor(
    private activationIssueService: ActivationIssueService,
    private lookupValueService: LookupValueService,
    private store: Store<any>
    ) { }

  ngOnInit(): void {
    this.store.dispatch(loadUserStatuses());
    this.activationIssueService.findByActivationAttemptId(this.attemptId).subscribe((res: ActivationIssue[]) => {
      this.issues = plainToInstance(ActivationIssue, res);
    });
    this.editEnabled$.subscribe((res) => {
      this.editEnabled = res;
    });

    this.lookupValueService.find('ACTIVATION_ISSUE_PRIMARY').subscribe((res: LookupValue[]) => {
      this.primaryCauses = res;
      this.lookupValueService.find('ACTIVATION_ISSUE_SECONDARY').subscribe((res: LookupValue[]) => {
        res.forEach(lv => {
          this.secondaryCauses = res;
          const parentCause = this.primaryCauses.find(c => c.id == lv.parentId)!.display;
          if (this.secondaryCauseMap.has(parentCause)) {
            this.secondaryCauseMap.get(parentCause)?.push(lv);
          } else {
            this.secondaryCauseMap.set(parentCause, [lv]);
          }
        });
        this.lookupValueService.find('ACTIVATION_ISSUE_TERTIARY').subscribe((res: LookupValue[]) => {
          this.tertiaryCauses = res;
          res.forEach(lv => {
            const parentCause = this.secondaryCauses.find(c => c.id == lv.parentId);
            const grandPararentCause = this.primaryCauses.find(c => c.id == parentCause?.parentId);
            const key = grandPararentCause?.display + '-' + parentCause?.display;
            if (this.tertiaryCauseMap.has(key)) {
              this.tertiaryCauseMap.get(key)?.push(lv);
            } else {
              this.tertiaryCauseMap.set(key, [lv]);
            }
          });
        });
      });
    });
  }

  ngAfterViewInit(): void {
    this.dialog.nativeElement.showModal();
  }

  onAddClicked(): void {
    let newIssue = new ActivationIssue();
    newIssue.activationAttemptId = this.attemptId;
    newIssue.rank = this.issues.length + 1;
    this.issues.push(newIssue);
  }

  onSaveClicked(): void {
    this.issues = this.issues.filter(i => i.primaryRootCause);
    for (let i = 0; i < this.issues.length; i++) {
      let issue = this.issues[i];
      issue.rank = i;
      this.activationIssueService.save(issue).subscribe();
    }
    this.dialog.nativeElement.close();
    this.dialogClosed.emit(this.issues.length);
  }

  onCancelClicked(): void {
    this.dialog.nativeElement.close();
    this.dialogClosed.emit(this.issues.length);
  }

  getSecondaryCauses(issue: ActivationIssue): LookupValue[] {  
    return this.secondaryCauseMap.get(issue.primaryRootCause)!;
  }

  getTertiaryCauses(issue: ActivationIssue): LookupValue[] {
    return this.tertiaryCauseMap.get(issue.primaryRootCause + '-' + issue.secondaryRootCause)!;
  }

  // Drag and Drop
  @ViewChild('placeholderRow') placeholderRow: ElementRef;
  @ViewChild('tableBody') tableBody: ElementRef;
  placeholderStyles: {} = {'visibility': 'hidden'};
  draggedIssue: any;
  draggedEl: any;

  onDragStart(event: any, issue: ActivationIssue): void {
    this.draggedIssue = issue;
    this.draggedEl = event.target;
    this.draggedEl.style.opacity = '.4';
  }

  onParentDragOver(event: Event): void {
    event.preventDefault();
  }

  onChildDragOver(event: Event, issue: ActivationIssue): void {
    let target = (event.target as HTMLElement);
    if (target instanceof HTMLTableCellElement && target.parentElement) {
      target = target.parentElement;
    }
    if (!target) {
      return;
    }
    this.placeholderStyles = {'visibility': '', 'height': '14px', 'background-color': '#5DCADE'};
    if (this.isBefore(this.draggedEl, target)) {
      target.parentElement?.insertBefore(this.placeholderRow.nativeElement, target);
    } else {
      target.parentElement?.insertBefore(this.placeholderRow.nativeElement, target.nextSibling);
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
    if (this.draggedIssue) {
      const placeholderIndex = [...this.tableBody.nativeElement.children].indexOf(this.placeholderRow.nativeElement);
      this.issues.splice(placeholderIndex, 0, this.draggedIssue);
      const draggedIndex = [...this.tableBody.nativeElement.children].indexOf(this.draggedEl);
      if (draggedIndex != -1) {
        this.issues.splice(draggedIndex, 1);
      }
      this.draggedIssue = null;
      if (this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = {'visibility': 'hidden'};
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
  onDragEnd(event:Event): void {
    if (this.counter == 0) {
      this.draggedIssue = null;
      if (this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = {'visibility': 'hidden'};
    }
    this.counter = 0;
  }
}
