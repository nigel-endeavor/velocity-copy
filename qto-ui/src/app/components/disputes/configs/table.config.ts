import * as disputesSelectors from '../store/disputes.selectors';
import * as disputesActions from '../store/disputes.actions';
import { createTableInternals } from 'src/app/features/shared-table/facade';
import { DisputesService } from 'src/app/services/disputes.service';

const DISPUTES_TABLE = '[Disputes TABLE]';
export const {
  selectors: disputesTableSelectors,
  actions: disputesTableActions,
  config: disputesTableConfig
} = createTableInternals<any>({
  feature: DISPUTES_TABLE,
  service: DisputesService,
  destroyAction: disputesActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: disputesSelectors.getFilters,
  getColumns: disputesSelectors.getColumns,
  readMethodName: 'search',
});