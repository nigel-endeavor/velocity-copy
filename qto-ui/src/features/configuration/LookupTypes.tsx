import { useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowRight, Layers3, Settings2 } from 'lucide-react';

import { AppPage, PageHeader } from '@/components/layout/PageScaffold';
import { DataTable, type DataTableColumn } from '@/shared/components/DataTable';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { useSearchLookupTypesQuery } from '@/services/api/configurationApi';
import type { LookupType } from '@/shared/types/models';
import { getLookupTypeSortLabel } from '@/shared/types/models';

export default function LookupTypes() {
  const navigate = useNavigate();
  const { data, isLoading, error, refetch } = useSearchLookupTypesQuery({ limit: 500 });

  const lookupTypes = data?.collection ?? [];

  const stats = useMemo(() => {
    const categories = new Set(lookupTypes.map((item) => item.category || 'Uncategorized'));
    const modifiable = lookupTypes.filter((item) => item.modifiable).length;

    return [
      { label: 'Lookup types', value: lookupTypes.length.toString(), detail: 'Available for management', tone: 'brand' as const },
      { label: 'Categories', value: categories.size.toString(), detail: 'Grouped for navigation', tone: 'warm' as const },
      { label: 'Modifiable', value: modifiable.toString(), detail: 'Editable in the current backend', tone: 'success' as const },
    ];
  }, [lookupTypes]);

  const columns: DataTableColumn<LookupType>[] = [
    {
      id: 'name',
      label: 'Lookup Type',
      sortable: true,
      width: 220,
      format: (value, row) => (
        <button
          type="button"
          className="text-left font-medium text-primary transition-colors hover:text-primary/80"
          onClick={() => navigate(`/configuration/${row.id}`)}
        >
          {value}
        </button>
      ),
      exportFormat: (value) => String(value ?? ''),
    },
    {
      id: 'typeCode',
      label: 'Type Code',
      sortable: true,
      width: 180,
    },
    {
      id: 'category',
      label: 'Category',
      sortable: true,
      width: 180,
      format: (value) => value || 'Uncategorized',
    },
    {
      id: 'sortStrategy',
      label: 'Sort',
      sortable: true,
      width: 140,
      format: (value) => getLookupTypeSortLabel(typeof value === 'number' ? value : undefined),
      exportFormat: (value) => getLookupTypeSortLabel(typeof value === 'number' ? value : undefined),
    },
    {
      id: 'modifiable',
      label: 'Modifiable',
      sortable: true,
      width: 120,
      format: (value) => <Badge variant={value ? 'default' : 'secondary'}>{value ? 'Yes' : 'No'}</Badge>,
      exportFormat: (value) => (value ? 'Yes' : 'No'),
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
      label: 'Open',
      width: 120,
      sortable: false,
      format: (_value, row) => (
        <Button type="button" size="sm" variant="outline" onClick={() => navigate(`/configuration/${row.id}`)}>
          Manage
          <ArrowRight className="ml-2 h-4 w-4" />
        </Button>
      ),
    },
  ];

  return (
    <AppPage>
      <PageHeader
        eyebrow="System controls"
        title="Configuration Lookups"
        description="Browse lookup types and open the value sets that drive service, order, and operational workflows."
        stats={stats}
      />

      <div className="mb-5 grid gap-4 md:grid-cols-2 xl:grid-cols-3">
        <div className="app-surface p-5">
          <div className="flex items-start gap-3">
            <div className="rounded-xl border border-slate-200 bg-slate-50 p-3 text-slate-600">
              <Settings2 className="h-5 w-5" />
            </div>
            <div>
              <h2 className="text-base font-semibold text-slate-900">Backend-aligned configuration</h2>
              <p className="mt-1 text-sm leading-6 text-slate-500">
                The React screen now uses the live lookup endpoints instead of the placeholder CRUD flow that expected a different contract.
              </p>
            </div>
          </div>
        </div>

        <div className="app-surface p-5">
          <div className="flex items-start gap-3">
            <div className="rounded-xl border border-slate-200 bg-slate-50 p-3 text-slate-600">
              <Layers3 className="h-5 w-5" />
            </div>
            <div>
              <h2 className="text-base font-semibold text-slate-900">Drill into value sets</h2>
              <p className="mt-1 text-sm leading-6 text-slate-500">
                Each row opens a dedicated lookup-value editor with support for dependent parent-child lookup chains.
              </p>
            </div>
          </div>
        </div>
      </div>

      <DataTable
        columns={columns}
        data={lookupTypes}
        loading={isLoading}
        error={error ? 'Failed to load lookup types' : null}
        totalElements={data?.total || lookupTypes.length}
        onRefresh={refetch}
        title="Lookup types"
        exportFileName="lookup-types"
        exportEnabled
      />
    </AppPage>
  );
}
