/**
 * Order Contacts Component
 *
 * Displays and manages order contacts (Sales, Technical, Authorization)
 * Mirrors Angular's order-contact-tabs functionality
 */

import { useState } from 'react';
import { useAppSelector } from '@/store/hooks';
import { selectOrder } from '@/store/slices/orderDetailsSelectors';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Mail, Phone, User } from 'lucide-react';
import type { OrderContact } from '@/shared/types/models/order.model';

/**
 * Contact Card Display
 */
function ContactCard({ contact, label }: { contact?: OrderContact; label: string }) {
  if (!contact) {
    return (
      <div className="text-sm text-muted-foreground py-4 text-center">
        No {label.toLowerCase()} contact assigned
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="grid grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label>First Name</Label>
          <div className="flex items-center gap-2">
            <User className="h-4 w-4 text-muted-foreground" />
            <Input value={contact.firstName || ''} readOnly className="bg-muted/50" />
          </div>
        </div>
        <div className="space-y-2">
          <Label>Last Name</Label>
          <Input value={contact.lastName || ''} readOnly className="bg-muted/50" />
        </div>
        <div className="space-y-2">
          <Label>Email</Label>
          <div className="flex items-center gap-2">
            <Mail className="h-4 w-4 text-muted-foreground" />
            <Input value={contact.email || ''} readOnly className="bg-muted/50" />
          </div>
        </div>
        <div className="space-y-2">
          <Label>Phone</Label>
          <div className="flex items-center gap-2">
            <Phone className="h-4 w-4 text-muted-foreground" />
            <Input value={contact.phone || ''} readOnly className="bg-muted/50" />
          </div>
        </div>
      </div>
    </div>
  );
}

/**
 * Order Contacts Component
 */
export default function OrderContacts() {
  const order = useAppSelector(selectOrder);
  const [activeContactTab, setActiveContactTab] = useState('sales');

  if (!order) return null;

  const contacts = order.contacts || [];

  const salesContact = contacts.find((c) => c.type === 'SALES');
  const techContact = contacts.find((c) => c.type === 'TECH');
  const authContact = contacts.find((c) => c.type === 'AUTHORIZATION');

  return (
    <Card>
      <CardHeader>
        <CardTitle className="text-base">Order Contacts</CardTitle>
      </CardHeader>
      <CardContent>
        <Tabs value={activeContactTab} onValueChange={setActiveContactTab}>
          <TabsList className="w-full justify-start">
            <TabsTrigger value="sales">
              Sales
              {salesContact && <span className="ml-1 w-2 h-2 rounded-full bg-green-500 inline-block" />}
            </TabsTrigger>
            <TabsTrigger value="technical">
              Technical
              {techContact && <span className="ml-1 w-2 h-2 rounded-full bg-green-500 inline-block" />}
            </TabsTrigger>
            <TabsTrigger value="authorization">
              Authorization
              {authContact && <span className="ml-1 w-2 h-2 rounded-full bg-green-500 inline-block" />}
            </TabsTrigger>
          </TabsList>

          <div className="pt-4">
            <TabsContent value="sales">
              <ContactCard contact={salesContact} label="Sales" />
            </TabsContent>
            <TabsContent value="technical">
              <ContactCard contact={techContact} label="Technical" />
            </TabsContent>
            <TabsContent value="authorization">
              <ContactCard contact={authContact} label="Authorization" />
            </TabsContent>
          </div>
        </Tabs>
      </CardContent>
    </Card>
  );
}
