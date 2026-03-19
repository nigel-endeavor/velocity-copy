/**
 * Table Component
 * Feature-rich data table with sorting, pagination, selection, and responsive design
 */

import React, { useState, useCallback, useMemo } from 'react';
import { TableProps } from './types';
import { TablePagination } from './TablePagination';
import { TableHeader } from './TableHeader';
import { TableRow } from './TableRow';
import { TableSkeleton } from './TableSkeleton';
import { EmptyState } from './EmptyState';

export function Table<T = Record<string, unknown>>({
  data,
  columns,
  loading = false,
  emptyMessage = 'No data available',
  selectable = false,
  selectionMode = 'multiple',
  selectedRows = new Set(),
  onSelectionChange,
  rowId = 'id',
  sortable = true,
  sortConfig,
  onSortChange,
  pagination,
  onPaginationChange,
  onRowClick,
  onRowDoubleClick,
  rowClassName,
  hoverable = true,
  striped = false,
  density = 'normal',
  className = '',
  showViewToggle = false,
  viewMode: externalViewMode,
  onViewModeChange,
  renderCard,
}: TableProps<T>) {
  // Internal view mode state
  const [internalViewMode, setInternalViewMode] = useState<'table' | 'card'>('table');
  const viewMode = externalViewMode ?? internalViewMode;

  // Get row identifier
  const getRowId = useCallback((row: T): string | number => {
    if (typeof rowId === 'function') {
      return rowId(row);
    }
    return (row as Record<string, unknown>)[rowId] as string | number;
  }, [rowId]);

  // Handle view mode toggle
  const handleViewModeChange = (mode: 'table' | 'card') => {
    if (onViewModeChange) {
      onViewModeChange(mode);
    } else {
      setInternalViewMode(mode);
    }
  };

  // Handle select all
  const handleSelectAll = (checked: boolean) => {
    if (!onSelectionChange) return;

    if (checked) {
      const allIds = new Set(data.map(row => getRowId(row)));
      onSelectionChange(allIds);
    } else {
      onSelectionChange(new Set());
    }
  };

  // Handle row selection
  const handleRowSelect = (rowIdValue: string | number, checked: boolean) => {
    if (!onSelectionChange) return;

    const newSelection = new Set(selectedRows);
    if (checked) {
      if (selectionMode === 'single') {
        newSelection.clear();
      }
      newSelection.add(rowIdValue);
    } else {
      newSelection.delete(rowIdValue);
    }
    onSelectionChange(newSelection);
  };

  // Check if all rows are selected
  const allSelected = data.length > 0 && data.every(row => selectedRows.has(getRowId(row)));
  const someSelected = data.some(row => selectedRows.has(getRowId(row)));

  // Visible columns (filter out hidden)
  const visibleColumns = useMemo(() =>
    columns.filter(col => !col.hidden),
    [columns]
  );

  // Loading state
  if (loading) {
    return (
      <div className={`bg-white rounded-lg shadow overflow-hidden ${className}`}>
        <TableSkeleton columns={visibleColumns.length} rows={5} />
      </div>
    );
  }

  // Empty state
  if (!data || data.length === 0) {
    return (
      <div className={`bg-white rounded-lg shadow overflow-hidden ${className}`}>
        <EmptyState message={emptyMessage} />
      </div>
    );
  }

  // Card view
  if (viewMode === 'card' && renderCard) {
    return (
      <div className={className}>
        {showViewToggle && (
          <div className="mb-4 flex justify-end">
            <ViewToggle viewMode={viewMode} onChange={handleViewModeChange} />
          </div>
        )}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {data.map((row, index) => (
            <div key={getRowId(row)} className="bg-white rounded-lg shadow hover:shadow-lg transition-shadow">
              {renderCard(row, index)}
            </div>
          ))}
        </div>
        {pagination && (
          <TablePagination
            currentPage={pagination.currentPage}
            pageSize={pagination.pageSize}
            totalItems={pagination.totalItems}
            pageSizeOptions={pagination.pageSizeOptions}
            onPageChange={(page) => onPaginationChange?.(page, pagination.pageSize)}
            onPageSizeChange={(size) => onPaginationChange?.(1, size)}
          />
        )}
      </div>
    );
  }

  // Table view
  return (
    <div className={className}>
      {showViewToggle && (
        <div className="mb-4 flex justify-end">
          <ViewToggle viewMode={viewMode} onChange={handleViewModeChange} />
        </div>
      )}
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <TableHeader
              columns={visibleColumns}
              selectable={selectable}
              allSelected={allSelected}
              someSelected={someSelected}
              onSelectAll={handleSelectAll}
              sortable={sortable}
              sortConfig={sortConfig}
              onSortChange={onSortChange}
              density={density}
            />
            <tbody className="bg-white divide-y divide-gray-200">
              {data.map((row, index) => (
                <TableRow
                  key={getRowId(row)}
                  row={row}
                  index={index}
                  columns={visibleColumns}
                  rowId={getRowId(row)}
                  selectable={selectable}
                  selected={selectedRows.has(getRowId(row))}
                  onSelect={(checked) => handleRowSelect(getRowId(row), checked)}
                  onClick={onRowClick}
                  onDoubleClick={onRowDoubleClick}
                  className={typeof rowClassName === 'function' ? rowClassName(row, index) : rowClassName}
                  hoverable={hoverable}
                  striped={striped && index % 2 === 1}
                  density={density}
                />
              ))}
            </tbody>
          </table>
        </div>
        {pagination && (
          <TablePagination
            currentPage={pagination.currentPage}
            pageSize={pagination.pageSize}
            totalItems={pagination.totalItems}
            pageSizeOptions={pagination.pageSizeOptions}
            onPageChange={(page) => onPaginationChange?.(page, pagination.pageSize)}
            onPageSizeChange={(size) => onPaginationChange?.(1, size)}
          />
        )}
      </div>
    </div>
  );
}

// View toggle component
function ViewToggle({
  viewMode,
  onChange
}: {
  viewMode: 'table' | 'card';
  onChange: (mode: 'table' | 'card') => void;
}) {
  return (
    <div className="inline-flex rounded-lg border border-gray-300 bg-white">
      <button
        onClick={() => onChange('table')}
        className={`px-4 py-2 text-sm font-medium rounded-l-lg transition-colors ${
          viewMode === 'table'
            ? 'bg-primary text-primary-foreground'
            : 'text-gray-700 hover:bg-gray-50'
        }`}
      >
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 10h18M3 14h18m-9-4v8m-7 0h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z" />
        </svg>
      </button>
      <button
        onClick={() => onChange('card')}
        className={`px-4 py-2 text-sm font-medium rounded-r-lg transition-colors ${
          viewMode === 'card'
            ? 'bg-primary text-primary-foreground'
            : 'text-gray-700 hover:bg-gray-50'
        }`}
      >
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z" />
        </svg>
      </button>
    </div>
  );
}
