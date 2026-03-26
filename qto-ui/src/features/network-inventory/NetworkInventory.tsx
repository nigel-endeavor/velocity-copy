/**
 * Network Inventory Page
 * Tabbed view for Location and Service inventory worklists
 */

import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import LocationInventoryWorklist from './LocationInventoryWorklist';
import ServiceInventoryWorklist from './ServiceInventoryWorklist';

export default function NetworkInventory() {
  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Network Inventory</h1>
      <Tabs defaultValue="locations">
        <TabsList>
          <TabsTrigger value="locations">Locations</TabsTrigger>
          <TabsTrigger value="services">Services</TabsTrigger>
        </TabsList>
        <TabsContent value="locations">
          <LocationInventoryWorklist />
        </TabsContent>
        <TabsContent value="services">
          <ServiceInventoryWorklist />
        </TabsContent>
      </Tabs>
    </div>
  );
}
