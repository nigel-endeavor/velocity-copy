/**
 * Disputes Worklist Component
 *
 * Billing dispute management and tracking
 */

import { useState, useCallback } from 'react';
import { Search, X } from 'lucide-react';
import { format } from 'date-fns';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { useSearchDisputesQuery, type Dispute } from '@/services/api/disputesApi';
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
 * Search criteria for disputes
 */
interface SearchCriteria {
  keyword?: string;
  orderId?: string;
  serviceId?: string;
  disputeType?: string;
  status?: string;
  priority?: string;
  pageNumber: number;
  pageSize: number;
  sortBy?: string;
  sortOrder: 'asc' | 'desc';
}

/**
 * Disputes Worklist Component
 */
export default function DisputesWorklist() {
  // Search criteria
  const [criteria, setCriteria] = useState<SearchCriteria>({
    pageNumber: 0,
    pageSize: 25,
    sortOrder: 'desc',
    sortBy: 'createdDate',
  });

  // Fetch disputes
  const { data, isLoading, error, refetch } = useSearchDisputesQuery(criteria);

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
      sortBy: 'createdDate',
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

  // Get status variant
  const getStatusVariant = (status: string): 'default' | 'success' | 'warning' | 'info' | 'destructive' => {
    switch (status) {
      case 'Resolved':
      case 'Closed':
        return 'success';
      case 'In Progress':
      case 'Under Review':
        return 'warning';
      case 'Open':
      case 'New':
        return 'info';
      case 'Rejected':
      case 'Denied':
        return 'destructive';
      default:
        return 'default';
    }
  };

  // Table columns
  const columns: DataTableColumn<Dispute>[] = [
    {
      id: 'id',
      label: 'Dispute ID',
      sortable: true,
      width: 120,
    },
    {
      id: 'serviceId',
      label: 'Service ID',
      sortable: true,
      width: 140,
      format: (value) => value || 'N/A',
      exportFormat: (value) => value || '',
    },
    {
      id: 'disputeType',
      label: 'Dispute Type',
      sortable: true,
      width: 150,
    },
    {
      id: 'disputeStatus',
      label: 'Status',
      sortable: true,
      width: 150,
      format: (value) => (
        <Badge variant={getStatusVariant(value || '')}>
          {value || 'Unknown'}
        </Badge>
      ),
      exportFormat: (value) => value || 'Unknown',
    },
    {
      id: 'disputeAssignment',
      label: 'Assignment',
      sortable: true,
      width: 160,
      format: (value) => value || 'Unassigned',
      exportFormat: (value) => value || 'Unassigned',
    },
    {
      id: 'amountDisputedMrc',
      label: 'MRC Amount',
      sortable: true,
      width: 120,
      align: 'right',
      format: (value) => `$${(value || 0).toFixed(2)}`,
      exportFormat: (value) => value || 0,
    },
    {
      id: 'amountDisputedNrc',
      label: 'NRC Amount',
      sortable: true,
      width: 120,
      align: 'right',
      format: (value) => `$${(value || 0).toFixed(2)}`,
      exportFormat: (value) => value || 0,
    },
    {
      id: 'openDate',
      label: 'Opened',
      sortable: true,
      width: 140,
      format: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : 'Not set',
      exportFormat: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : '',
    },
    {
      id: 'disputeClosedDate',
      label: 'Closed',
      sortable: true,
      width: 140,
      format: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : 'Not resolved',
      exportFormat: (value) =>
        value ? format(new Date(value), 'MM/dd/yyyy') : '',
    },
    {
      id: 'assignedTo',
      label: 'Assigned To',
      sortable: true,
      width: 150,
      format: (value) => value || 'Unassigned',
    },
  ];

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Disputes Worklist</h1>

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
                placeholder="Service ID"
                value={criteria.serviceId || ''}
                onChange={(e) => setCriteria({ ...criteria, serviceId: e.target.value })}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>

            <div className="col-span-12 md:col-span-2">
              <Select
                value={criteria.disputeType || ''}
                onValueChange={(value) => setCriteria({ ...criteria, disputeType: value })}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Dispute Type" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">All</SelectItem>
                  <SelectItem value="Billing">Billing</SelectItem>
                  <SelectItem value="Service">Service</SelectItem>
                  <SelectItem value="Installation">Installation</SelectItem>
                  <SelectItem value="Equipment">Equipment</SelectItem>
                  <SelectItem value="Other">Other</SelectItem>
                </SelectContent>
              </Select>
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
                  <SelectItem value="New">New</SelectItem>
                  <SelectItem value="Open">Open</SelectItem>
                  <SelectItem value="In Progress">In Progress</SelectItem>
                  <SelectItem value="Under Review">Under Review</SelectItem>
                  <SelectItem value="Resolved">Resolved</SelectItem>
                  <SelectItem value="Closed">Closed</SelectItem>
                  <SelectItem value="Rejected">Rejected</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="col-span-12 md:col-span-1">
              <Select
                value={criteria.priority || ''}
                onValueChange={(value) => setCriteria({ ...criteria, priority: value })}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Priority" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">All</SelectItem>
                  <SelectItem value="Critical">Critical</SelectItem>
                  <SelectItem value="High">High</SelectItem>
                  <SelectItem value="Medium">Medium</SelectItem>
                  <SelectItem value="Low">Low</SelectItem>
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
        error={error ? 'Failed to load disputes' : null}
        sortBy={criteria.sortBy}
        sortOrder={criteria.sortOrder}
        onSort={handleSort}
        page={criteria.pageNumber}
        pageSize={criteria.pageSize}
        totalElements={data?.totalElements || 0}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
        onRefresh={refetch}
        title={`${data?.totalElements || 0} Disputes`}
        exportFileName="disputes-worklist"
        exportEnabled
      />
    </div>
  );
}
