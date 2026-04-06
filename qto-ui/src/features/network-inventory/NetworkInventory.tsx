/**
 * Network Inventory Page
 * Tabbed view for Location and Service inventory worklists
 */

import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { AppPage, PageHeader } from '@/components/layout/PageScaffold';
import LocationInventoryWorklist from './LocationInventoryWorklist';
import ServiceInventoryWorklist from './ServiceInventoryWorklist';

export default function NetworkInventory() {
  return (
    <AppPage>
      <PageHeader
        eyebrow="Inventory intelligence"
        title="Network Inventory"
        description="Review site and service inventory through the same balanced shell used in delivery, finance, and customer operations."
        stats={[
          { label: 'Views', value: '2', detail: 'Location and service inventory', tone: 'brand' },
          { label: 'Coverage', value: 'Full', detail: 'Financials, status, disputes, and contracts', tone: 'warm' },
        ]}
      />
      <Tabs className="min-h-0" defaultValue="locations">
        <TabsList className="app-tablist">
          <TabsTrigger className="app-tab-trigger" value="locations">Locations</TabsTrigger>
          <TabsTrigger className="app-tab-trigger" value="services">Services</TabsTrigger>
        </TabsList>
        <TabsContent className="min-h-0" value="locations">
          <LocationInventoryWorklist />
        </TabsContent>
        <TabsContent className="min-h-0" value="services">
          <ServiceInventoryWorklist />
        </TabsContent>
      </Tabs>
    </AppPage>
  );
}
