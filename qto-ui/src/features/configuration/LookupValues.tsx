import { useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, Pencil, Plus, Save, Trash2 } from 'lucide-react';

import { AppPage, PageHeader, PageToolbar } from '@/components/layout/PageScaffold';
import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Checkbox } from '@/components/ui/checkbox';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { usePermissions } from '@/shared/hooks/usePermissions';
import {
  useGetLookupTypeQuery,
  useSearchLookupValuesQuery,
  useSetLookupTypeValuesMutation,
} from '@/services/api/configurationApi';
import type { LookupType, LookupValue } from '@/shared/types/models';

type LookupValueDraft = LookupValue;

function createEmptyDraft(parentId?: number): LookupValueDraft {
  return {
    display: '',
    value: '',
    active: true,
    sortSequence: 0,
    parentId,
  };
}

export default function LookupValues() {
  const { typeId } = useParams<{ typeId: string }>();
  const navigate = useNavigate();
  const { hasPermission } = usePermissions();
  const canWrite = hasPermission('admin') || hasPermission('any-write');
  const lookupTypeId = Number(typeId);

  const [dialogOpen, setDialogOpen] = useState(false);
  const [editingIndex, setEditingIndex] = useState<number | null>(null);
  const [draftValue, setDraftValue] = useState<LookupValueDraft>(createEmptyDraft());
  const [draftValues, setDraftValues] = useState<LookupValueDraft[]>([]);
  const [selectedGrandParentValueId, setSelectedGrandParentValueId] = useState('');
  const [selectedParentValueId, setSelectedParentValueId] = useState('');
  const [saveError, setSaveError] = useState<string | null>(null);

  const { data: lookupType, isLoading: lookupTypeLoading, error: lookupTypeError } = useGetLookupTypeQuery(lookupTypeId, {
    skip: !Number.isFinite(lookupTypeId),
  });

  const { data: parentLookupType } = useGetLookupTypeQuery(lookupType?.parentLookupTypeId ?? 0, {
    skip: !lookupType?.parentLookupTypeId,
  });

  const { data: grandParentLookupType } = useGetLookupTypeQuery(parentLookupType?.parentLookupTypeId ?? 0, {
    skip: !parentLookupType?.parentLookupTypeId,
  });

  const selectedGrandParentValue = selectedGrandParentValueId ? Number(selectedGrandParentValueId) : undefined;
  const selectedParentValue = selectedParentValueId ? Number(selectedParentValueId) : undefined;

  const { data: grandParentValuesData } = useSearchLookupValuesQuery(
    { typeCode: grandParentLookupType?.typeCode ?? '' },
    { skip: !grandParentLookupType?.typeCode }
  );

  const { data: parentValuesData } = useSearchLookupValuesQuery(
    {
      typeCode: parentLookupType?.typeCode ?? '',
      parentId: grandParentLookupType ? selectedGrandParentValue : undefined,
    },
    {
      skip: !parentLookupType?.typeCode || Boolean(grandParentLookupType && !selectedGrandParentValue),
    }
  );

  const {
    data: lookupValuesData,
    isLoading: lookupValuesLoading,
    error: lookupValuesError,
    refetch,
  } = useSearchLookupValuesQuery(
    {
      typeCode: lookupType?.typeCode ?? '',
      parentId: parentLookupType ? selectedParentValue : undefined,
    },
    {
      skip: !lookupType?.typeCode || Boolean(parentLookupType && !selectedParentValue),
    }
  );

  const [setLookupTypeValues, { isLoading: isSaving }] = useSetLookupTypeValuesMutation();

  useEffect(() => {
    setDraftValues((lookupValuesData?.collection ?? []).map((value) => ({ ...value })));
  }, [lookupValuesData]);

  useEffect(() => {
    setSelectedGrandParentValueId('');
    setSelectedParentValueId('');
    setSaveError(null);
  }, [typeId]);

  const validationError = useMemo(() => {
    if (draftValues.some((value) => !value.display.trim() || !value.value.trim())) {
      return 'Every lookup value needs both a display label and a stored value.';
    }

    const normalizedDisplays = draftValues.map((value) => value.display.trim().toLowerCase());
    const hasDuplicateDisplay = normalizedDisplays.some((value, index) => normalizedDisplays.indexOf(value) !== index);
    if (hasDuplicateDisplay) {
      return 'Duplicate display values are not allowed in the same lookup set.';
    }

    return null;
  }, [draftValues]);

  const headerStats = useMemo(() => {
    const activeValues = draftValues.filter((value) => value.active).length;
    return [
      { label: 'Type code', value: lookupType?.typeCode || 'N/A', detail: lookupType?.category || 'Uncategorized', tone: 'brand' as const },
      { label: 'Values', value: draftValues.length.toString(), detail: `${activeValues} active`, tone: 'warm' as const },
      { label: 'Save mode', value: 'Batch', detail: 'Matches backend lookup update flow', tone: 'success' as const },
    ];
  }, [draftValues, lookupType]);

  const blockedByParentSelection = Boolean(parentLookupType && !selectedParentValue);
  const grandParentOptions = grandParentValuesData?.collection ?? [];
  const parentOptions = parentValuesData?.collection ?? [];

  const openCreateDialog = () => {
    setEditingIndex(null);
    setDraftValue(createEmptyDraft(selectedParentValue));
    setDialogOpen(true);
  };

  const openEditDialog = (index: number) => {
    setEditingIndex(index);
    setDraftValue({ ...draftValues[index] });
    setDialogOpen(true);
  };

  const saveDialogValue = () => {
    const normalizedDraft: LookupValueDraft = {
      ...draftValue,
      display: draftValue.display.trim(),
      value: draftValue.value.trim(),
      sortSequence: draftValue.sortSequence || draftValues.length + 1,
      parentId: selectedParentValue,
    };

    if (!normalizedDraft.display || !normalizedDraft.value) {
      setSaveError('Display and stored value are required.');
      return;
    }

    setSaveError(null);
    setDraftValues((current) => {
      if (editingIndex == null) {
        return [...current, normalizedDraft];
      }

      return current.map((value, index) => (index === editingIndex ? normalizedDraft : value));
    });
    setDialogOpen(false);
  };

  const removeValue = (index: number) => {
    setDraftValues((current) => current.filter((_, currentIndex) => currentIndex !== index));
  };

  const handleSaveAll = async () => {
    if (!lookupType) {
      return;
    }

    if (validationError) {
      setSaveError(validationError);
      return;
    }

    const body: LookupType = {
      ...lookupType,
      values: draftValues.map((value, index) => ({
        ...value,
        sortSequence: value.sortSequence || index + 1,
        parentId: selectedParentValue,
      })),
    };

    try {
      setSaveError(null);
      await setLookupTypeValues({ id: lookupType.id!, body }).unwrap();
      refetch();
    } catch (error) {
      setSaveError(error instanceof Error ? error.message : 'Failed to save lookup values.');
    }
  };

  const columns: DataTableColumn<LookupValueDraft>[] = [
    {
      id: 'display',
      label: 'Display',
      sortable: true,
      width: 220,
    },
    {
      id: 'value',
      label: 'Stored Value',
      sortable: true,
      width: 220,
    },
    {
      id: 'sortSequence',
      label: 'Sequence',
      sortable: true,
      width: 110,
      align: 'center',
      format: (value) => value ?? '-',
    },
    {
      id: 'active',
      label: 'Status',
      sortable: true,
      width: 120,
      format: (value) => <Badge variant={value ? 'default' : 'secondary'}>{value ? 'Active' : 'Inactive'}</Badge>,
      exportFormat: (value) => (value ? 'Active' : 'Inactive'),
    },
    {
      id: 'id',
      label: 'Actions',
      sortable: false,
      width: 140,
      format: (_value, row) => {
        const index = draftValues.findIndex((value) => value === row);
        return (
          <div className="flex items-center justify-end gap-2">
            <Button type="button" size="sm" variant="outline" onClick={() => openEditDialog(index)} disabled={!canWrite}>
              <Pencil className="h-4 w-4" />
            </Button>
            <Button type="button" size="sm" variant="outline" onClick={() => removeValue(index)} disabled={!canWrite}>
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
        );
      },
    },
  ];

  return (
    <AppPage>
      <PageHeader
        eyebrow="System controls"
        title={lookupType?.name || 'Lookup Values'}
        description="Manage the value set for the selected lookup type and save the full collection back through the backend lookup-type update endpoint."
        actions={
          <div className="flex flex-wrap items-center gap-2">
            <Button type="button" variant="outline" onClick={() => navigate('/configuration')}>
              <ArrowLeft className="mr-2 h-4 w-4" />
              Back to Types
            </Button>
            <Button type="button" variant="outline" onClick={openCreateDialog} disabled={!canWrite || blockedByParentSelection}>
              <Plus className="mr-2 h-4 w-4" />
              Add Value
            </Button>
            <Button
              type="button"
              onClick={handleSaveAll}
              disabled={!canWrite || isSaving || blockedByParentSelection || draftValues.length === 0}
            >
              <Save className="mr-2 h-4 w-4" />
              Save Changes
            </Button>
          </div>
        }
        stats={headerStats}
      />

      <PageToolbar>
        {grandParentLookupType ? (
          <div className="min-w-[220px]">
            <Label className="mb-2 block">{grandParentLookupType.name}</Label>
            <Select value={selectedGrandParentValueId} onValueChange={setSelectedGrandParentValueId}>
              <SelectTrigger className="h-11 rounded-xl border-slate-200 bg-white shadow-none">
                <SelectValue placeholder={`Select ${grandParentLookupType.name}`} />
              </SelectTrigger>
              <SelectContent>
                {grandParentOptions.map((value) => (
                  <SelectItem key={value.id} value={String(value.id)}>
                    {value.display}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        ) : null}

        {parentLookupType ? (
          <div className="min-w-[220px]">
            <Label className="mb-2 block">{parentLookupType.name}</Label>
            <Select value={selectedParentValueId} onValueChange={setSelectedParentValueId}>
              <SelectTrigger className="h-11 rounded-xl border-slate-200 bg-white shadow-none">
                <SelectValue placeholder={`Select ${parentLookupType.name}`} />
              </SelectTrigger>
              <SelectContent>
                {parentOptions.map((value) => (
                  <SelectItem key={value.id} value={String(value.id)}>
                    {value.display}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        ) : null}

        <span className="app-page-toolbar-note">
          {parentLookupType
            ? 'Choose the parent value context before editing child values.'
            : 'Changes are staged locally until you save the full collection.'}
        </span>
      </PageToolbar>

      {validationError ? (
        <div className="mb-5 rounded-2xl border border-amber-200 bg-amber-50 px-4 py-3 text-sm text-amber-900">
          {validationError}
        </div>
      ) : null}

      {saveError ? (
        <div className="mb-5 rounded-2xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800">
          {saveError}
        </div>
      ) : null}

      {blockedByParentSelection ? (
        <div className="mb-5 rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm text-slate-600">
          Select a parent lookup value to load and edit the dependent values for this type.
        </div>
      ) : null}

      <DataTable
        columns={columns}
        data={draftValues}
        loading={lookupTypeLoading || lookupValuesLoading}
        error={lookupTypeError || lookupValuesError ? 'Failed to load lookup values' : null}
        totalElements={draftValues.length}
        onRefresh={refetch}
        title={lookupType ? `${lookupType.name} values` : 'Lookup values'}
        exportFileName={`lookup-values-${lookupType?.typeCode || typeId}`}
        exportEnabled
      />

      <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>{editingIndex == null ? 'Add Lookup Value' : 'Edit Lookup Value'}</DialogTitle>
            <DialogDescription>
              Update the label, stored value, sort sequence, and active state before saving the full collection.
            </DialogDescription>
          </DialogHeader>

          <div className="grid gap-4">
            <div className="grid gap-2">
              <Label htmlFor="lookup-display">Display</Label>
              <Input
                id="lookup-display"
                value={draftValue.display}
                onChange={(event) => setDraftValue((current) => ({ ...current, display: event.target.value }))}
                placeholder="Displayed label"
              />
            </div>

            <div className="grid gap-2">
              <Label htmlFor="lookup-value">Stored Value</Label>
              <Input
                id="lookup-value"
                value={draftValue.value}
                onChange={(event) => setDraftValue((current) => ({ ...current, value: event.target.value }))}
                placeholder="Stored backend value"
              />
            </div>

            <div className="grid gap-2">
              <Label htmlFor="lookup-sequence">Sort Sequence</Label>
              <Input
                id="lookup-sequence"
                type="number"
                value={draftValue.sortSequence ?? 0}
                onChange={(event) =>
                  setDraftValue((current) => ({
                    ...current,
                    sortSequence: Number(event.target.value) || 0,
                  }))
                }
              />
            </div>

            <div className="flex items-center gap-3 rounded-2xl border border-slate-200 px-4 py-3">
              <Checkbox
                id="lookup-active"
                checked={draftValue.active}
                onCheckedChange={(checked) => setDraftValue((current) => ({ ...current, active: checked === true }))}
              />
              <Label htmlFor="lookup-active">Active</Label>
            </div>
          </div>

          <DialogFooter>
            <Button type="button" variant="outline" onClick={() => setDialogOpen(false)}>
              Cancel
            </Button>
            <Button type="button" onClick={saveDialogValue}>
              {editingIndex == null ? 'Add Value' : 'Update Value'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </AppPage>
  );
}
