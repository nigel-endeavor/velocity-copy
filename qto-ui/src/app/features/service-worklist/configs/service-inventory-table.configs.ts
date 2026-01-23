import { ServiceInventoryViewService } from '../../../services/service-inventory-view.service';
import { createTableInternals } from '../../shared-table/facade';
import * as serviceInventoryWorklistActions from '../ngrx-inventory/service-inventory-worklist.actions';
import * as serviceInventoryWorklistSelectors from '../ngrx-inventory/service-inventory-worklist.selectors';

const SERVICE_INVENTORY_WORKLIST_TABLE = '[SERVICE INVENTORY VIEW TABLE]';
export const {
    selectors: serviceInventoryTableSelectors,
    actions: serviceInventoryTableActions,
    config: serviceInventoryTableConfig
} = createTableInternals<any>({
    feature: SERVICE_INVENTORY_WORKLIST_TABLE,
    service: ServiceInventoryViewService,
    destroyAction: serviceInventoryWorklistActions.pageDestroyed,
    errorMessage: 'Error loading service inventory',
    getReadFilters: serviceInventoryWorklistSelectors.getFilters,
    getColumns: serviceInventoryWorklistSelectors.getColumns,
    readMethodName: 'search',
});
