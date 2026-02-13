/**
 * Validation Utilities
 *
 * Zod schemas for form validation.
 * Replaces Angular validators.
 */

import { z } from 'zod';

/**
 * Phone Number Validation
 * Format: XXX-XXX-XXXX or (XXX) XXX-XXXX
 */
export const phoneSchema = z
  .string()
  .regex(
    /^(\d{3}-\d{3}-\d{4}|\(\d{3}\)\s?\d{3}-\d{4})$/,
    'Invalid phone number format. Use XXX-XXX-XXXX or (XXX) XXX-XXXX'
  );

/**
 * Zipcode Validation
 * Format: XXXXX or XXXXX-XXXX
 */
export const zipcodeSchema = z
  .string()
  .regex(/^\d{5}(-\d{4})?$/, 'Invalid zipcode format. Use XXXXX or XXXXX-XXXX');

/**
 * Email Validation
 */
export const emailSchema = z.string().email('Invalid email address');

/**
 * IP Address Validation
 * Supports IPv4
 */
export const ipAddressSchema = z
  .string()
  .regex(
    /^(\d{1,3}\.){3}\d{1,3}$/,
    'Invalid IP address format'
  )
  .refine(
    (ip) => {
      const parts = ip.split('.');
      return parts.every((part) => parseInt(part, 10) <= 255);
    },
    'Invalid IP address - octets must be between 0-255'
  );

/**
 * URL Validation
 */
export const urlSchema = z.string().url('Invalid URL format');

/**
 * Required String
 */
export const requiredStringSchema = z
  .string()
  .min(1, 'This field is required')
  .trim();

/**
 * Required Number
 */
export const requiredNumberSchema = z
  .number()
  .min(0, 'Must be a positive number');

/**
 * Date Validation
 */
export const dateSchema = z.date();

/**
 * Date String Validation (ISO format)
 */
export const dateStringSchema = z
  .string()
  .regex(/^\d{4}-\d{2}-\d{2}$/, 'Invalid date format. Use YYYY-MM-DD');

/**
 * Currency Validation
 */
export const currencySchema = z
  .number()
  .min(0, 'Amount must be positive')
  .multipleOf(0.01, 'Amount must have at most 2 decimal places');

/**
 * Circuit ID Validation (from Angular)
 */
export const circuitIdSchema = z
  .string()
  .min(1, 'Circuit ID is required')
  .max(50, 'Circuit ID must be less than 50 characters');

/**
 * Order ID Validation
 */
export const orderIdSchema = z
  .string()
  .min(1, 'Order ID is required')
  .max(100, 'Order ID must be less than 100 characters');

/**
 * Address Schema
 */
export const addressSchema = z.object({
  street: requiredStringSchema,
  city: requiredStringSchema,
  state: z.string().length(2, 'State must be 2 characters'),
  zipcode: zipcodeSchema,
  country: z.string().optional().default('US'),
});

/**
 * Contact Schema
 */
export const contactSchema = z.object({
  firstName: requiredStringSchema,
  lastName: requiredStringSchema,
  email: emailSchema,
  phone: phoneSchema.optional(),
});

/**
 * Service Schema (basic)
 */
export const serviceSchema = z.object({
  serviceType: requiredStringSchema,
  bandwidth: requiredStringSchema,
  circuitId: circuitIdSchema.optional(),
  ipAddress: ipAddressSchema.optional(),
  status: requiredStringSchema,
});

/**
 * Order Schema (basic)
 */
export const orderSchema = z.object({
  clientOrderId: orderIdSchema,
  customerId: z.number().min(1, 'Customer is required'),
  orderType: requiredStringSchema,
  status: requiredStringSchema,
  submittedDate: dateStringSchema.optional(),
  dueDate: dateStringSchema.optional(),
});

/**
 * Search Criteria Schema
 */
export const searchCriteriaSchema = z.object({
  keyword: z.string().optional(),
  startDate: dateStringSchema.optional(),
  endDate: dateStringSchema.optional(),
  status: z.string().optional(),
  pageNumber: z.number().min(0).default(0),
  pageSize: z.number().min(1).max(1000).default(25),
  sortBy: z.string().optional(),
  sortOrder: z.enum(['asc', 'desc']).optional(),
});

/**
 * Pagination Schema
 */
export const paginationSchema = z.object({
  pageNumber: z.number().min(0),
  pageSize: z.number().min(1).max(1000),
  totalElements: z.number().min(0),
  totalPages: z.number().min(0),
});

/**
 * Helper: Create optional version of schema
 */
export const optional = <T extends z.ZodTypeAny>(schema: T) =>
  schema.optional();

/**
 * Helper: Create nullable version of schema
 */
export const nullable = <T extends z.ZodTypeAny>(schema: T) =>
  schema.nullable();

export default {
  phoneSchema,
  zipcodeSchema,
  emailSchema,
  ipAddressSchema,
  urlSchema,
  requiredStringSchema,
  requiredNumberSchema,
  dateSchema,
  dateStringSchema,
  currencySchema,
  circuitIdSchema,
  orderIdSchema,
  addressSchema,
  contactSchema,
  serviceSchema,
  orderSchema,
  searchCriteriaSchema,
  paginationSchema,
};
