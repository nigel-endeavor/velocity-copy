/**
 * Order Model
 *
 * Represents a telecom order with locations and services
 */

import { z } from 'zod';
import { BaseModel } from './base.model';
import { Company } from './company.model';
import { Location } from './location.model';
import { Contact, ContactType, createContact } from './contact.model';

/**
 * Order Contact Interface
 * Extends Contact for order-specific contacts
 */
export interface OrderContact extends Contact {
  orderId?: number;
}

/**
 * Order Interface
 */
export interface Order extends BaseModel {
  // Company
  company: Company;

  // Identification
  clientOrderId: string;
  quoteId?: number;
  inventoryOrderId?: number;

  // People
  provisioner?: string;
  clientProjectManager?: string;
  vertekProjectManager?: string;
  activationEngineer?: string;
  qaManager?: string;
  vertekClient?: string;

  // Financial (readonly in Angular)
  mrc: number; // Monthly Recurring Cost
  nrc: number; // Non-Recurring Cost
  mrr: number; // Monthly Recurring Revenue
  nrr: number; // Non-Recurring Revenue
  icb: number; // Installation Cost Budget
  osp: number; // Outside Plant
  annualRecurringCost: number;

  // Status
  status: string;

  // Relationships
  locations: Location[];
  contacts: OrderContact[];

  // Dates
  createdDate?: Date | string;
}

/**
 * Order List Item — lightweight type backed by v_manage_orders view.
 * Used by /api/orderViews for the orders list page (fast, single SQL query).
 */
export interface OrderListItem {
  id: number;
  clientOrderId: string;
  companyName: string;
  companyId: number;
  status: string;
  vertekClient?: string;
  locationCount: number;
  mrc: number;
  nrc: number;
  createdDate?: string;
  lastUpdateDate?: string;
  quoteId?: string;
}

/**
 * Order Validation Schema
 */
export const orderSchema = z.object({
  id: z.number().optional(),
  version: z.number().optional(),
  clientOrderId: z.string().min(1, 'Order ID is required'),
  company: z.any(), // Company schema reference
  status: z.string().min(1, 'Status is required'),
  locations: z.array(z.any()), // Location schema reference
  contacts: z.array(z.any()), // Contact schema reference
  mrc: z.number().min(0),
  nrc: z.number().min(0),
  mrr: z.number().min(0),
  nrr: z.number().min(0),
  icb: z.number().min(0),
  osp: z.number().min(0),
  annualRecurringCost: z.number().min(0),
});

/**
 * Helper Functions
 */

/**
 * Get location count (excluding cancelled)
 */
export function getOrderLocationCount(order: Order): number {
  return order.locations.filter((l) => l.status !== 'Location Cancelled').length;
}

/**
 * Get service count (excluding cancelled)
 */
export function getOrderServiceCount(order: Order): number {
  let count = 0;
  order.locations.forEach((loc) => {
    count += loc.services?.filter((s) => s.status !== 'Service Cancelled').length || 0;
  });
  return count;
}

/**
 * Get unique service types string
 */
export function getOrderServiceListString(order: Order): string {
  const serviceTypes = new Set<string>();
  order.locations.forEach((loc) => {
    loc.services
      ?.filter((s) => s.status !== 'Service Cancelled')
      .forEach((s) => serviceTypes.add(s.type));
  });
  return Array.from(serviceTypes).join(', ');
}

/**
 * Get contact by type (creates if doesn't exist)
 */
export function getOrderContact(order: Order, type: ContactType): OrderContact {
  const contact = order.contacts.find((c) => c.type === type);
  if (contact) {
    return contact;
  }
  const newContact: OrderContact = {
    ...createContact(order.company?.id || 0, type),
    orderId: order.id,
  };
  order.contacts.push(newContact);
  return newContact;
}

/**
 * Get sales contact
 */
export function getOrderSalesContact(order: Order): OrderContact {
  return getOrderContact(order, ContactType.SALES);
}

/**
 * Get tech contact
 */
export function getOrderTechContact(order: Order): OrderContact {
  return getOrderContact(order, ContactType.TECH);
}

/**
 * Get authorization contact
 */
export function getOrderAuthContact(order: Order): OrderContact {
  return getOrderContact(order, ContactType.AUTH);
}

/**
 * Create new order
 */
export function createOrder(company: Company, partial?: Partial<Order>): Order {
  return {
    company,
    clientOrderId: '',
    status: '',
    locations: [],
    contacts: [],
    mrc: 0,
    nrc: 0,
    mrr: 0,
    nrr: 0,
    icb: 0,
    osp: 0,
    annualRecurringCost: 0,
    ...partial,
  };
}

export default Order;
