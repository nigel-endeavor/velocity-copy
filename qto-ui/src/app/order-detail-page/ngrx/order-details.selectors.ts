import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './order-details.reducer';
import { LookupValue } from '../../models/lookup-value.model';
import { Order } from '../../models/order.model';
import { Company } from '../../models/company.model';
import { TerminalLocationStatuses } from '../../models/constants/terminal-location-statuses';
import { TerminalServiceStatuses } from '../../models/constants/terminal-service-statuses';
import { Location } from '../../models/location.model';
import { Service } from '../../models/service.model';
import { plainToClass } from 'class-transformer';

export const selectOrderDetailsState
  = createFeatureSelector<reducer.OrderDetailsState>(reducer.orderDetailsFeatureKey);

export const getActiveTab = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.selectedTab
  }
);

export const getIsLoading = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isLoading
  }
);

export const getShowMasterCustomer = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.showMasterCustomerSave
  }
);

export const getSelectedMasterCompany = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.selectedMasterCompany
  }
);

export const getSelectedLocationOrService = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.selectedLocationOrService
  }
);

export const getSelectedisLocation = createSelector(getSelectedLocationOrService,
  (selected: Location | Service | null) => {
    return selected && selected instanceof Location
  }
);

export const getOrder = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.order
  }
);

export const getIsInventory = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isInventory
  }
);


export const getSelectedLocation = createSelector(
  getOrder,
  getSelectedLocationOrService,
  getIsInventory,
  (order: Order | null, service: Location | Service | null, isInventory: boolean) => {
    if (service != null) {
      if (service instanceof Location) {
        return plainToClass(Location, service);
      } else {
        return plainToClass(Location, order?.locations.find(l => l.id === (service as Service)?.locationId)!);
      }
    }
    return null;
  }
);

export const getCompany = createSelector(getOrder,
  (order: Order | null) => {
    return order?.company
  }
);

export const getCompanyId = createSelector(getCompany,
  (company: Company | undefined) => {
    return company?.id || 0
  }
);

export const getEditFlags = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.editFlags
  }
);

export const getFlagByName = (flag: string) =>
  createSelector(
    getEditFlags,
    (editFlags: Record<string, boolean>) => {
      return editFlags[flag];
    }
  );

export const getActiveLocationTab = createSelector(getActiveTab,
  (selectedTab: { location: string, service: string }) => {
    return selectedTab.location
  }
);

export const getActiveServiceTab = createSelector(getActiveTab,
  (selectedTab: { location: string, service: string }) => {
    return selectedTab.service
  }
);

export const getProvisioners = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.provisioners
  }
);

export const getClientManagers = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.clientManagers
  }
);

export const getClientManagersAsOptions = createSelector(getClientManagers,
  (clientManagers: LookupValue[]) => {
    return clientManagers.map(item => item.value);
  }
);

export const getIsAdmin = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isAdmin
  }
)

export const getIsInventoryWrite = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isInventoryWrite
  }
)

export const getIsInventoryRead = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isInventoryRead
  }
)

export const getIsOrderRead = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isOrderRead
  }
)

export const getIsOrderWrite = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isOrderWrite
  }
)

export const getIsOrderWriteTerminal = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isOrderWriteTerminal
  }
)

export const getRequirementTemplates = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.requirementTemplates
  }
)

// terminalStatuses is an array of strings. Combines the values of two objects, TerminalLocationStatuses and TerminalServiceStatuses, into a single array. checks whether state.selectedLocationOrService is truthy and whether the status property of state.selectedLocationOrService is included in the terminalStatuses array. !! is used to convert the result into a boolean value (true if the condition is met, false otherwise). Checks whether selectedLocationOrService in the order details state is included in the terminalStatuses array.
export const getSelectedIsInTerminalStatus = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    const terminalStatuses: string[] = [...Object.values(TerminalLocationStatuses), ...Object.values(TerminalServiceStatuses)];
    return !!state.selectedLocationOrService && terminalStatuses.includes(state.selectedLocationOrService.status);
  }
)

export const editEnabled = createSelector(
  getIsInventory,
  getIsAdmin,
  getIsInventoryWrite,
  getIsOrderWrite,
  getIsOrderWriteTerminal,
  getSelectedIsInTerminalStatus,
  (isInventory: boolean, isAdmin: boolean, isInventoryWrite: boolean, isOrderWrite: boolean, isOrderWriteTerminal: boolean, isTerminal: boolean ) => {
    return (isInventory && isInventoryWrite)
      || (!isInventory && !isTerminal && isOrderWrite)
      || (!isInventory && isOrderWriteTerminal)
      || isAdmin;
  }
)

export const getIsReadOnly = createSelector(
  getIsInventory,
  getIsAdmin,
  getIsInventoryWrite,
  getIsInventoryRead,
  getIsOrderWrite,
  getIsOrderRead,
  getIsOrderWriteTerminal,
  getSelectedIsInTerminalStatus,
  (isInventory: boolean, isAdmin: boolean, isInventoryWrite: boolean, isInventoryRead: boolean, isOrderWrite: boolean, isOrderRead: boolean, isOrderWriteTerminal: boolean, selectedIsTerminal: boolean ) => {
    if (isAdmin) {
      return false;
    }
    if (isInventory) {
      return isInventoryRead && !isInventoryWrite;
    } else {
      if (selectedIsTerminal) {
        return !isOrderWriteTerminal;
      } else {
        return isOrderRead && !isOrderWrite
      }
    }
  }
);

export const getIsEditing = createSelector(
  getFlagByName('editingAssignmentCard'),
  getFlagByName('editingContactCard'),
  getFlagByName('editingSalesTab'),
  getFlagByName('editingBillingCard'),
  getFlagByName('editingClientOrderId'),
  getFlagByName('editingLocInfo'),
  getFlagByName('editingLocRequirements'),
  getFlagByName('editingServiceInfo'),
  getFlagByName('editingServiceBilling'),
  getFlagByName('editingServiceProvider'),
  getFlagByName('editingThreatMDRDetails'),
  getFlagByName('editingLicenseOptions'),
  getFlagByName('editingServiceCircuit'),
  getFlagByName('editingServiceNetwork'),
  getFlagByName('editingServiceEquipment'),
  getFlagByName('editingServicePrioritization'),
  getFlagByName('editingServiceBrokerageOrderDetails'),
  getFlagByName('editingServiceBrokerageCommissions'),
  getFlagByName('editingServiceBrokerageAgency'),
  getFlagByName('editingServiceBrokerageProfitMonitoring'),
  getFlagByName('editingMasterCustomer'),
  (editingAssignmentCard: boolean, editingContactCard: boolean, editingSalesTab:boolean, editingBillingCard: boolean,
    editingClientOrderId: boolean, editingLocInfo: boolean, editingLocRequirements: boolean,
    editingServiceInfo: boolean,
    editingServiceBilling: boolean, editingServiceProvider: boolean, editingThreatMDRDetails: boolean,
    editingServiceCircuit: boolean, editingServiceNetwork: boolean, editingServiceEquipment: boolean, editingServicePrioritization: boolean,
    editingServiceBrokerageOrderDetails: boolean, editingServiceBrokerageCommissions: boolean, editingServiceBrokerageAgency: boolean,
    editingServiceBrokerageProfitMonitoring: boolean, editingMasterCustomer: boolean, editingLicenseOptions: boolean
  ) => {
    return editingAssignmentCard || editingContactCard || editingSalesTab || editingBillingCard
      || editingClientOrderId || editingLocInfo || editingLocRequirements
      || editingServiceInfo || editingServiceBilling || editingServiceProvider || editingThreatMDRDetails
      || editingServiceCircuit || editingServiceNetwork || editingServiceEquipment || editingServicePrioritization
      || editingServiceBrokerageOrderDetails || editingServiceBrokerageCommissions || editingServiceBrokerageAgency
      || editingServiceBrokerageProfitMonitoring || editingMasterCustomer || editingLicenseOptions;
  }
)


export const activationAttemptEditEnabled = (attemptStatus: string) => createSelector(
  getIsAdmin,
  getIsOrderWrite,
  getIsOrderWriteTerminal,
  getIsInventoryWrite,
  getSelectedLocationOrService,
  (isAdminUser: boolean, isOrderWrite: boolean, isOrderWriteTerminal:boolean, getIsInventoryWrite:boolean, service) => {
    const terminalServiceStatuses: string[] = [...Object.values(TerminalServiceStatuses)];
    const terminalActivationStatuses: string[] = ['Complete', 'Incomplete - Pending Re-Schedule', 'Partial Complete - Pending Re-Schedule', 'Cancelled'];
    const activationOrServiceInTerminalStatus = terminalActivationStatuses.includes(attemptStatus) || terminalServiceStatuses.includes(service?.status!)
    return isAdminUser || (!activationOrServiceInTerminalStatus && isOrderWrite) || (activationOrServiceInTerminalStatus && isOrderWriteTerminal) || (!activationOrServiceInTerminalStatus && getIsInventoryWrite);
  }
)

export const getSelectedActivationId = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.selectedActivationId;
  }
)

export const getInventoryLocation = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.inventoryLocation;
  }
);

export const getRelatedMacds = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.relatedMacds;
  }
);

export const getActivationCount = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.activationCount;
  }
);

export const getDisputesCount = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.disputeCount;
  }
);

export const getLockOneLocationPerOrder = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.lockOneLocationPerOrder;
  }
);

export const getShowBrokerageFields = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.showBrokerageFields;
  }
);

export const getAutoCreateClientServiceId = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.autoCreateClientServiceId;
  }
);

export const getUserHasTenantAccess = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.userHasTenantAccess;
  }
);

export const selectIsLoading = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.isLoading
  }
);

export const selectServiceBrokerage = createSelector(selectOrderDetailsState,
  (state: reducer.OrderDetailsState) => {
    return state.serviceBrokerage
  }
);
