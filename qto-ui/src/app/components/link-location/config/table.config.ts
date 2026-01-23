import { createTableInternals } from 'src/app/features/shared-table/facade';
import * as linkLocationActions from '../store/link-location.actions';
import * as linkLocationSelectors from '../store/link-location.selectors';
import { LocationInventoryViewService } from 'src/app/services/location-inventory-view.service';

const LINK_LOCATION_TABLE = '[Link Location TABLE]';
export const {
  selectors: linkLocationTableSelectors,
  actions: linkLocationTableActions,
  config: linkLocationTableConfig
} = createTableInternals<any>({
  feature: LINK_LOCATION_TABLE,
  service: LocationInventoryViewService,
  destroyAction: linkLocationActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: linkLocationSelectors.getFilters,
  getColumns: linkLocationSelectors.getColumns,
  readMethodName: 'getLinkLocations'
});