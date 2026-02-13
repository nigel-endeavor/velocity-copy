/**
 * Locations Worklist Component
 *
 * Displays all locations across orders with search, filter, and pagination
 */

import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, X } from 'lucide-react';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { useSearchLocationsQuery } from '@/services/api/locationsApi';
import type { Location } from '@/shared/types/models';
import { formatLocationAddress } from '@/shared/types/models/location.model';
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
 * Search criteria state
 */
interface SearchCriteria {
  keyword?: string;
  orderId?: string;
  companyId?: string;
  clientLocationId?: string;
  status?: string;
  city?: string;
  state?: string;
  pageNumber: number;
  pageSize: number;
  sortBy?: string;
  sortOrder: 'asc' | 'desc';
}

/**
 * Locations Worklist Component
 */
export default function LocationsWorklist() {
  const navigate = useNavigate();

  // Search criteria
  const [criteria, setCriteria] = useState<SearchCriteria>({
    pageNumber: 0,
    pageSize: 25,
    sortOrder: 'desc',
  });

  // Fetch locations
  const { data, isLoading, error, refetch } = useSearchLocationsQuery(criteria);

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
    (location: Location) => {
      if (location.orderId) {
        navigate(`/orders/${location.orderId}`);
      }
    },
    [navigate]
  );

  // Table columns
  const columns: DataTableColumn<Location>[] = [
    {
      id: 'clientLocationId',
      label: 'Location ID',
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
      id: 'address1',
      label: 'Address',
      sortable: false,
      width: 250,
      format: (_value, row) => formatLocationAddress(row) || 'No address',
      exportFormat: (_value, row) => formatLocationAddress(row) || '',
    },
    {
      id: 'city',
      label: 'City',
      sortable: true,
      width: 150,
    },
    {
      id: 'state',
      label: 'State',
      sortable: true,
      width: 80,
      align: 'center',
    },
    {
      id: 'postalCode',
      label: 'Zip Code',
      sortable: true,
      width: 100,
    },
    {
      id: 'status',
      label: 'Status',
      sortable: true,
      width: 150,
      format: (value) => (
        <Badge variant={value === 'Location Cancelled' ? 'destructive' : 'default'}>
          {value || 'Unknown'}
        </Badge>
      ),
      exportFormat: (value) => value || 'Unknown',
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
    {
      id: 'nrc',
      label: 'NRC',
      sortable: true,
      width: 100,
      align: 'right',
      format: (value) => `$${(value || 0).toFixed(2)}`,
      exportFormat: (value) => value || 0,
    },
  ];

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Locations Worklist</h1>

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
                placeholder="Location ID"
                value={criteria.clientLocationId || ''}
                onChange={(e) =>
                  setCriteria({ ...criteria, clientLocationId: e.target.value })
                }
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-2">
              <Input
                placeholder="City"
                value={criteria.city || ''}
                onChange={(e) => setCriteria({ ...criteria, city: e.target.value })}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-1">
              <Input
                placeholder="State"
                value={criteria.state || ''}
                onChange={(e) => setCriteria({ ...criteria, state: e.target.value })}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-2">
              <Select
                value={criteria.status || ''}
                onValueChange={(value) => setCriteria({ ...criteria, status: value })}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">All</SelectItem>
                  <SelectItem value="New Location">New Location</SelectItem>
                  <SelectItem value="In Progress">In Progress</SelectItem>
                  <SelectItem value="Completed">Completed</SelectItem>
                  <SelectItem value="Location Cancelled">Cancelled</SelectItem>
                </SelectContent>
              </Select>
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
        error={error ? 'Failed to load locations' : null}
        sortBy={criteria.sortBy}
        sortOrder={criteria.sortOrder}
        onSort={handleSort}
        page={criteria.pageNumber}
        pageSize={criteria.pageSize}
        totalElements={data?.totalElements || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${data?.totalElements || 0} Locations`}
        exportFileName="locations-worklist"
        exportEnabled
      />
    </div>
  );
}
