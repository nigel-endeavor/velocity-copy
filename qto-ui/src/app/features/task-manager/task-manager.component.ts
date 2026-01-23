import { Component, ElementRef, EventEmitter, Output, ViewChild } from "@angular/core";
import { select, Store } from "@ngrx/store";
import {
  deleteTaskGroup,
  loadLookupValuesByKey,
  saveTaskGroup,
  setSelectedTaskGroup,
  updateFilters,
  updateSort
} from "./ngrx/task-manager.actions";
import { getAccountOnboardingTasks, getColumns, getFilters, getSelectedTaskGroup } from "./ngrx/task-manager.selectors";
import { TaskGroup } from "../../models/task-group.model";
import { CdkDragDrop, moveItemInArray } from "@angular/cdk/drag-drop";
import { plainToClass } from "class-transformer";
import { Task } from "../../models/task.model";
import { LookupValue } from "../../models/lookup-value.model";
import { taskGroupTableActions, taskGroupTableSelectors } from "./configs/table.configs";
import { filter, Observable, tap } from "rxjs";
import { JsonPipe } from "@angular/common";

@Component({
  selector: 'app-task-manager',
  templateUrl: './task-manager.component.html',
  styleUrls: ['./task-manager.component.scss', '../../order-detail-page/form-styles.scss']
})
export class TaskManagerComponent {
  @Output() close = new EventEmitter();

  public showDialog: boolean = false;
  public selectedTaskGroup$ = this.store.pipe(select(getSelectedTaskGroup), tap(taskGroup => {
    this.selectedTaskGroup = plainToClass(TaskGroup, taskGroup);
  }));
  public selectedTaskGroup: TaskGroup | null;
  public accountOnboardingTasks$ = this.store.pipe(select(getAccountOnboardingTasks));
  public sourceTasks: Task[] = [];
  public targetTasks: Task[] = [];
  public editingExisitingTaskGroup: boolean = false;
  //task group table
  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any[]> = this.store.pipe(select(taskGroupTableSelectors.getTableData));
  public tableTotal$: Observable<number> = this.store.pipe(select(taskGroupTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));

  // need list of all task groups
  // need list of all lookups of type ACCOUNT_ONBOARDING_TASKS
  // need to be able to save changes to a task group
  // need to be able to add a new task group
  constructor(
    private store: Store
  ) { }

  ngOnInit() {
    this.store.dispatch(taskGroupTableActions.loadTableData());
    this.store.dispatch(loadLookupValuesByKey({ key: "accountOnboardingTasks", lookupKey: 'ACCOUNT_ONBOARDING_TASKS' }));
  }

  onSelectedTaskGroupChanged(taskGroupEvent: any) {
    this.store.dispatch(setSelectedTaskGroup({ taskGroup: taskGroupEvent.value }));
    this.selectedTaskGroup$.subscribe((taskGroup) => {
      this.accountOnboardingTasks$.subscribe((tasks) =>
        {
          if (taskGroup) {
            this.targetTasks = taskGroup.tasks.map(t => {
              return plainToClass(Task, t);
            });
            this.sourceTasks = tasks.map(task => {
              let exists = taskGroup.tasks.some(t => t.lookupValue.id === task.id);
              let t = new Task();
              let lv = plainToClass(LookupValue, task);
              t.lookupValue = lv;
              t.value = lv.value;
              t.active = !exists;
              return t;
            });
          } else {
            this.sourceTasks = tasks.map(lookupValue => {
              let t = new Task();
              let lv = plainToClass(LookupValue, lookupValue);
              t.lookupValue = lv;
              t.value = lv.value;
              return t;
            });
          }
        }
      );
    });
  }

  onSaveClicked(taskGroup: TaskGroup) {
    taskGroup.tasks = this.targetTasks;
    this.store.dispatch(saveTaskGroup({ taskGroup }));
  }

  onCancelClicked() {
    this.store.dispatch(setSelectedTaskGroup({ taskGroup: null }));
  }

  onCloseClicked() {
    this.close.emit();
  }

  //drag and drop
  // drop(event: CdkDragDrop<Task[]>) {
  //   if (event.previousContainer === event.container) {
  //     moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
  //   } else {
  //     // Transfer item between lists
  //     const movedItem = event.previousContainer.data[event.previousIndex];

  //     // Check if it's dropped in the source list or target list
  //     if (event.container.id === 'sourceList') {
  //       // If moved back to source list, mark it active
  //       // event.container.data.splice(event.currentIndex, 0, { ...movedItem, active: true });
  //       // find the item in the target list, if it exists, mark it as active
  //       let sourceIndex = this.sourceTasks.findIndex(t => t.lookupValue.id === movedItem.lookupValue.id);
  //       if (sourceIndex > -1) {
  //         this.sourceTasks[sourceIndex].active = true;
  //       }
  //       //remove from previousContainer
  //       // event.previousContainer.data.splice(event.previousIndex, 1);
  //     } else if (event.container.id === 'targetList') {
  //       // If moved to target list, mark it inactive
  //       event.container.data.splice(event.currentIndex, 0, { ...movedItem, active: true });
  //       event.previousContainer.data[event.previousIndex] = { ...movedItem, active: false };
  //     }

  //     // Keep the item in the source list but mark it inactive
  //   }
  // }
  // drop(event: CdkDragDrop<any[]>) {
  //   if (event.previousContainer === event.container) {
  //     // Handle reordering within the same list
  //     moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
  //   } else {
  //     // Copy the item to the target list (instead of moving)
  //     const draggedItem = event.previousContainer.data[event.previousIndex];

  //     // Check if the item already exists in the target list to prevent duplicates
  //     const alreadyInTarget = event.container.data.some(item => item.value === draggedItem.value);

  //     if (!alreadyInTarget) {
  //       // Push a copy of the item to the target list
  //       event.container.data.push({ ...draggedItem });

  //       // Optionally, you can disable the item in the source list if you want it to be non-draggable
  //       // in the future:
  //       // draggedItem.active = false;
  //     }
  //   }
  // }

  @ViewChild('placeholder') placeholder: ElementRef;
  @ViewChild('rightList') rightList: ElementRef;
  placeholderStyles: {} = {'visibility': 'hidden'}
  draggedTask: any;
  draggedFrom: 'source' | 'target' | null = null;
  draggedEl: any;

  isDraggable(task: Task): boolean {
    //@ts-ignore
    return this.selectedTaskGroup && !this.targetTasks.map(t => t.lookupValue.id).includes(task.lookupValue.id);
  }

  onLeftDragStart(event: Event, task: Task): void {
    this.draggedTask = task;
  }

  onRightDragStart(event: any, task: Task): void {
    console.log('drag start ' + task);
    this.draggedTask = task;
    this.draggedEl = event.target;
    this.draggedEl.style.opacity = '.4';
  }

  onDrop(event: Event): void {
    console.log('drop ' + event.target);
    if (this.draggedTask && this.targetTasks) {
      if (this.targetTasks.map(t => t.lookupValue.id).includes(this.draggedTask.lookupValue.id)) {
        const placeholderIndex = [...this.rightList.nativeElement.children].indexOf(this.placeholder.nativeElement);
        this.targetTasks.splice(placeholderIndex, 0, this.draggedTask);
        const draggedIndex = [...this.rightList.nativeElement.children].indexOf(this.draggedEl);
        if (draggedIndex != -1) {
          this.targetTasks.splice(draggedIndex, 1);
        }
      } else {
        const placeholderIndex = [...this.rightList.nativeElement.children].indexOf(this.placeholder.nativeElement);
        this.targetTasks.splice(placeholderIndex, 0, this.draggedTask);
      }
      this.draggedTask.active = false;
      // this.draggedTask = null;
      if(this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = {'visibility': 'hidden'}
    }
  }

  onParentDragOver(event: Event): void {
    event.preventDefault();
  }

  onChildDragOver(event: Event, task: Task): void {
    console.log('drag over');
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

  dragLeave: boolean = false;
  counter: number = 0;
  onDragEnter(event: Event): void {
    this.counter++;
  }

  onDragLeave(event: Event): void {
    this.counter--;
  }

  onDragEnd(event: Event): void {
    console.log('drag end ' + this.draggedTask);
    console.log(JSON.stringify(event));
    if (this.targetTasks && this.draggedEl) {
      //print event as json
      console.log(JSON.stringify(event));
      const draggedIndex = this.targetTasks.indexOf(this.draggedTask);
      this.targetTasks.splice(draggedIndex, 1);
      console.log('drag end ' + this.draggedTask);
      let sourceIndex = this.sourceTasks.findIndex(t => t.lookupValue.id === this.draggedTask.lookupValue.id);
      if (sourceIndex > -1) {
        this.sourceTasks[sourceIndex].active = true;
      }
      this.draggedTask = null;
      if(this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = {'visibility': 'hidden'}
    }
    this.counter = 0;
  }

  //task group table
  onTaskGroupDblClicked(taskGroup: TaskGroup) {
    this.onSelectedTaskGroupChanged({ value: taskGroup });
  }

  onAddTaskGroupClicked() {
    let newTaskGroup = new TaskGroup();
    newTaskGroup.tasks = [];
    this.onSelectedTaskGroupChanged({ value: newTaskGroup });
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.store.dispatch(updateFilters({ key, value}));
  }

  onDeleteClicked(selectedTaskGroup: TaskGroup) {
    this.store.dispatch(deleteTaskGroup({ taskGroup: selectedTaskGroup }));
    this.store.dispatch(setSelectedTaskGroup({ taskGroup: null }));
  }
}
