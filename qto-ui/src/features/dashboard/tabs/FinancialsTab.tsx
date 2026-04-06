/**
 * Financials Tab
 *
 * Shows:
 * - Total MRC by Provider (bar chart)
 * - New Network Spend by Month (line chart)
 * - Open Expense Accruals (bar chart + total)
 */

import { useMemo } from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  LineChart,
  Line,
} from 'recharts';
import {
  useGetMonthlySpendQuery,
  useGetIncrementalNetworkSpendQuery,
  useGetUnbillableNetworkExpenseAccrualQuery,
} from '@/services/api/wipViewsApi';
import type { WipServiceView } from '@/services/api/wipViewsApi';
import { Banknote, Landmark, WalletCards } from 'lucide-react';
import { DashboardDataSurface, DashboardMetricCard, DashboardPanel } from '../components/DashboardPrimitives';

const COLORS = ['#118ad3', '#11b5d8', '#ef5b93', '#8e61c9', '#ffb84d', '#2baa7b'];

function formatCurrency(val: number) {
  return `$${val.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
}

function aggregateMrcByProvider(services: WipServiceView[]) {
  const byProvider: Record<string, number> = {};
  services.forEach((s) => {
    const provider = s.provider || 'Unknown';
    byProvider[provider] = (byProvider[provider] || 0) + (s.serviceMrc || 0);
  });
  return Object.entries(byProvider)
    .map(([name, mrc]) => ({ name, mrc }))
    .sort((a, b) => b.mrc - a.mrc);
}

function aggregateSpendByMonth(services: WipServiceView[]) {
  const byMonth: Record<string, number> = {};
  services.forEach((s) => {
    if (s.completeDate) {
      const date = new Date(s.completeDate);
      const key = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}`;
      byMonth[key] = (byMonth[key] || 0) + (s.serviceMrc || 0);
    }
  });
  return Object.entries(byMonth)
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([month, spend]) => ({ month, spend }));
}

function aggregateExpenseByProvider(services: WipServiceView[]) {
  const byProvider: Record<string, number> = {};
  services.forEach((s) => {
    const provider = s.provider || 'Unknown';
    byProvider[provider] = (byProvider[provider] || 0) + (s.serviceMrc || 0);
  });
  return Object.entries(byProvider)
    .map(([name, accrual]) => ({ name, accrual }))
    .sort((a, b) => b.accrual - a.accrual);
}

export default function FinancialsTab() {
  const { data: monthlySpend = [], isLoading: loadingMonthly } = useGetMonthlySpendQuery();
  const { data: incrementalSpend = [], isLoading: loadingIncremental } = useGetIncrementalNetworkSpendQuery();
  const { data: expenseAccrual = [], isLoading: loadingExpense } = useGetUnbillableNetworkExpenseAccrualQuery();

  const mrcByProvider = useMemo(() => aggregateMrcByProvider(monthlySpend), [monthlySpend]);
  const spendByMonth = useMemo(() => aggregateSpendByMonth(incrementalSpend), [incrementalSpend]);
  const expenseByProvider = useMemo(() => aggregateExpenseByProvider(expenseAccrual), [expenseAccrual]);
  const totalExpenseAccrual = useMemo(() => expenseAccrual.reduce((sum, s) => sum + (s.serviceMrc || 0), 0), [expenseAccrual]);
  const topMrcProvider = mrcByProvider[0];

  const isLoading = loadingMonthly || loadingIncremental || loadingExpense;

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading financials data...</div>;
  }

  return (
    <div className="mt-4 space-y-6">
      <div className="grid gap-4 md:grid-cols-3">
        <DashboardMetricCard label="Expense accrual" value={formatCurrency(totalExpenseAccrual)} caption="Open unbillable expense exposure." icon={WalletCards} tone="pink" />
        <DashboardMetricCard label="Top provider" value={topMrcProvider?.name || 'N/A'} caption={topMrcProvider ? formatCurrency(topMrcProvider.mrc) : 'No MRC data available.'} icon={Landmark} tone="violet" />
        <DashboardMetricCard label="Spend periods" value={spendByMonth.length} caption="Distinct month buckets in the network spend trend." icon={Banknote} tone="brand" />
      </div>

      <DashboardPanel title="Total MRC by provider" description="Provider mix measured by recurring monthly revenue.">
          {mrcByProvider.length === 0 ? (
            <p className="text-sm text-slate-500">No monthly spend data available.</p>
          ) : (
            <DashboardDataSurface>
              <ResponsiveContainer width="100%" height={350}>
                <BarChart data={mrcByProvider}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                  <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <Tooltip />
                  <Bar dataKey="mrc" fill="#118ad3" radius={[10, 10, 0, 0]}>
                    {mrcByProvider.map((_, i) => (
                      <rect key={i} fill={COLORS[i % COLORS.length]} />
                    ))}
                  </Bar>
                </BarChart>
              </ResponsiveContainer>
            </DashboardDataSurface>
          )}
      </DashboardPanel>

      <DashboardPanel title="New network spend by month" description="Trendline for monthly spend expansion across the network portfolio.">
          {spendByMonth.length === 0 ? (
            <p className="text-sm text-slate-500">No incremental spend data available.</p>
          ) : (
            <DashboardDataSurface>
              <ResponsiveContainer width="100%" height={300}>
                <LineChart data={spendByMonth}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" tick={{ fontSize: 12 }} />
                  <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <Tooltip />
                  <Line type="monotone" dataKey="spend" stroke="#2baa7b" strokeWidth={3} dot={{ r: 4, fill: '#2baa7b' }} />
                </LineChart>
              </ResponsiveContainer>
            </DashboardDataSurface>
          )}
      </DashboardPanel>

      <DashboardPanel
        title={
          <span>
            Open Expense Accruals
            <span className="ml-4 text-sm font-normal text-slate-500">
              Total: <strong className="text-slate-900">{formatCurrency(totalExpenseAccrual)}</strong>
            </span>
          </span>
        }
        description="Providers contributing to current open accrual exposure."
      >
          {expenseByProvider.length === 0 ? (
            <p className="text-sm text-slate-500">No unbillable expense accrual data.</p>
          ) : (
            <DashboardDataSurface>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={expenseByProvider}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                  <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <Tooltip />
                  <Bar dataKey="accrual" fill="#ef5b93" radius={[10, 10, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </DashboardDataSurface>
          )}
      </DashboardPanel>
    </div>
  );
}
