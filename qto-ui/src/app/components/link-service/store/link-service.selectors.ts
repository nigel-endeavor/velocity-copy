import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './link-service.reducer';
import { ServiceView } from "../../../models/service-view.model";

export const selectLinkServiceState
  = createFeatureSelector<reducer.LinkServiceState>(reducer.linkServiceFeatureKey);

export const getIsLoading = createSelector(selectLinkServiceState,
  (state: reducer.LinkServiceState) => {
    return state.isLoading;
  }
);

export const getColumns = createSelector(selectLinkServiceState,
  (state: reducer.LinkServiceState) => {
    return state.columns;
  }
);

export const getFilters = createSelector(selectLinkServiceState,
  (state: reducer.LinkServiceState) => {
    return state.filters;
  }
);

export const getSelectedItems = createSelector(selectLinkServiceState,
  (state: reducer.LinkServiceState) => {
    return state.selectedItems
  }
);



export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: ServiceView[]) => {
    return selectedItems.map(item => item.id);
  }
);


