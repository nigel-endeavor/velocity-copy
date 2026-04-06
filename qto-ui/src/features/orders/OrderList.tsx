/**
 * Order List Page
 *
 * Displays a paginated table of orders with search, sort, and navigation
 * Mirrors Angular's order list/worklist functionality
 */

import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Plus, Search, X } from 'lucide-react';
import DataTable, { DataTableColumn } from '@/shared/components/DataTable/DataTable';
import { useListOrderViewsQuery } from '@/services/api/ordersApi';
import { OrderListItem } from '@/shared/types/models';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { AppPage, PageHeader, PageToolbar } from '@/components/layout/PageScaffold';

/**
 * Status badge color mapping
 */
function getStatusBadge(status: string) {
  const colors: Record<string, string> = {
    'New Order': 'bg-blue-100 text-blue-800',
    'In Progress': 'bg-yellow-100 text-yellow-800',
    'Pending': 'bg-orange-100 text-orange-800',
    'Complete': 'bg-green-100 text-green-800',
    'Completed': 'bg-green-100 text-green-800',
    'Cancelled': 'bg-red-100 text-red-800',
  };
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${colors[status] || 'bg-gray-100 text-gray-800'}`}>
      {status}
    </span>
  );
}

/**
 * Format currency
 */
function formatCurrency(value: number | null | undefined): string {
  if (value == null || isNaN(value)) return '$0.00';
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value);
}

/**
 * Order List Component
 */
export default function OrderList() {
  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(25);
  const [searchText, setSearchText] = useState('');
  const [debouncedSearch, setDebouncedSearch] = useState('');
  const [sortBy, setSortBy] = useState<string>('id');
  const [sortOrder, setSortOrder] = useState<'asc' | 'desc'>('desc');

  // Debounce: wait 300ms after the user stops typing before sending to the server
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedSearch(searchText);
      setPage(0); // reset to first page on new search
    }, 300);
    return () => clearTimeout(timer);
  }, [searchText]);

  const { data, isLoading, error, refetch } = useListOrderViewsQuery({
    offset: page * pageSize,
    limit: pageSize,
    search: debouncedSearch || undefined,
  });

  const orders = data?.collection || [];
  const totalElements = data?.total || 0;

  const columns: DataTableColumn<OrderListItem>[] = [
    {
      id: 'id',
      label: 'Order ID',
      sortable: true,
      width: 100,
      format: (value: number) => (
        <button
          className="text-blue-600 hover:underline font-medium"
          onClick={(e) => {
            e.stopPropagation();
            navigate(`/orders/${value}`);
          }}
        >
          {value}
        </button>
      ),
    },
    {
      id: 'clientOrderId',
      label: 'Client Order ID',
      sortable: true,
    },
    {
      id: 'companyName',
      label: 'Customer',
      sortable: true,
    },
    {
      id: 'status',
      label: 'Status',
      sortable: true,
      width: 140,
      format: (value: string) => getStatusBadge(value),
      exportFormat: (value: string) => value,
    },
    {
      id: 'vertekClient',
      label: 'Vertek Client',
      sortable: true,
    },
    {
      id: 'locationCount',
      label: 'Locations',
      sortable: false,
      align: 'center',
      width: 100,
      format: (value: number) => value ?? 0,
      exportFormat: (value: number) => value ?? 0,
    },
    {
      id: 'mrc',
      label: 'MRC',
      sortable: true,
      align: 'right',
      width: 120,
      format: (value: number) => formatCurrency(value),
      exportFormat: (value: number) => value || 0,
    },
    {
      id: 'nrc',
      label: 'NRC',
      sortable: true,
      align: 'right',
      width: 120,
      format: (value: number) => formatCurrency(value),
      exportFormat: (value: number) => value || 0,
    },
  ];

  const handleSort = (field: string, order: 'asc' | 'desc') => {
    setSortBy(field);
    setSortOrder(order);
  };

  const headerStats = [
    {
      label: 'Orders in view',
      value: totalElements.toLocaleString(),
      detail: debouncedSearch ? 'Filtered results' : 'Full active list',
      tone: 'brand' as const,
    },
    {
      label: 'Rows per page',
      value: pageSize.toString(),
      detail: `Page ${page + 1}`,
      tone: 'warm' as const,
    },
  ];

  return (
    <AppPage>
      <PageHeader
        eyebrow="Order operations"
        title="Orders"
        description="Manage telecom orders, locations, and service delivery without losing the current operational context."
        actions={
          <Button className="h-11 rounded-xl" onClick={() => navigate('/orders/new')}>
            <Plus className="mr-2 h-4 w-4" />
            New Order
          </Button>
        }
        stats={headerStats}
      />

      <PageToolbar>
        <div className="relative min-w-[18rem] flex-1">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
          <Input
            placeholder="Search orders by ID, customer, status..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            className="h-11 rounded-xl border-slate-200 bg-white pl-10 pr-10 shadow-none"
          />
          {searchText && (
            <button
              className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
              onClick={() => setSearchText('')}
            >
              <X className="h-4 w-4" />
            </button>
          )}
        </div>
        <span className="app-page-toolbar-note">
          {debouncedSearch ? `Searching for “${debouncedSearch}”` : 'Server-backed list view'}
        </span>
      </PageToolbar>

      <DataTable
        columns={columns}
        data={orders}
        loading={isLoading}
        error={error ? 'Failed to load orders' : null}
        sortBy={sortBy}
        sortOrder={sortOrder}
        onSort={handleSort}
        page={page}
        pageSize={pageSize}
        totalElements={totalElements}
        onPageChange={setPage}
        onPageSizeChange={(size) => {
          setPageSize(size);
          setPage(0);
        }}
        onRefresh={refetch}
        title="Order List"
        exportFileName="orders"
        exportEnabled
      />
    </AppPage>
  );
}
