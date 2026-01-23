import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Actions } from '@ngrx/effects';
import { of, throwError } from 'rxjs';

import { LookupValueService } from '../../../services/lookup-value.service';
import { SubjectService } from '../../../services/subject.service';
import { CompanyService } from '../../../services/company.service';
import { LocationViewService } from '../../../services/location-view.service';
import { LocationWorklistEffects } from './location-worklist.effects';
import * as actions from './location-worklist.actions';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { Company } from '../../../models/company.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { LocationView } from '../../../models/location-view.model';
import { locationTableActions } from '../configs/ordering-table.config';
import { MockStore, provideMockStore } from '@ngrx/store/testing';

describe('LocationWorklistEffects', () => {
  let actions$: Actions;
  let effects: LocationWorklistEffects;
  let store: MockStore;
  let lookupValueService: jest.Mocked<LookupValueService>;
  let subjectService: jest.Mocked<SubjectService>;
  let companyService: jest.Mocked<CompanyService>;
  let locationViewService: jest.Mocked<LocationViewService>;

  const location: LocationView = {}; // Mock location object
  const subjects: SubjectInterface[] = []; // Mock subjects array
  const customers: PaginatedResult<Company> = {}; // Mock customers object
  const lookupValues: LookupValue[] = []; // Mock lookup values array

  beforeEach(() => {
    const storeMock = {
      dispatch: jest.fn()
    };

    const lookupValueServiceMock = {
      find: jest.fn()
    };
    const subjectServiceMock = {
      getSubjects: jest.fn()
    };
    const companyServiceMock = {
      search: jest.fn()
    };
    const locationViewServiceMock = {
      save: jest.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        LocationWorklistEffects,
        provideMockActions(() => actions$),
        provideMockStore(),
        { provide: LookupValueService, useValue: lookupValueServiceMock },
        { provide: SubjectService, useValue: subjectServiceMock },
        { provide: CompanyService, useValue: companyServiceMock },
        { provide: LocationViewService, useValue: locationViewServiceMock }
      ]
    });

    effects = TestBed.inject(LocationWorklistEffects);
    store = TestBed.inject(MockStore);
    lookupValueService = TestBed.inject(LookupValueService) as jest.Mocked<LookupValueService>;
    subjectService = TestBed.inject(SubjectService) as jest.Mocked<SubjectService>;
    companyService = TestBed.inject(CompanyService) as jest.Mocked<CompanyService>;
    locationViewService = TestBed.inject(LocationViewService) as jest.Mocked<LocationViewService>;
  });

  describe('onUpdateLocation$', () => {
    test('should dispatch loadTableData and setUpdatedItem actions on success', () => {
      const action = actions.updateLocaion({ location });
      const loadTableDataAction = locationTableActions.loadTableData();
      const setUpdatedItemAction = actions.setUpdatedItem({ updatedItem: location });

      actions$ = of(action);
      locationViewService.save.mockReturnValue(of(location));

      effects.onUpdateLocation$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(loadTableDataAction);
        expect(store.dispatch).toHaveBeenCalledWith(setUpdatedItemAction);
      });
    });

    test('should dispatch loadCustomersFailure action on error', () => {
      const action = actions.updateLocaion({ location });
      const error = new Error('Some error');
      const failureAction = actions.loadCustomersFailure(error);

      actions$ = of(action);
      locationViewService.save.mockReturnValue(throwError(error));

      effects.onUpdateLocation$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(failureAction);
      });
    });
  });

  describe('onLoadSubjects$', () => {
    test('should dispatch loadProvisionersSuccess action on success', () => {
      const action = actions.loadProvisioners({ orderId: '123' });
      const successAction = actions.loadProvisionersSuccess({ subjects });

      actions$ = of(action);
      subjectService.getSubjects.mockReturnValue(of(subjects));

      effects.onLoadSubjects$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(successAction);
      });
    });

    test('should dispatch loadProvisionersFailure action on error', () => {
      const action = actions.loadProvisioners({ orderId: '123' });
      const error = new Error('Some error');
      const failureAction = actions.loadProvisionersFailure(error);

      actions$ = of(action);
      subjectService.getSubjects.mockReturnValue(throwError(error));

      effects.onLoadSubjects$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(failureAction);
      });
    });
  });

  describe('onLoadCustomers$', () => {
    test('should dispatch loadCustomersSuccess action on success', () => {
      const action = actions.loadCustomers();
      store.overrideSelector('getCustomersParams', {
        type: 'Master Customer',
        name: '',
        total: 0,
        limit: 50,
        offset: 0,
    });

      const successAction = actions.loadCustomersSuccess({ customers });

      actions$ = of(action);
      companyService.search.mockReturnValue(of(customers));

      effects.onLoadCustomers$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(successAction);
      });
    });

    test('should dispatch loadCustomersFailure action on error', () => {
      const action = actions.loadCustomers();
      const error = new Error('Some error');
      const failureAction = actions.loadCustomersFailure(error);

      actions$ = of(action);
      companyService.search.mockReturnValue(throwError(error));

      effects.onLoadCustomers$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(failureAction);
      });
    });
  });

  describe('onLoadLookupValue$', () => {
    test('should dispatch loadLookupValuesByKeySuccess action on success', () => {
      const action = actions.loadLookupValuesByKey({ lookupKey: 'key', companyId: '123' });
      const successAction = actions.loadLookupValuesByKeySuccess({ key: 'key', values: lookupValues });

      actions$ = of(action);
      lookupValueService.find.mockReturnValue(of(lookupValues));

      effects.onLoadLookupValue$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(successAction);
      });
    });

    test('should dispatch loadLookupValuesByKeyFailure action on error', () => {
      const action = actions.loadLookupValuesByKey({ lookupKey: 'key', companyId: '123' });
      const error = new Error('Some error');
      const failureAction = actions.loadLookupValuesByKeyFailure(error);

      actions$ = of(action);
      lookupValueService.find.mockReturnValue(throwError(error));

      effects.onLoadLookupValue$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(failureAction);
      });
    });
  });
});
