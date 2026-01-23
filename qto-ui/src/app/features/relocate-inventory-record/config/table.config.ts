import { createTableInternals } from '../../../features/shared-table/facade';
import * as relocateInventoryRecordActions from '../ngrx/relocate-inventory-record.actions';
import * as relocateInventoryRecordSelectors from '../ngrx/relocate-inventory-record.selectors';
import { LocationInventoryViewService } from '../../../services/location-inventory-view.service';

const RELOCATE_INVENTORY_RECORD_TABLE = '[Relocate Inventory Record TABLE]';
export const {
  selectors: relocateInventoryRecordTableSelectors,
  actions: relocateInventoryRecordTableActions,
  config: relocateInventoryRecordTableConfig
} = createTableInternals<any>({
  feature: RELOCATE_INVENTORY_RECORD_TABLE,
  service: LocationInventoryViewService,
  destroyAction: relocateInventoryRecordActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: relocateInventoryRecordSelectors.getFilters,
  getColumns: relocateInventoryRecordSelectors.getColumns,
  readMethodName: 'getRelocateLocations'
});