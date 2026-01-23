import { Component } from "@angular/core";
import { Store, select} from "@ngrx/store";
import { AbstractTableComponent } from "src/app/features/abstract-table/abstract-table.component";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { TASK_GROUP_COLUMNS } from "./data/task-group-table-columns.consts";
import { editEnabled} from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { TaskGroup } from "../../models/task-group.model";
import { TaskGroupService } from "../../services/task-group.service";

@Component({
  selector: 'app-task-group-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class TaskGroupTableComponent extends AbstractTableComponent<TaskGroup> {
  public override editEnabled$ = this.store.pipe(select(editEnabled));

  constructor(
    private taskGroupService: TaskGroupService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }
 override columns: CommonColumn[] = TASK_GROUP_COLUMNS;

  override getModelService() {
    return this.taskGroupService;
  }

  override getStatusColor(row: any) {
    return '#FFFFFF';
  }

}
