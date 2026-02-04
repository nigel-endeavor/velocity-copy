/**
 * Service Model
 *
 * Represents a telecom service (Internet, MPLS, Voice, etc.)
 * This is one of the largest models with 135+ properties.
 */

import { z } from 'zod';
import { BaseModel } from './base.model';
import { currencySchema } from '@/shared/utils/validation';

/**
 * Service Interface
 */
export interface Service extends BaseModel {
  // Relationships
  locationId: number;
  orderId: number;

  // Identification
  clientServiceId?: string;
  alternateId?: string;
  quoteSolutionId?: string;

  // Status
  status: string;
  subStatus?: string;
  subProductType?: string;
  orderType?: string;

  // Activation
  activationLink?: string;
  activationPhone?: string;
  followUpDate?: Date | string;

  // Contract
  contractTerm?: string;
  contractSignedDate?: Date | string;
  circuitTermEndDate?: number;
  poNumber?: string;

  // Financial
  mrc: number; // Monthly Recurring Cost
  nrc: number; // Non-Recurring Cost
  mrr: number; // Monthly Recurring Revenue
  nrr: number; // Non-Recurring Revenue
  hasIcb?: boolean;
  icb: number; // Installation Cost Budget
  progressPercentage?: number;
  hasOsp?: boolean;
  osp: number; // Outside Plant
  annualRecurringCost: number;
  earlyTerminationFee?: number;
  costChangeReason?: string;
  macdCostChange?: number;
  macdRevenueChange?: number;
  parentMrc?: number;
  parentMrr?: number;
  commissionableMrc?: number;
  commissionableNrc?: number;
  commissionableArc?: number;

  // Provider
  provider?: string;
  underlyingProvider?: string;
  accountNumber?: string;
  summaryBill?: string;
  providerOrderNum?: string;
  providerCircuitId?: string;

  // Technical - Installation
  insideWiringRequired?: string;
  dmarc?: string;

  // Technical - IP Addressing
  additionalIpBlockRequired?: string;
  additionalIpBlock?: string;
  wanIps?: string;
  wanGateway?: string;
  wanSubnet?: string;
  lanBlock?: string;
  lanIps?: string;
  lanGateway?: string;
  lanSubnet?: string;
  dns1?: string;
  dns2?: string;
  ipFormat?: string;

  // Technical - Service Details
  ospConstIntervalEst?: string;
  speed?: string;
  downloadSpeed?: string;
  uploadSpeed?: string;
  mediaType?: string;
  netStatus?: string;
  locationHours?: string;
  productInstallInterval?: number;
  expediteOrder?: boolean;
  trunkGroup?: string;
  connectionHandoffType?: string;
  tieDownInfo?: string;

  // Service Type
  type: string;
  typeId?: number;
  buildingStatus?: string;
  description?: string;

  // Billing
  serviceBilledTo?: string;
  currentInventory?: boolean;
  billable?: boolean;
  active?: boolean;
  billCycle?: number;
  billToLocation?: boolean;

  // Disconnect
  disconnectReason?: string;
  subOrderType?: string;

  // Inventory
  inventoryServiceId?: number;

  // Client Info
  clientServiceInfo?: string;
  clientServiceType?: string;
  accountPasscode?: string;
  projectName?: string;
  jobNumber?: string;

  // Metadata
  recordSource?: string;
  ignoreForRenewals?: boolean;

  // Linking/Bundling
  linked?: boolean;
  bundled?: boolean;
  linkedBundledParent?: boolean;
  linkedBundledParentId?: number;

  // TSP
  tspCode?: string;
  tspCodeExpirationDate?: Date | string | null;

  // Service Flags
  managedService?: boolean;
  productionImpacting?: boolean;
  autoRenewal?: boolean;
  coTerminus?: boolean;

  // Renewal
  renewalCancelNoticePeriod?: string;
  contractInfo?: string;

  // Submission
  submittedInAdvToProvider?: boolean;
  parentTsd?: string;
  submittedInAdvToTsd?: boolean;

  // Commission
  cieTeamedDealInfo?: string;
  commissionReductionPercent?: string;
  opportunityNum?: string;
  netProviderPoints?: string;
  promotions?: string;
  spiffDetails?: string;

  // External
  externalOrderReference?: string;
  customerBillingInstructions?: string;
  fieldServicesProvider?: string;

  // Address (for service-level address)
  address1?: string;
  address2?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  country?: string;
  billingEmail?: string;

  // Shipping
  shippingAddress1?: string;
  shippingAddress2?: string;
  shippingCity?: string;
  shippingState?: string;
  shippingPostalCode?: string;
  shippingCountry?: string;
  shipTo?: string;

  // Technical Notes
  technicalNotes?: string;

  // Capacity
  numberOfEndpoints?: number;
  numberOfUsers?: number;

  // Licensing
  microsoftLicensing?: string;

  // Terminal
  isTerminal?: boolean;
}

/**
 * Service Validation Schema (simplified - full schema would be too large)
 */
export const serviceSchema = z.object({
  id: z.number().optional(),
  version: z.number().optional(),
  locationId: z.number(),
  orderId: z.number(),
  type: z.string().min(1, 'Service type is required'),
  status: z.string().min(1, 'Status is required'),
  mrc: z.number().min(0),
  nrc: z.number().min(0),
  mrr: z.number().min(0),
  nrr: z.number().min(0),
  icb: z.number().min(0),
  osp: z.number().min(0),
  annualRecurringCost: z.number().min(0),
  // Additional fields can be added as optional
});

/**
 * Helper Functions
 */

/**
 * Get display text for service
 */
export function getServiceDisplayText(service: Service): string {
  return `${service.type}${service.provider ? `: ${service.provider}` : ''}`;
}

/**
 * Check if service is MACD
 */
export function isServiceMacd(service: Service): boolean {
  if (!service.orderType) return false;
  return ['Move', 'Add', 'Change', 'Disconnect'].some((type) =>
    service.orderType!.includes(type)
  );
}

/**
 * Set download speed and update combined speed
 */
export function setServiceDownloadSpeed(service: Service, value: string): Service {
  const downloadSpeed = value;
  const uploadSpeed = service.uploadSpeed || '';
  const speed = `${downloadSpeed || ''} / ${uploadSpeed || ''}`;
  return { ...service, downloadSpeed, speed };
}

/**
 * Set upload speed and update combined speed
 */
export function setServiceUploadSpeed(service: Service, value: string): Service {
  const uploadSpeed = value;
  const downloadSpeed = service.downloadSpeed || '';
  const speed = `${downloadSpeed || ''} / ${uploadSpeed || ''}`;
  return { ...service, uploadSpeed, speed };
}

/**
 * Set contract term and update renewal flag
 */
export function setServiceContractTerm(service: Service, value: string): Service {
  const ignoreForRenewals = value === 'MTM';
  return { ...service, contractTerm: value, ignoreForRenewals };
}

/**
 * Parse currency string to number
 */
function parseCurrency(value: string): number {
  const cleaned = value.replace(/[^0-9.]/g, '');
  return parseFloat(cleaned) || 0;
}

/**
 * Calculate MACD cost change
 */
export function calculateMacdCostChange(mrc: number, parentMrc: number): number {
  return mrc - (parentMrc || 0);
}

/**
 * Calculate MACD revenue change
 */
export function calculateMacdRevenueChange(mrr: number, parentMrr: number): number {
  return mrr - (parentMrr || 0);
}

/**
 * Create new service
 */
export function createService(
  locationId: number,
  orderId: number,
  partial?: Partial<Service>
): Service {
  return {
    locationId,
    orderId,
    type: '',
    status: '',
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

export default Service;
