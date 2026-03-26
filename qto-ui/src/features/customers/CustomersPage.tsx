/**
 * Customers Page
 * Tabbed view for Master Customers and End Customers worklists
 */

import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import MasterCustomersWorklist from './MasterCustomersWorklist';
import EndCustomersWorklist from './EndCustomersWorklist';

export default function CustomersPage() {
  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Customer Management</h1>
      <Tabs defaultValue="master">
        <TabsList>
          <TabsTrigger value="master">Master Customers</TabsTrigger>
          <TabsTrigger value="end">End Customers</TabsTrigger>
        </TabsList>
        <TabsContent value="master">
          <MasterCustomersWorklist />
        </TabsContent>
        <TabsContent value="end">
          <EndCustomersWorklist />
        </TabsContent>
      </Tabs>
    </div>
  );
}
