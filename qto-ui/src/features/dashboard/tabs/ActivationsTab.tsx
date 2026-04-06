/**
 * Activations Tab
 *
 * Shows:
 * - Activation Event Length (bar chart)
 * - Activation Success Rate (bar chart)
 *
 * Uses WIP service data for activation-related metrics since
 * activation view endpoints return similar service lifecycle data.
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
  Legend,
} from 'recharts';
import { useGetWipServicesQuery } from '@/services/api/wipViewsApi';
import type { WipServiceView } from '@/services/api/wipViewsApi';
import { Clock3, Sparkles, Workflow } from 'lucide-react';
import { DashboardDataSurface, DashboardMetricCard, DashboardPanel } from '../components/DashboardPrimitives';

const COLORS = ['#2baa7b', '#ef5b93', '#ffb84d', '#118ad3', '#8e61c9'];

function computeActivationMetrics(services: WipServiceView[]) {
  let completed = 0;
  let inProgress = 0;
  let notStarted = 0;

  services.forEach((s) => {
    if (s.completeDate) completed++;
    else if (s.dataProvisioningCompleteDate) inProgress++;
    else notStarted++;
  });

  return [
    { name: 'Completed', value: completed },
    { name: 'In Progress', value: inProgress },
    { name: 'Not Started', value: notStarted },
  ].filter((d) => d.value > 0);
}

function computeEventLengthByType(services: WipServiceView[]) {
  const byType: Record<string, { totalDays: number; count: number }> = {};

  services.forEach((s) => {
    if (s.dataProvisioningCompleteDate && s.serviceType) {
      const start = new Date(s.dataProvisioningCompleteDate);
      const end = s.completeDate ? new Date(s.completeDate) : new Date();
      const diffDays = Math.max(0, Math.round((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)));
      if (!byType[s.serviceType]) byType[s.serviceType] = { totalDays: 0, count: 0 };
      byType[s.serviceType].totalDays += diffDays;
      byType[s.serviceType].count++;
    }
  });

  return Object.entries(byType)
    .map(([name, { totalDays, count }]) => ({
      name,
      avgDays: Math.round(totalDays / count),
      count,
    }))
    .sort((a, b) => b.avgDays - a.avgDays);
}

export default function ActivationsTab() {
  const { data: services = [], isLoading } = useGetWipServicesQuery({ allStatuses: true });

  const activationMetrics = useMemo(() => computeActivationMetrics(services), [services]);
  const eventLengthByType = useMemo(() => computeEventLengthByType(services), [services]);
  const completedValue = activationMetrics.find((metric) => metric.name === 'Completed')?.value ?? 0;
  const activeValue = activationMetrics.find((metric) => metric.name === 'In Progress')?.value ?? 0;

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading activation data...</div>;
  }

  return (
    <div className="mt-4 space-y-6">
      <div className="grid gap-4 md:grid-cols-3">
        <DashboardMetricCard label="Completed" value={completedValue} caption="Activation-ready services that have fully closed." icon={Sparkles} tone="green" />
        <DashboardMetricCard label="In progress" value={activeValue} caption="Activation events still underway or staging." icon={Workflow} tone="amber" />
        <DashboardMetricCard label="Service types" value={eventLengthByType.length} caption="Activation duration buckets represented in the chart." icon={Clock3} tone="brand" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <DashboardPanel title="Activation status distribution" description="Share of completed, in-progress, and not-started activations.">
            {activationMetrics.length === 0 ? (
              <p className="text-sm text-slate-500">No activation data available.</p>
            ) : (
              <DashboardDataSurface>
                <ResponsiveContainer width="100%" height={300}>
                  <PieChart>
                    <Pie data={activationMetrics} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={100} label>
                      {activationMetrics.map((_, i) => (
                        <Cell key={i} fill={COLORS[i % COLORS.length]} />
                      ))}
                    </Pie>
                    <Tooltip />
                    <Legend />
                  </PieChart>
                </ResponsiveContainer>
              </DashboardDataSurface>
            )}
        </DashboardPanel>

        <DashboardPanel title="Activation duration by service type" description="Average days between provisioning completion and activation outcome.">
            {eventLengthByType.length === 0 ? (
              <p className="text-sm text-slate-500">No activation event data available.</p>
            ) : (
              <DashboardDataSurface>
                <ResponsiveContainer width="100%" height={300}>
                  <BarChart data={eventLengthByType} layout="vertical">
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis type="number" allowDecimals={false} />
                    <YAxis type="category" dataKey="name" width={120} tick={{ fontSize: 12 }} />
                    <Tooltip />
                    <Bar dataKey="avgDays" fill="#ffb84d" radius={[0, 10, 10, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              </DashboardDataSurface>
            )}
        </DashboardPanel>
      </div>
    </div>
  );
}
