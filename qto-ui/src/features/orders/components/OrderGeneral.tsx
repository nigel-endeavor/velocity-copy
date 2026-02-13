/**
 * Order General Component
 *
 * General information tab for order details
 */

import { Controller, useFormContext } from 'react-hook-form';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import type { Order } from '@/shared/types/models';

export default function OrderGeneral() {
  const { control } = useFormContext<Order>();

  return (
    <div className="grid grid-cols-12 gap-6">
      <div className="col-span-12 md:col-span-6">
        <Controller
          name="clientOrderId"
          control={control}
          render={({ field, fieldState: { error } }) => (
            <div className="space-y-2">
              <Label htmlFor="clientOrderId" className="required">Client Order ID</Label>
              <Input
                {...field}
                id="clientOrderId"
                className={error ? 'border-red-500' : ''}
              />
              {error && <p className="text-sm text-red-600">{error.message}</p>}
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="status"
          control={control}
          render={({ field, fieldState: { error } }) => (
            <div className="space-y-2">
              <Label htmlFor="status" className="required">Status</Label>
              <Select value={field.value} onValueChange={field.onChange}>
                <SelectTrigger id="status" className={error ? 'border-red-500' : ''}>
                  <SelectValue placeholder="Select status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="New Order">New Order</SelectItem>
                  <SelectItem value="In Progress">In Progress</SelectItem>
                  <SelectItem value="Pending">Pending</SelectItem>
                  <SelectItem value="Completed">Completed</SelectItem>
                  <SelectItem value="Cancelled">Cancelled</SelectItem>
                </SelectContent>
              </Select>
              {error && <p className="text-sm text-red-600">{error.message}</p>}
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="provisioner"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="provisioner">Provisioner</Label>
              <Input {...field} id="provisioner" />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="clientProjectManager"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="clientProjectManager">Client Project Manager</Label>
              <Input {...field} id="clientProjectManager" />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="vertekProjectManager"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="vertekProjectManager">Vertek Project Manager</Label>
              <Input {...field} id="vertekProjectManager" />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="activationEngineer"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="activationEngineer">Activation Engineer</Label>
              <Input {...field} id="activationEngineer" />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="qaManager"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="qaManager">QA Manager</Label>
              <Input {...field} id="qaManager" />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="vertekClient"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="vertekClient">Vertek Client</Label>
              <Input {...field} id="vertekClient" />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="quoteId"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="quoteId">Quote ID</Label>
              <Input
                {...field}
                id="quoteId"
                type="number"
                value={field.value || ''}
                onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
              />
            </div>
          )}
        />
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="inventoryOrderId"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="inventoryOrderId">Inventory Order ID</Label>
              <Input
                {...field}
                id="inventoryOrderId"
                type="number"
                value={field.value || ''}
                onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
              />
            </div>
          )}
        />
      </div>
    </div>
  );
}
