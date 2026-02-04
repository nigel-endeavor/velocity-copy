/**
 * Dashboard Providers Component
 *
 * Provider performance metrics and analytics
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
} from 'chart.js';
import { Bar, Line } from 'react-chartjs-2';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { cn } from '@/lib/utils';

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  LineElement,
  PointElement,
  Title,
  Tooltip,
  Legend
);

/**
 * Provider performance data
 */
interface ProviderPerformance {
  name: string;
  services: number;
  revenue: number;
  onTimeRate: number;
  satisfactionScore: number;
  activeIssues: number;
  trend: 'up' | 'down' | 'stable';
}

/**
 * Dashboard Providers Component
 */
export default function DashboardProviders() {
  // Mock data - replace with actual API calls
  const providerData = useMemo(
    () => ({
      totalProviders: 8,
      activeProviders: 7,
      topProvider: 'AT&T',
      avgOnTimeRate: 92.5,
      providerPerformance: [
        {
          name: 'AT&T',
          services: 412,
          revenue: 850000,
          onTimeRate: 94.5,
          satisfactionScore: 4.6,
          activeIssues: 12,
          trend: 'up' as const,
        },
        {
          name: 'Verizon',
          services: 358,
          revenue: 725000,
          onTimeRate: 93.2,
          satisfactionScore: 4.5,
          activeIssues: 8,
          trend: 'up' as const,
        },
        {
          name: 'Lumen',
          services: 285,
          revenue: 580000,
          onTimeRate: 91.8,
          satisfactionScore: 4.3,
          activeIssues: 15,
          trend: 'stable' as const,
        },
        {
          name: 'Comcast',
          services: 225,
          revenue: 465000,
          onTimeRate: 89.5,
          satisfactionScore: 4.1,
          activeIssues: 22,
          trend: 'down' as const,
        },
        {
          name: 'Spectrum',
          services: 167,
          revenue: 340000,
          onTimeRate: 90.2,
          satisfactionScore: 4.2,
          activeIssues: 18,
          trend: 'stable' as const,
        },
      ] as ProviderPerformance[],
      monthlyRevenue: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [
          {
            label: 'AT&T',
            data: [810000, 820000, 830000, 840000, 845000, 850000],
          },
          {
            label: 'Verizon',
            data: [690000, 700000, 710000, 715000, 720000, 725000],
          },
          {
            label: 'Lumen',
            data: [555000, 560000, 565000, 570000, 575000, 580000],
          },
        ],
      },
      onTimePerformance: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [
          {
            label: 'AT&T',
            data: [93.2, 93.8, 94.1, 94.3, 94.4, 94.5],
          },
          {
            label: 'Verizon',
            data: [92.5, 92.8, 93.0, 93.1, 93.1, 93.2],
          },
          {
            label: 'Lumen',
            data: [90.8, 91.0, 91.2, 91.5, 91.6, 91.8],
          },
        ],
      },
      issuesByProvider: {
        labels: ['AT&T', 'Verizon', 'Lumen', 'Comcast', 'Spectrum'],
        data: [12, 8, 15, 22, 18],
      },
    }),
    []
  );

  // Format currency
  const formatCurrency = (value: number): string => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(value);
  };

  // Get trend icon
  const getTrendIcon = (trend: 'up' | 'down' | 'stable') => {
    switch (trend) {
      case 'up':
        return '📈';
      case 'down':
        return '📉';
      default:
        return '➡️';
    }
  };

  // Get on-time rate color
  const getOnTimeRateColor = (rate: number): 'success' | 'warning' | 'destructive' => {
    if (rate >= 95) return 'success';
    if (rate >= 90) return 'warning';
    return 'destructive';
  };

  // Get on-time rate label
  const getOnTimeRateLabel = (rate: number) => {
    if (rate >= 95) return 'Excellent';
    if (rate >= 90) return 'Good';
    return 'Needs Improvement';
  };

  // Get issue count variant
  const getIssueCountVariant = (count: number): 'success' | 'warning' | 'destructive' => {
    if (count > 20) return 'destructive';
    if (count > 10) return 'warning';
    return 'success';
  };

  // Monthly Revenue Chart
  const revenueChartData = {
    labels: providerData.monthlyRevenue.labels,
    datasets: providerData.monthlyRevenue.datasets.map((dataset, index) => ({
      ...dataset,
      borderColor: [
        'rgb(25, 118, 210)',
        'rgb(220, 0, 78)',
        'rgb(76, 175, 80)',
      ][index],
      backgroundColor: [
        'rgba(25, 118, 210, 0.1)',
        'rgba(220, 0, 78, 0.1)',
        'rgba(76, 175, 80, 0.1)',
      ][index],
      tension: 0.4,
    })),
  };

  const revenueChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Monthly Revenue by Provider',
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

  // On-Time Performance Chart
  const onTimeChartData = {
    labels: providerData.onTimePerformance.labels,
    datasets: providerData.onTimePerformance.datasets.map((dataset, index) => ({
      ...dataset,
      borderColor: [
        'rgb(25, 118, 210)',
        'rgb(220, 0, 78)',
        'rgb(76, 175, 80)',
      ][index],
      backgroundColor: [
        'rgba(25, 118, 210, 0.1)',
        'rgba(220, 0, 78, 0.1)',
        'rgba(76, 175, 80, 0.1)',
      ][index],
      tension: 0.4,
    })),
  };

  const onTimeChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'On-Time Delivery Performance',
        font: { size: 16 },
      },
      datalabels: {
        display: false,
      },
    },
    scales: {
      y: {
        min: 85,
        max: 100,
        ticks: {
          callback: (value: any) => `${value}%`,
        },
      },
    },
  };

  // Issues Chart
  const issuesChartData = {
    labels: providerData.issuesByProvider.labels,
    datasets: [
      {
        label: 'Active Issues',
        data: providerData.issuesByProvider.data,
        backgroundColor: 'rgba(244, 67, 54, 0.8)',
      },
    ],
  };

  const issuesChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'Active Issues by Provider',
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
                Total Providers
              </p>
              <h2 className="text-3xl font-bold">
                {providerData.totalProviders}
              </h2>
              <p className="text-sm text-muted-foreground mt-2">
                {providerData.activeProviders} active
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Top Provider
              </p>
              <h2 className="text-3xl font-bold">
                {providerData.topProvider}
              </h2>
              <p className="text-sm text-green-600 mt-2">
                Leading in services & revenue
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Avg On-Time Rate
              </p>
              <h2 className="text-3xl font-bold">
                {providerData.avgOnTimeRate}%
              </h2>
              <p className="text-sm text-green-600 mt-2">
                +1.2% from last month
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Total Active Issues
              </p>
              <h2 className="text-3xl font-bold">
                {providerData.providerPerformance.reduce(
                  (sum, p) => sum + p.activeIssues,
                  0
                )}
              </h2>
              <p className="text-sm text-orange-500 mt-2">
                Requires attention
              </p>
            </CardContent>
          </Card>
        </div>
      </div>

      {/* Performance Table */}
      <div className="mb-6">
        <Card>
          <CardContent className="p-6">
            <h3 className="text-lg font-semibold mb-4">
              Provider Performance Summary
            </h3>
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Provider</TableHead>
                    <TableHead className="text-right">Services</TableHead>
                    <TableHead className="text-right">Revenue</TableHead>
                    <TableHead>On-Time Rate</TableHead>
                    <TableHead className="text-center">Satisfaction</TableHead>
                    <TableHead className="text-center">Issues</TableHead>
                    <TableHead className="text-center">Trend</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {providerData.providerPerformance.map((provider) => (
                    <TableRow key={provider.name}>
                      <TableCell>
                        <span className="font-medium text-sm">
                          {provider.name}
                        </span>
                      </TableCell>
                      <TableCell className="text-right">{provider.services}</TableCell>
                      <TableCell className="text-right">
                        {formatCurrency(provider.revenue)}
                      </TableCell>
                      <TableCell>
                        <div>
                          <div className="flex items-center gap-2 mb-1">
                            <span className="text-sm">
                              {provider.onTimeRate}%
                            </span>
                            <Badge variant={getOnTimeRateColor(provider.onTimeRate)}>
                              {getOnTimeRateLabel(provider.onTimeRate)}
                            </Badge>
                          </div>
                          <div className="w-full bg-gray-200 rounded-full h-2">
                            <div
                              className={cn(
                                "h-2 rounded-full transition-all",
                                provider.onTimeRate >= 95 ? "bg-green-600" :
                                provider.onTimeRate >= 90 ? "bg-orange-500" : "bg-red-600"
                              )}
                              style={{ width: `${provider.onTimeRate}%` }}
                            />
                          </div>
                        </div>
                      </TableCell>
                      <TableCell className="text-center">
                        {provider.satisfactionScore}/5.0
                      </TableCell>
                      <TableCell className="text-center">
                        <Badge variant={getIssueCountVariant(provider.activeIssues)}>
                          {provider.activeIssues}
                        </Badge>
                      </TableCell>
                      <TableCell className="text-center">
                        {getTrendIcon(provider.trend)}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12 lg:col-span-6">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Line data={revenueChartData} options={revenueChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-6">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Line data={onTimeChartData} options={onTimeChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={issuesChartData} options={issuesChartOptions} />
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
