/**
 * Customers Page
 * Tabbed view for Master Customers and End Customers worklists
 */

import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { AppPage, PageHeader } from '@/components/layout/PageScaffold';
import MasterCustomersWorklist from './MasterCustomersWorklist';
import EndCustomersWorklist from './EndCustomersWorklist';

export default function CustomersPage() {
  return (
    <AppPage>
      <PageHeader
        eyebrow="Account visibility"
        title="Customer Management"
        description="Switch between parent and end-customer worklists without losing consistent spacing, hierarchy, or table rhythm."
        stats={[
          { label: 'Views', value: '2', detail: 'Master and end customers', tone: 'brand' },
          { label: 'Mode', value: 'Tabbed', detail: 'Shared search and export surfaces', tone: 'warm' },
        ]}
      />
      <Tabs className="min-h-0" defaultValue="master">
        <TabsList className="app-tablist">
          <TabsTrigger className="app-tab-trigger" value="master">Master Customers</TabsTrigger>
          <TabsTrigger className="app-tab-trigger" value="end">End Customers</TabsTrigger>
        </TabsList>
        <TabsContent className="min-h-0" value="master">
          <MasterCustomersWorklist />
        </TabsContent>
        <TabsContent className="min-h-0" value="end">
          <EndCustomersWorklist />
        </TabsContent>
      </Tabs>
    </AppPage>
  );
}
