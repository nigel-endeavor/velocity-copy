import * as serviceHistorySelectors from '../store/service-history.selectors';
import * as serviceHistoryActions from '../store/service-history.actions';
import { createTableInternals } from 'src/app/features/shared-table/facade';
import { ServiceHistoryViewService } from 'src/app/services/service-history-view.service';

const SERVICE_HISTORY_TABLE = '[Service History TABLE]';
export const {
  selectors: serviceHistoryTableSelectors,
  actions: serviceHistoryTableActions,
  config: serviceHistoryTableConfig
} = createTableInternals<any>({
  feature: SERVICE_HISTORY_TABLE,
  service: ServiceHistoryViewService,
  destroyAction: serviceHistoryActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: serviceHistorySelectors.getFilters,
  getColumns: serviceHistorySelectors.getColumns,
  readMethodName: 'search',
});