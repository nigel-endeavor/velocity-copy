/**
 * WIP (Work in Progress) Tab
 *
 * Shows:
 * - Project status breakdown by service status (bar chart)
 * - Employee assignments: Provisioner, PM, Client PM (bar charts)
 * - Service & Location level jeopardies
 * - WIP Services data table
 */

import { useMemo, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import { Label } from '@/components/ui/label';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
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
import {
  useGetWipServicesQuery,
  useGetWipServiceJeopsQuery,
  useGetWipLocationJeopsQuery,
} from '@/services/api/wipViewsApi';
import type { WipServiceView } from '@/services/api/wipViewsApi';

const COLORS = ['#2563eb', '#16a34a', '#dc2626', '#f59e0b', '#8b5cf6', '#06b6d4', '#ec4899', '#84cc16'];

function groupBy<T>(arr: T[], keyFn: (item: T) => string): Record<string, T[]> {
  return arr.reduce((acc, item) => {
    const key = keyFn(item) || 'Unknown';
    (acc[key] = acc[key] || []).push(item);
    return acc;
  }, {} as Record<string, T[]>);
}

function toChartData(grouped: Record<string, unknown[]>) {
  return Object.entries(grouped)
    .map(([name, items]) => ({ name, count: items.length }))
    .sort((a, b) => b.count - a.count);
}

export default function WipTab() {
  const [allStatuses, setAllStatuses] = useState(false);
  const params = allStatuses ? { allStatuses: true } : undefined;

  const { data: wipServices = [], isLoading } = useGetWipServicesQuery(params);
  const { data: serviceJeops = [] } = useGetWipServiceJeopsQuery(params);
  const { data: locationJeops = [] } = useGetWipLocationJeopsQuery(params);

  const statusData = useMemo(() => toChartData(groupBy(wipServices, (s) => s.serviceStatus || 'Unknown')), [wipServices]);
  const provisionerData = useMemo(() => toChartData(groupBy(wipServices, (s) => s.provisioner || 'Unassigned')), [wipServices]);
  const pmData = useMemo(() => toChartData(groupBy(wipServices, (s) => s.vertekProjectManager || 'Unassigned')), [wipServices]);
  const serviceTypeData = useMemo(() => toChartData(groupBy(wipServices, (s) => s.serviceType || 'Unknown')), [wipServices]);

  const formatCurrency = (val: number | null) =>
    val != null ? `$${val.toLocaleString('en-US', { minimumFractionDigits: 2 })}` : '-';

  if (isLoading) {
    return <div className="flex justify-center p-12 text-muted-foreground">Loading WIP data...</div>;
  }

  return (
    <div className="space-y-6 mt-4">
      {/* Controls */}
      <div className="flex items-center gap-2">
        <Checkbox
          id="allStatuses"
          checked={allStatuses}
          onCheckedChange={(checked) => setAllStatuses(checked === true)}
        />
        <Label htmlFor="allStatuses">Show All Statuses</Label>
        <span className="ml-4 text-sm text-muted-foreground">
          Total WIP Services: <strong>{wipServices.length}</strong>
        </span>
      </div>

      {/* Project Status */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Project Status Breakdown</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={statusData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Bar dataKey="count" fill="#2563eb" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-base">Services by Type</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie data={serviceTypeData} dataKey="count" nameKey="name" cx="50%" cy="50%" outerRadius={100} label>
                  {serviceTypeData.map((_, i) => (
                    <Cell key={i} fill={COLORS[i % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>

      {/* Employee Assignments */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="text-base">Provisioner Assignments</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={250}>
              <BarChart data={provisionerData} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" allowDecimals={false} />
                <YAxis type="category" dataKey="name" width={140} tick={{ fontSize: 12 }} />
                <Tooltip />
                <Bar dataKey="count" fill="#16a34a" radius={[0, 4, 4, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-base">Project Manager Assignments</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={250}>
              <BarChart data={pmData} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" allowDecimals={false} />
                <YAxis type="category" dataKey="name" width={140} tick={{ fontSize: 12 }} />
                <Tooltip />
                <Bar dataKey="count" fill="#f59e0b" radius={[0, 4, 4, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>

      {/* Jeopardies */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="text-base">
              Service Level Jeopardies
              <span className="ml-2 text-sm font-normal text-muted-foreground">({serviceJeops.length})</span>
            </CardTitle>
          </CardHeader>
          <CardContent>
            {serviceJeops.length === 0 ? (
              <p className="text-muted-foreground text-sm">No service jeopardies found.</p>
            ) : (
              <JeopardyMiniTable data={serviceJeops} />
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-base">
              Location Level Jeopardies
              <span className="ml-2 text-sm font-normal text-muted-foreground">({locationJeops.length})</span>
            </CardTitle>
          </CardHeader>
          <CardContent>
            {locationJeops.length === 0 ? (
              <p className="text-muted-foreground text-sm">No location jeopardies found.</p>
            ) : (
              <JeopardyMiniTable data={locationJeops} />
            )}
          </CardContent>
        </Card>
      </div>

      {/* WIP Services Table */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">WIP Services Detail</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="overflow-x-auto max-h-[400px] overflow-y-auto">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Company</TableHead>
                  <TableHead>Location</TableHead>
                  <TableHead>Provider</TableHead>
                  <TableHead>Type</TableHead>
                  <TableHead>Status</TableHead>
                  <TableHead className="text-right">MRC</TableHead>
                  <TableHead>Provisioner</TableHead>
                  <TableHead>PM</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {wipServices.map((s) => (
                  <TableRow key={s.serviceId}>
                    <TableCell className="text-sm">{s.companyName}</TableCell>
                    <TableCell className="text-sm">{s.locationName}</TableCell>
                    <TableCell className="text-sm">{s.provider}</TableCell>
                    <TableCell className="text-sm">{s.serviceType}</TableCell>
                    <TableCell className="text-sm">{s.serviceStatus}</TableCell>
                    <TableCell className="text-sm text-right">{formatCurrency(s.serviceMrc)}</TableCell>
                    <TableCell className="text-sm">{s.provisioner}</TableCell>
                    <TableCell className="text-sm">{s.vertekProjectManager}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}

function JeopardyMiniTable({ data }: { data: WipServiceView[] }) {
  return (
    <div className="overflow-x-auto max-h-[200px] overflow-y-auto">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Company</TableHead>
            <TableHead>Location</TableHead>
            <TableHead>Provider</TableHead>
            <TableHead>Status</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((s) => (
            <TableRow key={s.serviceId}>
              <TableCell className="text-sm">{s.companyName}</TableCell>
              <TableCell className="text-sm">{s.locationName}</TableCell>
              <TableCell className="text-sm">{s.provider}</TableCell>
              <TableCell className="text-sm">{s.serviceStatus}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}
