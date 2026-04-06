import { ReactNode } from 'react';
import { cn } from '@/lib/utils';

type MetricTone = 'neutral' | 'brand' | 'warm' | 'success';

export interface PageMetricItem {
  label: string;
  value: ReactNode;
  detail?: ReactNode;
  tone?: MetricTone;
}

interface AppPageProps {
  children: ReactNode;
  className?: string;
}

interface PageHeaderProps {
  eyebrow?: string;
  title: string;
  description?: string;
  actions?: ReactNode;
  stats?: PageMetricItem[];
  className?: string;
}

interface PageToolbarProps {
  children: ReactNode;
  className?: string;
}

export function AppPage({ children, className }: AppPageProps) {
  return <div className={cn('app-page', className)}>{children}</div>;
}

export function PageHeader({
  eyebrow,
  title,
  description,
  actions,
  stats,
  className,
}: PageHeaderProps) {
  return (
    <section className={cn('app-page-hero', className)}>
      <div className="app-page-header-copy">
        {eyebrow ? <p className="app-page-eyebrow">{eyebrow}</p> : null}
        <div className="app-page-header">
          <div>
            <h1 className="app-page-title">{title}</h1>
            {description ? <p className="app-page-subtitle">{description}</p> : null}
          </div>
        </div>
      </div>

      {(actions || (stats && stats.length > 0)) ? (
        <div className="app-page-header-side">
          {actions ? <div className="app-page-actions">{actions}</div> : null}
          {stats && stats.length > 0 ? <PageMetricStrip items={stats} /> : null}
        </div>
      ) : null}
    </section>
  );
}

export function PageToolbar({ children, className }: PageToolbarProps) {
  return <div className={cn('app-page-toolbar', className)}>{children}</div>;
}

interface PageMetricStripProps {
  items: PageMetricItem[];
}

function PageMetricStrip({ items }: PageMetricStripProps) {
  return (
    <div className="app-page-metric-strip">
      {items.map((item) => (
        <div key={item.label} className="app-page-metric" data-tone={item.tone ?? 'neutral'}>
          <span>{item.label}</span>
          <strong>{item.value}</strong>
          {item.detail ? <small>{item.detail}</small> : null}
        </div>
      ))}
    </div>
  );
}
