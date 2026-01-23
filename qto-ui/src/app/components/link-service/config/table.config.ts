import * as linkServiceSelectors from '../store/link-service.selectors';
import * as linkServiceActions from '../store/link-service.actions';
import { createTableInternals } from 'src/app/features/shared-table/facade';
import { LinkServicesService } from "../../../services/link-services.service";

const LINK_SERVICE_TABLE = '[Link Service TABLE]';
export const {
  selectors: linkServiceTableSelectors,
  actions: linkServiceTableActions,
  config: linkServiceTableConfig
} = createTableInternals<any>({
  feature: LINK_SERVICE_TABLE,
  service: LinkServicesService,
  destroyAction: linkServiceActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: linkServiceSelectors.getFilters,
  getColumns: linkServiceSelectors.getColumns,
  readMethodName: 'getLinkServices'
});
