import { CompanyViewService } from "src/app/services/company-view.service";
import { createTableInternals } from "../../shared-table/facade";
import * as customerDetailsSelectors from '../ngrx/customer-details.selectors';
import * as customerDetailsActions from '../ngrx/customer-details.actions';

const COMPANY_VIEW_TABLE = '[Company View TABLE]';
export const {
  selectors: companyViewTableSelectors,
  actions: companyViewTableActions,
  config: companyViewTableConfig
} = createTableInternals<any>({
  feature: COMPANY_VIEW_TABLE,
  service: CompanyViewService,
  destroyAction: customerDetailsActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: customerDetailsSelectors.getFilters,
  getColumns: customerDetailsSelectors.getColumns,
  readMethodName: 'search'
});