import { createHashRouter, Navigate } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { LandingPage } from '../pages/LandingPage';
import { LoginPage } from '../pages/LoginPage';
import { ServiceWorklist } from '../features/service-worklist/ServiceWorklist';
import { AuthGuard } from '../shared/guards/AuthGuard';

export const router = createHashRouter([
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/',
    element: (
      <AuthGuard>
        <MainLayout />
      </AuthGuard>
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
    ],
  },
  {
    path: '/code',
    element: <Navigate to="/" replace />,
  },
]);

export default router;
