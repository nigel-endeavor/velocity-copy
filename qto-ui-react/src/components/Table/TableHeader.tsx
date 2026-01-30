/**
 * TableHeader Component
 * Renders table header with sorting and selection
 */

import React from 'react';
import { Column, SortConfig } from './types';

interface TableHeaderProps<T = Record<string, unknown>> {
  columns: Column<T>[];
  selectable?: boolean;
  allSelected?: boolean;
  someSelected?: boolean;
  onSelectAll?: (checked: boolean) => void;
  sortable?: boolean;
  sortConfig?: SortConfig;
  onSortChange?: (config: SortConfig) => void;
  density?: 'compact' | 'normal' | 'comfortable';
}

export function TableHeader<T = Record<string, unknown>>({
  columns,
  selectable,
  allSelected,
  someSelected,
  onSelectAll,
  sortable,
  sortConfig,
  onSortChange,
  density = 'normal',
}: TableHeaderProps<T>) {
  const densityClasses = {
    compact: 'py-2 px-3 text-sm',
    normal: 'py-3 px-4',
    comfortable: 'py-4 px-6',
  };

  const handleSort = (columnId: string) => {
    if (!sortable || !onSortChange) return;

    const newDirection =
      sortConfig?.column === columnId && sortConfig.direction === 'asc'
        ? 'desc'
        : 'asc';

    onSortChange({ column: columnId, direction: newDirection });
  };

  return (
    <thead className="bg-gray-50">
      <tr>
        {selectable && (
          <th
            scope="col"
            className={`${densityClasses[density]} text-left w-12`}
          >
            <input
              type="checkbox"
              checked={allSelected}
              ref={(input) => {
                if (input) {
                  input.indeterminate = !allSelected && someSelected;
                }
              }}
              onChange={(e) => onSelectAll?.(e.target.checked)}
              className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded cursor-pointer"
            />
          </th>
        )}
        {columns.map((column) => {
          const isSortable = sortable && column.sortable !== false;
          const isSorted = sortConfig?.column === column.id;
          const sortDirection = isSorted ? sortConfig.direction : undefined;

          return (
            <th
              key={column.id}
              scope="col"
              className={`${densityClasses[density]} text-left text-xs font-medium text-gray-500 uppercase tracking-wider ${
                isSortable ? 'cursor-pointer select-none hover:bg-gray-100' : ''
              }`}
              style={{ width: column.width, textAlign: column.align }}
              onClick={() => isSortable && handleSort(column.id)}
            >
              <div className="flex items-center space-x-1">
                {column.renderHeader ? (
                  column.renderHeader()
                ) : (
                  <span>{column.label}</span>
                )}
                {isSortable && (
                  <SortIcon direction={sortDirection} />
                )}
              </div>
            </th>
          );
        })}
      </tr>
    </thead>
  );
}

// Sort icon component
function SortIcon({ direction }: { direction?: 'asc' | 'desc' }) {
  if (direction === 'asc') {
    return (
      <svg
        className="w-4 h-4 text-primary-600"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={2}
          d="M5 15l7-7 7 7"
        />
      </svg>
    );
  }

  if (direction === 'desc') {
    return (
      <svg
        className="w-4 h-4 text-primary-600"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={2}
          d="M19 9l-7 7-7-7"
        />
      </svg>
    );
  }

  return (
    <svg
      className="w-4 h-4 text-gray-400"
      fill="none"
      stroke="currentColor"
      viewBox="0 0 24 24"
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth={2}
        d="M7 16V4m0 0L3 8m4-4l4 4m6 0v12m0 0l4-4m-4 4l-4-4"
      />
    </svg>
  );
}
