import { initialState, ServiceWorklistState } from './service-worklist.reducer';

import {
  selectServiceWorklistState,
  getIsLoading,
  getColumns,
  getFilters
} from './service-worklist.selectors';

describe('ServiceWorklistSelectors', () => {
  let rootState: ServiceWorklistState;

  beforeEach(() => {
    rootState = initialState;
  });

  test('should select the service worklist state', () => {

    const serviceWorklistState = selectServiceWorklistState.projector(initialState);
    expect(serviceWorklistState).toEqual(initialState);
  });

  test('should select the isLoading value', () => {

    const isLoading = getIsLoading.projector(initialState);
    expect(isLoading).toEqual(initialState.isLoading);
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
