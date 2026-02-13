/**
 * Company Model
 *
 * Represents a master customer or end customer company
 */

import { z } from 'zod';
import { BaseModel } from './base.model';
import { Contact, ContactType, contactSchema, createContact } from './contact.model';
import { addressSchema } from '@/shared/utils/validation';

/**
 * Company Type
 */
export const CompanyType = {
  MASTER_CUSTOMER: 'MASTER_CUSTOMER',
  END_CUSTOMER: 'END_CUSTOMER',
} as const;

export type CompanyType = (typeof CompanyType)[keyof typeof CompanyType];

/**
 * Company Interface
 */
export interface Company extends BaseModel {
  name: string;
  type: CompanyType;
  active: boolean;
  duplicatedMasterCustomerDetails?: boolean;
  uuid?: string;
  clientId?: string;
  billingContact?: Contact;
  parentCompany?: Company;
  accountNotes?: string;
  accountManager?: number;
  provisioner?: number;
  i90ProjectManager?: number;
  address1?: string;
  address2?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  country?: string;
  taskGroupId?: number;
  automateEmailAddresses?: string;
  automatedEmailsEnabled?: boolean;
}

/**
 * Company Validation Schema
 */
export const companySchema: z.ZodType<Company> = z.object({
  id: z.number().optional(),
  version: z.number().optional(),
  name: z.string().min(1, 'Company name is required'),
  type: z.string(),
  active: z.boolean(),
  duplicatedMasterCustomerDetails: z.boolean().optional(),
  uuid: z.string().optional(),
  clientId: z.string().optional(),
  billingContact: contactSchema.optional(),
  parentCompany: z.lazy(() => companySchema).optional(),
  accountNotes: z.string().optional(),
  accountManager: z.number().optional(),
  provisioner: z.number().optional(),
  i90ProjectManager: z.number().optional(),
  address1: z.string().optional(),
  address2: z.string().optional(),
  city: z.string().optional(),
  state: z.string().length(2).optional(),
  postalCode: z.string().optional(),
  country: z.string().optional(),
  taskGroupId: z.number().optional(),
  automateEmailAddresses: z.string().optional(),
  automatedEmailsEnabled: z.boolean().optional(),
}) as z.ZodType<Company>;

/**
 * Helper Functions
 */

/**
 * Get or create billing contact for company
 */
export function getBillingContact(company: Company): Contact {
  if (company.billingContact) {
    return company.billingContact;
  }
  return createContact(company.id || 0, ContactType.BILLING);
}

/**
 * Check if company is master customer
 */
export function isMasterCustomer(company: Company): boolean {
  return company.type === CompanyType.MASTER_CUSTOMER;
}

/**
 * Check if company is end customer
 */
export function isEndCustomer(company: Company): boolean {
  return company.type === CompanyType.END_CUSTOMER;
}

/**
 * Format company address
 */
export function formatCompanyAddress(company: Company): string {
  const parts = [
    company.address1,
    company.address2,
    company.city && company.state ? `${company.city}, ${company.state}` : company.city || company.state,
    company.postalCode,
    company.country,
  ].filter(Boolean);

  return parts.join('\n');
}

export default Company;
