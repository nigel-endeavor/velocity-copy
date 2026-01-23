import { createReducer, on } from '@ngrx/store';
import * as actions from './order-details.actions';
import { NgForm } from '@angular/forms';
import { Order } from '../../models/order.model';
import { Location } from '../../models/location.model';
import { Service } from '../../models/service.model';
import { Company } from '../../models/company.model';
import { LookupValue } from '../../models/lookup-value.model';
import { SubjectInterface } from '../../models/subject.model';
import { plainToClass } from 'class-transformer';
import { RequirementTemplate } from '../../models/requirement-template.model';
import { ServiceBrokerage } from '../../models/service-brokerage-model';

export const orderDetailsFeatureKey = 'orderDetailsState';

export function selectedIsLocation(selectedLocationOrService: Location | Service): boolean {
  return !!selectedLocationOrService && selectedLocationOrService instanceof Location;
}

export interface OrderDetailsState {
  forms: Map<NgForm, {}>;

  order: Order | null;
  selectedTab: {
    location: string;
    service: string;
  };
  selectedLocationOrService: Location | Service | null;
  isLoading: boolean;
  isAdmin: boolean;
  // isReadonly: boolean;
  isInventory: boolean;
  isInventoryRead: boolean;
  isInventoryWrite: boolean;
  isOrderWriteTerminal: boolean;
  isOrderRead: boolean;
  isOrderWrite: boolean;
  userHasTenantAccess: boolean;

  editFlags: Record<string, boolean>;

  selectedMasterCompany: Company | null;
  showMasterCustomerSave: boolean;
  provisioners: SubjectInterface[];
  clientManagers: LookupValue[];
  requirementTemplates: RequirementTemplate[];
  selectedActivationId: number | null; //this is only used to navigate back to the selected activation when the order is reloaded on activation save
  inventoryLocation: Location | null;
  relatedMacds: Service[];
  activationCount: number;
  disputeCount: number;

  //company config
  lockOneLocationPerOrder: boolean;
  showBrokerageFields: boolean;
  autoCreateClientServiceId: boolean;

  serviceBrokerage: ServiceBrokerage | null;
}

export const initialState: OrderDetailsState = {
  forms: new Map(),

  order: null,
  selectedTab: {
    location: 'General',
    service: 'General'
  },

  selectedLocationOrService: null,
  isLoading: true,
  isInventory: false,
  isAdmin: false,
  isOrderRead: false,
  isOrderWrite: false,
  isOrderWriteTerminal: false,
  isInventoryRead: false,
  isInventoryWrite: false,
  userHasTenantAccess: false,

  editFlags: {
    //order level edit flags
    editingAssignmentCard: false,
    editingContactCard: false,
    editingSalesTab: false,
    editingClientOrderId: false,
    editingMasterCustomer: false,
    //location level edit flags
    editingLocInfo: false,
    editingLocRequirements: false,
    //service level edit flags
    editingServiceInfo: false,
    editingServiceBilling: false,
    editingServiceProvider: false,
    editingServiceCircuit: false,
    editingServiceRouting: false,
    editingServiceNetwork: false,
    editingServicePrioritization: false,
    editingServiceEquipment: false,
    editingRequirementsTemplate: false,
    editingThreatMDRDetails: false,
    editingRiskMDRDetails: false,
    editingLicenseOptions: false,
    editingEngineeringIAMDetails: false,
    editingEngineeringMDMDetails: false,
    editingEngineeringEMSDetails: false,
    editingCyber360MXDRDetails: false,
    editingEngineeringEndpointProtectionDetails: false,
    editingEngineeringInformationProtectionDetails: false,
    editingRansomMDRDetails: false,
    editingALocation: false,
    editingZLocation: false,
    editingServiceBillingAddress: false,
    editingServiceBrokerageOrderDetails: false,
    editingServiceBrokerageAgency: false,
    editingServiceBrokerageCommissions: false,
    editingServiceBrokerageProfitMonitoring: false,
    editingShipping: false
  },

  selectedMasterCompany: null,
  showMasterCustomerSave: false,
  provisioners: [],
  clientManagers: [],
  requirementTemplates: [],
  selectedActivationId: null,
  inventoryLocation: null,
  relatedMacds: [],
  activationCount: 0,
  disputeCount: 0,

  lockOneLocationPerOrder: false,
  autoCreateClientServiceId: false,
  showBrokerageFields: false,
  serviceBrokerage: null
};

export const orderDetailsReducer = createReducer(
  initialState,

  on(actions.loadRequirementTemplatesSuccess, (state, action) => {
    return {
      ...state,
      requirementTemplates: action.requirementTemplates
    }
  }),

  on(actions.setSelectedMasterCompany, (state, action) => {
    return {
      ...state,
      selectedMasterCompany: action.company
    }
  }),

  on(actions.saveOrder, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.saveLocation, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.saveLocationFailure, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(actions.saveServiceFailure, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(actions.saveOrderFailure, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(actions.saveService, (state, action) => {
    return (action.service instanceof Service) ? {
      ...state,
      isLoading: true,
      selectedLocationOrService: plainToClass(Service, {
        ...state.selectedLocationOrService,
        clientServiceId: action.service.clientServiceId
      })
    } : {
      ...state,
      isLoading: true,
    }
  }),

  on(actions.setUserStatuses, (state, action) => {
    return {
      ...state,
      isAdmin: action.isAdmin,
      isOrderRead: action.isOrderRead,
      isOrderWrite: action.isOrderWrite,
      isOrderWriteTerminal: action.isOrderWriteTerminal,
      isInventoryRead: action.isInventoryRead,
      isInventoryWrite: action.isInventoryWrite
    }
  }),

  on(actions.loadOrderSuccess, (state, action) => {
    return {
      ...state,
      order: action.order,
      isLoading: false,
      editFlags: {
        ...initialState.editFlags,
        editingMasterCustomer: !action.order.company.parentCompany,
      }
    }
  }),

  on(actions.setActiveTab, (state, action) => {
    return {
      ...state,
      selectedTab: {
        ...state.selectedTab,
        [action.host]: action.tab
      }
    }
  }),

  on(actions.toggleEdit, (state, action) => {
    return {
      ...state,
      editFlags: {
        ...initialState.editFlags,
        [action.key]: !state.editFlags[action.key],
        editingMasterCustomer: action.key === 'editingMasterCustomer' ? !state.editFlags[action.key] : !state.order?.company.parentCompany
      }
    }
  }),

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects
    }
  }),

  on(actions.resetIsEditFlag, (state, action) => {
    return {
      ...state,
      editFlags: {
        ...initialState.editFlags,
        [action.key]: false
      }
    }
  }),

  // @ts-ignore
  on(actions.setAddress, (state, action) => {
    return {
      ...state,
      order: {
        ...state.order,
        company: {
          // @ts-ignore
          ...state.order.company,
          address: action.address
        }
      }
    }
  }),

  on(actions.setSelectedLocationOrService, (state, action) => {
    return {
      ...state,
      // @ts-ignore
      selectedLocationOrService: action.selectedLocationOrService?.locationId ?
        plainToClass(Service, action.selectedLocationOrService) :
        plainToClass(Location, action.selectedLocationOrService)
    }
  }),

  on(
    actions.toggleLocationDisplay,
    actions.openLocationDisplay, (state, action) => {
      const orderLocations = state.order?.locations.map(item => {
        if (item.id == action.location.id) {
          return plainToClass(Location, {
            ...item,
            displayServices: action.type.includes('Open') ? true : !item.displayServices
          })
        }
        return plainToClass(Location, item)
      }) || [];
      return state.order ? {
        ...state,
        order: {
          ...state.order,
          locations: orderLocations
        } as Order
      } : { ...state }
    }),

  on(actions.addNewService, (state, action) => {
    const orderLocations = state.order?.locations.map(item => {
      if (item.id == action.service.locationId) {
        if (item.currentInventory) {
          return plainToClass(Location, {
            ...item,
            inventoryServices: [action.service, ...item.services]
          })
        } else {
          return plainToClass(Location, {
            ...item,
            services: [action.service, ...item.services]
          })
        }
      }
      return plainToClass(Location, item)
    }) || [];
    return state.order ? {
      ...state,
      order: {
        ...state.order,
        locations: orderLocations
      } as Order
    } : { ...state }
  }),

  on(actions.removeNewService, (state, action) => {
    // remove the service from the location
    const orderLocations = state.order?.locations.map(item => {
      if (item.id == action.service.locationId) {
        if (item.currentInventory) {
          return plainToClass(Location, {
            ...item,
            inventoryServices: item.inventoryServices.filter(s => s.id !== action.service.id)
          })
        } else {
          return plainToClass(Location, {
            ...item,
            services: item.services.filter(s => s.id !== action.service.id)
          })
        }
      }
      return plainToClass(Location, item)
    }) || [];
    return state.order ? {
      ...state,
      order: {
        ...state.order,
        locations: orderLocations
      } as Order
    } : { ...state }
  }),

  on(actions.clearStore, (state, action) => {
    return {
      ...state,
      selectedLocationOrService: null,
      order: null
    }
  }),

  on(actions.addForm, (state, action) => {
    let initialState: any = {};
    for (const fc in action.form.form.controls) {
      initialState[fc] = action.form.controls[fc].value;
    }
    const forms = new Map();
    forms.set(action.form, initialState);
    return {
      ...state,
      forms: forms
    }
  }),

  on(actions.setSelectedService, (state, action) => {
    const service = state.selectedLocationOrService?.id === action.id ? state.selectedLocationOrService :
      state.selectedLocationOrService instanceof Location ?
        plainToClass(Service, state.selectedLocationOrService.services.find(s => s.id == action.id)) : state.selectedLocationOrService;
    return {
      ...state,
      selectedLocationOrService: service
    }
  }),

  on(actions.removeForm, (state, action) => {
    const forms = state.forms;
    forms.delete(action.form);
    return {
      ...state,
      forms: forms
    }
  }),

  on(actions.setIsInventory, (state, action) => {
    return {
      ...state,
      isInventory: action.isInventory
    }
  }),

  on(actions.setSelectedActivationId, (state, action) => {
    return {
      ...state,
      selectedActivationId: action.selectedActivationId
    }
  }),

  on(actions.loadInventoryLocationSuccess, (state, action) => {
    return {
      ...state,
      inventoryLocation: action.inventoryLocation
    }
  }),

  on(actions.loadRelatedMacdsSuccess, (state, action) => {
    return {
      ...state,
      relatedMacds: action.relatedMacds
    }
  }),

  on(actions.clearRelatedMacds, (state, action) => {
    return {
      ...state,
      relatedMacds: []
    }
  }),

  on(actions.loadActivationCountSuccess, (state, action) => {
    return {
      ...state,
      activationCount: action.activationCount
    }
  }),

  on(actions.loadDisputesCountSuccess, (state, action) => {
    return {
      ...state,
      disputeCount: action.disputesCount
    }
  }),

  on(actions.loadCompanyConfigSuccess, (state, action) => {
    return {
      ...state,
      lockOneLocationPerOrder: action.lockOneLocationPerOrder,
      showBrokerageFields: action.showBrokerageFields,
      autoCreateClientServiceId: action.autoCreateClientServiceId
    }
  }),

  on(actions.loadUserTenantAccessSuccess, (state, action) => {
    return {
      ...state,
      userHasTenantAccess: action.userHasTenantAccess
    }
  }),

  on(actions.saveServiceBrokerage, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadServiceBrokerageSuccess, (state, action) => {
    return {
      ...state,
      serviceBrokerage: action,
      isLoading: false
    }
  }),

  on(actions.pullFromCrm, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.pullFromCrmFailure, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(actions.deleteService, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.deleteServiceFailure, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(actions.deleteLocation, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.deleteLocationFailure, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(actions.deleteServiceSuccess, (state, action) => {
    return {
      ...state,
      isLoading: false
    }
  })


);

