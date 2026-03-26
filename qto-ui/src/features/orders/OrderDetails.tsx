/**
 * Order Details Component
 *
 * Main container for order management with nested locations and services
 * Features:
 * - Tabbed interface (General, Technical, Billing, Financial)
 * - React Hook Form integration
 * - Redux state management
 * - Permission-based editing
 * - Real-time updates via WebSocket
 * - Change detection and validation
 */

import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useForm, FormProvider } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Save, X, RefreshCw } from 'lucide-react';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Separator } from '@/components/ui/separator';

import { useAppDispatch, useAppSelector } from '@/store/hooks';
import {
  selectOrder,
  selectActiveTab,
  selectIsDirty,
  selectCanSave,
  selectCanReset,
  selectIsLoading,
  selectError,
  selectLocationCount,
  selectServiceCount,
} from '@/store/slices/orderDetailsSelectors';
import {
  setActiveTab,
  resetOrder,
  clearOrder,
} from '@/store/slices/orderDetailsSlice';
import {
  useGetOrderQuery,
  useSaveOrderMutation,
} from '@/services/api/ordersApi';
import { usePermissions } from '@/shared/hooks/usePermissions';
import { orderSchema } from '@/shared/types/models/order.model';
import type { Order } from '@/shared/types/models';

import OrderGeneral from './components/OrderGeneral';
import OrderTechnical from './components/OrderTechnical';
import OrderBilling from './components/OrderBilling';
import OrderFinancial from './components/OrderFinancial';
import OrderSummary from './components/OrderSummary';
import OrderContacts from './components/OrderContacts';
import LocationList from './components/LocationList';


/**
 * Order Details Component
 */
export default function OrderDetails() {
  const { orderId } = useParams<{ orderId: string }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  // Permissions
  const { canWrite } = usePermissions();
  const canEdit = canWrite('order-write');

  // Redux state
  const order = useAppSelector(selectOrder);
  const activeTab = useAppSelector(selectActiveTab);
  const isDirty = useAppSelector(selectIsDirty);
  const canSave = useAppSelector(selectCanSave);
  const canReset = useAppSelector(selectCanReset);
  const isLoading = useAppSelector(selectIsLoading);
  const error = useAppSelector(selectError);
  const locationCount = useAppSelector(selectLocationCount);
  const serviceCount = useAppSelector(selectServiceCount);

  // RTK Query
  const { isLoading: isFetching } = useGetOrderQuery(Number(orderId), {
    skip: !orderId,
  });
  const [saveOrder, { isLoading: isSaving }] = useSaveOrderMutation();

  // Local state
  const [saveError, setSaveError] = useState<string | null>(null);
  const [saveSuccess, setSaveSuccess] = useState(false);

  // React Hook Form
  const methods = useForm<Order>({
    resolver: zodResolver(orderSchema),
    mode: 'onChange',
    defaultValues: order || undefined,
  });

  const { handleSubmit, reset } = methods;

  // Sync form with Redux state
  useEffect(() => {
    if (order) {
      reset(order);
    }
  }, [order, reset]);

  // Cleanup on unmount
  useEffect(() => {
    return () => {
      dispatch(clearOrder());
    };
  }, [dispatch]);

  // Tab change handler
  const handleTabChange = (value: string) => {
    dispatch(setActiveTab(value as 'general' | 'technical' | 'billing' | 'financial'));
  };

  // Save handler
  const onSubmit = async (data: Order) => {
    try {
      setSaveError(null);
      setSaveSuccess(false);

      await saveOrder(data).unwrap();

      setSaveSuccess(true);
      setTimeout(() => setSaveSuccess(false), 3000);
    } catch (err) {
      setSaveError(err instanceof Error ? err.message : 'Failed to save order');
    }
  };

  // Reset handler
  const handleReset = () => {
    dispatch(resetOrder());
    setSaveError(null);
    setSaveSuccess(false);
  };

  // Cancel handler
  const handleCancel = () => {
    if (isDirty) {
      const confirmed = window.confirm(
        'You have unsaved changes. Are you sure you want to leave?'
      );
      if (!confirmed) return;
    }
    navigate('/orders');
  };

  // Loading state
  if (isFetching || isLoading) {
    return (
      <div className="flex justify-center items-center min-h-[400px]">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
      </div>
    );
  }

  // Error state
  if (error) {
    return (
      <div className="p-6">
        <div className="p-4 mb-4 text-sm text-red-800 bg-red-50 rounded-lg border border-red-200">
          {error}
        </div>
        <Button onClick={() => navigate('/orders')}>
          Back to Orders
        </Button>
      </div>
    );
  }

  // No order state
  if (!order) {
    return (
      <div className="p-6">
        <div className="p-4 mb-4 text-sm text-orange-800 bg-orange-50 rounded-lg border border-orange-200">
          Order not found
        </div>
        <Button onClick={() => navigate('/orders')}>
          Back to Orders
        </Button>
      </div>
    );
  }

  return (
    <FormProvider {...methods}>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Header */}
        <Card className="mb-4">
          <CardContent className="p-6">
            <div className="flex justify-between items-center">
              <div>
                <h1 className="text-2xl font-bold">
                  Order: {order.clientOrderId}
                </h1>
                <p className="text-sm text-muted-foreground">
                  {order.company?.name} • {locationCount} locations • {serviceCount} services
                </p>
              </div>

              <div className="flex gap-2">
                {canEdit && (
                  <>
                    <Button
                      variant="outline"
                      onClick={handleReset}
                      disabled={!canReset || isSaving}
                    >
                      <RefreshCw className="mr-2 h-4 w-4" />
                      Reset
                    </Button>
                    <Button
                      type="submit"
                      disabled={!canSave || isSaving}
                    >
                      <Save className="mr-2 h-4 w-4" />
                      {isSaving ? 'Saving...' : 'Save Changes'}
                    </Button>
                  </>
                )}
                <Button
                  variant="outline"
                  onClick={handleCancel}
                  disabled={isSaving}
                >
                  <X className="mr-2 h-4 w-4" />
                  {isDirty ? 'Cancel' : 'Close'}
                </Button>
              </div>
            </div>

            {/* Status Messages */}
            {saveSuccess && (
              <div className="p-4 mt-4 text-sm text-green-800 bg-green-50 rounded-lg border border-green-200">
                Order saved successfully!
              </div>
            )}
            {saveError && (
              <div className="p-4 mt-4 text-sm text-red-800 bg-red-50 rounded-lg border border-red-200">
                {saveError}
              </div>
            )}
            {isDirty && !saveSuccess && (
              <div className="p-4 mt-4 text-sm text-blue-800 bg-blue-50 rounded-lg border border-blue-200">
                You have unsaved changes
              </div>
            )}
          </CardContent>
        </Card>

        {/* Tabs */}
        <Card className="mb-4">
          <CardContent className="p-0">
            <Tabs value={activeTab} onValueChange={handleTabChange}>
              <TabsList className="w-full justify-start rounded-none border-b">
                <TabsTrigger value="general">General</TabsTrigger>
                <TabsTrigger value="technical">Technical</TabsTrigger>
                <TabsTrigger value="billing">Billing</TabsTrigger>
                <TabsTrigger value="financial">Financial</TabsTrigger>
              </TabsList>

              {/* Tab Panels */}
              <div className="p-6">
                <TabsContent value="general">
                  <OrderGeneral />
                </TabsContent>
                <TabsContent value="technical">
                  <OrderTechnical />
                </TabsContent>
                <TabsContent value="billing">
                  <OrderBilling />
                </TabsContent>
                <TabsContent value="financial">
                  <OrderFinancial />
                </TabsContent>
              </div>
            </Tabs>
          </CardContent>
        </Card>

        {/* Summary & Contacts */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-4 mb-4">
          <OrderSummary />
          <OrderContacts />
        </div>

        {/* Locations and Services */}
        <Card>
          <CardContent className="p-6">
            <h2 className="text-lg font-semibold mb-4">
              Locations & Services
            </h2>
            <Separator className="mb-4" />
            <LocationList />
          </CardContent>
        </Card>
      </form>
    </FormProvider>
  );
}
