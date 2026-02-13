/**
 * Table Component Type Definitions
 * TypeScript interfaces for the shared Table component
 */

export interface Column<T = Record<string, unknown>> {
  /** Unique identifier for the column */
  id: string;
  /** Display label for the column header */
  label: string;
  /** Key path to access data (e.g., 'user.name' or 'status') */
  accessor: string | ((row: T) => unknown);
  /** Whether the column is sortable */
  sortable?: boolean;
  /** Custom render function for the cell */
  render?: (value: unknown, row: T, index: number) => React.ReactNode;
  /** Column width (CSS value) */
  width?: string;
  /** Column alignment */
  align?: 'left' | 'center' | 'right';
  /** Whether the column is hidden */
  hidden?: boolean;
  /** Custom header render function */
  renderHeader?: () => React.ReactNode;
}

export interface SortConfig {
  /** Column ID to sort by */
  column: string;
  /** Sort direction */
  direction: 'asc' | 'desc';
}

export interface PaginationConfig {
  /** Current page number (1-based) */
  currentPage: number;
  /** Number of items per page */
  pageSize: number;
  /** Total number of items */
  totalItems: number;
  /** Available page size options */
  pageSizeOptions?: number[];
}

export interface TableProps<T = Record<string, unknown>> {
  /** Array of data to display */
  data: T[];
  /** Column configuration */
  columns: Column<T>[];
  /** Loading state */
  loading?: boolean;
  /** Empty state message */
  emptyMessage?: string;
  /** Enable row selection */
  selectable?: boolean;
  /** Selection mode */
  selectionMode?: 'single' | 'multiple';
  /** Selected row IDs */
  selectedRows?: Set<string | number>;
  /** Callback when selection changes */
  onSelectionChange?: (selectedIds: Set<string | number>) => void;
  /** Row ID accessor */
  rowId?: string | ((row: T) => string | number);
  /** Enable sorting */
  sortable?: boolean;
  /** Current sort configuration */
  sortConfig?: SortConfig;
  /** Callback when sort changes */
  onSortChange?: (config: SortConfig) => void;
  /** Enable pagination */
  pagination?: PaginationConfig;
  /** Callback when pagination changes */
  onPaginationChange?: (page: number, pageSize: number) => void;
  /** Row click handler */
  onRowClick?: (row: T, index: number) => void;
  /** Row double-click handler */
  onRowDoubleClick?: (row: T, index: number) => void;
  /** Custom row class name */
  rowClassName?: string | ((row: T, index: number) => string);
  /** Enable hover effect on rows */
  hoverable?: boolean;
  /** Enable striped rows */
  striped?: boolean;
  /** Table density */
  density?: 'compact' | 'normal' | 'comfortable';
  /** Additional CSS classes */
  className?: string;
  /** Show card view toggle */
  showViewToggle?: boolean;
  /** Current view mode */
  viewMode?: 'table' | 'card';
  /** Callback when view mode changes */
  onViewModeChange?: (mode: 'table' | 'card') => void;
  /** Custom card render function */
  renderCard?: (row: T, index: number) => React.ReactNode;
}

export interface TableState<T = Record<string, unknown>> {
  data: T[];
  selectedRows: Set<string | number>;
  sortConfig?: SortConfig;
  currentPage: number;
  pageSize: number;
  viewMode: 'table' | 'card';
}
