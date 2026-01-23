import { DisputeViewService } from '../../../services/dispute-view.service';
import * as disputeWorklistSelectors from '../ngrx/dispute-worklist.selectors';
import * as disputeWorklistActions from '../ngrx/dispute-worklist.actions';
import { createTableInternals } from '../../../features/shared-table/facade';

const DISPUTE_WORKLIST_TABLE = '[DISPUTE WORKLIST TABLE]';
export const {
  selectors: disputeTableSelectors,
  actions: disputeTableActions,
  config: disputeTableConfig
} = createTableInternals<any>({
  feature: DISPUTE_WORKLIST_TABLE,
  service: DisputeViewService,
  destroyAction: disputeWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: disputeWorklistSelectors.getFilters,
  getColumns: disputeWorklistSelectors.getColumns,
  readMethodName: 'search',
});
