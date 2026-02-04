/**
 * Lookup Values Management Component
 *
 * Manage lookup values for a specific lookup type
 */

import { useState, useCallback, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
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
  Breadcrumbs,
  Link,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  ArrowBack as ArrowBackIcon,
} from '@mui/icons-material';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';

import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import {
  useGetLookupTypeQuery,
  useSearchLookupValuesQuery,
  useCreateLookupValueMutation,
  useUpdateLookupValueMutation,
  useDeleteLookupValueMutation,
} from '@/services/api/configurationApi';
import type { LookupValue } from '@/shared/types/models';
import { usePermissions } from '@/shared/hooks/usePermissions';

/**
 * Lookup value form schema
 */
const lookupValueSchema = z.object({
  id: z.number().optional(),
  lookupTypeId: z.number(),
  code: z.string().min(1, 'Code is required').max(50, 'Code too long'),
  value: z.string().min(1, 'Value is required').max(100, 'Value too long'),
  description: z.string().max(500, 'Description too long').optional(),
  sortOrder: z.number().int().min(0, 'Sort order must be non-negative').optional(),
  active: z.boolean().default(true),
});

type LookupValueFormData = z.infer<typeof lookupValueSchema>;

/**
 * Lookup Values Component
 */
export default function LookupValues() {
  const { typeId } = useParams<{ typeId: string }>();
  const navigate = useNavigate();
  const { hasPermission } = usePermissions();
  const canWrite = hasPermission('configuration-write');

  // State
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editingValue, setEditingValue] = useState<LookupValue | null>(null);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [valueToDelete, setValueToDelete] = useState<LookupValue | null>(null);

  // API hooks
  const { data: lookupType } = useGetLookupTypeQuery(Number(typeId), {
    skip: !typeId,
  });
  const { data, isLoading, error, refetch } = useSearchLookupValuesQuery(
    { lookupTypeId: Number(typeId) },
    { skip: !typeId }
  );
  const [createLookupValue, { isLoading: isCreating }] = useCreateLookupValueMutation();
  const [updateLookupValue, { isLoading: isUpdating }] = useUpdateLookupValueMutation();
  const [deleteLookupValue, { isLoading: isDeleting }] = useDeleteLookupValueMutation();

  // Form
  const { control, handleSubmit, reset, formState: { errors } } = useForm<LookupValueFormData>({
    resolver: zodResolver(lookupValueSchema),
    defaultValues: {
      lookupTypeId: Number(typeId),
      code: '',
      value: '',
      description: '',
      sortOrder: 0,
      active: true,
    },
  });

  // Update form when typeId changes
  useEffect(() => {
    if (typeId) {
      reset({
        lookupTypeId: Number(typeId),
        code: '',
        value: '',
        description: '',
        sortOrder: 0,
        active: true,
      });
    }
  }, [typeId, reset]);

  // Handle add
  const handleAdd = useCallback(() => {
    setEditingValue(null);
    reset({
      lookupTypeId: Number(typeId),
      code: '',
      value: '',
      description: '',
      sortOrder: 0,
      active: true,
    });
    setDialogOpen(true);
  }, [typeId, reset]);

  // Handle edit
  const handleEdit = useCallback((value: LookupValue) => {
    setEditingValue(value);
    reset({
      id: value.id,
      lookupTypeId: Number(typeId),
      code: value.code,
      value: value.value,
      description: value.description || '',
      sortOrder: value.sortOrder || 0,
      active: value.active ?? true,
    });
    setDialogOpen(true);
  }, [typeId, reset]);

  // Handle save
  const onSubmit = async (data: LookupValueFormData) => {
    try {
      if (editingValue) {
        await updateLookupValue({ id: editingValue.id!, data }).unwrap();
      } else {
        await createLookupValue(data).unwrap();
      }
      setDialogOpen(false);
      refetch();
    } catch (error) {
      console.error('Failed to save lookup value:', error);
    }
  };

  // Handle delete
  const handleDeleteClick = useCallback((value: LookupValue) => {
    setValueToDelete(value);
    setDeleteDialogOpen(true);
  }, []);

  const handleDeleteConfirm = async () => {
    if (valueToDelete) {
      try {
        await deleteLookupValue(valueToDelete.id!).unwrap();
        setDeleteDialogOpen(false);
        setValueToDelete(null);
        refetch();
      } catch (error) {
        console.error('Failed to delete lookup value:', error);
      }
    }
  };

  // Handle back
  const handleBack = () => {
    navigate('/configuration/lookup-types');
  };

  // Table columns
  const columns: DataTableColumn<LookupValue>[] = [
    {
      id: 'code',
      label: 'Code',
      sortable: true,
      width: 150,
    },
    {
      id: 'value',
      label: 'Value',
      sortable: true,
      width: 200,
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
      {/* Breadcrumbs */}
      <Breadcrumbs sx={{ mb: 2 }}>
        <Link
          component="button"
          variant="body1"
          onClick={handleBack}
          underline="hover"
        >
          Lookup Types
        </Link>
        <Typography color="text.primary">{lookupType?.name || 'Lookup Values'}</Typography>
      </Breadcrumbs>

      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Box display="flex" alignItems="center" gap={2}>
          <IconButton onClick={handleBack}>
            <ArrowBackIcon />
          </IconButton>
          <Box>
            <Typography variant="h4">{lookupType?.name || 'Lookup Values'}</Typography>
            {lookupType?.description && (
              <Typography variant="body2" color="text.secondary">
                {lookupType.description}
              </Typography>
            )}
          </Box>
        </Box>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
          disabled={!canWrite}
        >
          Add Value
        </Button>
      </Box>

      <DataTable
        columns={columns}
        data={data?.content || []}
        loading={isLoading}
        error={error ? 'Failed to load lookup values' : null}
        totalElements={data?.totalElements || 0}
        onRefresh={refetch}
        title={`${data?.totalElements || 0} Values`}
        exportFileName={`lookup-values-${lookupType?.name || typeId}`}
        exportEnabled
      />

      {/* Add/Edit Dialog */}
      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)} maxWidth="sm" fullWidth>
        <form onSubmit={handleSubmit(onSubmit)}>
          <DialogTitle>
            {editingValue ? 'Edit Lookup Value' : 'Add Lookup Value'}
          </DialogTitle>
          <DialogContent>
            <Grid container spacing={2} sx={{ mt: 1 }}>
              <Grid item xs={12} sm={6}>
                <Controller
                  name="code"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Code"
                      fullWidth
                      required
                      error={!!errors.code}
                      helperText={errors.code?.message}
                    />
                  )}
                />
              </Grid>

              <Grid item xs={12} sm={6}>
                <Controller
                  name="value"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      label="Value"
                      fullWidth
                      required
                      error={!!errors.value}
                      helperText={errors.value?.message}
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
              {editingValue ? 'Update' : 'Create'}
            </Button>
          </DialogActions>
        </form>
      </Dialog>

      {/* Delete Confirmation Dialog */}
      <Dialog open={deleteDialogOpen} onClose={() => setDeleteDialogOpen(false)}>
        <DialogTitle>Confirm Delete</DialogTitle>
        <DialogContent>
          <Typography>
            Are you sure you want to delete the lookup value "{valueToDelete?.value}"?
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
