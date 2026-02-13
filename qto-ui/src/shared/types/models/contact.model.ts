/**
 * Contact Model
 *
 * Represents a contact person (sales, tech, billing, etc.)
 */

import { z } from 'zod';
import { BaseModel } from './base.model';
import { emailSchema, phoneSchema } from '@/shared/utils/validation';

/**
 * Contact Type
 */
export const ContactType = {
  SALES: 'SALES',
  TECH: 'TECH',
  BILLING: 'BILLING',
  LCON: 'LCON',
  AUTH: 'AUTHORIZATION',
} as const;

export type ContactType = (typeof ContactType)[keyof typeof ContactType];

/**
 * Contact Interface
 */
export interface Contact extends BaseModel {
  companyId: number;
  firstName: string | null;
  lastName: string | null;
  active: boolean;
  role?: string;
  notes?: string;
  type: ContactType;
  phone?: string;
  email?: string;
  lastUpdateBy?: string;
  lastUpdateDate?: Date | string;
}

/**
 * Contact Validation Schema
 */
export const contactSchema = z.object({
  id: z.number().optional(),
  version: z.number().optional(),
  companyId: z.number(),
  firstName: z.string().nullable(),
  lastName: z.string().nullable(),
  active: z.boolean(),
  role: z.string().optional(),
  notes: z.string().optional(),
  type: z.string(),
  phone: phoneSchema.optional(),
  email: emailSchema.optional(),
  lastUpdateBy: z.string().optional(),
  lastUpdateDate: z.union([z.date(), z.string()]).optional(),
});

/**
 * Helper Functions
 */

/**
 * Get full name from contact
 */
export function getContactFullName(contact: Contact): string {
  const first = contact.firstName || '';
  const last = contact.lastName || '';
  if (first && last) {
    return `${first} ${last}`;
  }
  return first || last || '';
}

/**
 * Parse name into first and last
 */
export function parseContactName(name: string): { firstName: string; lastName: string | null } {
  const index = name.indexOf(' ');
  if (index > -1) {
    return {
      firstName: name.slice(0, index),
      lastName: name.slice(index + 1),
    };
  }
  return {
    firstName: name,
    lastName: null,
  };
}

/**
 * Create new contact
 */
export function createContact(
  companyId: number,
  type: ContactType,
  partial?: Partial<Contact>
): Contact {
  return {
    companyId,
    type,
    active: true,
    firstName: null,
    lastName: null,
    ...partial,
  };
}

export default Contact;
