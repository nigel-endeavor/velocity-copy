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

  return (
    <div className="p-6 space-y-4">
      {/* Page Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Quote Management</h1>
          <p className="text-sm text-muted-foreground mt-1">
            {total} quote{total !== 1 ? 's' : ''} total
          </p>
        </div>
        <div className="flex gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => refetch()}
            disabled={isFetching}
          >
            <RefreshCw className={`h-4 w-4 mr-2 ${isFetching ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <Button
            size="sm"
            onClick={() => window.open(NEW_QUOTE_URL, '_blank', 'noopener,noreferrer')}
          >
            <ExternalLink className="h-4 w-4 mr-2" />
            New Quote
          </Button>
        </div>
      </div>

      {/* Search */}
      <div className="relative max-w-sm">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
        <Input
          placeholder="Search quotes..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="pl-9"
        />
      </div>

      {/* Table */}
      <Card>
        <CardHeader className="pb-2">
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
                  <tr className="border-b bg-muted/50">
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
                      className={`border-b hover:bg-muted/30 transition-colors ${idx % 2 === 1 ? 'bg-muted/10' : ''}`}
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
    </div>
  );
}
