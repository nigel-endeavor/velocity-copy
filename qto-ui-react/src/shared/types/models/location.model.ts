/**
 * Location Model
 *
 * Represents a physical location with services
 */

import { z } from 'zod';
import { BaseModel } from './base.model';
import { Service } from './service.model';
import { Contact, ContactType, createContact } from './contact.model';

/**
 * Location Interface
 */
export interface Location extends BaseModel {
  // Relationships
  orderId: number;
  parentLocationId?: number;

  // Identification
  clientLocationId?: string;
  clientLocationInfo?: string;
  clientLocationType?: string;
  quoteLocationId?: string;
  name?: string;

  // Status
  status: string;
  buildingType?: string;
  timezone?: string;
  phoneNumber?: string;

  // Financial (readonly in Angular)
  mrc: number;
  nrc: number;
  mrr: number;
  nrr: number;
  icb: number;
  osp: number;
  annualRecurringCost: number;

  // Inventory Financial
  inventoryMrc?: number;
  inventoryNrc?: number;
  inventoryMrr?: number;
  inventoryNrr?: number;
  inventoryIcb?: number;
  inventoryOsp?: number;
  inventoryAnnualRecurringCost?: number;

  // Progress
  progressPercentage?: number;

  // Commission
  commissionableMrc?: number;
  commissionableNrc?: number;
  commissionableArc?: number;
  grossProfitMrcOverride?: number;
  grossProfitOverride?: number;
  repGrossProfitProduction?: number;
  totalContractValue?: number;
  upliftMrc?: number;
  subaccountMrc?: number;

  // Services
  services: Service[];
  inventoryServices?: Service[];

  // Requirements
  requirementTemplateId?: number;

  // Contact
  lcon?: Contact; // Location Contact

  // Level of Effort
  levelOfEffort?: string;

  // Flags
  active?: boolean;
  isDisconnect?: boolean;
  currentInventory?: boolean;

  // Metadata
  recordSource?: string;
  description?: string;
  inventoryLocationId?: number;

  // Address
  address1?: string;
  address2?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  country?: string;

  // UI State (not from backend)
  displayServices?: boolean;
}

/**
 * Location Validation Schema
 */
export const locationSchema = z.object({
  id: z.number().optional(),
  version: z.number().optional(),
  orderId: z.number(),
  status: z.string().min(1, 'Status is required'),
  services: z.array(z.any()), // Service schema reference
  mrc: z.number().min(0),
  nrc: z.number().min(0),
  mrr: z.number().min(0),
  nrr: z.number().min(0),
  icb: z.number().min(0),
  osp: z.number().min(0),
  annualRecurringCost: z.number().min(0),
  // Additional fields optional
});

/**
 * Helper Functions
 */

/**
 * Format address string
 */
export function formatLocationAddress(location: Location): string {
  const parts = [
    location.address1,
    location.address2,
    location.city && location.state
      ? `${location.city}, ${location.state}`
      : location.city || location.state,
    location.postalCode,
    location.country,
  ].filter(Boolean);

  return parts.join('\n');
}

/**
 * Get location display text
 */
export function getLocationDisplayText(location: Location): string {
  if (!location.id) {
    return 'New Location';
  }
  let displayText = location.clientLocationId || '';
  if (location.address1) {
    displayText += '\n' + formatLocationAddress(location);
  }
  return displayText;
}

/**
 * Get inventory service list string
 */
export function getInventoryServiceListString(location: Location): string {
  if (!location.inventoryServices) return '';

  const services = new Set<string>();
  location.inventoryServices
    .filter((s) => s.status !== 'Disconnect Complete')
    .forEach((s) => services.add(s.type));

  return Array.from(services).join(', ');
}

/**
 * Get or create location contact
 */
export function getLocationContact(location: Location): Contact {
  if (location.lcon) {
    return location.lcon;
  }
  return createContact(location.id || 0, ContactType.LCON);
}

/**
 * Create new location
 */
export function createLocation(orderId: number, partial?: Partial<Location>): Location {
  return {
    orderId,
    status: '',
    services: [],
    mrc: 0,
    nrc: 0,
    mrr: 0,
    nrr: 0,
    icb: 0,
    osp: 0,
    annualRecurringCost: 0,
    displayServices: false,
    ...partial,
  };
}

export default Location;
