/**
 * Models Index
 *
 * Central export for all model types
 */

// Base
export * from './base.model';

// Core Models
export * from './contact.model';
export * from './company.model';
export * from './service.model';
export * from './location.model';
export * from './order.model';

// Re-export for convenience
export type { BaseModel } from './base.model';
export type { Contact } from './contact.model';
export type { Company } from './company.model';
export type { Service } from './service.model';
export type { Location } from './location.model';
export type { Order, OrderContact } from './order.model';

// Constants (formerly enums)
export { ContactType } from './contact.model';
export { CompanyType } from './company.model';
