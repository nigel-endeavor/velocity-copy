import { initialState, activationWorklistReducer, ActivationWorklistState } from './activation-worklist.reducer';
import * as actions from './activation-worklist.actions';

describe('ActivationWorklist Reducer', () => {

  test('should update filters', () => {
    const previousState: ActivationWorklistState = {
      ...initialState
    }

    const filterUpdate = {
      key: 'search',
      value: 'test'
    };

    const action = actions.updateFilters(filterUpdate);
    const newState = activationWorklistReducer(previousState, action);

    expect(newState.filters.search).toEqual('test');
  });

  test('should clear filters', () => {
    const previousState: ActivationWorklistState = {
      ...initialState,
      filters: {
        ...initialState.filters,
        search: 'test'
      }
    };

    const action = actions.clearFilters();

    const newState = activationWorklistReducer(previousState, action);

    expect(newState.filters.search).toEqual('');
  });

  test('should return the initial state when the previous state is undefined', () => {
    const action = { type: 'unknown' };
    const newState = activationWorklistReducer(undefined, action);

    expect(newState).toEqual(initialState);
  });


  test('should load lookup values by key successfully', () => {
    const key = 'statuses';
    const values = [{ id: 1, name: 'Status 1' }, { id: 2, name: 'Status 2' }];
    const action = actions.loadLookupValuesByKeySuccess({ key, values });
    const state = activationWorklistReducer(initialState, action);

    expect(state[key]).toEqual(values);
  });

  test('should set updated item', () => {
    const updatedItem = {
      statusCounts: [{ status: 'test', count: 1 }],
      ttuEquivTotal: 1,
      statusCountHeader: 'test'
  };
    const action = actions.loadMetaDataSuccess({ ...updatedItem });
    const state = activationWorklistReducer(initialState, action);

    expect(state.meta).toEqual(updatedItem);
  });

  test('should toggle column', () => {
    const columnName = 'scheduledAttemptStatus';
    const action = actions.toggleColumn({ columnName });
    const state = activationWorklistReducer(initialState, action);

    const column = state.columns.find(col => col.propertyName === columnName);
    expect(column.hidden).toEqual(!initialState.columns.find(col => col.propertyName === columnName).hidden);
  });
});
