/**
 * Financials Tab
 *
 * Shows:
 * - Total MRC by Provider (bar chart)
 * - New Network Spend by Month (line chart)
 * - Open Expense Accruals (bar chart + total)
 */

import { useMemo } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
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

const COLORS = ['#2563eb', '#16a34a', '#dc2626', '#f59e0b', '#8b5cf6', '#06b6d4'];

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

  const isLoading = loadingMonthly || loadingIncremental || loadingExpense;

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading financials data...</div>;
  }

  return (
    <div className="space-y-6 mt-4">
      {/* Total MRC by Provider */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">Total MRC by Provider</CardTitle>
        </CardHeader>
        <CardContent>
          {mrcByProvider.length === 0 ? (
            <p className="text-muted-foreground text-sm">No monthly spend data available.</p>
          ) : (
            <ResponsiveContainer width="100%" height={350}>
              <BarChart data={mrcByProvider}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                <Tooltip formatter={(value: number) => [formatCurrency(value), 'MRC']} />
                <Bar dataKey="mrc" fill="#2563eb" radius={[4, 4, 0, 0]}>
                  {mrcByProvider.map((_, i) => (
                    <rect key={i} fill={COLORS[i % COLORS.length]} />
                  ))}
                </Bar>
              </BarChart>
            </ResponsiveContainer>
          )}
        </CardContent>
      </Card>

      {/* New Network Spend by Month */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">New Network Spend by Month</CardTitle>
        </CardHeader>
        <CardContent>
          {spendByMonth.length === 0 ? (
            <p className="text-muted-foreground text-sm">No incremental spend data available.</p>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={spendByMonth}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" tick={{ fontSize: 12 }} />
                <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                <Tooltip formatter={(value: number) => [formatCurrency(value), 'Spend']} />
                <Line type="monotone" dataKey="spend" stroke="#16a34a" strokeWidth={2} dot={{ r: 4 }} />
              </LineChart>
            </ResponsiveContainer>
          )}
        </CardContent>
      </Card>

      {/* Open Expense Accruals */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">
            Open Expense Accruals
            <span className="ml-4 text-sm font-normal text-muted-foreground">
              Total: <strong className="text-foreground">{formatCurrency(totalExpenseAccrual)}</strong>
            </span>
          </CardTitle>
        </CardHeader>
        <CardContent>
          {expenseByProvider.length === 0 ? (
            <p className="text-muted-foreground text-sm">No unbillable expense accrual data.</p>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={expenseByProvider}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                <Tooltip formatter={(value: number) => [formatCurrency(value), 'Accrual']} />
                <Bar dataKey="accrual" fill="#dc2626" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
