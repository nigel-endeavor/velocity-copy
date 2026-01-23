import * as equipmentWorklistSelectors from '../ngrx/service-equipment.selectors';
import * as equipmentWorklistActions from '../ngrx/service-equipment.actions';
import { createTableInternals } from '../../../../features/shared-table/facade';
import { ServiceEquipmentService } from '../../../../services/service-equipment.service';

const SERVICE_EQUIPMENT_TABLE = '[SERVICE EQUIPMENT TABLE]';
export const {
  selectors: equipmentTableSelectors,
  actions: equipmentTableActions,
  config: equipmentTableConfig
} = createTableInternals<any>({
  feature: SERVICE_EQUIPMENT_TABLE,
  service: ServiceEquipmentService,
  destroyAction: equipmentWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: equipmentWorklistSelectors.getFilters,
  getColumns: equipmentWorklistSelectors.getColumns,
  readMethodName: 'search',
});
