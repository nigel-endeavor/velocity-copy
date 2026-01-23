import { initialState, LocationWorklistState } from "./location-worklist.reducer";

import {
  selectLocationWorklistState,
  getIsLoading,
  getCardViewSelected,
  getColumns,
  getFilters,
  getUpdatedItem
} from './location-worklist.selectors';

describe('LocationWorklistSelectors', () => {
  let rootState: LocationWorklistState;

  beforeEach(() => {
    rootState = initialState;
  });

  test('should select the location worklist state', () => {

    const locationWorklistState = selectLocationWorklistState.projector(initialState);
    expect(locationWorklistState).toEqual(initialState);
  });

  test('should select the isLoading value', () => {

    const isLoading = getIsLoading.projector(initialState);
    expect(isLoading).toEqual(initialState.isLoading);
  });

  test('should select the cardViewSelected value', () => {
    const cardViewSelected = getCardViewSelected.projector(initialState);
    expect(cardViewSelected).toBe(initialState.cardViewSelected);
  });

  test('should select the columns', () => {
    const columns = getColumns.projector(initialState)
    expect(columns).toEqual(initialState.columns);
  });

  test('should select the filters', () => {
    const filters = getFilters.projector(initialState);
    expect(filters).toEqual(initialState.filters);
  });

  test('should select the updatedItem', () => {
    const updatedItem = getUpdatedItem.projector(initialState);
    expect(updatedItem).toEqual(initialState.updatedItem);
  });
});
