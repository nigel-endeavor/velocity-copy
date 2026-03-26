/**
 * Order Summary Component
 *
 * Financial summary card showing MRC, NRC, MRR, NRR, ICB, OSP totals
 * Mirrors Angular's order-summary-card functionality
 */

import { useAppSelector } from '@/store/hooks';
import { selectOrder, selectLocationCount, selectServiceCount } from '@/store/slices/orderDetailsSelectors';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Separator } from '@/components/ui/separator';
import { DollarSign, MapPin, Wrench } from 'lucide-react';

function formatCurrency(value: number | null | undefined): string {
  if (value == null || isNaN(value)) return '$0.00';
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value);
}

export default function OrderSummary() {
  const order = useAppSelector(selectOrder);
  const locationCount = useAppSelector(selectLocationCount);
  const serviceCount = useAppSelector(selectServiceCount);

  if (!order) return null;

  const financials = [
    { label: 'Monthly Recurring Cost', value: order.mrc, abbr: 'MRC' },
    { label: 'Non-Recurring Cost', value: order.nrc, abbr: 'NRC' },
    { label: 'Monthly Recurring Revenue', value: order.mrr, abbr: 'MRR' },
    { label: 'Non-Recurring Revenue', value: order.nrr, abbr: 'NRR' },
    { label: 'Installation Cost Budget', value: order.icb, abbr: 'ICB' },
    { label: 'Outside Plant', value: order.osp, abbr: 'OSP' },
  ];

  return (
    <Card>
      <CardHeader>
        <CardTitle className="text-base flex items-center gap-2">
          <DollarSign className="h-4 w-4" />
          Order Summary
        </CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        {/* Counts */}
        <div className="flex gap-6">
          <div className="flex items-center gap-2 text-sm">
            <MapPin className="h-4 w-4 text-muted-foreground" />
            <span className="font-medium">{locationCount}</span>
            <span className="text-muted-foreground">Locations</span>
          </div>
          <div className="flex items-center gap-2 text-sm">
            <Wrench className="h-4 w-4 text-muted-foreground" />
            <span className="font-medium">{serviceCount}</span>
            <span className="text-muted-foreground">Services</span>
          </div>
        </div>

        <Separator />

        {/* Financial Summary */}
        <div className="grid grid-cols-2 gap-3">
          {financials.map(({ value, abbr }) => (
            <div key={abbr} className="flex justify-between items-center py-1">
              <span className="text-sm text-muted-foreground">{abbr}</span>
              <span className="text-sm font-medium tabular-nums">
                {formatCurrency(value)}
              </span>
            </div>
          ))}
        </div>

        <Separator />

        {/* Annual Recurring Cost */}
        <div className="flex justify-between items-center">
          <span className="text-sm font-medium">Annual Recurring Cost</span>
          <span className="text-base font-bold tabular-nums">
            {formatCurrency(order.annualRecurringCost)}
          </span>
        </div>
      </CardContent>
    </Card>
  );
}
