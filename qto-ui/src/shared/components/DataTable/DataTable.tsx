/**
 * DataTable Component
 *
 * Reusable table component with sorting, filtering, pagination, and export
 * Replaces Angular's abstract-table pattern
 */

import { useState } from 'react';
import { Download, Filter, RefreshCw, ChevronUp, ChevronDown } from 'lucide-react';
import * as XLSX from 'xlsx';

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Button } from '@/components/ui/button';
import { Checkbox } from '@/components/ui/checkbox';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { cn } from '@/lib/utils';

/**
 * Column definition
 */
export interface DataTableColumn<T> {
  id: keyof T | string;
  label: string;
  sortable?: boolean;
  width?: number | string;
  align?: 'left' | 'right' | 'center';
  format?: (value: any, row: T) => React.ReactNode;
  exportFormat?: (value: any, row: T) => string | number;
}

/**
 * DataTable props
 */
export interface DataTableProps<T> {
  columns: DataTableColumn<T>[];
  data: T[];
  loading?: boolean;
  error?: string | null;

  // Selection
  selectable?: boolean;
  selectedIds?: Set<number | string>;
  onSelectionChange?: (ids: Set<number | string>) => void;
  getRowId?: (row: T) => number | string;

  // Sorting
  sortBy?: string;
  sortOrder?: 'asc' | 'desc';
  onSort?: (sortBy: string, sortOrder: 'asc' | 'desc') => void;

  // Pagination
  page?: number;
  pageSize?: number;
  totalElements?: number;
  onPageChange?: (page: number) => void;
  onPageSizeChange?: (pageSize: number) => void;

  // Actions
  onRefresh?: () => void;
  onFilter?: () => void;
  onExport?: () => void;

  // Toolbar
  title?: string;
  toolbarActions?: React.ReactNode;

  // Export
  exportFileName?: string;
  exportEnabled?: boolean;
}

/**
 * DataTable Component
 */
export default function DataTable<T extends Record<string, any>>({
  columns,
  data,
  loading = false,
  error = null,
  selectable = false,
  selectedIds = new Set(),
  onSelectionChange,
  getRowId = (row) => row.id,
  sortBy,
  sortOrder = 'asc',
  onSort,
  page = 0,
  pageSize = 10,
  totalElements,
  onPageChange,
  onPageSizeChange,
  onRefresh,
  onFilter,
  onExport,
  title,
  toolbarActions,
  exportFileName = 'export',
  exportEnabled = true,
}: DataTableProps<T>) {
  const [localSortBy, setLocalSortBy] = useState<string | undefined>(sortBy);
  const [localSortOrder, setLocalSortOrder] = useState<'asc' | 'desc'>(sortOrder);

  // Handle sort
  const handleSort = (columnId: string) => {
    const isAsc = localSortBy === columnId && localSortOrder === 'asc';
    const newOrder = isAsc ? 'desc' : 'asc';

    setLocalSortBy(columnId);
    setLocalSortOrder(newOrder);

    if (onSort) {
      onSort(columnId, newOrder);
    }
  };

  // Handle select all
  const handleSelectAll = (checked: boolean) => {
    if (!onSelectionChange) return;

    if (checked) {
      const allIds = new Set(data.map(getRowId));
      onSelectionChange(allIds);
    } else {
      onSelectionChange(new Set());
    }
  };

  // Handle select row
  const handleSelectRow = (rowId: number | string) => {
    if (!onSelectionChange) return;

    const newSelection = new Set(selectedIds);
    if (newSelection.has(rowId)) {
      newSelection.delete(rowId);
    } else {
      newSelection.add(rowId);
    }
    onSelectionChange(newSelection);
  };

  // Handle export
  const handleExport = () => {
    if (onExport) {
      onExport();
      return;
    }

    // Default export implementation
    const exportData = data.map((row) => {
      const exportRow: Record<string, any> = {};
      columns.forEach((col) => {
        const value = row[col.id as keyof T];
        if (col.exportFormat) {
          exportRow[col.label] = col.exportFormat(value, row);
        } else if (col.format) {
          exportRow[col.label] = col.format(value, row);
        } else {
          exportRow[col.label] = value;
        }
      });
      return exportRow;
    });

    const ws = XLSX.utils.json_to_sheet(exportData);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Data');
    XLSX.writeFile(wb, `${exportFileName}.xlsx`);
  };

  const numSelected = selectedIds.size;
  const rowCount = data.length;
  const totalPages = totalElements ? Math.ceil(totalElements / pageSize) : 0;

  return (
    <Card className="app-table-panel w-full overflow-hidden border-slate-200 shadow-none">
      {/* Toolbar */}
      {(title || toolbarActions || onRefresh || onFilter || exportEnabled) && (
        <CardHeader className={cn(
          "flex flex-row flex-wrap items-center justify-between gap-3 space-y-0 border-b border-slate-200/80 px-5 py-4",
          numSelected > 0 && "bg-primary/10"
        )}>
          <CardTitle className="text-base font-semibold text-slate-900">
            {numSelected > 0 ? `${numSelected} selected` : title}
          </CardTitle>
          <div className="flex items-center gap-2">
            {toolbarActions}
            {onFilter && (
              <Button variant="ghost" size="icon" onClick={onFilter} title="Filter">
                <Filter className="h-4 w-4" />
              </Button>
            )}
            {onRefresh && (
              <Button variant="ghost" size="icon" onClick={onRefresh} title="Refresh">
                <RefreshCw className="h-4 w-4" />
              </Button>
            )}
            {exportEnabled && data.length > 0 && (
              <Button variant="ghost" size="icon" onClick={handleExport} title="Export to Excel">
                <Download className="h-4 w-4" />
              </Button>
            )}
          </div>
        </CardHeader>
      )}

      <CardContent className="p-0">
        {/* Error */}
        {error && (
          <div className="mx-5 mt-5 rounded-2xl border border-red-200 bg-red-50 p-4 text-sm text-red-800" role="alert">
            {error}
          </div>
        )}

        {/* Loading */}
        {loading && (
          <div className="flex min-h-[240px] items-center justify-center px-5 py-8">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary"></div>
          </div>
        )}

        {/* Table */}
        {!loading && !error && (
          <>
            <div className="rounded-none border-0">
              <Table>
                <TableHeader>
                  <TableRow>
                    {selectable && (
                      <TableHead className="w-12">
                        <Checkbox
                          checked={rowCount > 0 && numSelected === rowCount}
                          onCheckedChange={handleSelectAll}
                          aria-label="Select all"
                        />
                      </TableHead>
                    )}
                    {columns.map((column) => (
                      <TableHead
                        key={String(column.id)}
                        className={cn(
                          'h-11 bg-slate-50/80 px-4 text-[11px] font-semibold uppercase tracking-[0.18em] text-slate-500',
                          column.align === 'right' && 'text-right',
                          column.align === 'center' && 'text-center'
                        )}
                        style={{ width: column.width }}
                      >
                        {column.sortable !== false && onSort ? (
                          <button
                            className="flex items-center gap-1 hover:text-foreground transition-colors"
                            onClick={() => handleSort(String(column.id))}
                          >
                            {column.label}
                            {localSortBy === column.id && (
                              localSortOrder === 'asc' ? (
                                <ChevronUp className="h-4 w-4" />
                              ) : (
                                <ChevronDown className="h-4 w-4" />
                              )
                            )}
                          </button>
                        ) : (
                          column.label
                        )}
                      </TableHead>
                    ))}
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {data.length === 0 ? (
                    <TableRow>
                      <TableCell colSpan={columns.length + (selectable ? 1 : 0)} className="text-center h-24 text-muted-foreground">
                        No data available
                      </TableCell>
                    </TableRow>
                  ) : (
                    data.map((row) => {
                      const rowId = getRowId(row);
                      const isSelected = selectedIds.has(rowId);

                      return (
                        <TableRow
                          key={String(rowId)}
                          data-state={isSelected && "selected"}
                          className={cn('hover:bg-slate-50/80', selectable && "cursor-pointer")}
                          onClick={() => selectable && handleSelectRow(rowId)}
                        >
                          {selectable && (
                            <TableCell>
                              <Checkbox
                                checked={isSelected}
                                onCheckedChange={() => handleSelectRow(rowId)}
                                aria-label="Select row"
                              />
                            </TableCell>
                          )}
                          {columns.map((column) => {
                            const value = row[column.id as keyof T];
                            return (
                              <TableCell
                                key={String(column.id)}
                                className={cn(
                                  'px-4 py-3.5 text-sm text-slate-700',
                                  column.align === 'right' && 'text-right',
                                  column.align === 'center' && 'text-center'
                                )}
                              >
                                {column.format ? column.format(value, row) : value}
                              </TableCell>
                            );
                          })}
                        </TableRow>
                      );
                    })
                  )}
                </TableBody>
              </Table>
            </div>

            {/* Pagination */}
            {(onPageChange || onPageSizeChange) && (
              <div className="flex flex-wrap items-center justify-between gap-3 border-t border-slate-200/80 px-5 py-4">
                <div className="flex items-center gap-2">
                  <p className="text-sm text-muted-foreground">
                    Rows per page:
                  </p>
                  <select
                    className="h-8 w-[70px] rounded-md border border-input bg-transparent px-2 text-sm"
                    value={pageSize}
                    onChange={(e) => onPageSizeChange?.(parseInt(e.target.value, 10))}
                  >
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="25">25</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                  </select>
                </div>

                <div className="flex items-center gap-6">
                  <p className="text-sm text-muted-foreground">
                    {page * pageSize + 1}-{Math.min((page + 1) * pageSize, totalElements || data.length)} of {totalElements || data.length}
                  </p>
                  <div className="flex items-center gap-2">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => onPageChange?.(page - 1)}
                      disabled={page === 0}
                    >
                      Previous
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => onPageChange?.(page + 1)}
                      disabled={page >= totalPages - 1}
                    >
                      Next
                    </Button>
                  </div>
                </div>
              </div>
            )}
          </>
        )}
      </CardContent>
    </Card>
  );
}
