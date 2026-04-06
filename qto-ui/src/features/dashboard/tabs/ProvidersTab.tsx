/**
 * Providers Tab
 *
 * Shows:
 * - Install Interval by Provider (bar chart)
 * - Provider Reliance to Network (pie chart)
 * - Service distribution by provider
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
  PieChart,
  Pie,
  Cell,
} from 'recharts';
import { useGetWipServicesQuery } from '@/services/api/wipViewsApi';
import type { WipServiceView } from '@/services/api/wipViewsApi';
import { Building2, CircleDollarSign, Network } from 'lucide-react';
import { DashboardDataSurface, DashboardMetricCard, DashboardPanel } from '../components/DashboardPrimitives';

const COLORS = ['#118ad3', '#11b5d8', '#ef5b93', '#8e61c9', '#ffb84d', '#2baa7b', '#183f5c', '#8bc34a'];

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
  const topProvider = providerCounts[0];
  const topReliance = providerReliance[0];

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading provider data...</div>;
  }

  return (
    <div className="mt-4 space-y-6">
      <div className="grid gap-4 md:grid-cols-3">
        <DashboardMetricCard label="Providers" value={providerCounts.length} caption="Distinct provider groups represented in the dataset." icon={Building2} tone="brand" />
        <DashboardMetricCard label="Highest volume" value={topProvider?.name || 'N/A'} caption={topProvider ? `${topProvider.count} services tracked` : 'No provider volume available.'} icon={Network} tone="pink" className="md:col-span-2" />
      </div>

      <DashboardPanel title="Install interval by provider" description="Average days to install across providers with completed services.">
          {installIntervals.length === 0 ? (
            <p className="text-sm text-slate-500">No completed service data available for interval calculation.</p>
          ) : (
            <DashboardDataSurface>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={installIntervals} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis type="number" allowDecimals={false} label={{ value: 'Avg Days', position: 'insideBottom', offset: -5 }} />
                  <YAxis type="category" dataKey="name" width={120} tick={{ fontSize: 12 }} />
                  <Tooltip />
                  <Bar dataKey="avgDays" fill="#118ad3" radius={[0, 10, 10, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </DashboardDataSurface>
          )}
      </DashboardPanel>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <DashboardPanel
          title="Provider reliance"
          description={topReliance ? `${topReliance.name} currently represents the largest MRC concentration.` : 'MRC distribution across providers.'}
        >
          <DashboardDataSurface>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie data={providerReliance} dataKey="mrc" nameKey="name" cx="50%" cy="50%" outerRadius={100} label={({ name, percent }) => `${name} ${((percent ?? 0) * 100).toFixed(0)}%`}>
                  {providerReliance.map((_, i) => (
                    <Cell key={i} fill={COLORS[i % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </DashboardDataSurface>
        </DashboardPanel>

        <DashboardPanel title="Service count by provider" description="Volume comparison across provider partners.">
          <DashboardDataSurface>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={providerCounts}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Bar dataKey="count" fill="#8e61c9" radius={[10, 10, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </DashboardDataSurface>
        </DashboardPanel>
      </div>

      <DashboardPanel title="MRC concentration note" description="A quick takeaway card for procurement and delivery discussions.">
        <div className="rounded-[24px] bg-[linear-gradient(135deg,rgba(17,138,211,0.10),rgba(255,255,255,0.92))] px-5 py-5 text-sm leading-7 text-slate-600">
          <div className="mb-3 flex items-center gap-3">
            <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-white text-brand shadow-sm">
              <CircleDollarSign className="h-5 w-5" />
            </div>
            <div>
              <p className="font-heading text-lg uppercase tracking-[0.12em] text-slate-900">Portfolio weighting</p>
              <p className="text-sm text-slate-500">Use reliance and interval together before escalating provider performance.</p>
            </div>
          </div>
          The redesigned provider view keeps interval, spend concentration, and service volume adjacent so commercial and operational context can be reviewed in one pass.
        </div>
      </DashboardPanel>
    </div>
  );
}
