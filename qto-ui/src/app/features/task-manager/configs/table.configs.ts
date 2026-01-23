import * as taskManagerSelectors from '../ngrx/task-manager.selectors';
import * as taskManagerActions from '../ngrx/task-manager.actions';
import { createTableInternals } from 'src/app/features/shared-table/facade';
import { TaskGroupService } from '../../../services/task-group.service';

const TASK_GROUP_TABLE = '[Task Group TABLE]';
export const {
  selectors: taskGroupTableSelectors,
  actions: taskGroupTableActions,
  config: taskGroupTableConfig
} = createTableInternals<any>({
  feature: TASK_GROUP_TABLE,
  service: TaskGroupService,
  destroyAction: taskManagerActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: taskManagerSelectors.getFilters,
  getColumns: taskManagerSelectors.getColumns,
  readMethodName: 'search',
});