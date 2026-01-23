import { initialState, ActivationWorklistState } from './activation-worklist.reducer';

import {
  selectActivationWorklistState,
  getIsLoading,
  getColumns,
  getFilters,
  getMetaData
} from './activation-worklist.selectors';

describe('ActivationWorklistSelectors', () => {
  let rootState: ActivationWorklistState;

  beforeEach(() => {
    rootState = initialState;
  });

  test('should select the activation worklist state', () => {

    const activationWorklistState = selectActivationWorklistState.projector(initialState);
    expect(activationWorklistState).toEqual(initialState);
  });

  test('should select the isLoading value', () => {

    const isLoading = getIsLoading.projector(initialState);
    expect(isLoading).toEqual(initialState.isLoading);
  });

  test('should select the meta value', () => {
    const cardViewSelected = getMetaData.projector(initialState);
    expect(cardViewSelected).toEqual({statusCounts: undefined});
  });

  test('should select the columns', () => {
    const columns = getColumns.projector(initialState)
    expect(columns).toEqual(initialState.columns);
  });

  test('should select the filters', () => {
    const filters = getFilters.projector(initialState);
    expect(filters).toEqual(initialState.filters);
  });
});
