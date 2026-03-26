/**
 * Providers Tab
 *
 * Shows:
 * - Install Interval by Provider (bar chart)
 * - Provider Reliance to Network (pie chart)
 * - Service distribution by provider
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
  PieChart,
  Pie,
  Cell,
  Legend,
} from 'recharts';
import { useGetWipServicesQuery } from '@/services/api/wipViewsApi';
import type { WipServiceView } from '@/services/api/wipViewsApi';

const COLORS = ['#2563eb', '#16a34a', '#dc2626', '#f59e0b', '#8b5cf6', '#06b6d4', '#ec4899', '#84cc16'];

function computeInstallIntervals(services: WipServiceView[]) {
  const byProvider: Record<string, { totalDays: number; count: number }> = {};

  services.forEach((s) => {
    if (s.dataProvisioningCompleteDate && s.completeDate && s.provider) {
      const start = new Date(s.dataProvisioningCompleteDate);
      const end = new Date(s.completeDate);
      const diffDays = Math.max(0, Math.round((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)));
      if (!byProvider[s.provider]) byProvider[s.provider] = { totalDays: 0, count: 0 };
      byProvider[s.provider].totalDays += diffDays;
      byProvider[s.provider].count++;
    }
  });

  return Object.entries(byProvider)
    .map(([name, { totalDays, count }]) => ({
      name,
      avgDays: Math.round(totalDays / count),
      count,
    }))
    .sort((a, b) => b.avgDays - a.avgDays);
}

function computeProviderReliance(services: WipServiceView[]) {
  const byProvider: Record<string, number> = {};
  services.forEach((s) => {
    const provider = s.provider || 'Unknown';
    byProvider[provider] = (byProvider[provider] || 0) + (s.serviceMrc || 0);
  });
  return Object.entries(byProvider)
    .map(([name, mrc]) => ({ name, mrc }))
    .sort((a, b) => b.mrc - a.mrc);
}

function computeProviderServiceCounts(services: WipServiceView[]) {
  const byProvider: Record<string, number> = {};
  services.forEach((s) => {
    const provider = s.provider || 'Unknown';
    byProvider[provider] = (byProvider[provider] || 0) + 1;
  });
  return Object.entries(byProvider)
    .map(([name, count]) => ({ name, count }))
    .sort((a, b) => b.count - a.count);
}

export default function ProvidersTab() {
  const { data: services = [], isLoading } = useGetWipServicesQuery({ allStatuses: true });

  const installIntervals = useMemo(() => computeInstallIntervals(services), [services]);
  const providerReliance = useMemo(() => computeProviderReliance(services), [services]);
  const providerCounts = useMemo(() => computeProviderServiceCounts(services), [services]);

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading provider data...</div>;
  }

  return (
    <div className="space-y-6 mt-4">
      {/* Install Interval by Provider */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">Install Interval by Provider (Avg Days)</CardTitle>
        </CardHeader>
        <CardContent>
          {installIntervals.length === 0 ? (
            <p className="text-muted-foreground text-sm">No completed service data available for interval calculation.</p>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={installIntervals} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" allowDecimals={false} label={{ value: 'Avg Days', position: 'insideBottom', offset: -5 }} />
                <YAxis type="category" dataKey="name" width={120} tick={{ fontSize: 12 }} />
                <Tooltip formatter={(value: number, name: string) => [name === 'avgDays' ? `${value} days` : value, name === 'avgDays' ? 'Avg Install Days' : 'Count']} />
                <Bar dataKey="avgDays" fill="#2563eb" radius={[0, 4, 4, 0]} />
              </BarChart>
            </ResponsiveContainer>
          )}
        </CardContent>
      </Card>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Provider Reliance */}
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Provider Reliance (MRC Distribution)</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie data={providerReliance} dataKey="mrc" nameKey="name" cx="50%" cy="50%" outerRadius={100} label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}>
                  {providerReliance.map((_, i) => (
                    <Cell key={i} fill={COLORS[i % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip formatter={(value: number) => [`$${value.toLocaleString()}`, 'MRC']} />
              </PieChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        {/* Service Counts by Provider */}
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Service Count by Provider</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={providerCounts}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Bar dataKey="count" fill="#8b5cf6" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
