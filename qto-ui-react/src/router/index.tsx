import { createHashRouter, Navigate } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { LandingPage } from '../pages/LandingPage';
import { ServiceWorklist } from '../features/service-worklist/ServiceWorklist';
import { MsalAuthenticationTemplate } from '@azure/msal-react';
import { InteractionType } from '@azure/msal-browser';

export const router = createHashRouter([
  {
    path: '/',
    element: (
      <MsalAuthenticationTemplate interactionType={InteractionType.Redirect}>
        <MainLayout />
      </MsalAuthenticationTemplate>
    ),
    children: [
      {
        index: true,
        element: <LandingPage />,
      },
      {
        path: 'services',
        element: <ServiceWorklist />,
      },
      // Add more routes here as features are converted
      // { path: 'orders', element: <OrdersPage /> },
      // { path: 'locations', element: <LocationsPage /> },
      // etc.
    ],
  },
  {
    path: '/code',
    element: <Navigate to="/" replace />,
  },
]);

export default router;
