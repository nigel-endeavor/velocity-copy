import { OrderDetailsState, orderDetailsFeatureKey, initialState } from './order-details.reducer';
import {
  getActiveLocationTab,
  getActiveServiceTab,
  getActiveTab,
  getFlagByName
} from './order-details.selectors';

describe('OrderDetails Selectors', () => {
  let rootState: OrderDetailsState;

  beforeEach(() => {
    rootState = initialState;
  });

  test('should select the selected tab', () => {
    // Arrange
    const expectedSelectedTab = rootState.selectedTab;

    // Act
    const selecteSelectedTab = getActiveTab.projector(rootState);

    // Assert
    expect(selecteSelectedTab).toBe(expectedSelectedTab);
  });

  test('should select editingServiceBilling', () => {

    // Act
    const selecteflag = getFlagByName('editingServiceBilling').projector({
      editingServiceBilling: true
    });

    // Assert
    expect(selecteflag).toBeTruthy();
  });

  test('should select active service tab', () => {
    // Arrange
    const expectedFlag = 'test2'

    // Act
    const selecteflag = getActiveServiceTab.projector({
      location: 'test',
      service: 'test2'
    });

    // Assert
    expect(selecteflag).toBe(expectedFlag);
  });

  test('should select active location tab', () => {
    // Arrange
    const expectedFlag = 'test'

    // Act
    const selecteflag = getActiveLocationTab.projector({
      location: 'test',
      service: 'test2'
    });

    // Assert
    expect(selecteflag).toBe(expectedFlag);
  });
});
