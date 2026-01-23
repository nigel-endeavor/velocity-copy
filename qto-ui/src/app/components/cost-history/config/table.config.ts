import { createTableInternals } from "src/app/features/shared-table/facade";
import { CostHistoryService } from "src/app/services/cost-history.service";
import * as costHistorySelectors from '../store/cost-history.selectors';
import * as costHistoryActions from '../store/cost-history.actions';

const COST_HISTORY_TABLE = '[Cost History TABLE]';
export const {
  selectors: costHistoryTableSelectors,
  actions: costHistoryTableActions,
  config: costHistoryTableConfig
} = createTableInternals<any>({
  feature: COST_HISTORY_TABLE,
  service: CostHistoryService,
  destroyAction: costHistoryActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: costHistorySelectors.getFilters,
  getColumns: costHistorySelectors.getColumns,
  readMethodName: 'search'
});