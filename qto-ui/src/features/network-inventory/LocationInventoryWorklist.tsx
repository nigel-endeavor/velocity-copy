/**
 * Location Inventory Worklist
 * Displays locations in inventory with service counts, financials, and dispute info
 */

import { useState, useCallback, useMemo } from 'react';
import { Search, X } from 'lucide-react';
import { format } from 'date-fns';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import {
  useListLocationInventoryViewsQuery,
  type LocationInventoryView,
} from '@/services/api/locationInventoryViewsApi';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import { PageToolbar } from '@/components/layout/PageScaffold';

export default function LocationInventoryWorklist() {
  const [offset, setOffset] = useState(0);
  const [pageSize, setPageSize] = useState(25);
  const [search, setSearch] = useState('');

  const { data, isLoading, error, refetch } = useListLocationInventoryViewsQuery({ offset, limit: pageSize });

  const handlePageChange = useCallback((page: number) => {
    setOffset(page * pageSize);
  }, [pageSize]);

  const handlePageSizeChange = useCallback((size: number) => {
    setPageSize(size);
    setOffset(0);
  }, []);

  const formatCurrency = (value: number | null | undefined) =>
    value != null ? `$${Number(value).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : '$0.00';

  const formatDate = (value: string | null | undefined) =>
    value ? format(new Date(value), 'MM/dd/yyyy') : '';

  const columns: DataTableColumn<LocationInventoryView>[] = [
    { id: 'id', label: 'Location ID', sortable: true, width: 100 },
    { id: 'clientLocationId', label: 'Client Location ID', sortable: true, width: 140 },
    { id: 'parentCompanyName', label: 'Master Customer', sortable: true, width: 160 },
    { id: 'companyName', label: 'Customer', sortable: true, width: 160 },
    { id: 'address', label: 'Address', sortable: true, width: 220 },
    {
      id: 'services',
      label: 'Services',
      width: 140,
      format: (value) => value || '-',
    },
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
    { id: 'countServices', label: 'Service Count', sortable: true, width: 110, align: 'right' },
    { id: 'activeCompleteMrc', label: 'Location MRC', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'activeCompleteNrc', label: 'Location NRC', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'activeCompleteMrr', label: 'Location MRR', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'activeCompleteNrr', label: 'Location NRR', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'annualRecurringCost', label: 'Annual Recurring', sortable: true, width: 130, align: 'right', format: formatCurrency },
    { id: 'inventoryAddedDate', label: 'Date Added', sortable: true, width: 110, format: formatDate },
    { id: 'clientLocationInfo', label: 'Location Info', width: 130 },
    { id: 'clientLocationType', label: 'Location Type', width: 120 },
    { id: 'openDisputeMrc', label: 'Open MRC Disputed', sortable: true, width: 140, align: 'right', format: formatCurrency },
    { id: 'openDisputeNrc', label: 'Open NRC Disputed', sortable: true, width: 140, align: 'right', format: formatCurrency },
    { id: 'subOrderTypes', label: 'Sub Order Type', width: 120 },
  ];

  const filteredLocations = useMemo(() => {
    const query = search.trim().toLowerCase();
    const rows = data?.collection || [];

    if (!query) {
      return rows;
    }

    return rows.filter((location) =>
      [location.clientLocationId, location.companyName, location.parentCompanyName, location.address, location.services]
        .filter(Boolean)
        .some((value) => value.toLowerCase().includes(query))
    );
  }, [data?.collection, search]);

  return (
    <div className="mt-4 space-y-4">
      <PageToolbar>
        <Input
          placeholder="Search locations..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="h-11 max-w-sm rounded-xl border-slate-200 bg-white shadow-none"
        />
        <Button className="h-11 rounded-xl" onClick={() => refetch()}>
          <Search className="mr-2 h-4 w-4" />
          Refresh
        </Button>
        <Button className="h-11 rounded-xl" variant="outline" onClick={() => { setSearch(''); setOffset(0); }}>
          <X className="mr-2 h-4 w-4" />
          Clear
        </Button>
        <span className="app-page-toolbar-note">{filteredLocations.length} location records on this page.</span>
      </PageToolbar>

      <DataTable
        columns={columns}
        data={filteredLocations}
        loading={isLoading}
        error={error ? 'Failed to load location inventory' : null}
        page={Math.floor(offset / pageSize)}
        pageSize={pageSize}
        totalElements={search ? filteredLocations.length : data?.total || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${search ? filteredLocations.length : data?.total || 0} Locations`}
        exportFileName="location-inventory"
        exportEnabled
      />
    </div>
  );
}
