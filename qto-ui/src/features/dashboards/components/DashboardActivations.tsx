/**
 * Dashboard Activations Component
 *
 * Service activation metrics and tracking
 */

import { useMemo } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  LineElement,
  PointElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar, Line, Pie } from 'react-chartjs-2';
import { Card, CardContent } from '@/components/ui/card';

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  LineElement,
  PointElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

/**
 * Dashboard Activations Component
 */
export default function DashboardActivations() {
  // Mock data - replace with actual API calls
  const activationData = useMemo(
    () => ({
      totalActivations: 342,
      pendingActivations: 87,
      completedThisMonth: 128,
      avgActivationTime: 14.5,
      byStatus: {
        labels: ['Completed', 'Pending', 'In Progress', 'Failed'],
        data: [255, 87, 45, 12],
        colors: [
          'rgba(76, 175, 80, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(25, 118, 210, 0.8)',
          'rgba(244, 67, 54, 0.8)',
        ],
      },
      monthlyTrend: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        completed: [95, 105, 115, 120, 125, 128],
        failed: [8, 6, 7, 5, 4, 3],
      },
      byProvider: {
        labels: ['AT&T', 'Verizon', 'Lumen', 'Comcast', 'Spectrum'],
        data: [95, 85, 68, 55, 39],
      },
    }),
    []
  );

  // Status Distribution Chart
  const statusChartData = {
    labels: activationData.byStatus.labels,
    datasets: [
      {
        data: activationData.byStatus.data,
        backgroundColor: activationData.byStatus.colors,
      },
    ],
  };

  const statusChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'right' as const,
      },
      title: {
        display: true,
        text: 'Activations by Status',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 14 },
        formatter: (value: number, context: any) => {
          const total = context.dataset.data.reduce((a: number, b: number) => a + b, 0);
          const percentage = ((value / total) * 100).toFixed(1);
          return `${value}\n(${percentage}%)`;
        },
      },
    },
  };

  // Monthly Trend Chart
  const trendChartData = {
    labels: activationData.monthlyTrend.labels,
    datasets: [
      {
        label: 'Completed',
        data: activationData.monthlyTrend.completed,
        borderColor: 'rgb(76, 175, 80)',
        backgroundColor: 'rgba(76, 175, 80, 0.1)',
        tension: 0.4,
      },
      {
        label: 'Failed',
        data: activationData.monthlyTrend.failed,
        borderColor: 'rgb(244, 67, 54)',
        backgroundColor: 'rgba(244, 67, 54, 0.1)',
        tension: 0.4,
      },
    ],
  };

  const trendChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Monthly Activation Trend',
        font: { size: 16 },
      },
      datalabels: {
        display: false,
      },
    },
  };

  // By Provider Chart
  const providerChartData = {
    labels: activationData.byProvider.labels,
    datasets: [
      {
        label: 'Activations',
        data: activationData.byProvider.data,
        backgroundColor: 'rgba(25, 118, 210, 0.8)',
      },
    ],
  };

  const providerChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    indexAxis: 'y' as const,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'Activations by Provider',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 12 },
        anchor: 'end' as const,
        align: 'start' as const,
      },
    },
  };

  return (
    <div>
      {/* Summary Cards */}
      <div className="grid grid-cols-12 gap-6 mb-6">
        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Total Activations
              </p>
              <h2 className="text-3xl font-bold">
                {activationData.totalActivations}
              </h2>
              <p className="text-sm text-green-600 mt-2">
                +6.8% from last month
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Pending Activations
              </p>
              <h2 className="text-3xl font-bold">
                {activationData.pendingActivations}
              </h2>
              <p className="text-sm text-orange-500 mt-2">
                Requires attention
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Completed This Month
              </p>
              <h2 className="text-3xl font-bold">
                {activationData.completedThisMonth}
              </h2>
              <p className="text-sm text-green-600 mt-2">
                +2.4% from last month
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Avg Activation Time
              </p>
              <h2 className="text-3xl font-bold">
                {activationData.avgActivationTime} days
              </h2>
              <p className="text-sm text-green-600 mt-2">
                -1.2 days from last month
              </p>
            </CardContent>
          </Card>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12 lg:col-span-4">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Pie data={statusChartData} options={statusChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-8">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Line data={trendChartData} options={trendChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={providerChartData} options={providerChartOptions} />
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
