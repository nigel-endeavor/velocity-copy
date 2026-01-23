import { DisconnectViewService } from '../../../services/disconnect-view.service';
import * as disconnectWorklistSelectors from '../ngrx/disconnect-worklist.selectors';
import * as disconnectWorklistActions from '../ngrx/disconnect-worklist.actions';
import { createTableInternals } from '../../../features/shared-table/facade';

const DISCONNECT_WORKLIST_TABLE = '[DISCONNECT WORKLIST TABLE]';
export const {
  selectors: disconnectTableSelectors,
  actions: disconnectTableActions,
  config: disconnectTableConfig
} = createTableInternals<any>({
  feature: DISCONNECT_WORKLIST_TABLE,
  service: DisconnectViewService,
  destroyAction: disconnectWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: disconnectWorklistSelectors.getFilters,
  getColumns: disconnectWorklistSelectors.getColumns,
  readMethodName: 'search',
});
