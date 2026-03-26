/**
 * Expenses Page
 *
 * Shows unbillable network expense accruals - services that are billing
 * to Endeavor but have not yet been activated/completed.
 * Includes summary cards, expense accrual chart, and detail table.
 */

import { useMemo } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
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

const COLORS = ['#dc2626', '#f59e0b', '#2563eb', '#16a34a', '#8b5cf6', '#06b6d4'];

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

  if (isLoading) {
    return (
      <div className="p-6">
        <h1 className="text-3xl font-bold mb-6">Manage Expenses</h1>
        <div className="flex justify-center p-12 text-muted-foreground">Loading expense data...</div>
      </div>
    );
  }

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Manage Expenses</h1>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold text-red-600">{formatCurrency(totalAccrual)}</div>
            <p className="text-sm text-muted-foreground">Total Open Expense Accrual</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{expenses.length}</div>
            <p className="text-sm text-muted-foreground">Unbilled Services</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{byProvider.length}</div>
            <p className="text-sm text-muted-foreground">Providers with Accruals</p>
          </CardContent>
        </Card>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Expense Accrual by Provider</CardTitle>
          </CardHeader>
          <CardContent>
            {byProvider.length === 0 ? (
              <p className="text-muted-foreground text-sm">No expense accrual data.</p>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={byProvider}>
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

        <Card>
          <CardHeader>
            <CardTitle className="text-base">Expense Accrual by Company</CardTitle>
          </CardHeader>
          <CardContent>
            {byCompany.length === 0 ? (
              <p className="text-muted-foreground text-sm">No expense accrual data.</p>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={byCompany} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis type="number" tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <YAxis type="category" dataKey="name" width={120} tick={{ fontSize: 12 }} />
                  <Tooltip formatter={(value: number) => [formatCurrency(value), 'Accrual']} />
                  <Bar dataKey="accrual" fill="#f59e0b" radius={[0, 4, 4, 0]} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>
      </div>

      {/* Detail Table */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">Open Expense Accrual Detail</CardTitle>
        </CardHeader>
        <CardContent>
          {expenses.length === 0 ? (
            <p className="text-muted-foreground text-sm">No unbillable expense accrual data.</p>
          ) : (
            <div className="overflow-x-auto max-h-[400px] overflow-y-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Company</TableHead>
                    <TableHead>Location</TableHead>
                    <TableHead>Provider</TableHead>
                    <TableHead>Type</TableHead>
                    <TableHead>Status</TableHead>
                    <TableHead className="text-right">MRC</TableHead>
                    <TableHead>Billed To</TableHead>
                    <TableHead>Data Prov. Complete</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {expenses.map((s) => (
                    <TableRow key={s.serviceId}>
                      <TableCell className="text-sm">{s.companyName}</TableCell>
                      <TableCell className="text-sm">{s.locationName}</TableCell>
                      <TableCell className="text-sm">{s.provider}</TableCell>
                      <TableCell className="text-sm">{s.serviceType}</TableCell>
                      <TableCell className="text-sm">{s.serviceStatus}</TableCell>
                      <TableCell className="text-sm text-right">{formatCurrency(s.serviceMrc || 0)}</TableCell>
                      <TableCell className="text-sm">{s.serviceBilledTo}</TableCell>
                      <TableCell className="text-sm">
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
        </CardContent>
      </Card>
    </div>
  );
}
