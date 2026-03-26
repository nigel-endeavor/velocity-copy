/**
 * KPI (Key Performance Indicators) Tab
 *
 * Shows:
 * - Network Delivery Interval (line chart over past 6 months)
 * - Service completion metrics
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
  Legend,
} from 'recharts';
import { useGetWipServicesQuery } from '@/services/api/wipViewsApi';

function computeDeliveryIntervals(services: { dataProvisioningCompleteDate: string | null; completeDate: string | null }[]) {
  const now = new Date();
  const months: { month: string; avgDays: number; count: number }[] = [];

  for (let i = 5; i >= 0; i--) {
    const d = new Date(now.getFullYear(), now.getMonth() - i, 1);
    const key = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`;
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
    <div className="space-y-6 mt-4">
      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{totalComplete}</div>
            <p className="text-sm text-muted-foreground">Completed Services</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{totalInProgress}</div>
            <p className="text-sm text-muted-foreground">In Progress</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="pt-6">
            <div className="text-2xl font-bold">{wipServices.length}</div>
            <p className="text-sm text-muted-foreground">Total Services</p>
          </CardContent>
        </Card>
      </div>

      {/* Network Delivery Interval */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">Network Delivery Interval (Avg Business Days)</CardTitle>
        </CardHeader>
        <CardContent>
          <ResponsiveContainer width="100%" height={350}>
            <LineChart data={deliveryData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" tick={{ fontSize: 12 }} />
              <YAxis allowDecimals={false} label={{ value: 'Avg Days', angle: -90, position: 'insideLeft' }} />
              <Tooltip
                formatter={(value: number, name: string) => [
                  name === 'avgDays' ? `${value} days` : value,
                  name === 'avgDays' ? 'Avg Delivery Days' : 'Services Completed',
                ]}
              />
              <Legend />
              <Line type="monotone" dataKey="avgDays" name="Avg Delivery Days" stroke="#2563eb" strokeWidth={2} dot={{ r: 5 }} />
              <Line type="monotone" dataKey="count" name="Services Completed" stroke="#16a34a" strokeWidth={2} dot={{ r: 4 }} strokeDasharray="5 5" />
            </LineChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
    </div>
  );
}
