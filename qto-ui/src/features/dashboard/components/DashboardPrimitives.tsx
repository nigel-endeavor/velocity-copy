import { ReactNode } from 'react';
import type { LucideIcon } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { cn } from '@/lib/utils';

type MetricTone = 'brand' | 'cyan' | 'pink' | 'violet' | 'amber' | 'green';

const metricToneClasses: Record<MetricTone, string> = {
  brand: 'from-sky-500 to-sky-400 text-white',
  cyan: 'from-cyan-500 to-sky-400 text-white',
  pink: 'from-rose-500 to-pink-400 text-white',
  violet: 'from-violet-500 to-fuchsia-400 text-white',
  amber: 'from-amber-400 to-orange-400 text-slate-950',
  green: 'from-emerald-500 to-teal-400 text-white',
};

interface DashboardPanelProps {
  title?: ReactNode;
  description?: ReactNode;
  action?: ReactNode;
  className?: string;
  contentClassName?: string;
  headerClassName?: string;
  children: ReactNode;
}

interface DashboardMetricCardProps {
  label: string;
  value: ReactNode;
  caption?: ReactNode;
  icon: LucideIcon;
  tone?: MetricTone;
  className?: string;
}

export function DashboardPanel({
  title,
  description,
  action,
  className,
  contentClassName,
  headerClassName,
  children,
}: DashboardPanelProps) {
  return (
    <Card className={cn('dashboard-card border-0 shadow-none', className)}>
      {(title || description || action) && (
        <CardHeader className={cn('flex flex-row items-start justify-between gap-4 pb-4', headerClassName)}>
          <div className="space-y-2">
            {title ? <CardTitle className="font-heading text-xl uppercase tracking-[0.12em] text-slate-900">{title}</CardTitle> : null}
            {description ? <CardDescription className="max-w-2xl text-sm text-slate-500">{description}</CardDescription> : null}
          </div>
          {action ? <div className="shrink-0">{action}</div> : null}
        </CardHeader>
      )}
      <CardContent className={cn(title || description || action ? '' : 'pt-6', contentClassName)}>{children}</CardContent>
    </Card>
  );
}

export function DashboardMetricCard({
  label,
  value,
  caption,
  icon: Icon,
  tone = 'brand',
  className,
}: DashboardMetricCardProps) {
  return (
    <div
      className={cn(
        'relative overflow-hidden rounded-2xl border border-white/70 bg-gradient-to-br p-5 shadow-sm',
        metricToneClasses[tone],
        className
      )}
    >
      <div className="flex items-start justify-between gap-4">
        <div>
          <p className="font-heading text-sm uppercase tracking-[0.28em] text-current/80">{label}</p>
          <p className="mt-4 font-heading text-3xl font-bold uppercase tracking-[0.08em]">{value}</p>
          {caption ? <p className="mt-2 text-sm text-current/85">{caption}</p> : null}
        </div>
        <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-white/20">
          <Icon className="h-6 w-6" />
        </div>
      </div>
    </div>
  );
}

export function DashboardDataSurface({ children, className }: { children: ReactNode; className?: string }) {
  return <div className={cn('dashboard-chart rounded-xl p-2', className)}>{children}</div>;
}

export function DashboardTableSurface({ children, className }: { children: ReactNode; className?: string }) {
  return <div className={cn('dashboard-table-frame', className)}>{children}</div>;
}