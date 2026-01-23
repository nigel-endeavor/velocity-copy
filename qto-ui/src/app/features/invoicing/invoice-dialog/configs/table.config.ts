import { InvoiceChargeService } from '../../../../services/invoice-charge.service';
import * as invoicingChargeSelectors from '../store/invoice-charges.selectors';
import * as invoicingChargeActions from '../store/invoice-charges.actions';
import { createTableInternals } from '../../../../features/shared-table/facade';

const INVOICE_CHARGE_TABLE = '[Invoice Charge TABLE]';
export const {
  selectors: invoicingChargeTableSelectors,
  actions: invoicingChargeTableActions,
  config: invoiceChargeTableConfig
} = createTableInternals<any>({
  feature: INVOICE_CHARGE_TABLE,
  service: InvoiceChargeService,
  destroyAction: invoicingChargeActions.pageDestroyed,
  errorMessage: 'Can\'t load table data',
  getReadFilters: invoicingChargeSelectors.getFilters,
  getColumns: invoicingChargeSelectors.getColumns,
  readMethodName: 'search',
});
