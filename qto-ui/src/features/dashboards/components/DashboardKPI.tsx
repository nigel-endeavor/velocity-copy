/**
 * Dashboard KPI Component
 *
 * Key Performance Indicators visualization
 */

import { useMemo } from 'react';
import { TrendingUp, TrendingDown, CheckCircle, Clock } from 'lucide-react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { Card, CardContent } from '@/components/ui/card';
import { cn } from '@/lib/utils';

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

/**
 * KPI Card Component
 */
interface KPICardProps {
  title: string;
  value: string | number;
  trend?: number;
  target?: number;
  current?: number;
  icon?: React.ReactNode;
  color?: 'primary' | 'success' | 'warning' | 'error';
}

function KPICard({
  title,
  value,
  trend,
  target,
  current,
  icon,
  color = 'primary',
}: KPICardProps) {
  const progress = target && current ? (current / target) * 100 : undefined;

  const colorClasses = {
    primary: 'text-primary',
    success: 'text-green-600',
    warning: 'text-orange-500',
    error: 'text-red-600',
  };

  const progressColorClasses = {
    success: 'bg-green-600',
    warning: 'bg-orange-500',
    primary: 'bg-primary',
  };

  return (
    <Card>
      <CardContent className="p-6">
        <div className="flex items-center justify-between mb-2">
          <p className="text-sm text-muted-foreground">
            {title}
          </p>
          {icon}
        </div>

        <h2 className={cn("text-3xl font-bold", colorClasses[color])}>
          {value}
        </h2>

        {trend !== undefined && (
          <div className="flex items-center mt-2">
            {trend >= 0 ? (
              <TrendingUp className="h-4 w-4 text-green-600" />
            ) : (
              <TrendingDown className="h-4 w-4 text-red-600" />
            )}
            <p className={cn(
              "text-sm ml-1",
              trend >= 0 ? "text-green-600" : "text-red-600"
            )}>
              {trend > 0 ? '+' : ''}
              {trend}% from last month
            </p>
          </div>
        )}

        {progress !== undefined && (
          <div className="mt-4">
            <div className="flex justify-between mb-1">
              <span className="text-xs text-muted-foreground">Progress to Goal</span>
              <span className="text-xs text-muted-foreground">{progress.toFixed(0)}%</span>
            </div>
            <div className="w-full bg-gray-200 rounded-full h-2">
              <div
                className={cn(
                  "h-2 rounded-full transition-all",
                  progressColorClasses[progress >= 100 ? 'success' : progress >= 75 ? 'primary' : 'warning']
                )}
                style={{ width: `${Math.min(progress, 100)}%` }}
              />
            </div>
          </div>
        )}
      </CardContent>
    </Card>
  );
}

/**
 * Dashboard KPI Component
 */
export default function DashboardKPI() {
  // Mock data - replace with actual API calls
  const kpiData = useMemo(
    () => ({
      totalOrders: 1234,
      activeOrders: 456,
      completedOrders: 678,
      avgOrderValue: 137500,
      onTimeDelivery: 94.5,
      customerSatisfaction: 4.7,
      ordersByStatus: {
        labels: ['New', 'In Progress', 'Pending', 'Completed', 'Cancelled'],
        data: [150, 456, 120, 678, 30],
        colors: [
          'rgba(25, 118, 210, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(156, 39, 176, 0.8)',
          'rgba(76, 175, 80, 0.8)',
          'rgba(244, 67, 54, 0.8)',
        ],
      },
      monthlyOrders: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        data: [185, 195, 210, 205, 215, 224],
      },
    }),
    []
  );

  // Orders by Status Chart
  const statusChartData = {
    labels: kpiData.ordersByStatus.labels,
    datasets: [
      {
        label: 'Orders',
        data: kpiData.ordersByStatus.data,
        backgroundColor: kpiData.ordersByStatus.colors,
      },
    ],
  };

  const statusChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'Orders by Status',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 14 },
      },
    },
  };

  // Monthly Orders Trend Chart
  const trendChartData = {
    labels: kpiData.monthlyOrders.labels,
    datasets: [
      {
        label: 'Orders',
        data: kpiData.monthlyOrders.data,
        backgroundColor: 'rgba(25, 118, 210, 0.8)',
      },
    ],
  };

  const trendChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'Monthly Order Trend',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 12 },
      },
    },
  };

  return (
    <div>
      {/* KPI Cards */}
      <div className="grid grid-cols-12 gap-6 mb-6">
        <div className="col-span-12 md:col-span-6 lg:col-span-3">
          <KPICard
            title="Total Orders"
            value={kpiData.totalOrders.toLocaleString()}
            trend={5.3}
            icon={<CheckCircle className="h-5 w-5 text-primary" />}
            color="primary"
          />
        </div>

        <div className="col-span-12 md:col-span-6 lg:col-span-3">
          <KPICard
            title="Active Orders"
            value={kpiData.activeOrders.toLocaleString()}
            trend={2.1}
            icon={<Clock className="h-5 w-5 text-orange-500" />}
            color="warning"
          />
        </div>

        <div className="col-span-12 md:col-span-6 lg:col-span-3">
          <KPICard
            title="Completed Orders"
            value={kpiData.completedOrders.toLocaleString()}
            trend={8.7}
            icon={<CheckCircle className="h-5 w-5 text-green-600" />}
            color="success"
          />
        </div>

        <div className="col-span-12 md:col-span-6 lg:col-span-3">
          <KPICard
            title="Avg Order Value"
            value={`$${(kpiData.avgOrderValue / 1000).toFixed(0)}K`}
            trend={-1.5}
            icon={<TrendingUp className="h-5 w-5 text-primary" />}
            color="primary"
          />
        </div>
      </div>

      {/* Performance Metrics */}
      <div className="grid grid-cols-12 gap-6 mb-6">
        <div className="col-span-12 md:col-span-6">
          <KPICard
            title="On-Time Delivery Rate"
            value={`${kpiData.onTimeDelivery}%`}
            current={kpiData.onTimeDelivery}
            target={95}
            color="success"
          />
        </div>

        <div className="col-span-12 md:col-span-6">
          <KPICard
            title="Customer Satisfaction"
            value={`${kpiData.customerSatisfaction}/5.0`}
            current={kpiData.customerSatisfaction}
            target={4.5}
            color="success"
          />
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12 lg:col-span-6">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={statusChartData} options={statusChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-6">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={trendChartData} options={trendChartOptions} />
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
