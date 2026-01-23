import * as fromSelectors from './dashboards.selectors';
import * as fromReducer from './dashboards.reducer';
import { Company } from '../../../models/company.model';
import { initialState } from '../../locations-worklist/ngrx/location-worklist.reducer';

describe('Dashboards Selectors', () => {
  let mockState: fromReducer.DashboardsState;

  beforeEach(() => {
    mockState = initialState;
  });

  test('should select the dashboards state', () => {
    const selectedState = fromSelectors.selectDashboardsState.projector(mockState);
    expect(selectedState).toBe(mockState);
  });

  test('should select params by key', () => {
    const filterKey = 'customersParams'
    const selectedParams = fromSelectors.getParamsByKey(filterKey).projector(mockState);
    expect(selectedParams).toBe(mockState[filterKey].filters);
  });

  test('should select customers by key', () => {
    const optionKey = 'masterCustomers';
    const selectedCustomers = fromSelectors.getCustomersByKey(optionKey).projector(mockState);
    expect(selectedCustomers).toBe(mockState[optionKey]);
  });

  test('should select filters', () => {
    const selectedFilters = fromSelectors.getFilters.projector(mockState);
    expect(selectedFilters).toBe(mockState.filters);
  });

  test('should select customers by key as options', () => {
    const optionKey = 'masterCustomers';
    const mockCompanies: Company[] = [
      { name: 'Company 1' },
      { name: 'Company 2' }
    ];
    const expectedOptions = ['Company 1', 'Company 2'];

    const selectedOptions = fromSelectors.getCustomersByKeyAsOptions(optionKey).projector(mockCompanies);
    expect(selectedOptions).toEqual(expectedOptions);
  });
});
