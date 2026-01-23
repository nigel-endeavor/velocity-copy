import { tableDeprecatedReducer, initialState } from './table-deprecated.reducer';
import * as actions from './table-deprecated.actions';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';

describe('TableDeprecatedReducer', () => {
  test('should add a selected item', () => {
    const selectedItem = { id: 1, name: 'Item 1' };
    const action = actions.addSelectedItem({ selectedItem });
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.selectedItems.length).toBe(1);
    expect(newState.selectedItems[0]).toEqual(selectedItem);
  });

  test('should add multiple selected items', () => {
    const selectedItems = [
      { id: 1, name: 'Item 1' },
      { id: 2, name: 'Item 2' },
    ];
    const action = actions.addSelectedItems({ selectedItems });
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.selectedItems.length).toBe(2);
    expect(newState.selectedItems).toEqual(selectedItems);
  });

  test('should remove selected items', () => {
    const existingItems = [
      { id: 1, name: 'Item 1' },
      { id: 2, name: 'Item 2' },
    ];
    const unSelectedItems = [{ id: 1, name: 'Item 1' }];
    const initialStateWithItems = { ...initialState, selectedItems: existingItems };
    const action = actions.removeSelectedItems({ unSelectedItems });
    const newState = tableDeprecatedReducer(initialStateWithItems, action);
    expect(newState.selectedItems.length).toBe(1);
    expect(newState.selectedItems[0]).toEqual(existingItems[1]);
  });

  test('should load provisioners successfully', () => {
    const subjects: SubjectInterface[] = [{ id: 1, displayName: 'Subject 1' }];
    const action = actions.loadProvisionersSuccess({ subjects });
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.provisioners).toEqual(subjects);
  });

  test('should load lookup values by key successfully', () => {
    const key = 'clientManagers';
    const values: LookupValue[] = [{ id: 1, displayName: 'Value 1' }];
    const action = actions.loadLookupValuesByKeySuccess({ key, values });
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState[key]).toEqual(values);
  });

  test('should start loading on send multie edit', () => {
    const action = actions.sendMultieEdit();
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.isLoading).toBe(true);
  });

  test('should handle send multie edit success', () => {
    const action = actions.sendMultieEditSuccess();
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.isLoading).toBe(false);
  });

  test('should handle send multie edit failure', () => {
    const cantUpdate: MultiEditFailureInterface[] = [
      { service: { id: 1, displayName: 'Service 1' }, error: 'Error 1' },
    ];
    const action = actions.sendMultieEditFailure({ cantUpdate });
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.isLoading).toBe(false);
    expect(newState.cantUpdate).toEqual(cantUpdate);
  });

  test('should clear the store', () => {
    const action = actions.clearStore();
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState.selectedItems.length).toBe(0);
  });

  test('should return the initial state for an unknown action', () => {
    const action = { type: 'UnknownAction' };
    const newState = tableDeprecatedReducer(initialState, action);
    expect(newState).toBe(initialState);
  });
});
