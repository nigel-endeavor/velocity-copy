import { LocationInventoryViewService } from '../../../services/location-inventory-view.service';
import { createTableInternals } from '../../shared-table/facade';
import * as locationInventoryWorklistActions from '../ngrx-inventory/location-inventory-worklist.actions';
import * as locationInventoryWorklistSelectors from '../ngrx-inventory/location-inventory-worklist.selectors';

const LOCATION_INVENTORY_WORKLIST_TABLE = '[LOCATION INVENTORY VIEW TABLE]';
export const {
  selectors: locationInventoryTableSelectors,
  actions: locationInventoryTableActions,
  config: locationInventoryTableConfig
} = createTableInternals<any>({
  feature: LOCATION_INVENTORY_WORKLIST_TABLE,
  service: LocationInventoryViewService,
  destroyAction: locationInventoryWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: locationInventoryWorklistSelectors.getFilters,
  getColumns: locationInventoryWorklistSelectors.getColumns,
  readMethodName: 'search',
});