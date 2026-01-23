import { LocationViewService } from '../../../services/location-view.service';
import * as locationWorklistSelectors from '../ngrx/location-worklist.selectors';
import * as locationWorklistActions from '../ngrx/location-worklist.actions';
import { createTableInternals } from '../../shared-table/facade';

const LOCATION_WORKLIST_TABLE = '[LOCATION VIEW TABLE]';
export const {
  selectors: locationTableSelectors,
  actions: locationTableActions,
  config: locationTableConfig
} = createTableInternals<any>({
  feature: LOCATION_WORKLIST_TABLE,
  service: LocationViewService,
  destroyAction: locationWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: locationWorklistSelectors.getFilters,
  getColumns: locationWorklistSelectors.getColumns,
  readMethodName: 'search',
});
