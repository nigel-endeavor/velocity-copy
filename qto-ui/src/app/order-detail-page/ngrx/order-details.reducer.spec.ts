import { initialState, orderDetailsReducer, OrderDetailsState } from './order-details.reducer';
import * as actions from './order-details.actions';

describe('OrderDetails Reducer', () => {
  test('should update selectedTab', () => {
    const previousState: OrderDetailsState = initialState;

    const payload = {
      host: 'service',
      tab: 'value2',
    };

    const action = actions.setActiveTab(payload);
    const newState = orderDetailsReducer(previousState, action);

    expect(newState.selectedTab.service).toEqual('value2');
  });

  test('should toggle one of the edit keys', () => {
    const previousState: OrderDetailsState = initialState;


    const action = actions.toggleEdit({ key: 'editingServiceBilling' });
    const newState = orderDetailsReducer(previousState, action);

    expect(newState.editFlags['editingServiceBilling']).toBeTruthy();
  });
});
