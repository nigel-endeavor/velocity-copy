import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Actions } from '@ngrx/effects';
import { of, throwError } from 'rxjs';

import { ServiceWorklistState, initialState } from './service-worklist.reducer';
import { LookupValueService } from '../../../services/lookup-value.service';
import { ServiceWorklistEffects } from './service-worklist.effects';
import * as actions from './service-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { SubjectService } from '../../../services/subject.service';
import { CompanyService } from '../../../services/company.service';
import { ServiceViewService } from '../../../services/service-view.service';
import { MultiEditService } from '../../../services/multiEdit.service';
import { LevelOfEffortService } from '../../../services/level-of-effort.service';

describe('ServiceWorklistState', () => {
  let actions$: Actions;
  let effects: ServiceWorklistEffects;
  let store: MockStore<ServiceWorklistState>;
  let lookupValueService: jest.Mocked<LookupValueService>;
  let subjectService: jest.Mocked<SubjectService>;
  let companyService: jest.Mocked<CompanyService>;
  let serviceViewService: jest.Mocked<ServiceViewService>;
  let multiEditService: jest.Mocked<MultiEditService>;
  let levelOfEffortService: jest.Mocked<LevelOfEffortService>;

  const lookupValues: LookupValue[] = []; // Mock lookup values array


  beforeEach(() => {
    const storeMock = {
      dispatch: jest.fn()
    };

    const lookupValueServiceMock = {
      find: jest.fn()
    };
    const subjectServiceMock = {
      search: jest.fn(),
    };
    const companyServiceMock = {
      search: jest.fn(),
    };
    const serviceViewServiceMock = {
      search: jest.fn(),
    };
    const multiEditServiceMock = {
      sendMultiEdit: jest.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        ServiceWorklistEffects,
        provideMockActions(() => actions$),
        provideMockStore({
          initialState: initialState
        }),
        { provide: LookupValueService, useValue: lookupValueServiceMock },
        { provide: SubjectService, useValue: subjectServiceMock },
        { provide: CompanyService, useValue: companyServiceMock },
        { provide: ServiceViewService, useValue: serviceViewServiceMock },
        { provide: MultiEditService, useValue: multiEditServiceMock },
        { provide: LevelOfEffortService, useValue: levelOfEffortService }
      ]
    });

    effects = TestBed.inject(ServiceWorklistEffects)
    store = TestBed.inject(MockStore);
    lookupValueService = TestBed.inject(LookupValueService) as jest.Mocked<LookupValueService>;
    subjectService = TestBed.inject(SubjectService) as jest.Mocked<SubjectService>;
    companyService = TestBed.inject(CompanyService) as jest.Mocked<CompanyService>;
    serviceViewService = TestBed.inject(ServiceViewService) as jest.Mocked<ServiceViewService>;
    multiEditService = TestBed.inject(MultiEditService) as jest.Mocked<MultiEditService>;
    levelOfEffortService = TestBed.inject(LevelOfEffortService) as jest.Mocked<LevelOfEffortService>;
    jest.spyOn(store, 'dispatch');
  });

  describe('onLoadLookupValue$', () => {
    test('should dispatch loadLookupValuesByKeySuccess action on success', () => {
      const action = actions.loadLookupValuesByKey({ lookupKey: 'key', key: 'key' });
      const successAction = actions.loadLookupValuesByKeySuccess({ key: 'key', values: lookupValues });

      actions$ = of(action);
      lookupValueService.find.mockReturnValue(of(lookupValues));

      effects.onLoadLookupValue$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(successAction);
      });
    });

    test('should dispatch loadLookupValuesByKeyFailure action on error', () => {
      const action = actions.loadLookupValuesByKey({ lookupKey: 'key', key: 'key' });
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
