/**
 * Dashboards Container
 *
 * Main dashboard view with tabs for different analytics
 */

import { useState } from 'react';

import DashboardFinancials from './components/DashboardFinancials';
import DashboardKPI from './components/DashboardKPI';
import DashboardActivations from './components/DashboardActivations';
import DashboardInventory from './components/DashboardInventory';
import DashboardProviders from './components/DashboardProviders';
import DashboardWIP from './components/DashboardWIP';

const tabs = [
  { label: 'Financials', component: DashboardFinancials },
  { label: 'KPI', component: DashboardKPI },
  { label: 'Activations', component: DashboardActivations },
  { label: 'Inventory', component: DashboardInventory },
  { label: 'Providers', component: DashboardProviders },
  { label: 'WIP', component: DashboardWIP },
];

/**
 * Dashboards Component
 */
export default function Dashboards() {
  const [activeTab, setActiveTab] = useState(0);

  const ActiveComponent = tabs[activeTab].component;

  return (
    <div className="p-6">
      <h1 className="mb-6 text-2xl font-semibold text-slate-900">Dashboards &amp; Reports</h1>

      <div className="rounded-2xl border border-slate-200 bg-white">
        <div className="flex gap-0 border-b border-slate-200" role="tablist">
          {tabs.map((tab, index) => (
            <button
              key={tab.label}
              type="button"
              role="tab"
              id={`dashboard-tab-${index}`}
              aria-selected={activeTab === index}
              aria-controls={`dashboard-tabpanel-${index}`}
              className={`px-5 py-3 text-sm font-medium transition-colors ${
                activeTab === index
                  ? 'border-b-2 border-primary text-primary'
                  : 'text-slate-500 hover:text-slate-700'
              }`}
              onClick={() => setActiveTab(index)}
            >
              {tab.label}
            </button>
          ))}
        </div>

        <div className="p-6" role="tabpanel" id={`dashboard-tabpanel-${activeTab}`} aria-labelledby={`dashboard-tab-${activeTab}`}>
          <ActiveComponent />
        </div>
      </div>
    </div>
  );
}
