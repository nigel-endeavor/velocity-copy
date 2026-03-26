/**
 * Invoice Detail Page
 * Shows invoice header info and a table of invoice charges
 */

import { useState, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import { format } from 'date-fns';

import { useGetInvoiceQuery } from '@/services/api/invoicesApi';
import { useGetInvoiceChargesQuery, type InvoiceCharge } from '@/services/api/invoiceChargesApi';
import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';

export default function InvoiceDetail() {
  const { invoiceId } = useParams<{ invoiceId: string }>();
  const navigate = useNavigate();
  const id = Number(invoiceId);

  const { data: invoice, isLoading: invoiceLoading } = useGetInvoiceQuery(id);
  const [chargeOffset, setChargeOffset] = useState(0);
  const [chargePageSize, setChargePageSize] = useState(25);
  const { data: chargesData, isLoading: chargesLoading } = useGetInvoiceChargesQuery({ invoiceId: id, offset: chargeOffset, limit: chargePageSize });

  const handleChargePageChange = useCallback((page: number) => {
    setChargeOffset(page * chargePageSize);
  }, [chargePageSize]);

  const handleChargePageSizeChange = useCallback((size: number) => {
    setChargePageSize(size);
    setChargeOffset(0);
  }, []);

  const formatCurrency = (value: any) =>
    value != null ? `$${Number(value).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : '$0.00';

  const formatDate = (value: any) =>
    value ? format(new Date(value), 'MM/dd/yyyy') : '-';

  if (invoiceLoading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-4">
          <div className="h-8 bg-gray-200 rounded w-1/3"></div>
          <div className="h-64 bg-gray-200 rounded"></div>
        </div>
      </div>
    );
  }

  if (!invoice) {
    return (
      <div className="p-6">
        <p className="text-muted-foreground">Invoice not found.</p>
        <Button variant="outline" onClick={() => navigate('/invoicing')} className="mt-4">
          <ArrowLeft className="mr-2 h-4 w-4" />
          Back to Invoices
        </Button>
      </div>
    );
  }

  const chargeColumns: DataTableColumn<InvoiceCharge>[] = [
    { id: 'chargeDesc', label: 'Charge Description', width: 200 },
    { id: 'itemDesc', label: 'Item Description', width: 200 },
    { id: 'chargeType', label: 'Charge Type', width: 120 },
    { id: 'chargeLevel', label: 'Charge Level', width: 120 },
    { id: 'chargeCredit', label: 'Charge/Credit', width: 110 },
    { id: 'unitCost', label: 'Unit Cost', width: 110, align: 'right', format: formatCurrency },
    { id: 'previouslyBilled', label: 'Previously Billed', width: 130, align: 'right', format: formatCurrency },
    { id: 'invoicedAmount', label: 'Invoiced Amount', width: 130, align: 'right', format: formatCurrency },
    { id: 'masterCustomerName', label: 'Master Customer', width: 160 },
    { id: 'endCustomerName', label: 'End Customer', width: 160 },
    { id: 'billableEventMilestoneDescription', label: 'Billable Event', width: 180 },
    { id: 'billableEventDate', label: 'Event Date', width: 110, format: formatDate },
  ];

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-4">
        <Button variant="outline" size="sm" onClick={() => navigate('/invoicing')}>
          <ArrowLeft className="mr-2 h-4 w-4" />
          Back
        </Button>
        <h1 className="text-3xl font-bold">Invoice {invoice.invoiceNumber || `#${invoice.id}`}</h1>
        <Badge>{invoice.invoiceStatus || 'Draft'}</Badge>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card>
          <CardHeader className="pb-2"><CardTitle className="text-sm">Client</CardTitle></CardHeader>
          <CardContent><p className="text-lg font-medium">{invoice.clientName || '-'}</p></CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2"><CardTitle className="text-sm">Total Charges</CardTitle></CardHeader>
          <CardContent><p className="text-lg font-medium">{formatCurrency(invoice.totalCharges)}</p></CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2"><CardTitle className="text-sm">Period</CardTitle></CardHeader>
          <CardContent><p className="text-lg font-medium">{formatDate(invoice.invoiceStart)} - {formatDate(invoice.invoiceEnd)}</p></CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2"><CardTitle className="text-sm">Generated</CardTitle></CardHeader>
          <CardContent><p className="text-lg font-medium">{invoice.generatedBy || '-'} on {formatDate(invoice.generatedDate)}</p></CardContent>
        </Card>
      </div>

      <div>
        <h2 className="text-xl font-semibold mb-4">Charges</h2>
        <DataTable
          columns={chargeColumns}
          data={chargesData?.collection || []}
          loading={chargesLoading}
          error={null}
          page={Math.floor(chargeOffset / chargePageSize)}
          pageSize={chargePageSize}
          totalElements={chargesData?.total || 0}
          onPageChange={handleChargePageChange}
          onPageSizeChange={handleChargePageSizeChange}
          title={`${chargesData?.total || 0} Charges`}
          exportFileName={`invoice-${invoice.invoiceNumber}-charges`}
          exportEnabled
        />
      </div>
    </div>
  );
}
