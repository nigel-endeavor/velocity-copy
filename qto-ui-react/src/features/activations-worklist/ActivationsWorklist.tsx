/**
 * Activations Worklist Component
 *
 * Tracks service activation requests and status
 */

import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, X } from 'lucide-react';
import { format } from 'date-fns';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { useSearchServicesQuery } from '@/services/api/servicesApi';
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
 * Search criteria for activations
 */
interface SearchCriteria {
  keyword?: string;
  orderId?: string;
  companyId?: string;
  provider?: string;
  status?: string;
  pageNumber: number;
  pageSize: number;
  sortBy?: string;
  sortOrder: 'asc' | 'desc';
}

/**
 * Activations Worklist Component
 */
export default function ActivationsWorklist() {
  const navigate = useNavigate();

  // Search criteria
  const [criteria, setCriteria] = useState<SearchCriteria>({
    pageNumber: 0,
    pageSize: 25,
    sortOrder: 'desc',
    sortBy: 'activationDate',
  });

  // Fetch services
  const { data, isLoading, error, refetch } = useSearchServicesQuery(criteria);

  // Handle search
  const handleSearch = useCallback(() => {
    setCriteria((prev) => ({ ...prev, pageNumber: 0 }));
    refetch();
  }, [refetch]);

  // Handle clear
  const handleClear = useCallback(() => {
    setCriteria({
      pageNumber: 0,
      pageSize: 25,
      sortOrder: 'desc',
      sortBy: 'activationDate',
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
      case 'Activated':
      case 'Active':
        return 'success';
      case 'Activation Pending':
      case 'Activation In Progress':
        return 'warning';
      case 'Activation Requested':
        return 'info';
      case 'Activation Failed':
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
      id: 'activationDate',
      label: 'Activation Date',
      sortable: true,
      width: 140,
      format: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : 'Not set',
      exportFormat: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : '',
    },
    {
      id: 'rfsDate',
      label: 'RFS Date',
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
      <h1 className="text-3xl font-bold mb-6">Activations Worklist</h1>

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
                value={criteria.orderId || ''}
                onChange={(e) => setCriteria({ ...criteria, orderId: e.target.value })}
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
                  <SelectItem value="Activation Requested">Activation Requested</SelectItem>
                  <SelectItem value="Activation Pending">Activation Pending</SelectItem>
                  <SelectItem value="Activation In Progress">Activation In Progress</SelectItem>
                  <SelectItem value="Activated">Activated</SelectItem>
                  <SelectItem value="Active">Active</SelectItem>
                  <SelectItem value="Activation Failed">Activation Failed</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="col-span-12 md:col-span-2">
              <Input
                placeholder="Company ID"
                value={criteria.companyId || ''}
                onChange={(e) => setCriteria({ ...criteria, companyId: e.target.value })}
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
        error={error ? 'Failed to load activations' : null}
        sortBy={criteria.sortBy}
        sortOrder={criteria.sortOrder}
        onSort={handleSort}
        page={criteria.pageNumber}
        pageSize={criteria.pageSize}
        totalElements={data?.totalElements || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${data?.totalElements || 0} Activations`}
        exportFileName="activations-worklist"
        exportEnabled
      />
    </div>
  );
}
