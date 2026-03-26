/**
 * Service Inventory Worklist
 * Displays services in inventory with financials, contract info, and dispute data
 */

import { useState, useCallback } from 'react';
import { Search, X } from 'lucide-react';
import { format } from 'date-fns';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import {
  useListServiceInventoryViewsQuery,
  type ServiceInventoryView,
} from '@/services/api/serviceInventoryViewsApi';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';

export default function ServiceInventoryWorklist() {
  const [offset, setOffset] = useState(0);
  const [pageSize, setPageSize] = useState(25);
  const [search, setSearch] = useState('');

  const { data, isLoading, error, refetch } = useListServiceInventoryViewsQuery({ offset, limit: pageSize });

  const handlePageChange = useCallback((page: number) => {
    setOffset(page * pageSize);
  }, [pageSize]);

  const handlePageSizeChange = useCallback((size: number) => {
    setPageSize(size);
    setOffset(0);
  }, []);

  const formatCurrency = (value: any) =>
    value != null ? `$${Number(value).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : '$0.00';

  const formatDate = (value: any) =>
    value ? format(new Date(value), 'MM/dd/yyyy') : '';

  const columns: DataTableColumn<ServiceInventoryView>[] = [
    { id: 'locationId', label: 'Location ID', sortable: true, width: 100 },
    { id: 'clientLocationId', label: 'Client Location ID', sortable: true, width: 140 },
    { id: 'clientServiceId', label: 'Client Service ID', sortable: true, width: 140 },
    { id: 'parentCompanyName', label: 'Master Customer', sortable: true, width: 160 },
    { id: 'type', label: 'Service Type', sortable: true, width: 120 },
    { id: 'companyName', label: 'End Customer', sortable: true, width: 160 },
    { id: 'address', label: 'Address', sortable: true, width: 200 },
    { id: 'provider', label: 'Provider', sortable: true, width: 120 },
    { id: 'accountNumber', label: 'Account / BAN', sortable: true, width: 120 },
    { id: 'serviceBilledTo', label: 'Billed To', sortable: true, width: 120 },
    { id: 'summaryBill', label: 'Summary Bill', width: 120 },
    { id: 'providerCircuitId', label: 'Provider Circuit ID', sortable: true, width: 140 },
    { id: 'speed', label: 'Speed', width: 100 },
    {
      id: 'activeInactive',
      label: 'Status',
      sortable: true,
      width: 100,
      format: (value) => (
        <Badge variant={value === 'Active' ? 'default' : 'secondary'}>
          {value || 'Unknown'}
        </Badge>
      ),
    },
    { id: 'mrc', label: 'Service MRC', sortable: true, width: 110, align: 'right', format: formatCurrency },
    { id: 'nrc', label: 'Service NRC', sortable: true, width: 110, align: 'right', format: formatCurrency },
    { id: 'mrr', label: 'Service MRR', sortable: true, width: 110, align: 'right', format: formatCurrency },
    { id: 'nrr', label: 'Service NRR', sortable: true, width: 110, align: 'right', format: formatCurrency },
    { id: 'annualRecurringCost', label: 'Annual Recurring', sortable: true, width: 130, align: 'right', format: formatCurrency },
    { id: 'contractSignedDate', label: 'Contract Signed', sortable: true, width: 120, format: formatDate },
    { id: 'contractTerm', label: 'Contract Term', width: 110 },
    { id: 'circuitTermEndDate', label: 'Term End Date', sortable: true, width: 120, format: formatDate },
    { id: 'inventoryAddedDate', label: 'Date Added', sortable: true, width: 110, format: formatDate },
    { id: 'clientLocationInfo', label: 'Site Info', width: 120 },
    { id: 'clientLocationType', label: 'Site Type', width: 100 },
    {
      id: 'hasIcb',
      label: 'ICB',
      width: 60,
      format: (value) => value ? 'Yes' : 'No',
    },
    { id: 'openDisputeMrc', label: 'MRC Disputed', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'openDisputeNrc', label: 'NRC Disputed', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'disputeTypes', label: 'Dispute Type', width: 120 },
  ];

  return (
    <div className="mt-4">
      <Card className="mb-4">
        <CardContent className="p-4">
          <div className="flex gap-4 items-center">
            <Input
              placeholder="Search services..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="max-w-sm"
            />
            <Button onClick={() => refetch()}>
              <Search className="mr-2 h-4 w-4" />
              Search
            </Button>
            <Button variant="outline" onClick={() => { setSearch(''); setOffset(0); }}>
              <X className="mr-2 h-4 w-4" />
              Clear
            </Button>
          </div>
        </CardContent>
      </Card>

      <DataTable
        columns={columns}
        data={data?.collection || []}
        loading={isLoading}
        error={error ? 'Failed to load service inventory' : null}
        page={Math.floor(offset / pageSize)}
        pageSize={pageSize}
        totalElements={data?.total || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${data?.total || 0} Services`}
        exportFileName="service-inventory"
        exportEnabled
      />
    </div>
  );
}
