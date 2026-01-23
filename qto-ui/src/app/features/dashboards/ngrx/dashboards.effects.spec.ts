import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { StoreModule } from '@ngrx/store';
import { Observable, of } from 'rxjs';

import { DashboardsEffects } from './dashboards.effects';
import * as fromSelectors from './dashboards.selectors';
import * as actions from './dashboards.actions';
import { CompanyService } from '../../../services/company.service';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { WipService } from '../../../services/wip.service';
import {CarriersService} from "../../../services/providers.service";
import {ActivationAttemptViewService} from "../../../services/activation-attempt-view.service";

describe('DashboardsEffects', () => {
  let actions$: Observable<any>;
  let effects: DashboardsEffects;
  let store: MockStore;
  let companyService: CompanyService;
  let wipService: WipService;
  let carriersService: CarriersService;
  let activationAttemptViewService: ActivationAttemptViewService;
  const customers: PaginatedResult<Company> = {}; // Mock customers object

  const mockCompanyService = {
    search: jest.fn()
  };

  const mockWipService = {
    getWipServices: jest.fn()
  };

  const mockCarriersService = {
    getCarrierIntervals: jest.fn()
  };

  const mockActivationAttemptViewService = {
    getServiceIntervals: jest.fn()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DashboardsEffects,
        provideMockActions(() => actions$),
        provideMockStore(),
        {
          provide: CompanyService,
          useValue: mockCompanyService
        },
        {
          provide: WipService,
          useValue: mockWipService
        },
        {
          provide: CarriersService,
          useValue: mockCarriersService
        },
        {
          provide: ActivationAttemptViewService,
          useValue: mockActivationAttemptViewService
        }
      ],
      imports: [StoreModule.forRoot({})]
    });

    effects = TestBed.inject(DashboardsEffects);
    store = TestBed.inject(MockStore);
    companyService = TestBed.inject(CompanyService) as jest.Mocked<CompanyService>;
    wipService = TestBed.inject(WipService) as jest.Mocked<WipService>;
    carriersService = TestBed.inject(CarriersService) as jest.Mocked<CarriersService>;
    activationAttemptViewService = TestBed.inject(ActivationAttemptViewService) as jest.Mocked<ActivationAttemptViewService>;
  });

  test('should be created', () => {
    expect(effects).toBeTruthy();
  });

  describe('onLoadCustomers$', () => {
    test('should dispatch loadCustomersSuccess action on success', () => {
      // Arrange
      const action = actions.loadCustomers();
      const mockParams = {
        type: 'Master Customer',
        name: '',
        total: 0,
        limit: 50,
        offset: 0,
      };
      const successAction = actions.loadCustomersSuccess({ customers });

      store.overrideSelector(fromSelectors.getParamsByKey('masterCustomerSearchCriteria'), mockParams);

      actions$ = of(action);
      companyService.search.mockReturnValue(of(customers));

      // Act & Assert
      effects.onLoadCustomers$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(successAction);
      });
    });

    test('should dispatch loadCustomersFailure action on error', () => {
      // Arrange
      const action = actions.loadCustomers();
      const error = new Error('Some error');
      const failureAction = actions.loadCustomersFailure(error);

      actions$ = of(action);
      companyService.search.mockReturnValue(new Error(error));

      // Act & Assert
      effects.onLoadCustomers$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(failureAction);
      });
    });
  });
});
