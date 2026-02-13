/**
 * Lookup Types Management Component
 *
 * Manage system lookup types (categories)
 */

import { useState, useCallback } from 'react';
import {
  Box,
  Paper,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Grid,
  Typography,
  IconButton,
  Chip,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
} from '@mui/icons-material';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import {
  useSearchLookupTypesQuery,
  useCreateLookupTypeMutation,
  useUpdateLookupTypeMutation,
  useDeleteLookupTypeMutation,
} from '@/services/api/configurationApi';
import type { LookupType } from '@/shared/types/models';
import { usePermissions } from '@/shared/hooks/usePermissions';

/**
 * Lookup type form schema
 */
const lookupTypeSchema = z.object({
  id: z.number().optional(),
  name: z.string().min(1, 'Name is required').max(100, 'Name too long'),
  description: z.string().max(500, 'Description too long').optional(),
  category: z.string().min(1, 'Category is required').max(50, 'Category too long'),
  sortOrder: z.number().int().min(0, 'Sort order must be non-negative').optional(),
  active: z.boolean().default(true),
});

type LookupTypeFormData = z.infer<typeof lookupTypeSchema>;

/**
 * Lookup Types Component
 */
export default function LookupTypes() {
  const { hasPermission } = usePermissions();
  const canWrite = hasPermission('configuration-write');

  // State
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editingType, setEditingType] = useState<LookupType | null>(null);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [typeToDelete, setTypeToDelete] = useState<LookupType | null>(null);

  // API hooks
  const { data, isLoading, error, refetch } = useSearchLookupTypesQuery({});
  const [createLookupType, { isLoading: isCreating }] = useCreateLookupTypeMutation();
  const [updateLookupType, { isLoading: isUpdating }] = useUpdateLookupTypeMutation();
  const [deleteLookupType, { isLoading: isDeleting }] = useDeleteLookupTypeMutation();

  // Form
  const { control, handleSubmit, reset, formState: { errors } } = useForm<LookupTypeFormData>({
    resolver: zodResolver(lookupTypeSchema),
    defaultValues: {
      name: '',
      description: '',
      category: '',
      sortOrder: 0,
      active: true,
    },
  });

  // Handle add
  const handleAdd = useCallback(() => {
    setEditingType(null);
    reset({
      name: '',
      description: '',
      category: '',
      sortOrder: 0,
      active: true,
    });
    setDialogOpen(true);
  }, [reset]);

  // Handle edit
  const handleEdit = useCallback((type: LookupType) => {
    setEditingType(type);
    reset({
      id: type.id,
      name: type.name,
      description: type.description || '',
      category: type.category,
      sortOrder: type.sortOrder || 0,
      active: type.active ?? true,
    });
    setDialogOpen(true);
  }, [reset]);

  // Handle save
  const onSubmit = async (data: LookupTypeFormData) => {
    try {
      if (editingType) {
        await updateLookupType({ id: editingType.id!, data }).unwrap();
      } else {
        await createLookupType(data).unwrap();
      }
      setDialogOpen(false);
      refetch();
    } catch (error) {
      console.error('Failed to save lookup type:', error);
    }
  };

  // Handle delete
  const handleDeleteClick = useCallback((type: LookupType) => {
    setTypeToDelete(type);
    setDeleteDialogOpen(true);
  }, []);

  const handleDeleteConfirm = async () => {
    if (typeToDelete) {
      try {
        await deleteLookupType(typeToDelete.id!).unwrap();
        setDeleteDialogOpen(false);
        setTypeToDelete(null);
        refetch();
      } catch (error) {
        console.error('Failed to delete lookup type:', error);
      }
    }
  };

  // Table columns
  const columns: DataTableColumn<LookupType>[] = [
    {
      id: 'name',
      label: 'Name',
      sortable: true,
      width: 200,
    },
    {
      id: 'category',
      label: 'Category',
      sortable: true,
      width: 150,
    },
    {
      id: 'description',
      label: 'Description',
      sortable: false,
      width: 300,
      format: (value) => value || 'N/A',
    },
    {
      id: 'sortOrder',
      label: 'Sort Order',
      sortable: true,
      width: 100,
      align: 'center',
      format: (value) => value ?? 0,
    },
    {
      id: 'active',
      label: 'Status',
      sortable: true,
      width: 100,
      format: (value) => (
        <Chip
          label={value ? 'Active' : 'Inactive'}
          size="small"
          color={value ? 'success' : 'default'}
        />
      ),
      exportFormat: (value) => (value ? 'Active' : 'Inactive'),
    },
    {
      id: 'id',
      label: 'Actions',
      sortable: false,
      width: 120,
      format: (_value, row) => (
        <Box display="flex" gap={0.5}>
          <IconButton
            size="small"
            onClick={() => handleEdit(row)}
            disabled={!canWrite}
          >
            <EditIcon fontSize="small" />
          </IconButton>
          <IconButton
            size="small"
            onClick={() => handleDeleteClick(row)}
            disabled={!canWrite}
            color="error"
          >
            <DeleteIcon fontSize="small" />
          </IconButton>
        </Box>
      ),
    },
  ];

  return (
    <Box sx={{ p: 3 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">Lookup Types</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
          disabled={!canWrite}
        >
          Add Lookup Type
        </Button>
      </Box>

      <DataTable
        columns={columns}
        data={data?.content || []}
        loading={isLoading}
        error={error ? 'Failed to load lookup types' : null}
        totalElements={data?.totalElements || 0}
        onRefresh={refetch}
        title={`${data?.totalElements || 0} Lookup Types`}
        exportFileName="lookup-types"
        exportEnabled
      />

      {/* Add/Edit Dialog */}
      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)} maxWidth="sm" fullWidth>
        <form onSubmit={handleSubmit(onSubmit)}>
          <DialogTitle>
            {editingType ? 'Edit Lookup Type' : 'Add Lookup Type'}
          </DialogTitle>
          <DialogContent>
            <Grid container spacing={2} sx={{ mt: 1 }}>
              <Grid item xs={12}>
                <Controller
                  name="name"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Name"
                      fullWidth
                      required
                      error={!!errors.name}
                      helperText={errors.name?.message}
                    />
                  )}
                />
              </Grid>

              <Grid item xs={12}>
                <Controller
                  name="category"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Category"
                      fullWidth
                      required
                      error={!!errors.category}
                      helperText={errors.category?.message}
                    />
                  )}
                />
              </Grid>

              <Grid item xs={12}>
                <Controller
                  name="description"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Description"
                      fullWidth
                      multiline
                      rows={3}
                      error={!!errors.description}
                      helperText={errors.description?.message}
                    />
                  )}
                />
              </Grid>

              <Grid item xs={12} sm={6}>
                <Controller
                  name="sortOrder"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Sort Order"
                      type="number"
                      fullWidth
                      error={!!errors.sortOrder}
                      helperText={errors.sortOrder?.message}
                    />
                  )}
                />
              </Grid>

              <Grid item xs={12} sm={6}>
                <Controller
                  name="active"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Status"
                      select
                      fullWidth
                      SelectProps={{ native: true }}
                      value={field.value ? 'true' : 'false'}
                      onChange={(e) => field.onChange(e.target.value === 'true')}
                    >
                      <option value="true">Active</option>
                      <option value="false">Inactive</option>
                    </TextField>
                  )}
                />
              </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setDialogOpen(false)}>Cancel</Button>
            <Button
              type="submit"
              variant="contained"
              disabled={isCreating || isUpdating}
            >
              {editingType ? 'Update' : 'Create'}
            </Button>
          </DialogActions>
        </form>
      </Dialog>

      {/* Delete Confirmation Dialog */}
      <Dialog open={deleteDialogOpen} onClose={() => setDeleteDialogOpen(false)}>
        <DialogTitle>Confirm Delete</DialogTitle>
        <DialogContent>
          <Typography>
            Are you sure you want to delete the lookup type "{typeToDelete?.name}"?
            This action cannot be undone.
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>Cancel</Button>
          <Button
            onClick={handleDeleteConfirm}
            color="error"
            variant="contained"
            disabled={isDeleting}
          >
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
