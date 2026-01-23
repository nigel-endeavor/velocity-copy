import { initialState, locationWorklistReducer, LocationWorklistState } from './location-worklist.reducer';
import * as actions from './location-worklist.actions';

describe('LocationWorklist Reducer', () => {

  test('should update filters', () => {
    const previousState: LocationWorklistState = {
      ...initialState
    }

    const filterUpdate = {
      key: 'search',
      value: 'test'
    };

    const action = actions.updateFilters(filterUpdate);
    const newState = locationWorklistReducer(previousState, action);

    expect(newState.filters.search).toEqual('test');
  });

  test('should clear filters', () => {
    const previousState: LocationWorklistState = {
      ...initialState,
      filters: {
        ...initialState.filters,
        search: 'test'
      }
    };

    const action = actions.clearFilters();

    const newState = locationWorklistReducer(previousState, action);

    expect(newState.filters.search).toEqual('');
  });

  test('should return the initial state when the previous state is undefined', () => {
    const action = { type: 'unknown' };
    const newState = locationWorklistReducer(undefined, action);

    expect(newState).toEqual(initialState);
  });

  test('should toggle view', () => {
    const action = actions.toggleView();
    const state = locationWorklistReducer(initialState, action);

    expect(state.cardViewSelected).toEqual(!initialState.cardViewSelected);
  });

  test('should load provisioners successfully', () => {
    const subjects = [{ id: 1, name: 'Provisioner 1' }, { id: 2, name: 'Provisioner 2' }];
    const action = actions.loadProvisionersSuccess({ subjects });
    const state = locationWorklistReducer(initialState, action);

    expect(state.provisioners).toEqual(subjects);
  });

  test('should load customers successfully', () => {
    const customers = { collection: [{ id: 1, name: 'Customer 1' }, { id: 2, name: 'Customer 2' }] };
    const action = actions.loadCustomersSuccess({ customers });
    const state = locationWorklistReducer(initialState, action);

    expect(state.customers).toEqual(customers.collection);
  });

  test('should load lookup values by key successfully', () => {
    const key = 'statuses';
    const values = [{ id: 1, name: 'Status 1' }, { id: 2, name: 'Status 2' }];
    const action = actions.loadLookupValuesByKeySuccess({ key, values });
    const state = locationWorklistReducer(initialState, action);

    expect(state[key]).toEqual(values);
  });

  test('should set updated item', () => {
    const updatedItem = { id: 1, name: 'Updated Item' };
    const action = actions.setUpdatedItem({ updatedItem });
    const state = locationWorklistReducer(initialState, action);

    expect(state.updatedItem).toEqual(updatedItem);
  });

  test('should toggle column', () => {
    const columnName = 'companyName';
    const action = actions.toggleColumn({ columnName });
    const state = locationWorklistReducer(initialState, action);

    const column = state.columns.find(col => col.propertyName === columnName);
    expect(column.hidden).toEqual(!initialState.columns.find(col => col.propertyName === columnName).hidden);
  });
});
