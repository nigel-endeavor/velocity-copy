import { ReactNode, useMemo } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import type { LucideIcon } from 'lucide-react';
import {
  Activity,
  AlertTriangle,
  Bell,
  Briefcase,
  ClipboardList,
  FileText,
  House,
  LayoutDashboard,
  MapPin,
  Package,
  ReceiptText,
  Search,
  Settings2,
  Unplug,
  Users,
  Wallet,
} from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { cn } from '@/lib/utils';

interface MainLayoutProps {
  children?: ReactNode;
}

interface NavItem {
  label: string;
  path: string;
  icon: LucideIcon;
}

const navigationGroups: { title: string; items: NavItem[] }[] = [
  {
    title: 'Workspace',
    items: [
      { label: 'Home', path: '/', icon: House },
      { label: 'Dashboard', path: '/dashboard', icon: LayoutDashboard },
      { label: 'Orders', path: '/orders', icon: ClipboardList },
      { label: 'Quotes', path: '/quotes', icon: FileText },
    ],
  },
  {
    title: 'Operations',
    items: [
      { label: 'Services', path: '/services', icon: Briefcase },
      { label: 'Activations', path: '/activations', icon: Activity },
      { label: 'Disconnects', path: '/disconnects', icon: Unplug },
      { label: 'Disputes', path: '/disputes', icon: AlertTriangle },
      { label: 'Locations', path: '/locations', icon: MapPin },
      { label: 'Inventory', path: '/inventory', icon: Package },
      { label: 'Customers', path: '/customers', icon: Users },
      { label: 'Invoicing', path: '/invoicing', icon: ReceiptText },
      { label: 'Expenses', path: '/expenses', icon: Wallet },
      { label: 'Configuration', path: '/configuration', icon: Settings2 },
    ],
  },
];

export const MainLayout = ({ children }: MainLayoutProps) => {
  const location = useLocation();
  const todayLabel = useMemo(
    () =>
      new Intl.DateTimeFormat('en-US', {
        weekday: 'short',
        month: 'short',
        day: 'numeric',
      }).format(new Date()),
    []
  );

  const flatNavigation = navigationGroups.flatMap((group) => group.items);

  const isActive = (path: string) => {
    if (path === '/') {
      return location.pathname === '/';
    }

    return location.pathname === path || location.pathname.startsWith(`${path}/`);
  };

  return (
    <div className="page-shell min-h-screen">
      <div className="shell-frame w-full">
        <div className="grid h-screen gap-0 lg:grid-cols-[240px_minmax(0,1fr)]">
          <aside className="hidden h-screen overflow-hidden border-r border-slate-200 bg-white px-6 py-7 text-shell-foreground lg:flex lg:flex-col">
            <div className="space-y-4">
              <Link to="/dashboard" className="relative block rounded-[28px] border border-slate-200 bg-slate-50/80 px-5 py-5 text-left">
                <div className="w-full max-w-[11rem]">
                  <p className="font-heading text-sm uppercase tracking-[0.4em] text-slate-400">Endeavor</p>
                  <h1 className="mt-2 font-heading text-3xl font-bold uppercase tracking-[0.1em] text-slate-900">Velocity</h1>
                </div>
                {/* <span className="absolute right-4 top-4 rounded-full border border-orange-200 bg-orange-50 px-3 py-1 text-[10px] font-semibold uppercase tracking-[0.24em] text-orange-600">
                    Live
                </span> */}
              </Link>
              <p className="max-w-[14rem] text-sm leading-6 text-slate-500">
                Operational dashboard for program delivery, service pipeline, and financial control.
              </p>
            </div>

            <div className="mt-8 space-y-7">
              {navigationGroups.map((group) => (
                <div key={group.title} className="space-y-3">
                  <p className="font-heading text-xs uppercase tracking-[0.32em] text-slate-400">{group.title}</p>
                  <div className="space-y-2">
                    {group.items.map((item) => {
                      const Icon = item.icon;
                      const active = isActive(item.path);

                      return (
                        <Link
                          key={item.path}
                          to={item.path}
                          className={cn(
                            'flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium transition-colors duration-150',
                            active
                              ? 'bg-primary text-white'
                              : 'text-slate-500 hover:bg-slate-50 hover:text-slate-900'
                          )}
                        >
                          <span
                            className={cn(
                              'flex h-10 w-10 items-center justify-center rounded-xl border transition-colors duration-150',
                              active ? 'border-white/20 bg-white/15 text-white' : 'border-slate-200 bg-slate-50 text-slate-500'
                            )}
                          >
                            <Icon className="h-4 w-4" />
                          </span>
                          <span>{item.label}</span>
                        </Link>
                      );
                    })}
                  </div>
                </div>
              ))}
            </div>
          </aside>

          <div className="grid min-w-0 h-screen grid-rows-[auto_auto_1fr] bg-[#edf4fa]">
            <header className="border-b border-slate-200 bg-white px-4 py-4 sm:px-6">
              <div className="flex flex-col gap-4 xl:grid xl:grid-cols-[320px_minmax(0,1fr)_max-content] xl:items-center xl:gap-6">
                <div className="flex flex-col gap-3 sm:flex-row sm:items-center xl:col-start-1 xl:justify-self-start">
                  <div className="relative min-w-[220px] flex-1 sm:flex-none">
                    <Search className="pointer-events-none absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
                    <Input
                      aria-label="Search workspace"
                      placeholder="Search records"
                      className="h-11 rounded-xl border-slate-200 bg-white pl-10 text-sm shadow-none"
                    />
                  </div>
                  <Button variant="outline" size="icon" className="h-11 w-11 rounded-xl border-slate-200 bg-white shadow-none">
                    <Bell className="h-4 w-4" />
                  </Button>
                </div>

                <div className="xl:col-start-3 xl:justify-self-end xl:text-right">
                  <p className="dashboard-kicker xl:text-right">Operations cockpit</p>
                  <div className="mt-2 flex flex-wrap items-center gap-3 xl:justify-end">
                    <h2 className="font-heading text-2xl uppercase tracking-[0.12em] text-slate-900">Delivery command center</h2>
                    <span className="dashboard-pill">{todayLabel}</span>
                  </div>
                  <p className="mt-2 text-sm text-slate-500 xl:max-w-2xl xl:ml-auto">Cross-functional visibility across orders, billing, and service delivery.</p>
                </div>

                <div className="hidden xl:block" />
              </div>
            </header>

            <div className="border-b border-slate-200 bg-white p-2 lg:hidden">
              <nav className="flex gap-3 overflow-x-auto px-2 py-2">
                {flatNavigation.map((item) => {
                  const Icon = item.icon;
                  const active = isActive(item.path);

                  return (
                    <Link
                      key={item.path}
                      to={item.path}
                      className={cn(
                        'flex min-w-fit items-center gap-2 rounded-xl px-4 py-3 text-sm font-medium transition-colors duration-150',
                        active ? 'bg-primary text-primary-foreground' : 'bg-slate-100 text-slate-600'
                      )}
                    >
                      <Icon className="h-4 w-4" />
                      <span>{item.label}</span>
                    </Link>
                  );
                })}
              </nav>
            </div>

            <main className="min-w-0 min-h-0 overflow-y-auto p-4 sm:p-6">{children || <Outlet />}</main>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainLayout;
