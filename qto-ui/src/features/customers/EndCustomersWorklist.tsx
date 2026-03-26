/**
 * End Customers Worklist
 * Displays end customer companies with task progress and billing contact info
 */

import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, X } from 'lucide-react';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import {
  useListCompanyViewsQuery,
  type CompanyView,
} from '@/services/api/companyViewsApi';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';

export default function EndCustomersWorklist() {
  const navigate = useNavigate();
  const [offset, setOffset] = useState(0);
  const [pageSize, setPageSize] = useState(25);
  const [search, setSearch] = useState('');

  const { data, isLoading, error, refetch } = useListCompanyViewsQuery({
    offset,
    limit: pageSize,
    type: 'END_CUSTOMER',
  });

  const handlePageChange = useCallback((page: number) => {
    setOffset(page * pageSize);
  }, [pageSize]);

  const handlePageSizeChange = useCallback((size: number) => {
    setPageSize(size);
    setOffset(0);
  }, []);

  const formatCurrency = (value: any) =>
    value != null ? `$${Number(value).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : '$0.00';

  const getStatusVariant = (status: string) => {
    switch (status) {
      case 'active': return 'default' as const;
      case 'onboarding': return 'secondary' as const;
      default: return 'outline' as const;
    }
  };

  const columns: DataTableColumn<CompanyView>[] = [
    {
      id: 'name',
      label: 'Name',
      sortable: true,
      width: 200,
      format: (value, row) => (
        <span
          className="text-primary cursor-pointer hover:underline font-medium"
          onClick={() => navigate(`/customers/${row.id}`)}
        >
          {value}
        </span>
      ),
    },
    { id: 'clientId', label: 'Client ID', sortable: true, width: 120 },
    { id: 'inventoryLocationCount', label: 'Location Count', sortable: true, width: 120, align: 'right' },
    { id: 'inventoryMrc', label: 'Total MRC', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'inventoryMrr', label: 'Total MRR', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'inventoryNrr', label: 'Total NRR', sortable: true, width: 120, align: 'right', format: formatCurrency },
    { id: 'lastCompletedTask', label: 'Previous Task', width: 150 },
    { id: 'nextTask', label: 'Next Task', width: 150 },
    { id: 'remainingTasks', label: 'Tasks Remaining', width: 120, align: 'right' },
    { id: 'nextTaskAssignedTo', label: 'Task Assignment', width: 140 },
    {
      id: 'progressPercentage',
      label: 'Progress',
      sortable: true,
      width: 100,
      align: 'right',
      format: (value) => value != null ? `${value}%` : '0%',
    },
    { id: 'billingContactName', label: 'Billing Contact', width: 150 },
    { id: 'billingContactPhone', label: 'Billing Phone', width: 130 },
    { id: 'billingContactEmail', label: 'Billing Email', width: 180 },
    {
      id: 'status',
      label: 'Status',
      sortable: true,
      width: 100,
      format: (value) => (
        <Badge variant={getStatusVariant(value)}>
          {value || 'unknown'}
        </Badge>
      ),
    },
  ];

  return (
    <div className="mt-4">
      <Card className="mb-4">
        <CardContent className="p-4">
          <div className="flex gap-4 items-center">
            <Input
              placeholder="Search end customers..."
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
        error={error ? 'Failed to load end customers' : null}
        page={Math.floor(offset / pageSize)}
        pageSize={pageSize}
        totalElements={data?.total || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${data?.total || 0} End Customers`}
        exportFileName="end-customers"
        exportEnabled
      />
    </div>
  );
}
