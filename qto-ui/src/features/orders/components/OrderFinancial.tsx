/**
 * Order Financial Component
 *
 * Financial summary tab for order
 */

import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Separator } from '@/components/ui/separator';
import { useAppSelector } from '@/store/hooks';
import {
  selectOrder,
  selectTotalMrc,
  selectTotalNrc,
  selectServicesMrc,
  selectServicesNrc,
} from '@/store/slices/orderDetailsSelectors';

/**
 * Format currency
 */
function formatCurrency(value: number): string {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  }).format(value);
}

export default function OrderFinancial() {
  const order = useAppSelector(selectOrder);
  const totalMrc = useAppSelector(selectTotalMrc);
  const totalNrc = useAppSelector(selectTotalNrc);
  const servicesMrc = useAppSelector(selectServicesMrc);
  const servicesNrc = useAppSelector(selectServicesNrc);

  if (!order) return null;

  return (
    <div>
      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12">
          <h3 className="text-lg font-semibold">Cost Summary</h3>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="mrc">Monthly Recurring Cost (MRC)</Label>
            <Input
              id="mrc"
              value={formatCurrency(totalMrc)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">Total monthly recurring costs</p>
          </div>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="nrc">Non-Recurring Cost (NRC)</Label>
            <Input
              id="nrc"
              value={formatCurrency(totalNrc)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">One-time installation and setup costs</p>
          </div>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="annualCost">Annual Recurring Cost</Label>
            <Input
              id="annualCost"
              value={formatCurrency(order.annualRecurringCost || totalMrc * 12)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">MRC × 12 months</p>
          </div>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="icb">Installation Cost Budget (ICB)</Label>
            <Input
              id="icb"
              value={formatCurrency(order.icb || 0)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">Budget for installation costs</p>
          </div>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="osp">Outside Plant (OSP)</Label>
            <Input
              id="osp"
              value={formatCurrency(order.osp || 0)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">Outside plant costs</p>
          </div>
        </div>
      </div>

      <Separator className="my-6" />

      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12">
          <h3 className="text-lg font-semibold">Revenue Summary</h3>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="mrr">Monthly Recurring Revenue (MRR)</Label>
            <Input
              id="mrr"
              value={formatCurrency(order.mrr || 0)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">Total monthly recurring revenue</p>
          </div>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="nrr">Non-Recurring Revenue (NRR)</Label>
            <Input
              id="nrr"
              value={formatCurrency(order.nrr || 0)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">One-time revenue</p>
          </div>
        </div>
      </div>

      <Separator className="my-6" />

      <div className="grid grid-cols-12 gap-6">
        <div className="col-span-12">
          <h3 className="text-lg font-semibold">Service-Level Totals</h3>
          <p className="text-sm text-muted-foreground">Sum of all active service costs</p>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="servicesMrc">Services MRC</Label>
            <Input
              id="servicesMrc"
              value={formatCurrency(servicesMrc)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">Sum of all service MRC values</p>
          </div>
        </div>

        <div className="col-span-12 md:col-span-6">
          <div className="space-y-2">
            <Label htmlFor="servicesNrc">Services NRC</Label>
            <Input
              id="servicesNrc"
              value={formatCurrency(servicesNrc)}
              readOnly
            />
            <p className="text-sm text-muted-foreground">Sum of all service NRC values</p>
          </div>
        </div>
      </div>

      <div className="mt-6 p-4 bg-muted rounded-lg">
        <p className="text-sm text-muted-foreground">
          <strong>Note:</strong> Financial values are calculated automatically from
          service-level costs. Update individual service costs to modify totals.
        </p>
      </div>
    </div>
  );
}
