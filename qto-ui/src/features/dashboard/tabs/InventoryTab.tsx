/**
 * Inventory Tab
 *
 * Shows:
 * - Inventory Valuation (MRC over time)
 * - Inventory Counts (service counts over time)
 * - New Inventory (recently added to inventory)
 */

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
import { Boxes, ChartNoAxesColumn, PackageCheck } from 'lucide-react';
import { DashboardDataSurface, DashboardMetricCard, DashboardPanel } from '../components/DashboardPrimitives';

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

  const valuation = computeInventoryValuation(allServices);
  const counts = computeInventoryCounts(allServices);
  const newInventory = computeNewInventory(allServices);

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading inventory data...</div>;
  }

  return (
    <div className="mt-4 space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <DashboardMetricCard label="Inventory services" value={inventoryServices.length} caption="Active services currently flagged as inventory." icon={Boxes} tone="green" />
        <DashboardMetricCard label="Monthly MRC" value={formatCurrency(totalMrc)} caption="Recurring value represented by the current inventory set." icon={ChartNoAxesColumn} tone="brand" />
        <DashboardMetricCard label="Spend records" value={monthlySpend.length} caption="Inventory-adjacent records with monthly spend data." icon={PackageCheck} tone="violet" />
      </div>

      <DashboardPanel title="Inventory valuation by provider" description="Recurring monthly value of inventory by provider.">
          {valuation.length === 0 ? (
            <p className="text-sm text-slate-500">No inventory service data available.</p>
          ) : (
            <DashboardDataSurface>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={valuation}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                  <YAxis tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                  <Tooltip />
                  <Bar dataKey="mrc" fill="#118ad3" radius={[10, 10, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </DashboardDataSurface>
          )}
      </DashboardPanel>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <DashboardPanel title="Inventory counts by service type" description="Service-type mix for current inventory holdings.">
            {counts.length === 0 ? (
              <p className="text-sm text-slate-500">No inventory data available.</p>
            ) : (
              <DashboardDataSurface>
                <ResponsiveContainer width="100%" height={300}>
                  <BarChart data={counts}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#2baa7b" radius={[10, 10, 0, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              </DashboardDataSurface>
            )}
        </DashboardPanel>

        <DashboardPanel title="New inventory by month" description="Monthly service additions and associated recurring value.">
          <DashboardDataSurface>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={newInventory}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" tick={{ fontSize: 12 }} />
                <YAxis yAxisId="left" allowDecimals={false} />
                <YAxis yAxisId="right" orientation="right" tickFormatter={(v) => `$${(v / 1000).toFixed(0)}k`} />
                <Tooltip />
                <Legend />
                <Line yAxisId="left" type="monotone" dataKey="count" name="Services" stroke="#8e61c9" strokeWidth={3} dot={{ r: 4, fill: '#8e61c9' }} />
                <Line yAxisId="right" type="monotone" dataKey="mrc" name="MRC" stroke="#ffb84d" strokeWidth={3} dot={{ r: 4, fill: '#ffb84d' }} />
              </LineChart>
            </ResponsiveContainer>
          </DashboardDataSurface>
        </DashboardPanel>
      </div>
    </div>
  );
}
