import { ImportActivityService } from "../../../services/import-activity.service";
import { createTableInternals } from "../../shared-table/facade";
import * as importActivityWorklistSelectors from "../ngrx/import-activity-worklist.selectors";
import * as importActivityWorklistActions from "../ngrx/import-activity-worklist.actions";

const IMPORT_ACTIVITY_WORKLIST_TABLE = '[IMPORT ACTIVITY WORKLIST TABLE]';
export const {
  selectors: importActivityTableSelectors,
  actions: importActivityTableActions,
  config: importActivityTableConfig
} = createTableInternals<any>({
  feature: IMPORT_ACTIVITY_WORKLIST_TABLE,
  service: ImportActivityService,
  destroyAction: importActivityWorklistActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: importActivityWorklistSelectors.getFilters,
  getColumns: importActivityWorklistSelectors.getColumns,
  readMethodName: 'search',
});