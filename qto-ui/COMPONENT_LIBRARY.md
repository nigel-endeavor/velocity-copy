# QTO UI Component Library

Comprehensive collection of reusable React components built with Tailwind CSS for the QTO application.

## 📦 Available Components

### Base Components

#### Button
Flexible button component with multiple variants and sizes.

```tsx
import { Button } from '@/components';

<Button variant="primary" size="md">
  Click Me
</Button>
```

**Props:**
- `variant`: 'primary' | 'secondary' | 'danger' | 'ghost'
- `size`: 'sm' | 'md' | 'lg'

#### Card
Container component for grouped content.

```tsx
import { Card } from '@/components';

<Card padding="lg">
  <h2>Card Title</h2>
  <p>Card content</p>
</Card>
```

**Props:**
- `padding`: 'sm' | 'md' | 'lg' | 'xl' | 'none'

---

### Table Component

**Full-featured data table** with sorting, pagination, selection, and multiple view modes.

```tsx
import { Table, Column } from '@/components';

const columns: Column<User>[] = [
  { id: 'name', label: 'Name', accessor: 'name', sortable: true },
  { id: 'email', label: 'Email', accessor: 'email' },
  {
    id: 'status',
    label: 'Status',
    accessor: 'status',
    render: (value) => <StatusBadge status={value} />
  },
];

<Table
  data={users}
  columns={columns}
  selectable
  pagination={{ currentPage: 1, pageSize: 25, totalItems: 100 }}
  onPaginationChange={(page, size) => handlePageChange(page, size)}
/>
```

**Features:**
- ✅ Sorting (click column headers)
- ✅ Pagination (configurable page sizes)
- ✅ Row selection (single or multiple)
- ✅ Loading skeleton
- ✅ Empty state
- ✅ Custom cell rendering
- ✅ Card/Table view toggle
- ✅ Row click handlers
- ✅ Nested property access
- ✅ Density options
- ✅ Striped rows
- ✅ Responsive design

**See:** `src/components/Table/README.md` for complete documentation

---

### Form Components

#### Input
Text input with label, icons, and error handling.

```tsx
import { Input } from '@/components';

<Input
  label="Email"
  type="email"
  placeholder="Enter your email"
  error={errors.email}
  required
  fullWidth
/>
```

**Props:**
- `label`: Optional label text
- `error`: Error message
- `helperText`: Helper text below input
- `startIcon`: Icon on the left
- `endIcon`: Icon on the right
- `fullWidth`: Expand to full width

#### Select
Dropdown select with options.

```tsx
import { Select } from '@/components';

const options = [
  { value: 'admin', label: 'Administrator' },
  { value: 'user', label: 'User' },
];

<Select
  label="Role"
  options={options}
  placeholder="Select a role"
  error={errors.role}
  required
/>
```

**Props:**
- `label`: Optional label text
- `options`: Array of `{ value, label, disabled? }`
- `placeholder`: Placeholder option
- `error`: Error message

#### Checkbox
Checkbox with label and helper text.

```tsx
import { Checkbox } from '@/components';

<Checkbox
  label="Accept terms and conditions"
  helperText="You must accept to continue"
  error={errors.terms}
/>
```

**Props:**
- `label`: Label text
- `helperText`: Helper text
- `error`: Error message

#### Textarea
Multi-line text input with resize options.

```tsx
import { Textarea } from '@/components';

<Textarea
  label="Description"
  rows={4}
  resize="vertical"
  placeholder="Enter description..."
  error={errors.description}
/>
```

**Props:**
- `label`: Optional label text
- `resize`: 'none' | 'vertical' | 'horizontal' | 'both'
- `rows`: Number of rows (default: 4)
- `error`: Error message

---

### Modal Component

**Full-featured modal dialog** with backdrop, animations, and focus management.

```tsx
import { Modal } from '@/components';

<Modal
  isOpen={isOpen}
  onClose={() => setIsOpen(false)}
  title="Confirm Action"
  size="md"
  footer={
    <div className="flex justify-end space-x-3">
      <Button variant="secondary" onClick={() => setIsOpen(false)}>
        Cancel
      </Button>
      <Button variant="primary" onClick={handleConfirm}>
        Confirm
      </Button>
    </div>
  }
>
  <p>Are you sure you want to proceed?</p>
</Modal>
```

**Features:**
- ✅ Portal rendering (appended to body)
- ✅ Backdrop with opacity
- ✅ Focus trap (keyboard navigation)
- ✅ Escape key handling
- ✅ Body scroll prevention
- ✅ Multiple sizes (sm, md, lg, xl, full)
- ✅ Header, body, footer sections
- ✅ Close button (optional)
- ✅ Click outside to close (optional)

**Props:**
- `isOpen`: Control modal visibility
- `onClose`: Close handler
- `title`: Optional title
- `footer`: Optional footer content
- `size`: 'sm' | 'md' | 'lg' | 'xl' | 'full'
- `closeOnBackdropClick`: Close on backdrop click (default: true)
- `closeOnEscape`: Close on Escape key (default: true)
- `showCloseButton`: Show X button (default: true)

---

## 🎨 Styling Conventions

All components use **Tailwind CSS** for styling. Refer to `TAILWIND_GUIDE.md` for:
- Common patterns
- Custom colors (primary palette)
- Responsive design
- Best practices

### Custom Primary Colors

```js
primary: {
  50: '#e3f2fd',   // Lightest
  100: '#bbdefb',
  200: '#90caf9',
  300: '#64b5f6',
  400: '#42a5f5',
  500: '#1976d2',  // Base
  600: '#1565c0',
  700: '#0d47a1',
  800: '#0a3d91',
  900: '#082e6f',  // Darkest
}
```

Usage: `bg-primary-600`, `text-primary-500`, `border-primary-400`

---

## 🚀 Usage Examples

### Form with Validation

```tsx
import { Input, Select, Checkbox, Button } from '@/components';
import { useForm } from 'react-hook-form';

function RegistrationForm() {
  const { register, handleSubmit, formState: { errors } } = useForm();

  const onSubmit = (data) => {
    console.log(data);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <Input
        {...register('email', { required: 'Email is required' })}
        label="Email"
        type="email"
        error={errors.email?.message}
        fullWidth
      />
      <Select
        {...register('role', { required: 'Role is required' })}
        label="Role"
        options={roleOptions}
        error={errors.role?.message}
        fullWidth
      />
      <Checkbox
        {...register('terms', { required: 'You must accept terms' })}
        label="Accept terms and conditions"
        error={errors.terms?.message}
      />
      <Button type="submit" variant="primary">
        Register
      </Button>
    </form>
  );
}
```

### Data Table with Actions

```tsx
import { Table, Column, Button, Modal } from '@/components';
import { useState } from 'react';

function UserManagement() {
  const [selectedRows, setSelectedRows] = useState(new Set());
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const columns: Column<User>[] = [
    { id: 'name', label: 'Name', accessor: 'name', sortable: true },
    { id: 'email', label: 'Email', accessor: 'email', sortable: true },
    {
      id: 'actions',
      label: 'Actions',
      accessor: 'id',
      render: (id, row) => (
        <div className="flex space-x-2">
          <Button size="sm" onClick={() => handleEdit(row)}>Edit</Button>
          <Button size="sm" variant="danger" onClick={() => handleDelete(id)}>
            Delete
          </Button>
        </div>
      ),
    },
  ];

  return (
    <>
      <Table
        data={users}
        columns={columns}
        selectable
        selectedRows={selectedRows}
        onSelectionChange={setSelectedRows}
      />
      <Modal
        isOpen={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        title="Confirm Delete"
      >
        <p>Are you sure you want to delete {selectedRows.size} users?</p>
      </Modal>
    </>
  );
}
```

---

## 📚 Component Documentation

Detailed documentation for each component:

- **Table**: `src/components/Table/README.md`
- **Tailwind Guide**: `TAILWIND_GUIDE.md`
- **Examples**: `src/components/Table/TableExample.tsx`

---

## ✨ Best Practices

### 1. Import from Root
Always import components from the root `@/components`:

```tsx
✅ import { Button, Table, Input } from '@/components';
❌ import { Button } from '@/components/Button';
```

### 2. Use TypeScript
All components are fully typed. Leverage TypeScript for better DX:

```tsx
import { Column } from '@/components';

const columns: Column<User>[] = [
  // TypeScript will autocomplete and validate
];
```

### 3. Composition Over Configuration
Build complex UIs by composing simple components:

```tsx
<Modal isOpen={isOpen} onClose={handleClose}>
  <form>
    <Input label="Name" />
    <Select label="Role" options={roles} />
    <div className="flex justify-end space-x-2">
      <Button variant="secondary" onClick={handleClose}>Cancel</Button>
      <Button variant="primary" type="submit">Submit</Button>
    </div>
  </form>
</Modal>
```

### 4. Accessibility
All components include:
- Proper semantic HTML
- ARIA attributes
- Keyboard navigation
- Focus management
- Screen reader support

---

## 🔄 Migration Status

**Component Library: COMPLETED ✅**

- [x] Base components (Button, Card)
- [x] Table component (full-featured)
- [x] Form components (Input, Select, Checkbox, Textarea)
- [x] Modal component
- [ ] Tabs component
- [ ] Toast notifications
- [ ] Loading indicators (additional)

**Ready for feature migration!** The core component library is complete and ready to support the migration of Angular features to React.

---

## 🚦 Next Steps

1. Migrate Service Worklist feature using Table component
2. Migrate Order Detail page using Form components
3. Add additional components as needed (Tabs, Toasts, etc.)
4. Build domain-specific components for worklists

---

**Last Updated:** January 29, 2026
**Version:** 1.0.0
