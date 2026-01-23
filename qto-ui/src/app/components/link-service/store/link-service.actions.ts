import { createAction, props } from "@ngrx/store";
import { ServiceView } from "../../../models/service-view.model";

export const updateFilters = createAction(
  '[LinkService] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[LinkService] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[LinkService] clear filters',
);

export const pageDestroyed = createAction(
  '[LinkService] page destroyed',
);

export const addSelectedItems = createAction(
  '[LinkService] Update Add Selected Items',
  props<{ value: ServiceView[] }>()
);

export const removeUnselectedItems = createAction(
  '[LinkService] Update Remove Selected Items',
  props<{ value: ServiceView[] }>()
);

export const saveLink = createAction(
  '[LinkService] Save Link',
  props<{ incomingServiceId : number, selectedItems: number[], linkType: string }>()
);

export const saveLinkSuccess = createAction(
  '[LinkService] Save Link Success'
);

export const saveLinkFailure = createAction(
  '[LinkService] Save Link Failure',
  props<{ errorMessage: string }>()
);

export const saveLinkInventory = createAction(
  '[LinkService] Save Link Inventory',
  props<{ incomingServiceId : number, selectedItem: any, linkType: string }>()
);

export const saveLinkInevntorySuccess = createAction(
  '[LinkService] Save Link Inventory Success'
);

export const saveLinkInventoryFailure = createAction(
  '[LinkService] Save Link Inventory Failure',
  props<{ errorMessage: string }>()
);


