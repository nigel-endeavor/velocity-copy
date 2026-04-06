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
import { DashboardDataSurface, DashboardPanel, DashboardTableSurface } from '../components/DashboardPrimitives';

const COLORS = ['#118ad3', '#11b5d8', '#ef5b93', '#8e61c9', '#ffb84d', '#2baa7b', '#183f5c', '#8bc34a'];

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

function statusTone(status: string | null | undefined) {
  const normalized = (status || '').toLowerCase();

  if (normalized.includes('complete')) return 'bg-emerald-100 text-emerald-700';
  if (normalized.includes('progress')) return 'bg-sky-100 text-sky-700';
  if (normalized.includes('jeopard')) return 'bg-rose-100 text-rose-700';
  if (normalized.includes('pending')) return 'bg-amber-100 text-amber-700';

  return 'bg-slate-100 text-slate-600';
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
    <div className="mt-4 space-y-6">
      <div className="flex flex-wrap items-center gap-3 rounded-[24px] border border-white/60 bg-white/72 px-5 py-4 shadow-[0_18px_38px_-28px_rgba(13,31,48,0.55)] backdrop-blur">
        <Checkbox
          id="allStatuses"
          checked={allStatuses}
          onCheckedChange={(checked) => setAllStatuses(checked === true)}
        />
        <Label htmlFor="allStatuses" className="font-medium text-slate-700">Show all statuses</Label>
        <span className="dashboard-pill">{wipServices.length} services loaded</span>
        <span className="text-sm text-slate-500">
          Service jeopardies: <strong className="text-slate-900">{serviceJeops.length}</strong>
        </span>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <DashboardPanel title="Project status breakdown" description="Quick scan of current service status mix across the active portfolio.">
          <DashboardDataSurface>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={statusData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" tick={{ fontSize: 12 }} />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Bar dataKey="count" fill="#118ad3" radius={[10, 10, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </DashboardDataSurface>
        </DashboardPanel>

        <DashboardPanel title="Services by type" description="A portfolio composition view using the new branded palette.">
          <DashboardDataSurface>
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
          </DashboardDataSurface>
        </DashboardPanel>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <DashboardPanel title="Provisioner assignments" description="Headcount distribution for active delivery owners.">
          <DashboardDataSurface>
            <ResponsiveContainer width="100%" height={250}>
              <BarChart data={provisionerData} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" allowDecimals={false} />
                <YAxis type="category" dataKey="name" width={140} tick={{ fontSize: 12 }} />
                <Tooltip />
                <Bar dataKey="count" fill="#2baa7b" radius={[0, 10, 10, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </DashboardDataSurface>
        </DashboardPanel>

        <DashboardPanel title="Project manager assignments" description="Program management load across the current WIP queue.">
          <DashboardDataSurface>
            <ResponsiveContainer width="100%" height={250}>
              <BarChart data={pmData} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" allowDecimals={false} />
                <YAxis type="category" dataKey="name" width={140} tick={{ fontSize: 12 }} />
                <Tooltip />
                <Bar dataKey="count" fill="#ffb84d" radius={[0, 10, 10, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </DashboardDataSurface>
        </DashboardPanel>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <DashboardPanel
          title={
            <span>
              Service Level Jeopardies
              <span className="ml-2 text-sm font-normal text-slate-500">({serviceJeops.length})</span>
            </span>
          }
          description="Items requiring immediate attention at the service level."
        >
            {serviceJeops.length === 0 ? (
              <p className="text-sm text-slate-500">No service jeopardies found.</p>
            ) : (
              <JeopardyMiniTable data={serviceJeops} />
            )}
        </DashboardPanel>

        <DashboardPanel
          title={
            <span>
              Location Level Jeopardies
              <span className="ml-2 text-sm font-normal text-slate-500">({locationJeops.length})</span>
            </span>
          }
          description="Location-level blockers surfaced for delivery teams."
        >
            {locationJeops.length === 0 ? (
              <p className="text-sm text-slate-500">No location jeopardies found.</p>
            ) : (
              <JeopardyMiniTable data={locationJeops} />
            )}
        </DashboardPanel>
      </div>

      <DashboardPanel title="WIP services detail" description="Detailed queue view for company, location, provider, and staffing assignments.">
        <DashboardTableSurface>
          <div className="max-h-[420px] overflow-x-auto overflow-y-auto">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Company</TableHead>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Location</TableHead>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Provider</TableHead>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Type</TableHead>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Status</TableHead>
                  <TableHead className="text-right text-[11px] uppercase tracking-[0.18em] text-slate-500">MRC</TableHead>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Provisioner</TableHead>
                  <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">PM</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {wipServices.map((s) => (
                  <TableRow key={s.serviceId}>
                    <TableCell className="text-sm text-slate-700">{s.companyName}</TableCell>
                    <TableCell className="text-sm text-slate-700">{s.locationName}</TableCell>
                    <TableCell className="text-sm text-slate-700">{s.provider}</TableCell>
                    <TableCell className="text-sm text-slate-700">{s.serviceType}</TableCell>
                    <TableCell className="text-sm text-slate-700">
                      <span className={`inline-flex rounded-full px-2.5 py-1 text-xs font-semibold ${statusTone(s.serviceStatus)}`}>
                        {s.serviceStatus}
                      </span>
                    </TableCell>
                    <TableCell className="text-right text-sm font-medium text-slate-900">{formatCurrency(s.serviceMrc)}</TableCell>
                    <TableCell className="text-sm text-slate-700">{s.provisioner}</TableCell>
                    <TableCell className="text-sm text-slate-700">{s.vertekProjectManager}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </DashboardTableSurface>
      </DashboardPanel>
    </div>
  );
}

function JeopardyMiniTable({ data }: { data: WipServiceView[] }) {
  return (
    <DashboardTableSurface>
      <div className="max-h-[220px] overflow-x-auto overflow-y-auto">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Company</TableHead>
              <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Location</TableHead>
              <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Provider</TableHead>
              <TableHead className="text-[11px] uppercase tracking-[0.18em] text-slate-500">Status</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {data.map((s) => (
              <TableRow key={s.serviceId}>
                <TableCell className="text-sm text-slate-700">{s.companyName}</TableCell>
                <TableCell className="text-sm text-slate-700">{s.locationName}</TableCell>
                <TableCell className="text-sm text-slate-700">{s.provider}</TableCell>
                <TableCell className="text-sm text-slate-700">
                  <span className={`inline-flex rounded-full px-2.5 py-1 text-xs font-semibold ${statusTone(s.serviceStatus)}`}>
                    {s.serviceStatus}
                  </span>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </DashboardTableSurface>
  );
}
