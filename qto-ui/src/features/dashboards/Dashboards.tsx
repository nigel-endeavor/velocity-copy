/**
 * Dashboards Container
 *
 * Main dashboard view with tabs for different analytics
 */

import { useState } from 'react';
import {
  Box,
  Paper,
  Tabs,
  Tab,
  Typography,
} from '@mui/material';

import DashboardFinancials from './components/DashboardFinancials';
import DashboardKPI from './components/DashboardKPI';
import DashboardActivations from './components/DashboardActivations';
import DashboardInventory from './components/DashboardInventory';
import DashboardProviders from './components/DashboardProviders';
import DashboardWIP from './components/DashboardWIP';

/**
 * Tab Panel Component
 */
interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel({ children, value, index }: TabPanelProps) {
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`dashboard-tabpanel-${index}`}
      aria-labelledby={`dashboard-tab-${index}`}
    >
      {value === index && <Box sx={{ py: 3 }}>{children}</Box>}
    </div>
  );
}

/**
 * Dashboards Component
 */
export default function Dashboards() {
  const [activeTab, setActiveTab] = useState(0);

  const handleTabChange = (_event: React.SyntheticEvent, newValue: number) => {
    setActiveTab(newValue);
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Dashboards & Reports
      </Typography>

      <Paper>
        <Tabs
          value={activeTab}
          onChange={handleTabChange}
          aria-label="Dashboard tabs"
          sx={{ borderBottom: 1, borderColor: 'divider' }}
        >
          <Tab label="Financials" id="dashboard-tab-0" />
          <Tab label="KPI" id="dashboard-tab-1" />
          <Tab label="Activations" id="dashboard-tab-2" />
          <Tab label="Inventory" id="dashboard-tab-3" />
          <Tab label="Providers" id="dashboard-tab-4" />
          <Tab label="WIP" id="dashboard-tab-5" />
        </Tabs>

        <Box sx={{ p: 3 }}>
          <TabPanel value={activeTab} index={0}>
            <DashboardFinancials />
          </TabPanel>
          <TabPanel value={activeTab} index={1}>
            <DashboardKPI />
          </TabPanel>
          <TabPanel value={activeTab} index={2}>
            <DashboardActivations />
          </TabPanel>
          <TabPanel value={activeTab} index={3}>
            <DashboardInventory />
          </TabPanel>
          <TabPanel value={activeTab} index={4}>
            <DashboardProviders />
          </TabPanel>
          <TabPanel value={activeTab} index={5}>
            <DashboardWIP />
          </TabPanel>
        </Box>
      </Paper>
    </Box>
  );
}
