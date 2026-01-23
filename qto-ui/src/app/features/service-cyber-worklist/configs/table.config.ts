import { createTableInternals } from "../../shared-table/facade";
import { ServiceCyberViewService } from "../../../services/service-cyber-view.service";
import * as serviceCyberWorklistActions from "../ngrx/service-cyber-worklist.actions";
import * as serviceCyberWorklistSelectors from "../ngrx/service-cyber-worklist.selectors";

const SERVICE_CYBER_WORKLIST_TABLE = '[SERVICE CYBER WORKLIST TABLE]';

export const {
  selectors: serviceCyberTableSelectors,
  actions: serviceCyberTableActions,
  config: serviceCyberTableConfig
} = createTableInternals<any>({
  feature: SERVICE_CYBER_WORKLIST_TABLE,
  service: ServiceCyberViewService,
  destroyAction: serviceCyberWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: serviceCyberWorklistSelectors.getFilters,
  getColumns: serviceCyberWorklistSelectors.getColumns,
  readMethodName: 'search',
});
