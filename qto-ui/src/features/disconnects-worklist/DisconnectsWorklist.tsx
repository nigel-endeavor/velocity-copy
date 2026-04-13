/**
 * Disconnects Worklist Component
 *
 * Tracks service disconnection requests and status
 */

import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, X } from 'lucide-react';
import { format } from 'date-fns';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { useSearchServicesQuery, type ServiceSearchCriteria } from '@/services/api/servicesApi';
import type { Service } from '@/shared/types/models';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import { Card, CardContent } from '@/components/ui/card';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';

/**
 * Search criteria for disconnects
 */
interface SearchCriteria {
  keyword?: string;
  orderId?: number;
  companyId?: number;
  provider?: string;
  status?: string;
  orderType?: string; // Filter for disconnect types
  pageNumber: number;
  pageSize: number;
  sortBy?: string;
  sortOrder: 'asc' | 'desc';
}

/**
 * Disconnects Worklist Component
 */
export default function DisconnectsWorklist() {
  const navigate = useNavigate();

  // Search criteria - filter for disconnect services
  const [criteria, setCriteria] = useState<SearchCriteria>({
    orderType: 'Disconnect', // Pre-filter for disconnects
    pageNumber: 0,
    pageSize: 25,
    sortOrder: 'desc',
  });

  // Fetch services
  const queryCriteria: ServiceSearchCriteria = criteria;
  const { data, isLoading, error, refetch } = useSearchServicesQuery(queryCriteria);

  // Handle search
  const handleSearch = useCallback(() => {
    setCriteria((prev) => ({ ...prev, pageNumber: 0 }));
    refetch();
  }, [refetch]);

  // Handle clear
  const handleClear = useCallback(() => {
    setCriteria({
      orderType: 'Disconnect',
      pageNumber: 0,
      pageSize: 25,
      sortOrder: 'desc',
    });
  }, []);

  // Handle sort
  const handleSort = useCallback((sortBy: string, sortOrder: 'asc' | 'desc') => {
    setCriteria((prev) => ({ ...prev, sortBy, sortOrder, pageNumber: 0 }));
  }, []);

  // Handle page change
  const handlePageChange = useCallback((page: number) => {
    setCriteria((prev) => ({ ...prev, pageNumber: page }));
  }, []);

  // Handle page size change
  const handlePageSizeChange = useCallback((pageSize: number) => {
    setCriteria((prev) => ({ ...prev, pageSize, pageNumber: 0 }));
  }, []);

  // Handle row click
  const handleRowClick = useCallback(
    (service: Service) => {
      if (service.orderId) {
        navigate(`/orders/${service.orderId}`);
      }
    },
    [navigate]
  );

  // Get status variant
  const getStatusVariant = (status: string): 'default' | 'success' | 'warning' | 'info' | 'destructive' => {
    switch (status) {
      case 'Disconnected':
        return 'success';
      case 'Disconnect Pending':
        return 'warning';
      case 'Disconnect Requested':
        return 'info';
      case 'Service Cancelled':
        return 'destructive';
      default:
        return 'default';
    }
  };

  // Table columns
  const columns: DataTableColumn<Service>[] = [
    {
      id: 'clientServiceId',
      label: 'Service ID',
      sortable: true,
      width: 150,
      format: (value) => value || 'N/A',
    },
    {
      id: 'orderId',
      label: 'Order ID',
      sortable: true,
      width: 120,
      format: (_value, row) => (
        <span
          className="text-primary cursor-pointer hover:underline"
          onClick={() => handleRowClick(row)}
        >
          {row.orderId}
        </span>
      ),
      exportFormat: (_value, row) => row.orderId || '',
    },
    {
      id: 'type',
      label: 'Service Type',
      sortable: true,
      width: 150,
    },
    {
      id: 'provider',
      label: 'Provider',
      sortable: true,
      width: 150,
    },
    {
      id: 'circuitId',
      label: 'Circuit ID',
      sortable: false,
      width: 180,
      format: (value) => value || 'N/A',
    },
    {
      id: 'status',
      label: 'Status',
      sortable: true,
      width: 180,
      format: (value) => (
        <Badge variant={getStatusVariant(value || '')}>
          {value || 'Unknown'}
        </Badge>
      ),
      exportFormat: (value) => value || 'Unknown',
    },
    {
      id: 'disconnectDate',
      label: 'Disconnect Date',
      sortable: true,
      width: 140,
      format: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : 'Not set',
      exportFormat: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : '',
    },
    {
      id: 'mrc',
      label: 'MRC',
      sortable: true,
      width: 100,
      align: 'right',
      format: (value) => `$${(value || 0).toFixed(2)}`,
      exportFormat: (value) => value || 0,
    },
  ];

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Disconnects Worklist</h1>

      {/* Search Filters */}
      <Card className="mb-4">
        <CardContent className="p-4">
          <div className="grid grid-cols-12 gap-4">
            <div className="col-span-12 md:col-span-3">
              <Input
                placeholder="Keyword"
                value={criteria.keyword || ''}
                onChange={(e) => setCriteria({ ...criteria, keyword: e.target.value })}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-2">
              <Input
                placeholder="Order ID"
                value={criteria.orderId?.toString() || ''}
                onChange={(e) =>
                  setCriteria({
                    ...criteria,
                    orderId: e.target.value ? Number(e.target.value) : undefined,
                  })
                }
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-2">
              <Input
                placeholder="Provider"
                value={criteria.provider || ''}
                onChange={(e) => setCriteria({ ...criteria, provider: e.target.value })}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-3">
              <Select
                value={criteria.status || ''}
                onValueChange={(value) => setCriteria({ ...criteria, status: value })}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">All</SelectItem>
                  <SelectItem value="Disconnect Requested">Disconnect Requested</SelectItem>
                  <SelectItem value="Disconnect Pending">Disconnect Pending</SelectItem>
                  <SelectItem value="Disconnected">Disconnected</SelectItem>
                  <SelectItem value="Service Cancelled">Cancelled</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="col-span-12 md:col-span-2">
              <Input
                placeholder="Company ID"
                value={criteria.companyId?.toString() || ''}
                onChange={(e) =>
                  setCriteria({
                    ...criteria,
                    companyId: e.target.value ? Number(e.target.value) : undefined,
                  })
                }
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12">
              <div className="flex gap-2">
                <Button onClick={handleSearch}>
                  <Search className="mr-2 h-4 w-4" />
                  Search
                </Button>
                <Button variant="outline" onClick={handleClear}>
                  <X className="mr-2 h-4 w-4" />
                  Clear
                </Button>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Results Table */}
      <DataTable
        columns={columns}
        data={data?.content || []}
        loading={isLoading}
        error={error ? 'Failed to load disconnects' : null}
        sortBy={criteria.sortBy}
        sortOrder={criteria.sortOrder}
        onSort={handleSort}
        page={criteria.pageNumber}
        pageSize={criteria.pageSize}
        totalElements={data?.totalElements || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${data?.totalElements || 0} Disconnects`}
        exportFileName="disconnects-worklist"
        exportEnabled
      />
    </div>
  );
}
