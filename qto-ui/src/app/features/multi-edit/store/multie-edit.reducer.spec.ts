import { initialState, multieEditReducer, MultiEditState } from './multie-edit.reducer';
import * as actions from './multie-edit.actions';

describe('MultiEdit Reducer', () => {
  test('should update the form state', () => {
    const previousState: MultiEditState = {
      formState: {},
    };

    const formState = {
      field1: 'value1',
      field2: 'value2',
    };

    const action = actions.updateFormState({ formState });
    const newState = multieEditReducer(previousState, action);

    expect(newState.formState).toEqual(formState);
  });

  test('should clear the store', () => {
    const previousState: MultiEditState = {
      formState: {
        field1: 'value1',
        field2: 'value2',
      },
    };

    const action = actions.clearStore();

    const newState = multieEditReducer(previousState, action);

    expect(newState.formState).toEqual({});
  });

  test('should return the initial state when the previous state is undefined', () => {
    const action = { type: 'unknown' };
    const newState = multieEditReducer(undefined, action);

    expect(newState).toEqual(initialState);
  });
});
