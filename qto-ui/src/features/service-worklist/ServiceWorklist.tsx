/**
 * Service Worklist Page
 * Main page for viewing and managing services
 */

import React, { useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from '@/store';
import { Table, Column, Input, Select, Button, Modal } from '@/components';
import { Service, ServiceSearchCriteria } from './types';
import {
  fetchServices,
  exportServices,
  setSearchCriteria,
  setSelectedServices,
  clearSelectedServices,
  setPage,
  setPageSize,
  selectServices,
  selectLoading,
  selectError,
  selectSearchCriteria,
  selectSelectedServices,
  selectTotalItems,
  selectCurrentPage,
  selectPageSize,
} from './serviceWorklistSlice';

export function ServiceWorklist() {
  const dispatch = useAppDispatch();
  const services = useAppSelector(selectServices);
  const loading = useAppSelector(selectLoading);
  const error = useAppSelector(selectError);
  const searchCriteria = useAppSelector(selectSearchCriteria);
  const selectedServices = useAppSelector(selectSelectedServices);
  const totalItems = useAppSelector(selectTotalItems);
  const currentPage = useAppSelector(selectCurrentPage);
  const pageSize = useAppSelector(selectPageSize);

  const [showExportModal, setShowExportModal] = useState(false);
  const [localCriteria, setLocalCriteria] = useState<ServiceSearchCriteria>(searchCriteria);

  // Load services on mount and when search criteria changes
  useEffect(() => {
    dispatch(fetchServices(searchCriteria));
  }, [dispatch, searchCriteria]);

  // Define table columns
  const columns: Column<Service>[] = [
    {
      id: 'serviceId',
      label: 'Service ID',
      accessor: 'serviceId',
      sortable: true,
      render: (value, row) => (
        <a
          href={`#/services/${row.id}`}
          className="text-primary-600 hover:text-primary-700 font-medium"
        >
          {value}
        </a>
      ),
    },
    {
      id: 'customerName',
      label: 'Customer',
      accessor: 'customerName',
      sortable: true,
    },
    {
      id: 'locationName',
      label: 'Location',
      accessor: 'locationName',
      sortable: true,
    },
    {
      id: 'serviceType',
      label: 'Type',
      accessor: 'serviceType',
      sortable: true,
    },
    {
      id: 'status',
      label: 'Status',
      accessor: 'status',
      sortable: true,
      render: (value) => {
        const colors = {
          PENDING: 'bg-yellow-100 text-yellow-800',
          IN_PROGRESS: 'bg-blue-100 text-blue-800',
          COMPLETED: 'bg-green-100 text-green-800',
          CANCELLED: 'bg-red-100 text-red-800',
        };
        return (
          <span className={`px-2 py-1 rounded-full text-xs font-medium ${colors[value as keyof typeof colors]}`}>
            {value.replace('_', ' ')}
          </span>
        );
      },
    },
    {
      id: 'priority',
      label: 'Priority',
      accessor: 'priority',
      sortable: true,
      render: (value) => {
        const colors = {
          LOW: 'text-gray-600',
          MEDIUM: 'text-blue-600',
          HIGH: 'text-orange-600',
          CRITICAL: 'text-red-600',
        };
        return (
          <span className={`font-medium ${colors[value as keyof typeof colors]}`}>
            {value}
          </span>
        );
      },
    },
    {
      id: 'bandwidth',
      label: 'Bandwidth',
      accessor: 'bandwidth',
    },
    {
      id: 'orderDate',
      label: 'Order Date',
      accessor: 'orderDate',
      sortable: true,
      render: (value) => new Date(value).toLocaleDateString(),
    },
    {
      id: 'dueDate',
      label: 'Due Date',
      accessor: 'dueDate',
      sortable: true,
      render: (value) => new Date(value).toLocaleDateString(),
    },
    {
      id: 'assignedTo',
      label: 'Assigned To',
      accessor: 'assignedTo',
    },
  ];

  // Handle search
  const handleSearch = () => {
    dispatch(setSearchCriteria(localCriteria));
  };

  // Handle clear filters
  const handleClearFilters = () => {
    const clearedCriteria: ServiceSearchCriteria = {
      page: 1,
      pageSize: pageSize,
      sortBy: 'orderDate',
      sortOrder: 'desc',
    };
    setLocalCriteria(clearedCriteria);
    dispatch(setSearchCriteria(clearedCriteria));
  };

  // Handle export
  const handleExport = () => {
    const serviceIds = Array.from(selectedServices);
    if (serviceIds.length === 0) {
      alert('Please select services to export');
      return;
    }
    dispatch(exportServices(serviceIds));
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
  const handleRowClick = (service: Service) => {
    window.location.hash = `/services/${service.id}`;
  };

  const statusOptions = [
    { value: '', label: 'All Statuses' },
    { value: 'PENDING', label: 'Pending' },
    { value: 'IN_PROGRESS', label: 'In Progress' },
    { value: 'COMPLETED', label: 'Completed' },
    { value: 'CANCELLED', label: 'Cancelled' },
  ];

  const priorityOptions = [
    { value: '', label: 'All Priorities' },
    { value: 'LOW', label: 'Low' },
    { value: 'MEDIUM', label: 'Medium' },
    { value: 'HIGH', label: 'High' },
    { value: 'CRITICAL', label: 'Critical' },
  ];

  const serviceTypeOptions = [
    { value: '', label: 'All Types' },
    { value: 'INTERNET', label: 'Internet' },
    { value: 'VOICE', label: 'Voice' },
    { value: 'DATA', label: 'Data' },
    { value: 'CLOUD', label: 'Cloud' },
  ];

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Service Worklist</h1>
          <p className="mt-1 text-sm text-gray-500">
            Manage and track service orders
          </p>
        </div>
        <div className="flex space-x-3">
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
        </div>
      </div>

      {/* Error Alert */}
      {error && (
        <div className="bg-red-50 border border-red-200 rounded-lg p-4">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-red-400" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
              </svg>
            </div>
            <div className="ml-3">
              <p className="text-sm text-red-800">{error}</p>
            </div>
          </div>
        </div>
      )}

      {/* Search Filters */}
      <div className="bg-white rounded-lg shadow p-6">
        <h2 className="text-lg font-semibold text-gray-900 mb-4">Search Filters</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <Input
            label="Customer Name"
            placeholder="Search by customer..."
            value={localCriteria.customerName || ''}
            onChange={(e) => setLocalCriteria({ ...localCriteria, customerName: e.target.value })}
            fullWidth
          />
          <Select
            label="Service Type"
            options={serviceTypeOptions}
            value={localCriteria.serviceType || ''}
            onChange={(e) => setLocalCriteria({ ...localCriteria, serviceType: e.target.value })}
            fullWidth
          />
          <Select
            label="Status"
            options={statusOptions}
            value={localCriteria.status || ''}
            onChange={(e) => setLocalCriteria({ ...localCriteria, status: e.target.value })}
            fullWidth
          />
          <Select
            label="Priority"
            options={priorityOptions}
            value={localCriteria.priority || ''}
            onChange={(e) => setLocalCriteria({ ...localCriteria, priority: e.target.value })}
            fullWidth
          />
        </div>
        <div className="mt-4 flex justify-end space-x-3">
          <Button variant="secondary" onClick={handleClearFilters}>
            Clear Filters
          </Button>
          <Button variant="primary" onClick={handleSearch}>
            Search
          </Button>
        </div>
      </div>

      {/* Services Table */}
      <Table
        data={services}
        columns={columns}
        loading={loading}
        emptyMessage="No services found. Try adjusting your search criteria."
        selectable
        selectionMode="multiple"
        selectedRows={selectedServices}
        onSelectionChange={(selected) => dispatch(setSelectedServices(selected))}
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
    </div>
  );
}
