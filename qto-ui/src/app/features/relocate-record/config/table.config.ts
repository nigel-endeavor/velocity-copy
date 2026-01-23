import { createTableInternals } from 'src/app/features/shared-table/facade';
import * as relocateRecordActions from '../ngrx/relocate-record.actions';
import * as relocateRecordSelectors from '../ngrx/relocate-record.selectors';
import { LocationViewService } from 'src/app/services/location-view.service';

const RELOCATE_RECORD_TABLE = '[Relocate Record TABLE]';
export const {
  selectors: relocateRecordTableSelectors,
  actions: relocateRecordTableActions,
  config: relocateRecordTableConfig
} = createTableInternals<any>({
  feature: RELOCATE_RECORD_TABLE,
  service: LocationViewService,
  destroyAction: relocateRecordActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: relocateRecordSelectors.getFilters,
  getColumns: relocateRecordSelectors.getColumns,
  readMethodName: 'getRelocateLocations'
});