import { InvoiceService } from '../../../services/invoice.service';
import * as invoicingSelectors from '../store/invoicing.selectors';
import * as invoicingActions from '../store/invoicing.actions';
import { createTableInternals } from '../../../features/shared-table/facade';

const INVOICE_TABLE = '[Invoice TABLE]';
export const {
  selectors: invoicingTableSelectors,
  actions: invoicingTableActions,
  config: invoiceTableConfig
} = createTableInternals<any>({
  feature: INVOICE_TABLE,
  service: InvoiceService,
  destroyAction: invoicingActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: invoicingSelectors.getFilters,
  getColumns: invoicingSelectors.getColumns,
  readMethodName: 'search',
});
