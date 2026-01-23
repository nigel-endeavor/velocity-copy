import * as fromReducer from './dashboards.reducer';
import * as actions from './dashboards.actions';
import { Company } from '../../../models/company.model';

describe('Dashboards Reducer', () => {
  test('should update customer params correctly', () => {
    const initialState: fromReducer.DashboardsState = {
      filters: {
        selectedMasterCompanies: '',
        selectedEndCompanies: ''
      },
      masterCustomers: [],
      masterCustomerSearchCriteria: {
        filters: {
          type: 'Master Customer',
          name: '',
          tenants: '',
          total: 0,
          limit: 50,
          offset: 0
        },
        wasChanged: false
      },
      endCustomers: [],
      endCustomerSearchCriteria: {
        filters: {
          type: 'End Customer',
          name: '',
          tenants: '',
          total: 0,
          limit: 50,
          offset: 0
        },
        wasChanged: false
      }
    };

    const action = actions.updateCustomerParams({
      key: 'name',
      value: 'Test Company',
      filterKey: 'masterCustomerSearchCriteria',
      optionKey: 'masterCustomers'
    });

    const updatedState = fromReducer.dashboardsReducer(initialState, action);

    expect(updatedState.masterCustomerSearchCriteria.filters.name).toEqual('Test Company');
    expect(updatedState.masterCustomers).toEqual([]);
  });

  test('should load customers successfully and update state', () => {
    const initialState: fromReducer.DashboardsState = {
      filters: {
        selectedMasterCompanies: '',
        selectedEndCompanies: ''
      },
      masterCustomers: [],
      masterCustomerSearchCriteria: {
        filters: {
          type: 'Master Customer',
          name: '',
          tenants: '',
          total: 0,
          limit: 50,
          offset: 0
        },
        wasChanged: false
      },
      endCustomers: [],
      endCustomerSearchCriteria: {
        filters: {
          type: 'End Customer',
          name: '',
          tenants: '',
          total: 0,
          limit: 50,
          offset: 0
        },
        wasChanged: false
      }
    };

    const mockCustomers: Company[] = [
      { name: 'Company 1' },
      { name: 'Company 2' }
    ];

    const action = actions.loadCustomersSuccess({
      filterKey: 'masterCustomerSearchCriteria',
      optionKey: 'masterCustomers',
      customers: {
        collection: mockCustomers,
        limit: 50,
        total: 2,
        offset: 0
      }
    });

    const updatedState = fromReducer.dashboardsReducer(initialState, action);

    expect(updatedState.masterCustomers).toEqual(mockCustomers);
    expect(updatedState.masterCustomerSearchCriteria.filters.total).toEqual(2);
    expect(updatedState.masterCustomerSearchCriteria.filters.offset).toEqual(0);
  });

  test('should update filters correctly', () => {
    const initialState: fromReducer.DashboardsState = {
      filters: {
        selectedMasterCompanies: '',
        selectedEndCompanies: '',
        offset: 0
      },
      masterCustomers: [],
      masterCustomerSearchCriteria: {
        filters: {
          type: 'Master Customer',
          name: '',
          tenants: '',
          total: 0,
          limit: 50,
          offset: 0
        },
        wasChanged: false
      },
      endCustomers: [],
      endCustomerSearchCriteria: {
        filters: {
          type: 'End Customer',
          name: '',
          tenants: '',
          total: 0,
          limit: 50,
          offset: 0
        },
        wasChanged: false
      }
    };

    const action = actions.updateFilters({ key: 'selectedMasterCompanies', value: 'Company 1' });

    const updatedState = fromReducer.dashboardsReducer(initialState, action);

    expect(updatedState.filters.selectedMasterCompanies).toEqual('Company 1');
  });
});
