/**
 * Dashboard Financials Component
 *
 * Financial metrics and trends visualization
 */

import { useMemo } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  LineElement,
  PointElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';
import { Bar, Line, Doughnut } from 'react-chartjs-2';
import ChartDataLabels from 'chartjs-plugin-datalabels';
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
  Legend,
  ChartDataLabels
);

/**
 * Format currency
 */
function formatCurrency(value: number): string {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(value);
}

/**
 * Dashboard Financials Component
 */
export default function DashboardFinancials() {
  // Mock data - replace with actual API calls
  const financialData = useMemo(
    () => ({
      totalMRC: 1250000,
      totalNRC: 450000,
      totalRevenue: 1700000,
      monthlyTrend: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        mrc: [1000000, 1050000, 1100000, 1150000, 1200000, 1250000],
        nrc: [400000, 420000, 435000, 440000, 445000, 450000],
      },
      byProvider: {
        labels: ['AT&T', 'Verizon', 'Lumen', 'Comcast', 'Spectrum'],
        mrc: [400000, 350000, 250000, 150000, 100000],
        nrc: [150000, 125000, 100000, 50000, 25000],
      },
      byServiceType: {
        labels: ['Internet', 'MPLS', 'Voice', 'Ethernet', 'Other'],
        values: [450000, 350000, 250000, 150000, 50000],
      },
    }),
    []
  );

  // MRC/NRC Trend Chart
  const trendChartData = {
    labels: financialData.monthlyTrend.labels,
    datasets: [
      {
        label: 'MRC',
        data: financialData.monthlyTrend.mrc,
        borderColor: 'rgb(25, 118, 210)',
        backgroundColor: 'rgba(25, 118, 210, 0.1)',
        tension: 0.4,
      },
      {
        label: 'NRC',
        data: financialData.monthlyTrend.nrc,
        borderColor: 'rgb(220, 0, 78)',
        backgroundColor: 'rgba(220, 0, 78, 0.1)',
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
        text: 'Monthly Financial Trends',
        font: { size: 16 },
      },
      datalabels: {
        display: false,
      },
    },
    scales: {
      y: {
        ticks: {
          callback: (value: any) => formatCurrency(value),
        },
      },
    },
  };

  // By Provider Chart
  const providerChartData = {
    labels: financialData.byProvider.labels,
    datasets: [
      {
        label: 'MRC',
        data: financialData.byProvider.mrc,
        backgroundColor: 'rgba(25, 118, 210, 0.8)',
      },
      {
        label: 'NRC',
        data: financialData.byProvider.nrc,
        backgroundColor: 'rgba(220, 0, 78, 0.8)',
      },
    ],
  };

  const providerChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Revenue by Provider',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 10 },
        formatter: (value: number) => formatCurrency(value),
      },
    },
    scales: {
      y: {
        ticks: {
          callback: (value: any) => formatCurrency(value),
        },
      },
    },
  };

  // By Service Type Chart
  const serviceTypeChartData = {
    labels: financialData.byServiceType.labels,
    datasets: [
      {
        data: financialData.byServiceType.values,
        backgroundColor: [
          'rgba(25, 118, 210, 0.8)',
          'rgba(220, 0, 78, 0.8)',
          'rgba(76, 175, 80, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(156, 39, 176, 0.8)',
        ],
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
        text: 'MRC by Service Type',
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

  return (
    <div>
      {/* Summary Cards */}
      <div className="grid grid-cols-12 gap-6 mb-6">
        <div className="col-span-12 md:col-span-4">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Total MRC
              </p>
              <h2 className="text-3xl font-bold">
                {formatCurrency(financialData.totalMRC)}
              </h2>
              <p className="text-sm text-success mt-2">
                +4.2% from last month
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-4">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Total NRC
              </p>
              <h2 className="text-3xl font-bold">
                {formatCurrency(financialData.totalNRC)}
              </h2>
              <p className="text-sm text-success mt-2">
                +1.1% from last month
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-4">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Total Revenue
              </p>
              <h2 className="text-3xl font-bold">
                {formatCurrency(financialData.totalRevenue)}
              </h2>
              <p className="text-sm text-success mt-2">
                +3.5% from last month
              </p>
            </CardContent>
          </Card>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12 lg:col-span-8">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Line data={trendChartData} options={trendChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-4">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Doughnut data={serviceTypeChartData} options={serviceTypeChartOptions} />
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
