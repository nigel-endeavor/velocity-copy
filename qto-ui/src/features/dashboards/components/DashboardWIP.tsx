/**
 * Dashboard WIP Component
 *
 * Work in Progress tracking and metrics
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
 * WIP item data
 */
interface WIPItem {
  orderType: string;
  count: number;
  avgDaysInProgress: number;
  atRisk: number;
  onTrack: number;
}

/**
 * Dashboard WIP Component
 */
export default function DashboardWIP() {
  // Mock data - replace with actual API calls
  const wipData = useMemo(
    () => ({
      totalWIP: 456,
      atRisk: 87,
      onTrack: 289,
      delayed: 80,
      avgDaysInProgress: 18.5,
      byOrderType: [
        {
          orderType: 'New Install',
          count: 158,
          avgDaysInProgress: 22.3,
          atRisk: 32,
          onTrack: 98,
        },
        {
          orderType: 'Upgrade',
          count: 95,
          avgDaysInProgress: 15.8,
          atRisk: 18,
          onTrack: 65,
        },
        {
          orderType: 'Move/Transfer',
          count: 78,
          avgDaysInProgress: 12.5,
          atRisk: 12,
          onTrack: 58,
        },
        {
          orderType: 'Change',
          count: 67,
          avgDaysInProgress: 16.2,
          atRisk: 15,
          onTrack: 42,
        },
        {
          orderType: 'Disconnect',
          count: 58,
          avgDaysInProgress: 19.7,
          atRisk: 10,
          onTrack: 26,
        },
      ] as WIPItem[],
      weeklyTrend: {
        labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5', 'Week 6'],
        totalWIP: [445, 452, 458, 461, 459, 456],
        atRisk: [72, 78, 82, 85, 88, 87],
        completed: [85, 92, 88, 95, 90, 93],
      },
      byStage: {
        labels: ['Order Entry', 'Provisioning', 'Installation', 'Testing', 'Completion'],
        data: [85, 145, 125, 68, 33],
      },
      ageDistribution: {
        labels: ['0-7 days', '8-14 days', '15-30 days', '31-60 days', '> 60 days'],
        data: [125, 158, 102, 48, 23],
      },
    }),
    []
  );

  // Get risk color
  const getRiskColor = (atRisk: number, total: number): 'success' | 'warning' | 'destructive' => {
    const percentage = (atRisk / total) * 100;
    if (percentage >= 30) return 'destructive';
    if (percentage >= 15) return 'warning';
    return 'success';
  };

  // Get risk label
  const getRiskLabel = (atRisk: number, total: number) => {
    const percentage = (atRisk / total) * 100;
    if (percentage >= 30) return 'High Risk';
    if (percentage >= 15) return 'Medium Risk';
    return 'Low Risk';
  };

  // Weekly Trend Chart
  const trendChartData = {
    labels: wipData.weeklyTrend.labels,
    datasets: [
      {
        label: 'Total WIP',
        data: wipData.weeklyTrend.totalWIP,
        borderColor: 'rgb(25, 118, 210)',
        backgroundColor: 'rgba(25, 118, 210, 0.1)',
        tension: 0.4,
      },
      {
        label: 'At Risk',
        data: wipData.weeklyTrend.atRisk,
        borderColor: 'rgb(244, 67, 54)',
        backgroundColor: 'rgba(244, 67, 54, 0.1)',
        tension: 0.4,
      },
      {
        label: 'Completed',
        data: wipData.weeklyTrend.completed,
        borderColor: 'rgb(76, 175, 80)',
        backgroundColor: 'rgba(76, 175, 80, 0.1)',
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
        text: 'Weekly WIP Trend',
        font: { size: 16 },
      },
      datalabels: {
        display: false,
      },
    },
  };

  // By Stage Chart
  const stageChartData = {
    labels: wipData.byStage.labels,
    datasets: [
      {
        label: 'Orders',
        data: wipData.byStage.data,
        backgroundColor: [
          'rgba(25, 118, 210, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(156, 39, 176, 0.8)',
          'rgba(76, 175, 80, 0.8)',
          'rgba(220, 0, 78, 0.8)',
        ],
      },
    ],
  };

  const stageChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'WIP by Stage',
        font: { size: 16 },
      },
      datalabels: {
        color: '#fff',
        font: { weight: 'bold' as const, size: 12 },
      },
    },
  };

  // Age Distribution Chart
  const ageChartData = {
    labels: wipData.ageDistribution.labels,
    datasets: [
      {
        label: 'Orders',
        data: wipData.ageDistribution.data,
        backgroundColor: [
          'rgba(76, 175, 80, 0.8)',
          'rgba(25, 118, 210, 0.8)',
          'rgba(255, 152, 0, 0.8)',
          'rgba(255, 193, 7, 0.8)',
          'rgba(244, 67, 54, 0.8)',
        ],
      },
    ],
  };

  const ageChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: true,
        text: 'WIP Age Distribution',
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
                Total WIP
              </p>
              <h2 className="text-3xl font-bold">
                {wipData.totalWIP}
              </h2>
              <p className="text-sm text-muted-foreground mt-2">
                -0.7% from last week
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                At Risk
              </p>
              <h2 className="text-3xl font-bold">
                {wipData.atRisk}
              </h2>
              <p className="text-sm text-red-600 mt-2">
                {((wipData.atRisk / wipData.totalWIP) * 100).toFixed(1)}% of total
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                On Track
              </p>
              <h2 className="text-3xl font-bold">
                {wipData.onTrack}
              </h2>
              <p className="text-sm text-green-600 mt-2">
                {((wipData.onTrack / wipData.totalWIP) * 100).toFixed(1)}% of total
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 md:col-span-3">
          <Card>
            <CardContent className="p-6">
              <p className="text-sm text-muted-foreground mb-2">
                Avg Days in Progress
              </p>
              <h2 className="text-3xl font-bold">
                {wipData.avgDaysInProgress}
              </h2>
              <p className="text-sm text-orange-500 mt-2">
                +0.5 days from last week
              </p>
            </CardContent>
          </Card>
        </div>
      </div>

      {/* WIP by Order Type Table */}
      <div className="mb-6">
        <Card>
          <CardContent className="p-6">
            <h3 className="text-lg font-semibold mb-4">
              WIP by Order Type
            </h3>
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Order Type</TableHead>
                    <TableHead className="text-right">Count</TableHead>
                    <TableHead className="text-right">Avg Days</TableHead>
                    <TableHead>Risk Status</TableHead>
                    <TableHead className="text-right">At Risk</TableHead>
                    <TableHead className="text-right">On Track</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {wipData.byOrderType.map((item) => {
                    const riskPercentage = (item.atRisk / item.count) * 100;
                    return (
                      <TableRow key={item.orderType}>
                        <TableCell>
                          <span className="font-medium text-sm">
                            {item.orderType}
                          </span>
                        </TableCell>
                        <TableCell className="text-right">{item.count}</TableCell>
                        <TableCell className="text-right">
                          {item.avgDaysInProgress.toFixed(1)}
                        </TableCell>
                        <TableCell>
                          <div>
                            <div className="flex items-center gap-2 mb-1">
                              <span className="text-sm">
                                {riskPercentage.toFixed(1)}% at risk
                              </span>
                              <Badge variant={getRiskColor(item.atRisk, item.count)}>
                                {getRiskLabel(item.atRisk, item.count)}
                              </Badge>
                            </div>
                            <div className="w-full bg-gray-200 rounded-full h-2">
                              <div
                                className={cn(
                                  "h-2 rounded-full transition-all",
                                  riskPercentage >= 30 ? "bg-red-600" :
                                  riskPercentage >= 15 ? "bg-orange-500" : "bg-green-600"
                                )}
                                style={{ width: `${Math.min(riskPercentage, 100)}%` }}
                              />
                            </div>
                          </div>
                        </TableCell>
                        <TableCell className="text-right">
                          <Badge variant="destructive">
                            {item.atRisk}
                          </Badge>
                        </TableCell>
                        <TableCell className="text-right">
                          <Badge variant="success">
                            {item.onTrack}
                          </Badge>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Line data={trendChartData} options={trendChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-6">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={stageChartData} options={stageChartOptions} />
            </CardContent>
          </Card>
        </div>

        <div className="col-span-12 lg:col-span-6">
          <Card>
            <CardContent className="p-4 h-[400px]">
              <Bar data={ageChartData} options={ageChartOptions} />
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
