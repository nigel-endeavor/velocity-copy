/**
 * Dashboard Page
 *
 * Main dashboard with 6 tabs matching the original Angular application:
 * WIP, KPIs, Providers, Financials, Activations, Inventory
 */

import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/ui/tabs';
import WipTab from './tabs/WipTab';
import KpiTab from './tabs/KpiTab';
import ProvidersTab from './tabs/ProvidersTab';
import FinancialsTab from './tabs/FinancialsTab';
import ActivationsTab from './tabs/ActivationsTab';
import InventoryTab from './tabs/InventoryTab';

export default function DashboardPage() {
  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>
      <Tabs defaultValue="wip" className="w-full">
        <TabsList className="grid w-full grid-cols-6">
          <TabsTrigger value="wip">WIP</TabsTrigger>
          <TabsTrigger value="kpi">KPIs</TabsTrigger>
          <TabsTrigger value="providers">Providers</TabsTrigger>
          <TabsTrigger value="financials">Financials</TabsTrigger>
          <TabsTrigger value="activations">Activations</TabsTrigger>
          <TabsTrigger value="inventory">Inventory</TabsTrigger>
        </TabsList>

        <TabsContent value="wip">
          <WipTab />
        </TabsContent>
        <TabsContent value="kpi">
          <KpiTab />
        </TabsContent>
        <TabsContent value="providers">
          <ProvidersTab />
        </TabsContent>
        <TabsContent value="financials">
          <FinancialsTab />
        </TabsContent>
        <TabsContent value="activations">
          <ActivationsTab />
        </TabsContent>
        <TabsContent value="inventory">
          <InventoryTab />
        </TabsContent>
      </Tabs>
    </div>
  );
}
