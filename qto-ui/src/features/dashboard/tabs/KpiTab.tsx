/**
 * KPI (Key Performance Indicators) Tab
 *
 * Shows:
 * - Network Delivery Interval (line chart over past 6 months)
 * - Service completion metrics
 */

import { useMemo } from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from 'recharts';
import { useGetWipServicesQuery } from '@/services/api/wipViewsApi';
import { Activity, CheckCircle2, Gauge } from 'lucide-react';
import { DashboardDataSurface, DashboardMetricCard, DashboardPanel } from '../components/DashboardPrimitives';

function computeDeliveryIntervals(services: { dataProvisioningCompleteDate: string | null; completeDate: string | null }[]) {
  const now = new Date();
  const months: { month: string; avgDays: number; count: number }[] = [];

  for (let i = 5; i >= 0; i--) {
    const d = new Date(now.getFullYear(), now.getMonth() - i, 1);
    const label = d.toLocaleDateString('en-US', { month: 'short', year: 'numeric' });

    const monthServices = services.filter((s) => {
      if (!s.completeDate) return false;
      const cd = new Date(s.completeDate);
      return cd.getFullYear() === d.getFullYear() && cd.getMonth() === d.getMonth();
    });

    let totalDays = 0;
    let count = 0;
    monthServices.forEach((s) => {
      if (s.dataProvisioningCompleteDate && s.completeDate) {
        const start = new Date(s.dataProvisioningCompleteDate);
        const end = new Date(s.completeDate);
        const diffMs = end.getTime() - start.getTime();
        const diffDays = Math.max(0, Math.round(diffMs / (1000 * 60 * 60 * 24)));
        totalDays += diffDays;
        count++;
      }
    });

    months.push({
      month: label,
      avgDays: count > 0 ? Math.round(totalDays / count) : 0,
      count,
    });
  }

  return months;
}

export default function KpiTab() {
  const { data: wipServices = [], isLoading } = useGetWipServicesQuery({ allStatuses: true });

  const deliveryData = useMemo(() => computeDeliveryIntervals(wipServices), [wipServices]);

  const totalComplete = wipServices.filter((s) => s.completeDate).length;
  const totalInProgress = wipServices.filter((s) => !s.completeDate && s.serviceStatus === 'In Progress').length;

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading KPI data...</div>;
  }

  return (
    <div className="mt-4 space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <DashboardMetricCard label="Completed" value={totalComplete} caption="Services that reached completion." icon={CheckCircle2} tone="green" />
        <DashboardMetricCard label="In progress" value={totalInProgress} caption="Active items still moving through delivery." icon={Activity} tone="cyan" />
        <DashboardMetricCard label="Total tracked" value={wipServices.length} caption="The full KPI dataset for this reporting window." icon={Gauge} tone="brand" />
      </div>

      <DashboardPanel title="Network delivery interval" description="Average delivery days and completion volume over the last six months.">
        <DashboardDataSurface>
          <ResponsiveContainer width="100%" height={350}>
            <LineChart data={deliveryData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" tick={{ fontSize: 12 }} />
              <YAxis allowDecimals={false} label={{ value: 'Avg Days', angle: -90, position: 'insideLeft' }} />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="avgDays" name="Avg Delivery Days" stroke="#118ad3" strokeWidth={3} dot={{ r: 5, fill: '#118ad3' }} />
              <Line type="monotone" dataKey="count" name="Services Completed" stroke="#2baa7b" strokeWidth={2.5} dot={{ r: 4, fill: '#2baa7b' }} strokeDasharray="6 5" />
            </LineChart>
          </ResponsiveContainer>
        </DashboardDataSurface>
      </DashboardPanel>
    </div>
  );
}
