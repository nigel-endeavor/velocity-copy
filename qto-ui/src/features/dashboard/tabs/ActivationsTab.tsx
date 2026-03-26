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

const COLORS = ['#16a34a', '#dc2626', '#f59e0b', '#2563eb', '#8b5cf6'];

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

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading activation data...</div>;
  }

  return (
    <div className="space-y-6 mt-4">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Activation Success Rate */}
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Activation Status Distribution</CardTitle>
          </CardHeader>
          <CardContent>
            {activationMetrics.length === 0 ? (
              <p className="text-muted-foreground text-sm">No activation data available.</p>
            ) : (
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
            )}
          </CardContent>
        </Card>

        {/* Activation Event Length */}
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Avg Activation Duration by Service Type (Days)</CardTitle>
          </CardHeader>
          <CardContent>
            {eventLengthByType.length === 0 ? (
              <p className="text-muted-foreground text-sm">No activation event data available.</p>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={eventLengthByType} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis type="number" allowDecimals={false} />
                  <YAxis type="category" dataKey="name" width={120} tick={{ fontSize: 12 }} />
                  <Tooltip formatter={(value: number, name: string) => [name === 'avgDays' ? `${value} days` : value, name === 'avgDays' ? 'Avg Days' : 'Count']} />
                  <Bar dataKey="avgDays" fill="#f59e0b" radius={[0, 4, 4, 0]} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
