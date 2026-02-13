/**
 * Dashboard Inventory Component
 *
 * Service inventory metrics and tracking
 */

import { useMemo } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar, Doughnut } from 'react-chartjs-2';
import { Card, CardContent } from '@/components/ui/card';

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

/**
 * Dashboard Inventory Component
 */
export default function DashboardInventory() {
  // Mock data - replace with actual API calls
  const inventoryData = useMemo(
    () => ({
      totalServices: 1547,
      activeServices: 1289,
      inactiveServices: 158,
      pendingServices: 100,
      byServiceType: {
        labels: ['Internet', 'MPLS', 'Voice', 'Ethernet', 'Cloud', 'Other'],
        data: [425, 368, 295, 234, 145, 80],
        colors: [
          'rgba(25, 118, 210, 0.8)',
          'rgba(220, 0, 78, 0.8)',
          'rgba(76, 175, 80, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(156, 39, 176, 0.8)',
          'rgba(96, 125, 139, 0.8)',
        ],
      },
      byProvider: {
        labels: ['AT&T', 'Verizon', 'Lumen', 'Comcast', 'Spectrum', 'Other'],
        data: [412, 358, 285, 225, 167, 100],
      },
      byStatus: {
        labels: ['Active', 'Pending', 'Suspended', 'Inactive', 'Cancelled'],
        data: [1289, 100, 68, 58, 32],
        colors: [
          'rgba(76, 175, 80, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(255, 193, 7, 0.8)',
          'rgba(158, 158, 158, 0.8)',
          'rgba(244, 67, 54, 0.8)',
        ],
      },
      byBandwidth: {
        labels: ['< 10 Mbps', '10-100 Mbps', '100-500 Mbps', '500 Mbps - 1 Gbps', '> 1 Gbps'],
        data: [215, 485, 425, 285, 137],
      },
    }),
    []
  );

  // Service Type Distribution Chart
  const serviceTypeChartData = {
    labels: inventoryData.byServiceType.labels,
    datasets: [
      {
        data: inventoryData.byServiceType.data,
        backgroundColor: inventoryData.byServiceType.colors,
      },
    ],
  };

  const serviceTypeChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'right' as const,
      },
      title: {
        display: true,
        text: 'Services by Type',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 12 },
        formatter: (value: number, context: any) => {
          const total = context.dataset.data.reduce((a: number, b: number) => a + b, 0);
          const percentage = ((value / total) * 100).toFixed(1);
          return `${percentage}%`;
        },
      },
    },
  };

  // By Provider Chart
  const providerChartData = {
    labels: inventoryData.byProvider.labels,
    datasets: [
      {
        label: 'Services',
        data: inventoryData.byProvider.data,
        backgroundColor: 'rgba(25, 118, 210, 0.8)',
      },
    ],
  };

  const providerChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'Services by Provider',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 12 },
      },
    },
  };

  // By Status Chart
  const statusChartData = {
    labels: inventoryData.byStatus.labels,
    datasets: [
      {
        data: inventoryData.byStatus.data,
        backgroundColor: inventoryData.byStatus.colors,
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
        text: 'Services by Status',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 12 },
        formatter: (value: number) => value,
      },
    },
  };

  // By Bandwidth Chart
  const bandwidthChartData = {
    labels: inventoryData.byBandwidth.labels,
    datasets: [
      {
        label: 'Services',
        data: inventoryData.byBandwidth.data,
        backgroundColor: 'rgba(76, 175, 80, 0.8)',
      },
    ],
  };

  const bandwidthChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'Services by Bandwidth',
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
      {/* Summary Cards */}
      <div className="grid grid-cols-12 gap-6 mb-6">
        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Total Services
              </p>
              <h2 className="text-3xl font-bold">
                {inventoryData.totalServices}
              </h2>
              <p className="text-sm text-green-600 mt-2">
                +3.2% from last month
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Active Services
              </p>
              <h2 className="text-3xl font-bold">
                {inventoryData.activeServices}
              </h2>
              <p className="text-sm text-green-600 mt-2">
                83.3% of total
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Pending Services
              </p>
              <h2 className="text-3xl font-bold">
                {inventoryData.pendingServices}
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
                Inactive Services
              </p>
              <h2 className="text-3xl font-bold">
                {inventoryData.inactiveServices}
              </h2>
              <p className="text-sm text-muted-foreground mt-2">
                10.2% of total
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
              <Doughnut data={serviceTypeChartData} options={serviceTypeChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-8">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={providerChartData} options={providerChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-4">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Doughnut data={statusChartData} options={statusChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-8">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={bandwidthChartData} options={bandwidthChartOptions} />
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
