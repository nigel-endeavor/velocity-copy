import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './table-deprecated.reducer';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { SelectedItems } from './table-deprecated.reducer';

export const selectTableDeprecatedState
  = createFeatureSelector<reducer.TableDeprecatedState>(reducer.tableDeprecatedStoreFeatureKey);

export const getSelectedItems = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.selectedItems
  }
);
export const getIsLoading = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.isLoading
  }
);

export const getCantUpdate = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.cantUpdate
  }
);

export const getSelectedIs = createSelector(getSelectedItems,
  (selectedItems: SelectedItems[]) => {
  return selectedItems.map(item => item.id)
  }
);

export const getProvisioners = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.provisioners
  }
);

export const getClientManagers = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.clientManagers
  }
);

export const getServiceJeopardy = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.serviceJeopardy
  }
);

export const getJeopResponsibility = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.jeopardyResponsibility
  }
);

export const getProviders = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.providers
  }
);

export const getSpeed = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.speed
  }
);

export const getProtocols = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.protocols
  }
);

export const getMediaTypes = createSelector(selectTableDeprecatedState,
  (state: reducer.TableDeprecatedState) => {
  return state.mediaTypes
  }
);

export const getMultieditConfig = createSelector(
  getProvisioners,
  getClientManagers,
  getServiceJeopardy,
  getJeopResponsibility,
  getProviders,
  getSpeed,
  getProtocols,
  getMediaTypes,
  (
    provisioners: SubjectInterface[],
    clientManagers: LookupValue[],
    serviceJeopardy: LookupValue[],
    jeopardyResponsibility: LookupValue[],
    providers: LookupValue[],
    speed: LookupValue[],
    protocols: LookupValue[],
    mediaTypes: LookupValue[]
  ) => {
  return {
    provisioner: provisioners,
    vertekProjectManager: provisioners,
    clientProjectManager: clientManagers,
    jeopDescription: serviceJeopardy,
    jeopResponsibility: jeopardyResponsibility,
    jeopAssignedTo: provisioners,
    provider: providers,
    networkProtocol: protocols,
    uploadSpeed: speed,
    downloadSpeed: speed,
    mediaType: mediaTypes
  }}
);
