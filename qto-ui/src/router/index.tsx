import { createHashRouter, Navigate } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { LandingPage } from '../pages/LandingPage';
import { ServiceWorklist } from '../features/service-worklist/ServiceWorklist';
import OrderDetails from '../features/orders/OrderDetails';
import OrderList from '../features/orders/OrderList';
import NewOrderWizard from '../features/orders/NewOrderWizard';
import QuotesPage from '../features/quotes/QuotesPage';
import NetworkInventory from '../features/network-inventory/NetworkInventory';
import CustomersPage from '../features/customers/CustomersPage';
import CustomerDetails from '../features/customers/CustomerDetails';
import InvoicingPage from '../features/invoicing/InvoicingPage';
import InvoiceDetail from '../features/invoicing/InvoiceDetail';
import ExpensesPage from '../features/expenses/ExpensesPage';
import DashboardPage from '../features/dashboard/DashboardPage';
import ActivationsWorklist from '../features/activations-worklist/ActivationsWorklist';
import DisconnectsWorklist from '../features/disconnects-worklist/DisconnectsWorklist';
import DisputesWorklist from '../features/disputes-worklist/DisputesWorklist';
import LocationsWorklist from '../features/locations-worklist/LocationsWorklist';
import LookupTypes from '../features/configuration/LookupTypes';
import LookupValues from '../features/configuration/LookupValues';
// MSAL disabled for local development

export const router = createHashRouter([
  {
    path: '/',
    element: <MainLayout />,
    children: [
      {
        index: true,
        element: <LandingPage />,
      },
      {
        path: 'services',
        element: <ServiceWorklist />,
      },
      {
        path: 'activations',
        element: <ActivationsWorklist />,
      },
      {
        path: 'disconnects',
        element: <DisconnectsWorklist />,
      },
      {
        path: 'disputes',
        element: <DisputesWorklist />,
      },
      {
        path: 'locations',
        element: <LocationsWorklist />,
      },
      {
        path: 'orders',
        element: <OrderList />,
      },
      {
        path: 'orders/new',
        element: <NewOrderWizard />,
      },
      {
        path: 'orders/:orderId',
        element: <OrderDetails />,
      },
      {
        path: 'quotes',
        element: <QuotesPage />,
      },
      {
        path: 'inventory',
        element: <NetworkInventory />,
      },
      {
        path: 'customers',
        element: <CustomersPage />,
      },
      {
        path: 'customers/:customerId',
        element: <CustomerDetails />,
      },
      {
        path: 'invoicing',
        element: <InvoicingPage />,
      },
      {
        path: 'invoicing/:invoiceId',
        element: <InvoiceDetail />,
      },
      {
        path: 'expenses',
        element: <ExpensesPage />,
      },
      {
        path: 'configuration',
        element: <LookupTypes />,
      },
      {
        path: 'configuration/:typeId',
        element: <LookupValues />,
      },
      {
        path: 'dashboard',
        element: <DashboardPage />,
      },
    ],
  },
  {
    path: '/code',
    element: <Navigate to="/" replace />,
  },
]);

export default router;
