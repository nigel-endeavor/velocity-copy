/**
 * Inventory Tab
 *
 * Shows:
 * - Inventory Valuation (MRC over time)
 * - Inventory Counts (service counts over time)
 * - New Inventory (recently added to inventory)
 */

import { useMemo } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  BarChart,
  Bar,
  Legend,
} from 'recharts';
import { useGetWipServicesQuery, useGetMonthlySpendQuery } from '@/services/api/wipViewsApi';
import type { WipServiceView } from '@/services/api/wipViewsApi';

function formatCurrency(val: number) {
  return `$${val.toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
}

function computeInventoryValuation(services: WipServiceView[]) {
  const inventoryServices = services.filter((s) => s.currentInventory);
  const byProvider: Record<string, number> = {};
  inventoryServices.forEach((s) => {
    const provider = s.provider || 'Unknown';
    byProvider[provider] = (byProvider[provider] || 0) + (s.serviceMrc || 0);
  });
  return Object.entries(byProvider)
    .map(([name, mrc]) => ({ name, mrc }))
    .sort((a, b) => b.mrc - a.mrc);
}

function computeInventoryCounts(services: WipServiceView[]) {
  const inventoryServices = services.filter((s) => s.currentInventory);
  const byType: Record<string, number> = {};
  inventoryServices.forEach((s) => {
    const type = s.serviceType || 'Unknown';
    byType[type] = (byType[type] || 0) + 1;
  });
  return Object.entries(byType)
    .map(([name, count]) => ({ name, count }))
    .sort((a, b) => b.count - a.count);
}

function computeNewInventory(services: WipServiceView[]) {
  const now = new Date();
  const months: { month: string; count: number; mrc: number }[] = [];

  for (let i = 5; i >= 0; i--) {
    const d = new Date(now.getFullYear(), now.getMonth() - i, 1);
    const label = d.toLocaleDateString('en-US', { month: 'short', year: 'numeric' });

    const monthServices = services.filter((s) => {
      if (!s.completeDate || !s.currentInventory) return false;
      const cd = new Date(s.completeDate);
      return cd.getFullYear() === d.getFullYear() && cd.getMonth() === d.getMonth();
    });

    months.push({
      month: label,
      count: monthServices.length,
      mrc: monthServices.reduce((sum, s) => sum + (s.serviceMrc || 0), 0),
    });
  }

  return months;
}

export default function InventoryTab() {
  const { data: allServices = [], isLoading } = useGetWipServicesQuery({ allStatuses: true });
  const { data: monthlySpend = [] } = useGetMonthlySpendQuery();

  const inventoryServices = allServices.filter((s) => s.currentInventory);
  const totalMrc = inventoryServices.reduce((sum, s) => sum + (s.serviceMrc || 0), 0);

  const valuation = useMemo(() => computeInventoryValuation(allServices), [allServices]);
  const counts = useMemo(() => computeInventoryCounts(allServices), [allServices]);
  const newInventory = useMemo(() => computeNewInventory(allServices), [allServices]);

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading inventory data...</div>;
  }

  return (
    <div className="space-y-6 mt-4">
      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{inventoryServices.length}</div>
            <p className="text-sm text-muted-foreground">Total Inventory Services</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{formatCurrency(totalMrc)}</div>
            <p className="text-sm text-muted-foreground">Total Monthly MRC</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{monthlySpend.length}</div>
            <p className="text-sm text-muted-foreground">Services with Spend Data</p>
          </CardContent>
        </Card>
      </div>

      {/* Inventory Valuation by Provider */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">Inventory Valuation by Provider (MRC)</CardTitle>
        </CardHeader>
        <CardContent>
          {valuation.length === 0 ? (
            <p className="text-muted-foreground text-sm">No inventory service data available.</p>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={valuation}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                <Tooltip formatter={(value: number) => [formatCurrency(value), 'MRC']} />
                <Bar dataKey="mrc" fill="#2563eb" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          )}
        </CardContent>
      </Card>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Inventory Counts by Type */}
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Inventory Counts by Service Type</CardTitle>
          </CardHeader>
          <CardContent>
            {counts.length === 0 ? (
              <p className="text-muted-foreground text-sm">No inventory data available.</p>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={counts}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                  <YAxis allowDecimals={false} />
                  <Tooltip />
                  <Bar dataKey="count" fill="#16a34a" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>

        {/* New Inventory by Month */}
        <Card>
          <CardHeader>
            <CardTitle className="text-base">New Inventory by Month</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={newInventory}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" tick={{ fontSize: 12 }} />
                <YAxis yAxisId="left" allowDecimals={false} />
                <YAxis yAxisId="right" orientation="right" tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                <Tooltip />
                <Legend />
                <Line yAxisId="left" type="monotone" dataKey="count" name="Services" stroke="#8b5cf6" strokeWidth={2} dot={{ r: 4 }} />
                <Line yAxisId="right" type="monotone" dataKey="mrc" name="MRC" stroke="#f59e0b" strokeWidth={2} dot={{ r: 4 }} />
              </LineChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
