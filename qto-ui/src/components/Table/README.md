# Table Component

A feature-rich, customizable data table component built with Tailwind CSS.

## Features

- ✅ **Sorting** - Click column headers to sort data
- ✅ **Pagination** - Built-in pagination with customizable page sizes
- ✅ **Selection** - Single or multiple row selection
- ✅ **Loading States** - Skeleton loader during data fetching
- ✅ **Empty States** - Customizable empty data display
- ✅ **Row Actions** - Click, double-click handlers
- ✅ **Responsive Design** - Mobile-friendly with overflow handling
- ✅ **Custom Rendering** - Custom cell and header rendering
- ✅ **Density Options** - Compact, normal, or comfortable spacing
- ✅ **Striped Rows** - Alternate row coloring
- ✅ **Hover Effects** - Visual feedback on row hover
- ✅ **View Toggle** - Switch between table and card views
- ✅ **TypeScript** - Full type safety

## Basic Usage

```tsx
import { Table, Column } from '@/components/Table';

interface User {
  id: number;
  name: string;
  email: string;
  role: string;
  status: 'active' | 'inactive';
}

const columns: Column<User>[] = [
  {
    id: 'name',
    label: 'Name',
    accessor: 'name',
    sortable: true,
  },
  {
    id: 'email',
    label: 'Email',
    accessor: 'email',
  },
  {
    id: 'role',
    label: 'Role',
    accessor: 'role',
  },
  {
    id: 'status',
    label: 'Status',
    accessor: 'status',
    render: (value) => (
      <span className={`px-2 py-1 rounded-full text-xs ${
        value === 'active' ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'
      }`}>
        {value}
      </span>
    ),
  },
];

function UserTable() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchUsers().then(data => {
      setUsers(data);
      setLoading(false);
    });
  }, []);

  return (
    <Table
      data={users}
      columns={columns}
      loading={loading}
    />
  );
}
```

## With Pagination

```tsx
function PaginatedTable() {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(25);
  const [totalItems, setTotalItems] = useState(0);
  const [data, setData] = useState([]);

  const handlePaginationChange = (page: number, size: number) => {
    setCurrentPage(page);
    setPageSize(size);
    fetchData(page, size);
  };

  return (
    <Table
      data={data}
      columns={columns}
      pagination={{
        currentPage,
        pageSize,
        totalItems,
        pageSizeOptions: [10, 25, 50, 100],
      }}
      onPaginationChange={handlePaginationChange}
    />
  );
}
```

## With Sorting

```tsx
function SortableTable() {
  const [data, setData] = useState([]);
  const [sortConfig, setSortConfig] = useState<SortConfig>({
    column: 'name',
    direction: 'asc',
  });

  const handleSortChange = (config: SortConfig) => {
    setSortConfig(config);
    // Sort data locally or fetch sorted data from server
    const sorted = [...data].sort((a, b) => {
      const aVal = a[config.column];
      const bVal = b[config.column];
      if (config.direction === 'asc') {
        return aVal > bVal ? 1 : -1;
      }
      return aVal < bVal ? 1 : -1;
    });
    setData(sorted);
  };

  return (
    <Table
      data={data}
      columns={columns}
      sortable
      sortConfig={sortConfig}
      onSortChange={handleSortChange}
    />
  );
}
```

## With Row Selection

```tsx
function SelectableTable() {
  const [selectedRows, setSelectedRows] = useState<Set<string | number>>(new Set());

  return (
    <Table
      data={data}
      columns={columns}
      selectable
      selectionMode="multiple"
      selectedRows={selectedRows}
      onSelectionChange={setSelectedRows}
      rowId="id"
    />
  );
}
```

## With Row Actions

```tsx
function ClickableTable() {
  const handleRowClick = (row: User) => {
    console.log('Row clicked:', row);
  };

  const handleRowDoubleClick = (row: User) => {
    navigate(`/users/${row.id}`);
  };

  return (
    <Table
      data={data}
      columns={columns}
      onRowClick={handleRowClick}
      onRowDoubleClick={handleRowDoubleClick}
    />
  );
}
```

## Custom Cell Rendering

```tsx
const columns: Column<User>[] = [
  {
    id: 'avatar',
    label: 'Avatar',
    accessor: 'avatarUrl',
    render: (url) => (
      <img src={url} alt="Avatar" className="w-8 h-8 rounded-full" />
    ),
  },
  {
    id: 'fullName',
    label: 'Full Name',
    accessor: (row) => `${row.firstName} ${row.lastName}`,
  },
  {
    id: 'actions',
    label: 'Actions',
    accessor: 'id',
    render: (id, row) => (
      <div className="flex space-x-2">
        <button onClick={() => handleEdit(row)}>Edit</button>
        <button onClick={() => handleDelete(id)}>Delete</button>
      </div>
    ),
  },
];
```

## Card View Toggle

```tsx
function TableWithCardView() {
  const [viewMode, setViewMode] = useState<'table' | 'card'>('table');

  const renderCard = (user: User) => (
    <div className="p-4">
      <h3 className="text-lg font-semibold">{user.name}</h3>
      <p className="text-gray-600">{user.email}</p>
      <span className="text-sm text-gray-500">{user.role}</span>
    </div>
  );

  return (
    <Table
      data={data}
      columns={columns}
      showViewToggle
      viewMode={viewMode}
      onViewModeChange={setViewMode}
      renderCard={renderCard}
    />
  );
}
```

## Nested Property Access

```tsx
const columns: Column<Order>[] = [
  {
    id: 'customerName',
    label: 'Customer',
    accessor: 'customer.name', // Nested property
  },
  {
    id: 'shippingAddress',
    label: 'Shipping Address',
    accessor: 'shipping.address.street',
  },
];
```

## Density Options

```tsx
<Table
  data={data}
  columns={columns}
  density="compact" // or "normal" or "comfortable"
/>
```

## Striped Rows

```tsx
<Table
  data={data}
  columns={columns}
  striped
/>
```

## Custom Row Styling

```tsx
<Table
  data={data}
  columns={columns}
  rowClassName={(row, index) =>
    row.status === 'error' ? 'bg-red-50' : ''
  }
/>
```

## API Reference

### TableProps

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `data` | `T[]` | required | Array of data to display |
| `columns` | `Column<T>[]` | required | Column configuration |
| `loading` | `boolean` | `false` | Show loading skeleton |
| `emptyMessage` | `string` | `"No data available"` | Empty state message |
| `selectable` | `boolean` | `false` | Enable row selection |
| `selectionMode` | `'single' \| 'multiple'` | `'multiple'` | Selection mode |
| `selectedRows` | `Set<string \| number>` | `new Set()` | Selected row IDs |
| `onSelectionChange` | `(ids: Set) => void` | - | Selection change handler |
| `rowId` | `string \| (row) => id` | `'id'` | Row identifier |
| `sortable` | `boolean` | `true` | Enable sorting |
| `sortConfig` | `SortConfig` | - | Current sort state |
| `onSortChange` | `(config) => void` | - | Sort change handler |
| `pagination` | `PaginationConfig` | - | Pagination configuration |
| `onPaginationChange` | `(page, size) => void` | - | Pagination change handler |
| `onRowClick` | `(row, index) => void` | - | Row click handler |
| `onRowDoubleClick` | `(row, index) => void` | - | Row double-click handler |
| `rowClassName` | `string \| (row) => string` | - | Custom row class |
| `hoverable` | `boolean` | `true` | Enable hover effect |
| `striped` | `boolean` | `false` | Striped rows |
| `density` | `'compact' \| 'normal' \| 'comfortable'` | `'normal'` | Row spacing |
| `showViewToggle` | `boolean` | `false` | Show card/table toggle |
| `viewMode` | `'table' \| 'card'` | `'table'` | Current view mode |
| `onViewModeChange` | `(mode) => void` | - | View mode change handler |
| `renderCard` | `(row) => ReactNode` | - | Card render function |

### Column

| Property | Type | Description |
|----------|------|-------------|
| `id` | `string` | Unique column identifier |
| `label` | `string` | Column header label |
| `accessor` | `string \| (row) => any` | Data accessor |
| `sortable` | `boolean` | Enable sorting for column |
| `render` | `(value, row, index) => ReactNode` | Custom cell renderer |
| `width` | `string` | Column width (CSS) |
| `align` | `'left' \| 'center' \| 'right'` | Text alignment |
| `hidden` | `boolean` | Hide column |
| `renderHeader` | `() => ReactNode` | Custom header renderer |

## Performance Tips

1. **Use `React.memo`** for expensive cell renderers
2. **Memoize columns** with `useMemo` to prevent re-renders
3. **Server-side pagination** for large datasets (>1000 rows)
4. **Virtualization** for very large datasets (consider adding react-virtual)
5. **Debounce** search/filter inputs to reduce re-renders

## Accessibility

- Proper semantic HTML (`<table>`, `<thead>`, `<tbody>`)
- ARIA labels for interactive elements
- Keyboard navigation support
- Screen reader friendly
- Focus indicators
