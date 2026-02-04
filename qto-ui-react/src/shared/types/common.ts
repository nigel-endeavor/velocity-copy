/**
 * Common Types
 *
 * Shared types used across the application
 */

/**
 * Pagination Response
 */
export interface PaginatedResult<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  pageNumber: number;
  pageSize: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

/**
 * Search Criteria Base
 */
export interface BaseSearchCriteria {
  keyword?: string;
  startDate?: string;
  endDate?: string;
  status?: string;
  pageNumber?: number;
  pageSize?: number;
  sortBy?: string;
  sortOrder?: 'asc' | 'desc';
}

/**
 * Sort Configuration
 */
export interface SortConfig {
  field: string;
  order: 'asc' | 'desc';
}

/**
 * API Response Wrapper
 */
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

/**
 * Error Response
 */
export interface ApiError {
  message: string;
  status: number;
  timestamp: string;
  errors?: Record<string, string[]>;
}

/**
 * Select Option (for dropdowns)
 */
export interface SelectOption<T = string> {
  label: string;
  value: T;
  disabled?: boolean;
}

/**
 * Lookup Value (from backend configuration)
 */
export interface LookupValue {
  id: number;
  lookupTypeId: number;
  value: string;
  description?: string;
  active: boolean;
  sortOrder?: number;
}

/**
 * User Info (from authentication)
 */
export interface UserInfo {
  username: string;
  name: string;
  email: string;
  roles: string[];
}

// No default export - use named exports only
