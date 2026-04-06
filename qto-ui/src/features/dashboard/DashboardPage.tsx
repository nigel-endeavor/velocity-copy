/**
 * Dashboard Page
 *
 * Main dashboard with 6 tabs matching the original Angular application:
 * WIP, KPIs, Providers, Financials, Activations, Inventory
 */

import { useMemo } from 'react';
import { Link } from 'react-router-dom';
import {
  Activity,
  ArrowRight,
  Banknote,
  BarChart3,
  Boxes,
  Building2,
  CalendarDays,
  CircleAlert,
  Gauge,
  ShieldCheck,
  Sparkles,
  UserRound,
} from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/ui/tabs';
import { useGetWipLocationJeopsQuery, useGetWipServiceJeopsQuery, useGetWipServicesQuery } from '@/services/api/wipViewsApi';
import { DashboardMetricCard, DashboardPanel } from './components/DashboardPrimitives';
import WipTab from './tabs/WipTab';
import KpiTab from './tabs/KpiTab';
import ProvidersTab from './tabs/ProvidersTab';
import FinancialsTab from './tabs/FinancialsTab';
import ActivationsTab from './tabs/ActivationsTab';
import InventoryTab from './tabs/InventoryTab';

const dashboardTabs = [
  {
    value: 'wip',
    label: 'WIP',
    description: 'Monitor open work, service mix, and jeopardy flags.',
    icon: Activity,
    iconClassName: 'bg-gradient-to-br from-sky-500 to-cyan-400',
  },
  {
    value: 'kpi',
    label: 'KPIs',
    description: 'Track delivery intervals and operational throughput.',
    icon: Gauge,
    iconClassName: 'bg-gradient-to-br from-cyan-500 to-sky-500',
  },
  {
    value: 'providers',
    label: 'Providers',
    description: 'Compare partner performance, reliance, and counts.',
    icon: Building2,
    iconClassName: 'bg-gradient-to-br from-rose-500 to-pink-500',
  },
  {
    value: 'financials',
    label: 'Financials',
    description: 'Review spend, MRC distribution, and open accruals.',
    icon: Banknote,
    iconClassName: 'bg-gradient-to-br from-violet-500 to-fuchsia-500',
  },
  {
    value: 'activations',
    label: 'Activations',
    description: 'Measure launch health and activation duration trends.',
    icon: Sparkles,
    iconClassName: 'bg-gradient-to-br from-amber-400 to-orange-400',
  },
  {
    value: 'inventory',
    label: 'Inventory',
    description: 'Keep watch on current inventory value and growth.',
    icon: Boxes,
    iconClassName: 'bg-gradient-to-br from-emerald-500 to-teal-500',
  },
];

export default function DashboardPage() {
  const { data: services = [] } = useGetWipServicesQuery({ allStatuses: true });
  const { data: serviceJeops = [] } = useGetWipServiceJeopsQuery({ allStatuses: true });
  const { data: locationJeops = [] } = useGetWipLocationJeopsQuery({ allStatuses: true });

  const metrics = useMemo(() => {
    const inFlight = services.filter((service) => !service.completeDate).length;
    const completed = services.filter((service) => Boolean(service.completeDate)).length;
    const inventory = services.filter((service) => service.currentInventory).length;
    const completionRate = services.length > 0 ? Math.round((completed / services.length) * 100) : 0;

    return {
      totalServices: services.length,
      inFlight,
      inventory,
      completionRate,
      jeopardies: serviceJeops.length + locationJeops.length,
    };
  }, [locationJeops.length, serviceJeops.length, services]);

  const monthLabel = new Intl.DateTimeFormat('en-US', { month: 'long', year: 'numeric' }).format(new Date());

  return (
    <div className="space-y-5 pb-2">
      <section className="dashboard-card relative overflow-hidden px-6 py-7 sm:px-8">
        <div className="absolute inset-y-0 right-0 hidden w-1/3 bg-[radial-gradient(circle_at_center,rgba(56,189,248,0.10),transparent_62%)] lg:block" />
        <div className="relative grid gap-8 xl:grid-cols-[minmax(0,1.3fr)_minmax(260px,0.7fr)] xl:items-center">
          <div className="space-y-5">
            <div className="space-y-3">
              <p className="dashboard-kicker">Endeavor operations suite</p>
              <h1 className="dashboard-title">Service delivery dashboard</h1>
              <p className="max-w-2xl text-base leading-7 text-slate-600">
                A branded control room for delivery, provider performance, and financial visibility, styled to mirror your attached reference while keeping the live Velocity data model intact.
              </p>
            </div>
            <div className="flex flex-wrap gap-3">
              <Button asChild className="h-11 rounded-xl bg-primary px-5 font-semibold uppercase tracking-[0.12em] hover:bg-primary/95">
                <Link to="/orders/new">
                  New order
                  <ArrowRight className="h-4 w-4" />
                </Link>
              </Button>
              <Button asChild variant="outline" className="h-11 rounded-xl border-slate-200 bg-white px-5 font-semibold uppercase tracking-[0.12em] shadow-none hover:bg-slate-50">
                <Link to="/services">Open services</Link>
              </Button>
            </div>
          </div>

          <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-1">
            <div className="rounded-2xl border border-sky-100 bg-[linear-gradient(135deg,rgba(14,141,214,0.08),rgba(255,255,255,1))] p-5">
              <div className="flex items-center justify-between gap-4">
                <div>
                  <p className="dashboard-kicker">Current cycle</p>
                  <p className="mt-2 font-heading text-3xl font-extrabold uppercase tracking-[0.02em] text-slate-900">{monthLabel}</p>
                </div>
                <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-white text-primary shadow-sm">
                  <CalendarDays className="h-6 w-6" />
                </div>
              </div>
              <div className="mt-5 grid grid-cols-2 gap-3 text-sm text-slate-600">
                <div className="rounded-xl border border-slate-100 bg-white px-4 py-3">
                  <p className="font-heading text-xs uppercase tracking-[0.18em] text-slate-500">Completion</p>
                  <p className="mt-2 text-2xl font-bold text-slate-900">{metrics.completionRate}%</p>
                </div>
                <div className="rounded-xl border border-slate-100 bg-white px-4 py-3">
                  <p className="font-heading text-xs uppercase tracking-[0.18em] text-slate-500">Jeopardies</p>
                  <p className="mt-2 text-2xl font-bold text-slate-900">{metrics.jeopardies}</p>
                </div>
              </div>
            </div>

            <div className="rounded-2xl border border-sky-100 bg-[linear-gradient(135deg,#1d7fc1_0%,#14639d_100%)] p-5 text-white shadow-sm">
              <div className="flex items-center gap-3">
                <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-white/12 text-white">
                  <ShieldCheck className="h-7 w-7" />
                </div>
                <div>
                  <p className="dashboard-kicker !text-white/55">Status note</p>
                  <p className="font-heading text-2xl font-extrabold uppercase tracking-[0.02em] text-white">Portfolio stable</p>
                </div>
              </div>
              <p className="mt-4 text-sm leading-6 text-white/72">
                Live delivery trends and financial signals are consolidated below with emphasis on action-ready visuals rather than raw system layout.
              </p>
            </div>
          </div>
        </div>
      </section>

      <section className="grid gap-4 xl:grid-cols-4">
        <DashboardMetricCard
          label="Total services"
          value={metrics.totalServices}
          caption="All tracked services across the current portfolio"
          icon={BarChart3}
          tone="brand"
        />
        <DashboardMetricCard
          label="In flight"
          value={metrics.inFlight}
          caption="Work items that still need closure or completion"
          icon={Activity}
          tone="cyan"
        />
        <DashboardMetricCard
          label="Open jeopardies"
          value={metrics.jeopardies}
          caption="Service-level and location-level escalation points"
          icon={CircleAlert}
          tone="pink"
        />
        <DashboardMetricCard
          label="Inventory tracked"
          value={metrics.inventory}
          caption="Services flagged as active inventory in the estate"
          icon={Boxes}
          tone="violet"
        />
      </section>

      <Tabs defaultValue="wip" className="w-full space-y-5">
        <TabsList className="dashboard-tablist">
          {dashboardTabs.map((tab) => {
            const Icon = tab.icon;

            return (
              <TabsTrigger key={tab.value} value={tab.value} className="dashboard-tab-trigger">
                <span className={`dashboard-tab-icon ${tab.iconClassName}`}>
                  <Icon className="h-5 w-5" />
                </span>
                <span className="dashboard-tab-copy">
                  <strong>{tab.label}</strong>
                  <span>{tab.description}</span>
                </span>
              </TabsTrigger>
            );
          })}
        </TabsList>

        <div className="grid gap-5 2xl:grid-cols-[minmax(0,1.4fr)_340px]">
          <div className="min-w-0">
            <TabsContent value="wip" className="mt-0">
            <WipTab />
          </TabsContent>
          <TabsContent value="kpi" className="mt-0">
            <KpiTab />
          </TabsContent>
          <TabsContent value="providers" className="mt-0">
            <ProvidersTab />
          </TabsContent>
          <TabsContent value="financials" className="mt-0">
            <FinancialsTab />
          </TabsContent>
          <TabsContent value="activations" className="mt-0">
            <ActivationsTab />
          </TabsContent>
          <TabsContent value="inventory" className="mt-0">
            <InventoryTab />
          </TabsContent>
          </div>

          <aside className="space-y-6">
            <DashboardPanel
              title="Operations profile"
              description="A persistent briefing card inspired by the right-hand profile panel from the supplied dashboard reference."
            >
              <div className="space-y-5">
                <div className="overflow-hidden rounded-2xl bg-[linear-gradient(135deg,#118ad3_0%,#18679b_100%)] p-5 text-white">
                  <div className="flex items-start justify-between gap-4">
                    <div>
                      <p className="dashboard-kicker !text-white/55">Program owner</p>
                      <p className="mt-2 font-heading text-3xl font-bold uppercase tracking-[0.08em]">Operations board</p>
                      <p className="mt-1 text-sm text-white/72">delivery@endeavor.example</p>
                    </div>
                    <div className="flex h-16 w-16 items-center justify-center rounded-full border-4 border-white/25 bg-white/12 text-white">
                      <UserRound className="h-8 w-8" />
                    </div>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-3 text-sm text-slate-600">
                  <div className="rounded-xl border border-slate-100 bg-slate-50 px-4 py-3">
                    <p className="font-heading text-xs uppercase tracking-[0.18em] text-slate-500">Cycle</p>
                    <p className="mt-2 font-semibold text-slate-900">Q2 delivery</p>
                  </div>
                  <div className="rounded-xl border border-slate-100 bg-slate-50 px-4 py-3">
                    <p className="font-heading text-xs uppercase tracking-[0.18em] text-slate-500">Target</p>
                    <p className="mt-2 font-semibold text-slate-900">92% on-time</p>
                  </div>
                </div>

                <div className="space-y-3">
                  <div className="dashboard-list-item">
                    <ShieldCheck />
                    <div>
                      <strong>Delivery review</strong>
                      <span>Watch KPI completion and keep jeopardy backlog below threshold.</span>
                    </div>
                  </div>
                  <div className="dashboard-list-item">
                    <CalendarDays />
                    <div>
                      <strong>Weekly cadence</strong>
                      <span>Executive refresh every Tuesday, provider alignment on Thursday.</span>
                    </div>
                  </div>
                  <div className="dashboard-list-item">
                    <Activity />
                    <div>
                      <strong>Primary focus</strong>
                      <span>Use this board to move quickly from status to action instead of raw tabular review.</span>
                    </div>
                  </div>
                </div>
              </div>
            </DashboardPanel>

            <DashboardPanel title="This month" description="Compact quick-look metrics and reminders for delivery leadership.">
              <div className="space-y-4">
                <div className="grid grid-cols-2 gap-3">
                  <div className="rounded-xl border border-slate-100 bg-slate-50 px-4 py-4">
                    <p className="font-heading text-xs uppercase tracking-[0.18em] text-slate-500">Services</p>
                    <p className="mt-2 text-2xl font-bold text-slate-900">{metrics.totalServices}</p>
                  </div>
                  <div className="rounded-xl border border-slate-100 bg-slate-50 px-4 py-4">
                    <p className="font-heading text-xs uppercase tracking-[0.18em] text-slate-500">Resolved</p>
                    <p className="mt-2 text-2xl font-bold text-slate-900">{metrics.completionRate}%</p>
                  </div>
                </div>
                <div className="rounded-xl border border-rose-100 bg-[linear-gradient(135deg,rgba(241,111,151,0.08),rgba(255,255,255,1))] px-4 py-4 text-sm leading-6 text-slate-600">
                  Provider performance and finance tabs share a common visual hierarchy so analysts can compare trends without relearning the page structure.
                </div>
              </div>
            </DashboardPanel>
          </aside>
        </div>
      </Tabs>
    </div>
  );
}
