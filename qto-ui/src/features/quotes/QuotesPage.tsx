/**
 * Quotes Management Page
 *
 * Lists all quotes from the database. "New Quote" opens external ConnectBase URL.
 * Mirrors the Angular app's quote management approach.
 */

import { useState, useMemo } from 'react';
import { ExternalLink, Search, RefreshCw, FileText, CheckCircle, Clock } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { AppPage, PageHeader, PageToolbar } from '@/components/layout/PageScaffold';
import { useListQuotesQuery } from '@/services/api/quotesApi';

const NEW_QUOTE_URL = 'https://cw.connectbase.com/#/login';

const ROWS_PER_PAGE = 25;

function formatDate(dateStr?: string | null): string {
  if (!dateStr) return '—';
  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  }).format(new Date(dateStr));
}

export default function QuotesPage() {
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');

  const { data, isLoading, isFetching, refetch } = useListQuotesQuery({
    offset: page * ROWS_PER_PAGE,
    limit: ROWS_PER_PAGE,
  });

  const total = data?.total ?? 0;
  const totalPages = Math.max(1, Math.ceil(total / ROWS_PER_PAGE));

  const filtered = useMemo(() => {
    const q = search.toLowerCase();
    const list = data?.collection ?? [];
    if (!q) return list;
    return list.filter(
      (quote) =>
        quote.vendorQuoteId?.toLowerCase().includes(q) ||
        quote.quoteNumber?.toLowerCase().includes(q) ||
        quote.accountName?.toLowerCase().includes(q) ||
        quote.userName?.toLowerCase().includes(q) ||
        quote.quoteProvider?.toLowerCase().includes(q),
    );
  }, [data?.collection, search]);

  const processedCount = filtered.filter((quote) => Boolean(quote.handledTime)).length;

  return (
    <AppPage>
      <PageHeader
        eyebrow="Commercial pipeline"
        title="Quote Management"
        description="Keep sales intake, provider responses, and quote handling in a compact surface designed for fast scanning."
        actions={
          <>
            <Button
              className="h-11 rounded-xl"
              variant="outline"
              onClick={() => refetch()}
              disabled={isFetching}
            >
              <RefreshCw className={`mr-2 h-4 w-4 ${isFetching ? 'animate-spin' : ''}`} />
              Refresh
            </Button>
            <Button
              className="h-11 rounded-xl"
              onClick={() => window.open(NEW_QUOTE_URL, '_blank', 'noopener,noreferrer')}
            >
              <ExternalLink className="mr-2 h-4 w-4" />
              New Quote
            </Button>
          </>
        }
        stats={[
          {
            label: 'Quote count',
            value: total.toLocaleString(),
            detail: `${filtered.length} visible`,
            tone: 'brand',
          },
          {
            label: 'Processed',
            value: processedCount.toString(),
            detail: `${filtered.length - processedCount} pending`,
            tone: 'warm',
          },
        ]}
      />

      <PageToolbar>
        <div className="relative min-w-[18rem] flex-1 max-w-md">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
          <Input
            placeholder="Search quotes..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="h-11 rounded-xl border-slate-200 bg-white pl-9 shadow-none"
          />
        </div>
        <span className="app-page-toolbar-note">Search the active quote slice instantly by account, number, user, or provider.</span>
      </PageToolbar>

      {/* Table */}
      <Card className="app-table-panel overflow-hidden border-slate-200 shadow-none">
        <CardHeader className="border-b border-slate-200/80 pb-3">
          <CardTitle className="text-base flex items-center gap-2">
            <FileText className="h-4 w-4" />
            Quotes
          </CardTitle>
        </CardHeader>
        <CardContent className="p-0">
          {isLoading ? (
            <div className="flex justify-center items-center h-40">
              <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary" />
            </div>
          ) : filtered.length === 0 ? (
            <div className="text-center text-muted-foreground py-16">
              {search ? 'No quotes match your search.' : 'No quotes found.'}
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="w-full text-sm">
                <thead>
                  <tr className="border-b bg-slate-50/80">
                    <th className="text-left px-4 py-3 font-medium">ID</th>
                    <th className="text-left px-4 py-3 font-medium">Quote #</th>
                    <th className="text-left px-4 py-3 font-medium">Vendor Quote ID</th>
                    <th className="text-left px-4 py-3 font-medium">Account</th>
                    <th className="text-left px-4 py-3 font-medium">Submitted By</th>
                    <th className="text-left px-4 py-3 font-medium">Provider</th>
                    <th className="text-left px-4 py-3 font-medium">Status</th>
                    <th className="text-left px-4 py-3 font-medium">Order ID</th>
                    <th className="text-left px-4 py-3 font-medium">Handled</th>
                  </tr>
                </thead>
                <tbody>
                  {filtered.map((quote, idx) => (
                    <tr
                      key={quote.id}
                      className={`border-b transition-colors hover:bg-slate-50/80 ${idx % 2 === 1 ? 'bg-slate-50/30' : ''}`}
                    >
                      <td className="px-4 py-3 font-mono text-xs">{quote.id}</td>
                      <td className="px-4 py-3">{quote.quoteNumber ?? '—'}</td>
                      <td className="px-4 py-3 font-mono text-xs">{quote.vendorQuoteId ?? '—'}</td>
                      <td className="px-4 py-3">{quote.accountName ?? '—'}</td>
                      <td className="px-4 py-3">
                        <div className="text-xs">
                          <div>{quote.userName ?? '—'}</div>
                          {quote.userEmail && (
                            <div className="text-muted-foreground">{quote.userEmail}</div>
                          )}
                        </div>
                      </td>
                      <td className="px-4 py-3">{quote.quoteProvider ?? '—'}</td>
                      <td className="px-4 py-3">
                        {quote.handledTime ? (
                          <Badge variant="default" className="bg-green-100 text-green-800 hover:bg-green-100">
                            <CheckCircle className="h-3 w-3 mr-1" />
                            Processed
                          </Badge>
                        ) : (
                          <Badge variant="secondary">
                            <Clock className="h-3 w-3 mr-1" />
                            Pending
                          </Badge>
                        )}
                      </td>
                      <td className="px-4 py-3">
                        {quote.orderId ? (
                          <a
                            href={`#/orders/${quote.orderId}`}
                            className="text-primary hover:underline font-mono text-xs"
                          >
                            #{quote.orderId}
                          </a>
                        ) : (
                          '—'
                        )}
                      </td>
                      <td className="px-4 py-3 text-xs text-muted-foreground">
                        {formatDate(quote.handledTime)}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </CardContent>
      </Card>

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex items-center justify-between">
          <p className="text-sm text-muted-foreground">
            Page {page + 1} of {totalPages} ({total} total)
          </p>
          <div className="flex gap-2">
            <Button
              variant="outline"
              size="sm"
              onClick={() => setPage((p) => Math.max(0, p - 1))}
              disabled={page === 0}
            >
              Previous
            </Button>
            <Button
              variant="outline"
              size="sm"
              onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
              disabled={page >= totalPages - 1}
            >
              Next
            </Button>
          </div>
        </div>
      )}
    </AppPage>
  );
}
