import { ServiceSurchargeService } from '../../../services/service-surcharge.service';
import * as surchargeSelectors from '../store/surcharge-dialog.selectors';
import * as surchargeActions from '../store/surcharge-dialog.actions';
import { createTableInternals } from '../../../features/shared-table/facade';

const SURCHARGE_TABLE = '[Surcharge TABLE]';
export const {
  selectors: surchargeTableSelectors,
  actions: surchargeTableActions,
  config: surchargeTableConfig
} = createTableInternals<any>({
  feature: SURCHARGE_TABLE,
  service: ServiceSurchargeService,
  destroyAction: surchargeActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: surchargeSelectors.getFilters,
  getColumns: surchargeSelectors.getColumns,
  readMethodName: 'search',
});
