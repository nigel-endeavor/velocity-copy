/**
 * Service Worklist Page
 * Main page for viewing and managing services
 */

import React, { useState } from 'react';
import { useAppDispatch, useAppSelector } from '@/store';
import { Table, Column, Input, Select, Button, Modal } from '@/components';
import { ServiceView, useListServiceViewsQuery } from '@/services/api/serviceViewsApi';
import { AppPage, PageHeader } from '@/components/layout/PageScaffold';
import {
  setSelectedServices,
  clearSelectedServices,
  setPage,
  setPageSize,
  selectSelectedServices,
  selectCurrentPage,
  selectPageSize,
} from './serviceWorklistSlice';

export function ServiceWorklist() {
  const dispatch = useAppDispatch();
  const selectedServices = useAppSelector(selectSelectedServices);
  const currentPage = useAppSelector(selectCurrentPage);
  const pageSize = useAppSelector(selectPageSize);

  const [showExportModal, setShowExportModal] = useState(false);
  const [companyFilter, setCompanyFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');

  // Calculate offset from page and pageSize
  const offset = (currentPage - 1) * pageSize;

  // Fetch service views via RTK Query
  const { data, isLoading, error } = useListServiceViewsQuery({ offset, limit: pageSize });

  const services = data?.collection ?? [];
  const totalItems = data?.total ?? 0;

  // Client-side filtering (backend search criteria can be added later)
  const filteredServices = services.filter((s) => {
    if (companyFilter && !s.companyName?.toLowerCase().includes(companyFilter.toLowerCase())) return false;
    if (statusFilter && s.status !== statusFilter) return false;
    if (typeFilter && s.type !== typeFilter) return false;
    return true;
  });

  // Define table columns matching ServiceView entity fields
  const columns: Column<ServiceView>[] = [
    {
      id: 'clientServiceId',
      label: 'Service ID',
      accessor: 'clientServiceId',
      sortable: true,
      render: (value, row) => (
        <a
          href={`#/services/${row.id}`}
          className="text-primary hover:text-primary/80 font-medium"
        >
          {String(value || `SVC-${row.id}`)}
        </a>
      ),
    },
    {
      id: 'companyName',
      label: 'Customer',
      accessor: 'companyName',
      sortable: true,
    },
    {
      id: 'address',
      label: 'Location',
      accessor: 'address',
      sortable: true,
    },
    {
      id: 'type',
      label: 'Type',
      accessor: 'type',
      sortable: true,
    },
    {
      id: 'status',
      label: 'Status',
      accessor: 'status',
      sortable: true,
      render: (value) => {
        if (!value) return null;
        const statusLower = String(value).toLowerCase();
        let colorClass = 'bg-gray-100 text-gray-800';
        if (statusLower.includes('progress') || statusLower.includes('active')) {
          colorClass = 'bg-blue-100 text-blue-800';
        } else if (statusLower.includes('complete') || statusLower.includes('done')) {
          colorClass = 'bg-green-100 text-green-800';
        } else if (statusLower.includes('cancel') || statusLower.includes('disconnect')) {
          colorClass = 'bg-red-100 text-red-800';
        } else if (statusLower.includes('hold') || statusLower.includes('pending')) {
          colorClass = 'bg-yellow-100 text-yellow-800';
        }
        return (
          <span className={`px-2 py-1 rounded-full text-xs font-medium ${colorClass}`}>
            {String(value)}
          </span>
        );
      },
    },
    {
      id: 'speed',
      label: 'Bandwidth',
      accessor: 'speed',
    },
    {
      id: 'projectManager',
      label: 'Project Manager',
      accessor: 'projectManager',
      sortable: true,
    },
    {
      id: 'provider',
      label: 'Provider',
      accessor: 'provider',
    },
    {
      id: 'mrc',
      label: 'MRC',
      accessor: 'mrc',
      render: (value) => value != null ? `$${Number(value).toLocaleString()}` : '',
    },
    {
      id: 'statusAge',
      label: 'Status Age',
      accessor: 'statusAge',
      sortable: true,
      render: (value) => value != null ? `${value} days` : '',
    },
  ];

  // Handle export
  const handleExport = () => {
    setShowExportModal(false);
  };

  // Handle pagination
  const handlePaginationChange = (page: number, size: number) => {
    if (size !== pageSize) {
      dispatch(setPageSize(size));
    } else {
      dispatch(setPage(page));
    }
  };

  // Handle row click
  const handleRowClick = (service: ServiceView) => {
    window.location.hash = `/services/${service.id}`;
  };

  const handleClearFilters = () => {
    setCompanyFilter('');
    setStatusFilter('');
    setTypeFilter('');
  };

  const statusOptions = [
    { value: '', label: 'All Statuses' },
    { value: 'In Progress', label: 'In Progress' },
    { value: 'Complete', label: 'Complete' },
    { value: 'On Hold', label: 'On Hold' },
    { value: 'Cancelled', label: 'Cancelled' },
  ];

  const serviceTypeOptions = [
    { value: '', label: 'All Types' },
    { value: 'DIA', label: 'DIA' },
    { value: 'Broadband', label: 'Broadband' },
    { value: 'Ethernet', label: 'Ethernet' },
    { value: 'MPLS', label: 'MPLS' },
    { value: 'Voice', label: 'Voice' },
  ];

  const errorMessage = error ? ('status' in error ? `Error ${error.status}` : error.message) : null;

  return (
    <AppPage>
      <PageHeader
        eyebrow="Delivery operations"
        title="Service Worklist"
        description="Track provisioning status, ownership, and provider execution from one viewport-contained workspace."
        actions={
          <>
            <Button
              variant="secondary"
              onClick={() => dispatch(clearSelectedServices())}
              disabled={selectedServices.size === 0}
            >
              Clear Selection ({selectedServices.size})
            </Button>
            <Button
              variant="primary"
              onClick={() => setShowExportModal(true)}
              disabled={selectedServices.size === 0}
            >
              Export Selected
            </Button>
          </>
        }
        stats={[
          {
            label: 'Services in view',
            value: totalItems.toLocaleString(),
            detail: `${filteredServices.length} visible now`,
            tone: 'brand',
          },
          {
            label: 'Selection',
            value: selectedServices.size.toString(),
            detail: 'Ready for export',
            tone: selectedServices.size > 0 ? 'warm' : 'neutral',
          },
        ]}
      />

      {/* Error Alert */}
      {errorMessage && (
        <div className="rounded-2xl border border-red-200 bg-red-50 p-4">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-red-400" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
              </svg>
            </div>
            <div className="ml-3">
              <p className="text-sm text-red-800">{errorMessage}</p>
            </div>
          </div>
        </div>
      )}

      {/* Search Filters */}
      <div className="app-surface p-5">
        <div className="mb-4 flex flex-wrap items-end justify-between gap-3">
          <div>
            <h2 className="text-lg font-semibold text-slate-900">Filters</h2>
            <p className="mt-1 text-sm text-slate-500">Refine the current service page by customer, lifecycle status, or service type.</p>
          </div>
          <span className="app-page-toolbar-note">Pagination remains inside the content frame.</span>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <Input
            label="Customer Name"
            placeholder="Search by customer..."
            value={companyFilter}
            onChange={(e) => setCompanyFilter(e.target.value)}
            fullWidth
          />
          <Select
            label="Service Type"
            options={serviceTypeOptions}
            value={typeFilter}
            onChange={(e) => setTypeFilter(e.target.value)}
            fullWidth
          />
          <Select
            label="Status"
            options={statusOptions}
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            fullWidth
          />
        </div>
        <div className="mt-4 flex justify-end space-x-3">
          <Button variant="secondary" onClick={handleClearFilters}>
            Clear Filters
          </Button>
        </div>
      </div>

      {/* Services Table */}
      <Table
        data={filteredServices}
        columns={columns}
        loading={isLoading}
        emptyMessage="No services found. Try adjusting your search criteria."
        selectable
        selectionMode="multiple"
        selectedRows={selectedServices}
        onSelectionChange={(selected) => dispatch(setSelectedServices(selected as Set<number>))}
        rowId="id"
        pagination={{
          currentPage,
          pageSize,
          totalItems,
          pageSizeOptions: [10, 25, 50, 100],
        }}
        onPaginationChange={handlePaginationChange}
        onRowClick={handleRowClick}
        hoverable
        density="normal"
      />

      {/* Export Confirmation Modal */}
      <Modal
        isOpen={showExportModal}
        onClose={() => setShowExportModal(false)}
        title="Export Services"
        size="md"
        footer={
          <div className="flex justify-end space-x-3">
            <Button variant="secondary" onClick={() => setShowExportModal(false)}>
              Cancel
            </Button>
            <Button variant="primary" onClick={handleExport}>
              Export {selectedServices.size} Services
            </Button>
          </div>
        }
      >
        <p className="text-gray-600">
          You are about to export {selectedServices.size} selected service(s) to an Excel file.
          Do you want to continue?
        </p>
      </Modal>
    </AppPage>
  );
}
