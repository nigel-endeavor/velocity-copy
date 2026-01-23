import { Component, EventEmitter, HostListener, Input, Output, SimpleChanges } from "@angular/core";
import { select, Store } from "@ngrx/store";
import { TaskGroup } from "../../models/task-group.model";
import { Subscription, tap } from "rxjs";
import { plainToClass } from "class-transformer";
import { CompanyTask } from "../../models/company-task.model";
import { getCustomerTasks, getProvisioners, getTaskGroup } from "./ngrx/customer-tasks.selectors";
import * as actions from "./ngrx/customer-tasks.actions";
import { Actions, ofType } from "@ngrx/effects";

@Component({
  selector: 'app-customer-tasks',
  templateUrl: './customer-tasks.component.html',
  styleUrls: ['../../order-detail-page/form-styles.scss', './customer-tasks.component.scss']
})
export class CustomerTasksComponent {

  // Declare an input properties
  @Input() customerId!: number;
  @Input() taskGroup!: TaskGroup;
  @Input() taskGroupId!: number;
  @Input() editEnabled = false;
  @Input() currentAssignment: string | null = null;
  @Output() saveCompanyTasksClicked: EventEmitter<void> = new EventEmitter<void>();
  editTask: boolean = false;
  unsavedChanges: boolean = false;
  // Display properties and observables
  public provisioners$ = this.store.pipe(select(getProvisioners));
  public customerTasks$ = this.store.pipe(select(getCustomerTasks), tap(tasks => {
      this.customerTasks = tasks.map(task => plainToClass(CompanyTask, task));
      console.log(this.customerTasks);
    })
  );
  public customerTasks: CompanyTask[] = [];
  public taskGroup$ = this.store.pipe(select(getTaskGroup), tap(taskGroup => {
      this.taskGroup = plainToClass(TaskGroup, taskGroup);
      this.taskGroupId = this.taskGroup?.id;
    })
  );

  taskHeaderTooltip = 'The function or job to be completed';
  completedHeaderTooltip = 'The date the task was executed';
  assignedToHeaderTooltip = 'The individual in i90 responsible for performing or following up on this task';
  commentsHeaderTooltip = 'Any additional information or notes about the task';

  private saveSuccessSubscription: Subscription = this.actions$.pipe(
    ofType(actions.saveCustomerTaskSuccess)
  ).subscribe(() => {
    this.saveCompanyTasksClicked.emit();
  });

  constructor(
    private store: Store,
    private actions$: Actions
  ) {  }

  ngOnInit() {
    this.store.dispatch(actions.loadProvisioners());
    if (this.taskGroupId) {
      this.store.dispatch(actions.loadTaskGroup({ taskGroupId: this.taskGroupId }));
    } else if (this.taskGroup) {
      this.store.dispatch(actions.setTaskGroup({ taskGroup: this.taskGroup }));
    }
    this.store.dispatch(actions.loadCustomerTasks({ customerId: this.customerId }));
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['taskGroupId'] && !changes['taskGroupId'].firstChange) {
      if (this.taskGroupId) {
        this.store.dispatch(actions.loadTaskGroup({ taskGroupId: this.taskGroupId }));
      } else {
        this.store.dispatch(actions.setTaskGroup({ taskGroup: null }));
      }
    }
    if (changes['customerId'] && !changes['customerId'].firstChange) {
      this.store.dispatch(actions.loadCustomerTasks({ customerId: this.customerId }));
    }
    if (changes['taskGroup'] && !changes['taskGroup'].firstChange) {
      this.store.dispatch(actions.setTaskGroup({ taskGroup: this.taskGroup }));
      if (this.customerId) {
        this.store.dispatch(actions.loadCustomerTasks({ customerId: this.customerId }));
      }
    }
  }

  ngOnDestroy() {
    console.log('Destroying customer tasks');
    this.store.dispatch(actions.pageDestroyed());
    if (this.saveSuccessSubscription) {
      this.saveSuccessSubscription.unsubscribe();
    }
  }

  onSaveCompanyTasksClicked(): void {
    this.customerTasks.forEach(task => {
      task.assignedTo == '' ? task.assignedTo = null : task.assignedTo;
      this.store.dispatch(actions.saveCustomerTask({ customerTask: task }));
    });
    this.editTask = false;
    this.unsavedChanges = false;
  }

  markUnsaved() {
    if (this.editTask) {
      this.unsavedChanges = true; // Set unsaved changes flag when any input changes
    }
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    const clickedInside = (event.target as HTMLElement).closest('table');
    const applyButton = document.querySelector('button[mat-raised-button][matdatepickerapply]');
    if (!clickedInside && !applyButton && this.unsavedChanges && this.editTask) {
      if (confirm('You have unsaved changes. Do you want to leave?')) {
        event.stopPropagation();
        window.location.reload();
      }
      else {
        event.stopPropagation();
      }
    }
  }

}
