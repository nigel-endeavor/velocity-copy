import { MultiEditState, multieEditFeatureKey, initialState } from './multie-edit.reducer';
import { getFormState } from './multie-edit.selectors';

describe('MultiEdit Selectors', () => {
  let rootState: MultiEditState;

  beforeEach(() => {
    rootState = initialState;
  });


  test('should select the form state', () => {
    // Arrange
    const expectedFormState = rootState.formState;

    // Act
    const selectedFormState = getFormState.projector(rootState);

    // Assert
    expect(selectedFormState).toBe(expectedFormState);
  });
});
