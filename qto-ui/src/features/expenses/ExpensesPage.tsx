/**
 * Expenses Page
 *
 * Shows unbillable network expense accruals - services that are billing
 * to Endeavor but have not yet been activated/completed.
 * Includes summary cards, expense accrual chart, and detail table.
 */

import { useMemo } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from 'recharts';
import { useGetUnbillableNetworkExpenseAccrualQuery } from '@/services/api/wipViewsApi';
import { AlertTriangle, Building2, ReceiptText } from 'lucide-react';

function formatCurrency(val: number) {
  return `$${val.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
}

export default function ExpensesPage() {
  const { data: expenses = [], isLoading } = useGetUnbillableNetworkExpenseAccrualQuery();

  const totalAccrual = useMemo(() => expenses.reduce((sum, s) => sum + (s.serviceMrc || 0), 0), [expenses]);

  const byProvider = useMemo(() => {
    const map: Record<string, number> = {};
    expenses.forEach((s) => {
      const provider = s.provider || 'Unknown';
      map[provider] = (map[provider] || 0) + (s.serviceMrc || 0);
    });
    return Object.entries(map)
      .map(([name, accrual]) => ({ name, accrual }))
      .sort((a, b) => b.accrual - a.accrual);
  }, [expenses]);

  const byCompany = useMemo(() => {
    const map: Record<string, number> = {};
    expenses.forEach((s) => {
      const company = s.companyName || 'Unknown';
      map[company] = (map[company] || 0) + (s.serviceMrc || 0);
    });
    return Object.entries(map)
      .map(([name, accrual]) => ({ name, accrual }))
      .sort((a, b) => b.accrual - a.accrual);
  }, [expenses]);

  const topCompanies = byCompany.slice(0, 5);

  if (isLoading) {
    return (
      <div className="app-page">
        <div className="app-page-header">
          <div>
            <p className="dashboard-kicker">Operations finance</p>
            <h1 className="app-page-title">Manage Expenses</h1>
          </div>
        </div>
        <div className="flex justify-center p-12 text-muted-foreground">Loading expense data...</div>
      </div>
    );
  }

  return (
    <div className="app-page">
      <div className="app-page-header">
        <div>
          <p className="dashboard-kicker">Operations finance</p>
          <h1 className="app-page-title">Manage Expenses</h1>
          <p className="app-page-subtitle">Expense accrual monitoring with accents balanced into the core blue system.</p>
        </div>
        <span className="dashboard-pill">Accruals overview</span>
      </div>

      <div className="grid grid-cols-1 gap-4 lg:grid-cols-3">
        <div className="app-stat-card">
          <div className="flex items-start justify-between gap-3">
            <div>
              <p className="dashboard-kicker text-rose-500">Open accrual</p>
              <div className="mt-3 text-[2rem] font-extrabold tracking-[-0.03em] text-rose-600">{formatCurrency(totalAccrual)}</div>
              <p className="mt-1 text-sm text-slate-500">Total open expense accrual</p>
            </div>
            <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-rose-50 text-rose-500">
              <AlertTriangle className="h-5 w-5" />
            </div>
          </div>
        </div>
        <div className="app-stat-card">
          <div className="flex items-start justify-between gap-3">
            <div>
              <p className="dashboard-kicker text-orange-500">Unbilled services</p>
              <div className="mt-3 text-[2rem] font-extrabold tracking-[-0.03em] text-slate-900">{expenses.length}</div>
              <p className="mt-1 text-sm text-slate-500">Services currently accruing expense</p>
            </div>
            <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-orange-50 text-orange-500">
              <ReceiptText className="h-5 w-5" />
            </div>
          </div>
        </div>
        <div className="app-stat-card">
          <div className="flex items-start justify-between gap-3">
            <div>
              <p className="dashboard-kicker text-sky-500">Providers</p>
              <div className="mt-3 text-[2rem] font-extrabold tracking-[-0.03em] text-slate-900">{byProvider.length}</div>
              <p className="mt-1 text-sm text-slate-500">Providers contributing to accruals</p>
            </div>
            <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-sky-50 text-sky-500">
              <Building2 className="h-5 w-5" />
            </div>
          </div>
        </div>
      </div>

      <div className="grid min-h-0 grid-cols-1 gap-4 xl:grid-cols-2">
        <div className="app-chart-panel">
          <div className="mb-3">
            <h2 className="font-heading text-lg font-bold tracking-[-0.02em] text-slate-900">Expense Accrual by Provider</h2>
            <p className="text-sm text-slate-500">Primary exposure concentrated by provider.</p>
          </div>
          <div className="h-[240px] rounded-xl bg-slate-50/70 p-2">
            {byProvider.length === 0 ? (
              <p className="text-sm text-slate-500">No expense accrual data.</p>
            ) : (
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={byProvider}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                  <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <Tooltip />
                  <Bar dataKey="accrual" fill="#e02424" radius={[8, 8, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </div>
        </div>

        <div className="app-chart-panel">
          <div className="mb-3">
            <h2 className="font-heading text-lg font-bold tracking-[-0.02em] text-slate-900">Expense Accrual by Company</h2>
            <p className="text-sm text-slate-500">Company exposure using the orange accent as a secondary signal.</p>
          </div>
          <div className="h-[240px] rounded-xl bg-slate-50/70 p-2">
            {topCompanies.length === 0 ? (
              <p className="text-sm text-slate-500">No expense accrual data.</p>
            ) : (
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={topCompanies} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis type="number" tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <YAxis type="category" dataKey="name" width={120} tick={{ fontSize: 12 }} />
                  <Tooltip />
                  <Bar dataKey="accrual" fill="#f59e0b" radius={[0, 8, 8, 0]} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </div>
        </div>
      </div>

      <div className="app-table-panel min-h-0 flex-1">
        <div className="border-b border-slate-200 px-5 py-4">
          <h2 className="font-heading text-lg font-bold tracking-[-0.02em] text-slate-900">Open Expense Accrual Detail</h2>
        </div>
        <div className="px-5 py-4 min-h-0">
          {expenses.length === 0 ? (
            <p className="text-sm text-slate-500">No unbillable expense accrual data.</p>
          ) : (
            <div className="max-h-[calc(100vh-26rem)] overflow-x-auto overflow-y-auto rounded-xl border border-slate-200">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Company</TableHead>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Location</TableHead>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Provider</TableHead>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Type</TableHead>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Status</TableHead>
                    <TableHead className="text-right text-[11px] uppercase tracking-[0.18em] text-slate-500">MRC</TableHead>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Billed To</TableHead>
                    <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Data Prov. Complete</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {expenses.map((s) => (
                    <TableRow key={s.serviceId}>
                      <TableCell className="text-sm text-slate-700">{s.companyName}</TableCell>
                      <TableCell className="text-sm text-slate-700">{s.locationName}</TableCell>
                      <TableCell className="text-sm text-slate-700">{s.provider}</TableCell>
                      <TableCell className="text-sm text-slate-700">{s.serviceType}</TableCell>
                      <TableCell className="text-sm text-slate-700">{s.serviceStatus}</TableCell>
                      <TableCell className="text-right text-sm font-semibold text-slate-900">{formatCurrency(s.serviceMrc || 0)}</TableCell>
                      <TableCell className="text-sm text-slate-700">{s.serviceBilledTo}</TableCell>
                      <TableCell className="text-sm text-slate-700">
                        {s.dataProvisioningCompleteDate
                          ? new Date(s.dataProvisioningCompleteDate).toLocaleDateString()
                          : '-'}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
