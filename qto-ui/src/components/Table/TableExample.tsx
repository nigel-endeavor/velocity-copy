/**
 * Table Component Example
 * Demonstrates all table features with sample data
 */

import React, { useState } from 'react';
import { Table, Column, SortConfig } from './index';

interface SampleUser {
  id: number;
  name: string;
  email: string;
  role: string;
  status: 'active' | 'inactive';
  joinDate: string;
  department: string;
}

const sampleData: SampleUser[] = [
  { id: 1, name: 'John Doe', email: 'john@example.com', role: 'Admin', status: 'active', joinDate: '2023-01-15', department: 'Engineering' },
  { id: 2, name: 'Jane Smith', email: 'jane@example.com', role: 'User', status: 'active', joinDate: '2023-02-20', department: 'Marketing' },
  { id: 3, name: 'Bob Johnson', email: 'bob@example.com', role: 'Manager', status: 'active', joinDate: '2023-03-10', department: 'Sales' },
  { id: 4, name: 'Alice Williams', email: 'alice@example.com', role: 'User', status: 'inactive', joinDate: '2023-04-05', department: 'Support' },
  { id: 5, name: 'Charlie Brown', email: 'charlie@example.com', role: 'Admin', status: 'active', joinDate: '2023-05-12', department: 'Engineering' },
  { id: 6, name: 'Diana Davis', email: 'diana@example.com', role: 'User', status: 'active', joinDate: '2023-06-18', department: 'Marketing' },
  { id: 7, name: 'Eve Martinez', email: 'eve@example.com', role: 'Manager', status: 'active', joinDate: '2023-07-22', department: 'HR' },
  { id: 8, name: 'Frank Wilson', email: 'frank@example.com', role: 'User', status: 'inactive', joinDate: '2023-08-30', department: 'Sales' },
  { id: 9, name: 'Grace Lee', email: 'grace@example.com', role: 'Admin', status: 'active', joinDate: '2023-09-14', department: 'Engineering' },
  { id: 10, name: 'Henry Taylor', email: 'henry@example.com', role: 'User', status: 'active', joinDate: '2023-10-25', department: 'Support' },
];

export function TableExample() {
  const [data, setData] = useState<SampleUser[]>(sampleData);
  const [loading, setLoading] = useState(false);
  const [selectedRows, setSelectedRows] = useState<Set<string | number>>(new Set());
  const [sortConfig, setSortConfig] = useState<SortConfig>({
    column: 'name',
    direction: 'asc',
  });
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(5);
  const [viewMode, setViewMode] = useState<'table' | 'card'>('table');

  // Define columns
  const columns: Column<SampleUser>[] = [
    {
      id: 'name',
      label: 'Name',
      accessor: 'name',
      sortable: true,
      render: (value) => (
        <div className="flex items-center">
          <div className="flex-shrink-0 h-10 w-10">
            <div className="h-10 w-10 rounded-full bg-primary/10 flex items-center justify-center">
              <span className="text-primary font-medium text-sm">
                {String(value).charAt(0)}
              </span>
            </div>
          </div>
          <div className="ml-4">
            <div className="text-sm font-medium text-gray-900">{String(value)}</div>
          </div>
        </div>
      ),
    },
    {
      id: 'email',
      label: 'Email',
      accessor: 'email',
      sortable: true,
    },
    {
      id: 'role',
      label: 'Role',
      accessor: 'role',
      sortable: true,
      render: (value) => {
        const colors = {
          Admin: 'bg-purple-100 text-purple-800',
          Manager: 'bg-blue-100 text-blue-800',
          User: 'bg-gray-100 text-gray-800',
        };
        return (
          <span className={`px-2 py-1 rounded-full text-xs font-medium ${colors[value as keyof typeof colors]}`}>
            {String(value)}
          </span>
        );
      },
    },
    {
      id: 'department',
      label: 'Department',
      accessor: 'department',
      sortable: true,
    },
    {
      id: 'status',
      label: 'Status',
      accessor: 'status',
      sortable: true,
      render: (value) => (
        <span className={`px-2 py-1 rounded-full text-xs font-medium ${
          value === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
        }`}>
          {String(value)}
        </span>
      ),
    },
    {
      id: 'joinDate',
      label: 'Join Date',
      accessor: 'joinDate',
      sortable: true,
      render: (value) => new Date(value as string | number).toLocaleDateString(),
    },
  ];

  // Handle sorting
  const handleSortChange = (config: SortConfig) => {
    setSortConfig(config);
    const sorted = [...data].sort((a, b) => {
      const aVal = a[config.column as keyof SampleUser];
      const bVal = b[config.column as keyof SampleUser];
      if (config.direction === 'asc') {
        return aVal > bVal ? 1 : -1;
      }
      return aVal < bVal ? 1 : -1;
    });
    setData(sorted);
  };

  // Handle pagination
  const handlePaginationChange = (page: number, size: number) => {
    setCurrentPage(page);
    setPageSize(size);
  };

  // Get paginated data
  const paginatedData = data.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );

  // Handle row click
  const handleRowClick = (row: SampleUser) => {
    console.log('Row clicked:', row);
  };

  // Handle row double-click
  const handleRowDoubleClick = (row: SampleUser) => {
    console.log('Row double-clicked:', row);
    alert(`Opening details for ${row.name}`);
  };

  // Simulate loading
  const handleRefresh = () => {
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
    }, 2000);
  };

  // Card render function
  const renderCard = (user: SampleUser) => (
    <div className="p-4">
      <div className="flex items-center mb-3">
        <div className="h-12 w-12 rounded-full bg-primary/10 flex items-center justify-center">
          <span className="text-primary font-medium">{user.name.charAt(0)}</span>
        </div>
        <div className="ml-3">
          <h3 className="text-lg font-semibold text-gray-900">{user.name}</h3>
          <p className="text-sm text-gray-500">{user.email}</p>
        </div>
      </div>
      <div className="space-y-2">
        <div className="flex justify-between">
          <span className="text-sm text-gray-600">Role:</span>
          <span className="text-sm font-medium">{user.role}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-sm text-gray-600">Department:</span>
          <span className="text-sm font-medium">{user.department}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-sm text-gray-600">Status:</span>
          <span className={`text-sm font-medium ${
            user.status === 'active' ? 'text-green-600' : 'text-red-600'
          }`}>
            {user.status}
          </span>
        </div>
      </div>
    </div>
  );

  return (
    <div className="max-w-7xl mx-auto">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Table Component Demo</h1>
          <p className="mt-2 text-gray-600">
            Interactive example showing all table features
          </p>
        </div>
        <button
          onClick={handleRefresh}
          className="px-4 py-2 bg-primary text-primary-foreground rounded-lg hover:bg-primary/90 transition-colors"
        >
          Refresh Data
        </button>
      </div>

      {/* Feature indicators */}
      <div className="mb-6 grid grid-cols-2 md:grid-cols-4 gap-4">
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="text-sm text-gray-600">Selected Rows</div>
          <div className="text-2xl font-bold text-primary">{selectedRows.size}</div>
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="text-sm text-gray-600">Total Items</div>
          <div className="text-2xl font-bold text-gray-900">{data.length}</div>
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="text-sm text-gray-600">Current Page</div>
          <div className="text-2xl font-bold text-gray-900">{currentPage}</div>
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="text-sm text-gray-600">View Mode</div>
          <div className="text-2xl font-bold text-gray-900 capitalize">{viewMode}</div>
        </div>
      </div>

      {/* Table */}
      <Table
        data={paginatedData}
        columns={columns}
        loading={loading}
        emptyMessage="No users found"
        selectable
        selectionMode="multiple"
        selectedRows={selectedRows}
        onSelectionChange={setSelectedRows}
        rowId="id"
        sortable
        sortConfig={sortConfig}
        onSortChange={handleSortChange}
        pagination={{
          currentPage,
          pageSize,
          totalItems: data.length,
          pageSizeOptions: [5, 10, 25, 50],
        }}
        onPaginationChange={handlePaginationChange}
        onRowClick={handleRowClick}
        onRowDoubleClick={handleRowDoubleClick}
        hoverable
        density="normal"
        showViewToggle
        viewMode={viewMode}
        onViewModeChange={setViewMode}
        renderCard={renderCard}
      />
    </div>
  );
}
