/**
 * Order Billing Component
 *
 * Billing information tab for order
 */

import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import type { Order } from '@/shared/types/models';
import { useAppSelector } from '@/store/hooks';
import { selectOrder } from '@/store/slices/orderDetailsSelectors';

export default function OrderBilling() {
  const order = useAppSelector(selectOrder);

  if (!order) return null;

  return (
    <div className="grid grid-cols-12 gap-6">
      <div className="col-span-12">
        <h3 className="text-lg font-semibold">Company Information</h3>
      </div>

      <div className="col-span-12 md:col-span-6">
        <div className="space-y-2">
          <Label htmlFor="companyName">Company Name</Label>
          <Input
            id="companyName"
            value={order.company?.name || ''}
            readOnly
          />
        </div>
      </div>

      <div className="col-span-12 md:col-span-6">
        <div className="space-y-2">
          <Label htmlFor="companyType">Company Type</Label>
          <Input
            id="companyType"
            value={order.company?.type || ''}
            readOnly
          />
        </div>
      </div>

      {order.company?.address1 && (
        <>
          <div className="col-span-12">
            <h3 className="text-lg font-semibold mt-4">Billing Address</h3>
          </div>

          <div className="col-span-12">
            <div className="space-y-2">
              <Label htmlFor="address1">Address Line 1</Label>
              <Input
                id="address1"
                value={order.company.address1}
                readOnly
              />
            </div>
          </div>

          {order.company.address2 && (
            <div className="col-span-12">
              <div className="space-y-2">
                <Label htmlFor="address2">Address Line 2</Label>
                <Input
                  id="address2"
                  value={order.company.address2}
                  readOnly
                />
              </div>
            </div>
          )}

          <div className="col-span-12 md:col-span-6">
            <div className="space-y-2">
              <Label htmlFor="city">City</Label>
              <Input
                id="city"
                value={order.company.city || ''}
                readOnly
              />
            </div>
          </div>

          <div className="col-span-12 md:col-span-3">
            <div className="space-y-2">
              <Label htmlFor="state">State</Label>
              <Input
                id="state"
                value={order.company.state || ''}
                readOnly
              />
            </div>
          </div>

          <div className="col-span-12 md:col-span-3">
            <div className="space-y-2">
              <Label htmlFor="postalCode">Postal Code</Label>
              <Input
                id="postalCode"
                value={order.company.postalCode || ''}
                readOnly
              />
            </div>
          </div>

          {order.company.country && (
            <div className="col-span-12 md:col-span-6">
              <div className="space-y-2">
                <Label htmlFor="country">Country</Label>
                <Input
                  id="country"
                  value={order.company.country}
                  readOnly
                />
              </div>
            </div>
          )}
        </>
      )}

      <div className="col-span-12">
        <p className="text-sm text-muted-foreground mt-4">
          To update billing information, navigate to the company record.
        </p>
      </div>
    </div>
  );
}
