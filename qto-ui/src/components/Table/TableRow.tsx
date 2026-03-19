/**
 * TableRow Component
 * Renders individual table row with selection and click handlers
 */

import React from 'react';
import { Column } from './types';

interface TableRowProps<T = Record<string, unknown>> {
  row: T;
  index: number;
  columns: Column<T>[];
  rowId: string | number;
  selectable?: boolean;
  selected?: boolean;
  onSelect?: (checked: boolean) => void;
  onClick?: (row: T, index: number) => void;
  onDoubleClick?: (row: T, index: number) => void;
  className?: string;
  hoverable?: boolean;
  striped?: boolean;
  density?: 'compact' | 'normal' | 'comfortable';
}

export function TableRow<T = Record<string, unknown>>({
  row,
  index,
  columns,
  selectable,
  selected,
  onSelect,
  onClick,
  onDoubleClick,
  className = '',
  hoverable = true,
  striped = false,
  density = 'normal',
}: TableRowProps<T>) {
  const densityClasses = {
    compact: 'py-2 px-3 text-sm',
    normal: 'py-3 px-4',
    comfortable: 'py-4 px-6',
  };

  // Get cell value from row using accessor
  const getCellValue = (column: Column<T>, row: T): unknown => {
    if (typeof column.accessor === 'function') {
      return column.accessor(row);
    }

    // Support nested property access (e.g., 'user.name')
    const keys = column.accessor.split('.');
    let value: unknown = row;
    for (const key of keys) {
      if (value == null) return null;
      value = (value as Record<string, unknown>)[key];
    }
    return value;
  };

  const handleRowClick = () => {
    onClick?.(row, index);
  };

  const handleRowDoubleClick = () => {
    onDoubleClick?.(row, index);
  };

  const rowClasses = [
    hoverable && 'hover:bg-gray-50',
    striped && 'bg-gray-50',
    selected && 'bg-primary-50',
    onClick && 'cursor-pointer',
    className,
  ]
    .filter(Boolean)
    .join(' ');

  return (
    <tr
      className={rowClasses}
      onClick={handleRowClick}
      onDoubleClick={handleRowDoubleClick}
    >
      {selectable && (
        <td
          className={`${densityClasses[density]} w-12`}
          onClick={(e) => e.stopPropagation()}
        >
          <input
            type="checkbox"
            checked={selected}
            onChange={(e) => onSelect?.(e.target.checked)}
            className="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded cursor-pointer"
          />
        </td>
      )}
      {columns.map((column) => {
        const value = getCellValue(column, row);
        const content = column.render ? column.render(value, row, index) : value;

        return (
          <td
            key={column.id}
            className={`${densityClasses[density]} text-sm text-gray-900`}
            style={{ textAlign: column.align }}
          >
            {content}
          </td>
        );
      })}
    </tr>
  );
}
