import { ActivationViewService } from '../../../services/activation-view.service';
import * as activationWorklistSelectors from '../ngrx/activation-worklist.selectors';
import * as activationWorklistActions from '../ngrx/activation-worklist.actions';
import { createTableInternals } from '../../shared-table/facade';

const ACTIVATION_WORKLIST_TABLE = '[ACTIVATION VIEW TABLE]';
export const {
  selectors: activationTableSelectors,
  actions: activationTableActions,
  config: activationTableConfig
} = createTableInternals<any>({
  feature: ACTIVATION_WORKLIST_TABLE,
  service: ActivationViewService,
  destroyAction: activationWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: activationWorklistSelectors.getFilters,
  getColumns: activationWorklistSelectors.getColumns,
  readMethodName: 'search',
});
