/**
 * Service Worklist Types
 * Type definitions for service worklist feature
 */

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

export interface ServiceSearchCriteria {
  customerName?: string;
  serviceType?: string;
  status?: string;
  priority?: string;
  assignedTo?: string;
  fromDate?: string;
  toDate?: string;
  page?: number;
  pageSize?: number;
  sortBy?: string;
  sortOrder?: 'asc' | 'desc';
}

export interface ServiceWorklistState {
  services: Service[];
  loading: boolean;
  error: string | null;
  searchCriteria: ServiceSearchCriteria;
  selectedServices: Set<number>;
  totalItems: number;
  currentPage: number;
  pageSize: number;
}
