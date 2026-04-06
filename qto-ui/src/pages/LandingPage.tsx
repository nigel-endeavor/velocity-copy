import { useEffect, useState } from 'react';
import { Activity, RefreshCw, Server, ShieldCheck, Zap } from 'lucide-react';
import { environment } from '../config/environment';
import { AppPage, PageHeader } from '@/components/layout/PageScaffold';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

interface BackendStatus {
  connected: boolean;
  message: string;
  endpoint: string;
  responseTime?: number;
}

export const LandingPage = () => {
  const [status, setStatus] = useState<BackendStatus | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkBackend = async () => {
      const start = performance.now();
      try {
        setLoading(true);
        const res = await fetch(`${environment.appUrl}/services?page=0&size=1`);
        const elapsed = Math.round(performance.now() - start);
        if (res.ok) {
          setStatus({
            connected: true,
            message: `Backend responding (${res.status})`,
            endpoint: `${environment.appUrl}/services`,
            responseTime: elapsed,
          });
        } else {
          setStatus({
            connected: false,
            message: `Backend returned ${res.status} ${res.statusText}`,
            endpoint: `${environment.appUrl}/services`,
            responseTime: elapsed,
          });
        }
      } catch (err) {
        setStatus({
          connected: false,
          message: err instanceof Error ? err.message : 'Network request failed',
          endpoint: `${environment.appUrl}/services`,
        });
      } finally {
        setLoading(false);
      }
    };

    checkBackend();
  }, []);

  const handlePing = async () => {
    const start = performance.now();
    try {
      const res = await fetch(`${environment.appUrl}/services?page=0&size=1`);
      const elapsed = Math.round(performance.now() - start);
      alert(`Ping: ${res.status} in ${elapsed}ms`);
    } catch (err) {
      alert(`Ping failed: ${err instanceof Error ? err.message : 'Unknown error'}`);
    }
  };

  return (
    <AppPage>
      <PageHeader
        eyebrow="Platform overview"
        title="QTO Application"
        description="The home surface now uses the same product rhythm as the operational pages: balanced header, compact status visibility, and restrained accent color." 
        actions={
          <Button className="h-11 rounded-xl" onClick={handlePing}>
            <RefreshCw className="mr-2 h-4 w-4" />
            Test Ping
          </Button>
        }
        stats={[
          {
            label: 'Environment',
            value: status?.connected ? 'Connected' : loading ? 'Checking' : 'Offline',
            detail: status?.responseTime != null ? `${status.responseTime}ms latency` : 'Backend connectivity',
            tone: status?.connected ? 'success' : 'warm',
          },
          {
            label: 'Endpoint',
            value: 'Services API',
            detail: '/services?page=0&size=1',
            tone: 'brand',
          },
        ]}
      />

      <div className="grid gap-4 xl:grid-cols-[minmax(0,1.2fr)_minmax(22rem,0.8fr)]">
        <Card className="app-surface border-slate-200 shadow-none">
          <CardHeader className="border-b border-slate-200/80">
            <CardTitle className="flex items-center gap-2 text-base">
              <Server className="h-4 w-4 text-primary" />
              Backend Status
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-4 pt-6">
            {loading ? (
              <div className="flex items-center justify-center py-10">
                <div className="h-12 w-12 animate-spin rounded-full border-b-2 border-primary"></div>
              </div>
            ) : status ? (
              <div className="grid gap-3 sm:grid-cols-2">
                <div className="rounded-2xl border border-slate-200 bg-slate-50 p-4">
                  <span className="text-[11px] font-semibold uppercase tracking-[0.24em] text-slate-400">Status</span>
                  <p className={`mt-2 text-xl font-bold ${status.connected ? 'text-emerald-600' : 'text-red-600'}`}>
                    {status.connected ? 'Connected' : 'Disconnected'}
                  </p>
                  <p className="mt-1 text-sm text-slate-500">{status.message}</p>
                </div>
                <div className="rounded-2xl border border-slate-200 bg-slate-50 p-4">
                  <span className="text-[11px] font-semibold uppercase tracking-[0.24em] text-slate-400">Endpoint</span>
                  <p className="mt-2 text-sm font-semibold text-slate-900 break-all">{status.endpoint}</p>
                  <p className="mt-1 text-sm text-slate-500">Session-based connectivity check</p>
                </div>
              </div>
            ) : null}
          </CardContent>
        </Card>

        <div className="grid gap-4">
          <Card className="app-surface border-slate-200 shadow-none">
            <CardContent className="grid gap-3 p-5">
              <div className="flex items-start gap-3 rounded-2xl border border-slate-200 bg-slate-50 p-4">
                <Activity className="mt-1 h-4 w-4 text-primary" />
                <div>
                  <p className="text-sm font-semibold text-slate-900">Operationally focused shell</p>
                  <p className="mt-1 text-sm text-slate-500">Viewport-contained layout for dashboard, worklists, billing, and inventory.</p>
                </div>
              </div>
              <div className="flex items-start gap-3 rounded-2xl border border-slate-200 bg-slate-50 p-4">
                <Zap className="mt-1 h-4 w-4 text-orange-500" />
                <div>
                  <p className="text-sm font-semibold text-slate-900">Fast page-to-page consistency</p>
                  <p className="mt-1 text-sm text-slate-500">Shared page headers, data surfaces, and restrained accent color across the app.</p>
                </div>
              </div>
              <div className="flex items-start gap-3 rounded-2xl border border-slate-200 bg-slate-50 p-4">
                <ShieldCheck className="mt-1 h-4 w-4 text-emerald-500" />
                <div>
                  <p className="text-sm font-semibold text-slate-900">Production-ready defaults</p>
                  <p className="mt-1 text-sm text-slate-500">Clean typography, lower motion, and balanced right-side utility weight.</p>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </AppPage>
  );
};

export default LandingPage;
