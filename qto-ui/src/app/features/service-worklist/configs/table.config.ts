import { ServiceViewService } from '../../../services/service-view.service';
import * as serviceWorklistSelectors from '../ngrx/service-worklist.selectors';
import * as serviceWorklistActions from '../ngrx/service-worklist.actions';
import { createTableInternals } from '../../shared-table/facade';

const SERTVICE_WORKLIST_TABLE = '[SERTVICE VIEW TABLE]';
export const {
  selectors: serviceTableSelectors,
  actions: serviceTableActions,
  config: serviceTableConfig
} = createTableInternals<any>({
  feature: SERTVICE_WORKLIST_TABLE,
  service: ServiceViewService,
  destroyAction: serviceWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: serviceWorklistSelectors.getFilters,
  getColumns: serviceWorklistSelectors.getColumns,
  readMethodName: 'search',
});
