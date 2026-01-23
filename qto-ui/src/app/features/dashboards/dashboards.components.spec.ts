import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { DashboardsComponent } from './dashboards.component';
import { CompanyService } from '../../services/company.service';
import { SecurityUtilService } from '../../services/security-util.service';
import { loadCustomers, updateCustomerParams, updateFilters } from './ngrx/dashboards.actions';
import { getFilters, getCustomersByKeyAsOptions, getParamsByKey } from './ngrx/dashboards.selectors';
import { PaginatedResult } from '../../models/paginated-result.model';
import { ServiceType } from '../../models/constants/service-type';
import { Permissions } from '../../services/security-util.service';
import { CompanySearchCriteria } from '../../models/company-search-criteria';
import { of } from 'rxjs';
import { Company } from '../../models/company.model';
import { Store } from '@ngrx/store';

describe('DashboardsComponent', () => {
  let component: DashboardsComponent;
  let fixture: ComponentFixture<DashboardsComponent>;
  let mockStore: MockStore;
  let mockCompanyService: CompanyService;
  let mockSecurityUtils: SecurityUtilService;

  const initialState = {
    // Define your initial state here if needed
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardsComponent],
      providers: [
        provideMockStore({ initialState }),
        {
          provide: CompanyService,
          useValue: {
            search: jest.fn(() => of({ collection: [] } as PaginatedResult<Company>))
          }
        },
        {
          provide: SecurityUtilService,
          useValue: {
            userHasPermission: jest.fn()
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardsComponent);
    component = fixture.componentInstance;
    mockStore = TestBed.inject(MockStore);
    mockCompanyService = TestBed.inject(CompanyService);
    mockSecurityUtils = TestBed.inject(SecurityUtilService);

    mockStore.overrideSelector(getFilters, {});
    mockStore.overrideSelector(getCustomersByKeyAsOptions('masterCustomers'), []);
    mockStore.overrideSelector(getParamsByKey('masterCustomerSearchCriteria'), {});
    mockStore.overrideSelector(getCustomersByKeyAsOptions('endCustomers'), []);
    mockStore.overrideSelector(getParamsByKey('endCustomerSearchCriteria'), {});

    fixture.detectChanges();
  });

  test('should create', () => {
    expect(component).toBeTruthy();
  });
  test('should initialize tenantOptions on ngOnInit', () => {
    const mockCompany: Company = { name: 'Test Company' };
    const searchSpy = jest.spyOn(mockCompanyService, 'search').mockReturnValue(of({ collection: [mockCompany] } as PaginatedResult<Company>));

    component.ngOnInit();

    expect(searchSpy).toHaveBeenCalled();
    expect(component.tenantOptions).toEqual([mockCompany.name]);
  });

  test('should update selectedTenants and dispatch actions on onTenantSelected', () => {
    const mockTenant = 'Mock Tenant';
    const updateParamsSpy = jest.spyOn(component, 'onUpdateParams');
    const updateFiltersSpy = jest.spyOn(component, 'onFilterChanged');
    const initMasterCustomerOptionsSpy = jest.spyOn(component, 'initMasterCustomerOptions');
    const initCustomerOptionsSpy = jest.spyOn(component, 'initCustomerOptions');

    component.onTenantSelected(mockTenant);

    expect(component.selectedTenants).toEqual(mockTenant);
    expect(updateParamsSpy).toHaveBeenCalledTimes(2);
    expect(updateFiltersSpy).toHaveBeenCalledTimes(2);
    expect(initMasterCustomerOptionsSpy).toHaveBeenCalled();
    expect(initCustomerOptionsSpy).toHaveBeenCalled();
  });

  // Add more test cases for other component methods and interactions

  // Example: Testing onUpdateParams method
  test('should dispatch updateCustomerParams action on onUpdateParams', () => {
    const mockKey = 'testKey';
    const mockValue = 'testValue';
    const mockFilterKey = 'masterCustomerSearchCriteria'
    const mockOptionKey = 'masterCustomers';

    const dispatchSpy = jest.spyOn(mockStore, 'dispatch');

    component.onUpdateParams(mockValue, mockFilterKey, mockKey, mockOptionKey);

    expect(dispatchSpy).toHaveBeenCalledWith(
      updateCustomerParams({ key: mockKey, value: mockValue, filterKey: mockFilterKey, optionKey: mockOptionKey })
    );
  });

  test('should dispatch loadCustomers action on initMasterCustomerOptions', () => {
    const dispatchSpy = jest.spyOn(mockStore, 'dispatch');
    component.initMasterCustomerOptions();
    expect(dispatchSpy).toHaveBeenCalledWith(
      loadCustomers({ filterKey: 'masterCustomerSearchCriteria', optionKey: 'masterCustomers' })
    );
  });

  test('should dispatch loadCustomers action on initCustomerOptions', () => {
    const dispatchSpy = jest.spyOn(mockStore, 'dispatch');
    component.initCustomerOptions();
    expect(dispatchSpy).toHaveBeenCalledWith(
      loadCustomers({ filterKey: 'endCustomerSearchCriteria', optionKey: 'endCustomers' })
    );
  });

  test('should return false from getIsReadonlyUser if user has permission', () => {
    jest.spyOn(mockSecurityUtils, 'userHasPermission').mockReturnValue(true);
    expect(component.getIsReadonlyUser()).toBe(false);
  });

  test('should return true from getIsReadonlyUser if user lacks permission', () => {
    jest.spyOn(mockSecurityUtils, 'userHasPermission').mockReturnValue(false);
    expect(component.getIsReadonlyUser()).toBe(true);
  });

  test('should dispatch updateFilters action on onFilterChanged', () => {
    const mockKey = 'testKey';
    const mockValue = 'testValue';
    const dispatchSpy = jest.spyOn(mockStore, 'dispatch');
    component.onFilterChanged(mockValue, mockKey);
    expect(dispatchSpy).toHaveBeenCalledWith(updateFilters({ key: mockKey, value: mockValue }));
  });
});
