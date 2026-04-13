/**
 * Service Worklist Types
 * Type definitions for service worklist feature
 */

export interface ServiceSearchCriteria {
  companyName?: string;
  serviceType?: string;
  status?: string;
  projectManager?: string;
  fromDate?: string;
  toDate?: string;
  page?: number;
  pageSize?: number;
  sortBy?: string;
  sortOrder?: 'asc' | 'desc';
}

export interface ServiceWorklistState {
  loading: boolean;
  error: string | null;
  searchCriteria: ServiceSearchCriteria;
  selectedServices: Set<number>;
  currentPage: number;
  pageSize: number;
}

export interface Service {
  id: number;
  serviceId: string;
  customerName: string;
  locationName: string;
  serviceType: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';
  bandwidth: string;
  orderDate: string;
  dueDate: string;
  assignedTo: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
  notes: string;
}
