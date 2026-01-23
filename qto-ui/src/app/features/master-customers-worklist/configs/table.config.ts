import * as customersWorklistSelectors from '../ngrx/master-customers-worklist.selectors';
import * as customersWorklistActions from '../ngrx/master-customers-worklist.actions';
import { createTableInternals } from '../../../features/shared-table/facade';
import { CompanyViewService } from '../../../services/company-view.service';

const MASTER_CUSTOMERS_WORKLIST_TABLE = '[MASTER CUSTOMERS WORKLIST TABLE]';
export const {
  selectors: customerTableSelectors,
  actions: customerTableActions,
  config: customerTableConfig
} = createTableInternals<any>({
  feature: MASTER_CUSTOMERS_WORKLIST_TABLE,
  service: CompanyViewService,
  destroyAction: customersWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: customersWorklistSelectors.getFilters,
  getColumns: customersWorklistSelectors.getColumns,
  readMethodName: 'search',
});
