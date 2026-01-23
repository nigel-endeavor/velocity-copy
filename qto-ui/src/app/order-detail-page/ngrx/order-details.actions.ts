import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../models/lookup-value.model';
import { SubjectInterface } from '../../models/subject.model';
import { Order } from '../../models/order.model';
import { Address } from '../../models/address.model';
import { Service } from '../../models/service.model';
import { Location } from '../../models/location.model';
import { RequirementTemplate } from '../../models/requirement-template.model';
import { NgForm } from '@angular/forms';
import { Company } from "../../models/company.model";
import { ServiceBrokerage } from '../../models/service-brokerage-model';

export const clearStore = createAction(
  '[OrderDetails] Remove Form State',
);
export const reloadOrder = createAction(
  '[OrderDetails] Reload order',
);
export const setUserStatuses = createAction(
  '[OrderDetails] Set user statuses',
  props<{
    isAdmin: boolean,
    isOrderRead: boolean;
    isOrderWrite: boolean;
    isOrderWriteTerminal: boolean;
    isInventoryRead: boolean;
    isInventoryWrite: boolean;
  }>()
);

export const loadUserStatuses = createAction(
  '[OrderDetails] Load user statuses',
);

export const setActiveTab = createAction(
  '[OrderDetails] Set Active Tab',
  props<{ tab: string, host: 'location' | 'service' }>()
);

export const toggleEdit = createAction(
  '[OrderDetails] Toggle Edit',
  props<{ key: string }>()
);

export const toggleLocationDisplay = createAction(
  '[OrderDetails] Toggle Location Display',
  props<{ location: Location }>()
);


export const openLocationDisplay = createAction(
  '[OrderDetails] Open Location Display',
  props<{ location: Location }>()
);

export const saveOrder = createAction(
  '[OrderDetails] Order Save',
);

export const setAddress = createAction(
  '[OrderDetails] Set Address',
  props<{ address: Address }>()
);

export const setSelectedLocationOrService = createAction(
  '[OrderDetails] Set Selected Location Or Service',
  props<{ selectedLocationOrService: Location | Service | null }>()
);

export const setSelectedService = createAction(
  '[OrderDetails] Set Selected Service',
  props<{ id: number }>()
);

export const setMissingServiceField = createAction(
  '[OrderDetails] Set Missing Service Field',
  props<{
    milestoneName: string,
    missingFieldName: string,
    missingFieldDisplayName: string
  }>()
);

export const onReadParams = createAction(
  '[OrderDetails] Read url params',
  props<Record<string, string>>()
);

export const saveLocation = createAction(
  '[OrderDetails] Save location',
  props<{ location: Location }>()
);

export const saveLocationFailure = createAction(
  '[OrderDetails] Save location failure',
  props<any>()
);

export const addNewService = createAction(
  '[OrderDetails] Add New Service',
  props<{ service: Service }>()
);

export const removeNewService = createAction(
  '[OrderDetails] Remove New Service',
  props<{ service: Service }>()
);

export const saveService = createAction(
  '[OrderDetails] Save service',
  props<{ service: Service }>()
);

export const saveServiceFailure = createAction(
  '[OrderDetails] Save service failure',
  props<any>()
);

export const saveOrderFailure = createAction(
  '[OrderDetails] Save order failure',
  props<any>()
);

export const cloneService = createAction(
  '[OrderDetails] Clone service',
  props<{ serviceType: String, service: Service }>()
);

export const cloneServiceFailure = createAction(
  '[OrderDetails] Clone service failure',
  props<any>()
);

export const resetIsEditFlag = createAction(
  '[OrderDetails] Reset is Edit flag',
  props<{ key: string }>()
);

export const setSelectedMasterCompany = createAction(
  '[OrderDetails] Set Selected Master Company',
  props<{ company: Company | null }>()
);

export const loadLookupValuesByKey = createAction(
  '[OrderDetails] Load Lookup Values by key',
  props<{ key: string, lookupKey: string, companyId?: number }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[OrderDetails] Load Lookup Values Failure',
  props<any>()
);

export const loadOrderSuccess = createAction(
  '[OrderDetails] Load Order Success',
  props<{ order: Order }>()
);

export const loadOrderKeyFailure = createAction(
  '[OrderDetails] Load Order Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[OrderDetails] Load Lookup Values Success',
  props<{ values: LookupValue[], key: string }>()
);

export const loadProvisioners = createAction(
  '[OrderDetails] Load Provisioners',
  props<{ orderId: number | null }>()
);

export const loadProvisionersFailure = createAction(
  '[OrderDetails] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[OrderDetails] Load Provisioners Success',
  props<{ subjects: SubjectInterface[] }>()
);


export const loadRequirementTemplatesFailure = createAction(
  '[OrderDetails] Load Requirement Templates Failure',
  props<any>()
);

export const loadRequirementTemplatesSuccess = createAction(
  '[OrderDetails] Load Requirement Templates Success',
  props<{ requirementTemplates: RequirementTemplate[] }>()
);

export const addForm = createAction(
  '[OrderDetails] Add Form',
  props<{ form: NgForm }>()
);

export const removeForm = createAction(
  '[OrderDetails] Remove Form',
  props<{ form: NgForm }>()
);

export const setIsInventory = createAction(
  '[OrderDetails] Toggle Is Inventory',
  props<{ isInventory: boolean }>()
);

export const setSelectedActivationId = createAction(
  '[OrderDetails] Set Selected Activation ID',
  props<{ selectedActivationId: number }>()
);

export const loadInventoryLocation = createAction(
  '[OrderDetails] Load Inventory Location',
  props<{ serviceId: number }>()
);

export const loadInventoryLocationSuccess = createAction(
  '[OrderDetails] Load Inventory Location Success',
  props<{ inventoryLocation: Location }>()
);

export const loadInventoryLocationFailure = createAction(
  '[OrderDetails] Load Inventory Location Failure',
  props<{ errorMessage: string }>()
);

export const loadRelatedMacds = createAction(
  '[OrderDetails] Load Related MACDs',
  props<{ serviceId: number }>()
);

export const loadRelatedMacdsSuccess = createAction(
  '[OrderDetails] Load Related MACDs Success',
  props<{ relatedMacds: Service[] }>()
);

export const loadRelatedMacdsFailure = createAction(
  '[OrderDetails] Load Related MACDs Failure',
  props<{ errorMessage: string }>()
);

export const clearRelatedMacds = createAction(
  '[OrderDetails] Clear Related MACDs',
);

export const loadActivationCount = createAction(
  '[OrderDetails] Load Activation Count',
  props<{ serviceId: number }>()
);

export const loadActivationCountSuccess = createAction(
  '[OrderDetails] Load Activation Count Success',
  props<{ activationCount: number }>()
);

export const loadDisputesCount = createAction(
  '[OrderDetails] Load Disputes Count',
  props<{ serviceId?: number, locationId?: number }>()
);

export const loadDisputesCountSuccess = createAction(
  '[OrderDetails] Load Disputes Count Success',
  props<{ disputesCount: number }>()
);

export const loadCompanyConfig = createAction(
  '[OrderDetails] Load Company Config',
);

export const loadCompanyConfigSuccess = createAction(
  '[OrderDetails] Load Company Config Success',
  props<{ lockOneLocationPerOrder: boolean, showBrokerageFields: boolean, autoCreateClientServiceId: boolean }>()
);

export const loadUserTenantAccess = createAction(
  '[OrderDetails] Load User Tenant Access',
);

export const loadUserTenantAccessSuccess = createAction(
  '[OrderDetails] Load User Tenant Access Success',
  props<{ userHasTenantAccess: any }>()
);
export const loadServiceBrokerage = createAction(
  '[OrderDetails] Load Service Brokerage',
  props<{ serviceId: number }>()
);

export const pullFromCrm = createAction(
  '[OrderDetails] Pull From CRM',
  props<{ service: Service }>()
);

export const pullFromCrmFailure = createAction(
  '[OrderDetails] Pull From CRM Failure',
  props<any>()
);

export const deleteService = createAction(
  '[OrderDetails] Delete Service',
  props<{ service: Service, location: Location }>()
);

export const deleteServiceSuccess = createAction(
  '[OrderDetails] Delete Service Success',
  props<{ location: Location }>()
);

export const deleteServiceFailure = createAction(
  '[OrderDetails] Delete Service Failure',
  props<any>()
);

export const deleteLocation = createAction(
  '[OrderDetails] Delete Location',
  props<{ location: Location, order: Order }>()
);

export const deleteLocationSuccess = createAction(
  '[OrderDetails] Delete Location Success',
  props<{ order: Order, isInventory: boolean }>()
);

export const deleteLocationFailure = createAction(
  '[OrderDetails] Delete Location Failure',
  props<any>()
);

export const loadServiceBrokerageSuccess = createAction(
  '[OrderDetails] Load Service Brokerage Success',
  props<ServiceBrokerage>()
);

export const saveServiceBrokerage = createAction(
  '[OrderDetails] Save Service Brokerage',
  props<{ serviceBrokerage: ServiceBrokerage }>()
);

export const saveServiceBrokerageSuccess = createAction(
  '[OrderDetails] Save Service Brokerage Success',
  props<{ serviceBrokerage: ServiceBrokerage }>()
);
