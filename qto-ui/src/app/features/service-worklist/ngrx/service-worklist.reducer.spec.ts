import { initialState, serviceWorklistReducer, ServiceWorklistState } from './service-worklist.reducer';
import * as actions from './service-worklist.actions';

describe('ServiceWorklist Reducer', () => {

  test('should update filters', () => {
    const previousState: ServiceWorklistState = {
      ...initialState
    }

    const filterUpdate = {
      key: 'search',
      value: 'test'
    };

    const action = actions.updateFilters(filterUpdate);
    const newState = serviceWorklistReducer(previousState, action);

    expect(newState.filters.search).toEqual('test');
  });

  test('should clear filters', () => {
    const previousState: ServiceWorklistState = {
      ...initialState,
      filters: {
        ...initialState.filters,
        search: 'test'
      }
    };

    const action = actions.clearFilters();

    const newState = serviceWorklistReducer(previousState, action);

    expect(newState.filters.search).toEqual('');
  });

  test('should return the initial state when the previous state is undefined', () => {
    const action = { type: 'unknown' };
    const newState = serviceWorklistReducer(undefined, action);

    expect(newState).toEqual(initialState);
  });


  test('should load lookup values by key successfully', () => {
    const key = 'statuses';
    const values = [{ id: 1, name: 'Status 1' }, { id: 2, name: 'Status 2' }];
    const action = actions.loadLookupValuesByKeySuccess({ key, values });
    const state = serviceWorklistReducer(initialState, action);

    expect(state[key]).toEqual(values);
  });


  test('should toggle column', () => {
    const columnName = 'customerRequestedInstall';
    const action = actions.toggleColumn({ columnName });
    const state = serviceWorklistReducer(initialState, action);

    const column = state.columns.find(col => col.propertyName === columnName);
    expect(column.hidden).toEqual(!initialState.columns.find(col => col.propertyName === columnName).hidden);
  });
});
