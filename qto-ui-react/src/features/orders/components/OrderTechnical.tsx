/**
 * Order Technical Component
 *
 * Technical details tab for order
 */

import { Controller, useFormContext } from 'react-hook-form';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import type { Order } from '@/shared/types/models';

export default function OrderTechnical() {
  const { control } = useFormContext<Order>();

  return (
    <div className="grid grid-cols-12 gap-6">
      <div className="col-span-12">
        <p className="text-sm text-muted-foreground">
          Technical information is managed at the service level.
          Navigate to individual services in the Locations & Services section below.
        </p>
      </div>

      <div className="col-span-12 md:col-span-6">
        <Controller
          name="provisioner"
          control={control}
          render={({ field }) => (
            <div className="space-y-2">
              <Label htmlFor="provisioner">Primary Provisioner</Label>
              <Input {...field} id="provisioner" />
              <p className="text-sm text-muted-foreground">Responsible for technical provisioning</p>
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
              <p className="text-sm text-muted-foreground">Responsible for service activation</p>
            </div>
          )}
        />
      </div>
    </div>
  );
}
