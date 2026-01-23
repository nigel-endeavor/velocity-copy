import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Actions } from '@ngrx/effects';
import { of, throwError } from 'rxjs';

import { ActivationWorklistState, initialState } from './activation-worklist.reducer';
import { LookupValueService } from '../../../services/lookup-value.service';
import { ActivationViewService } from '../../../services/activation-view.service';
import { ActivationWorklistEffects } from './activation-worklist.effects';
import * as actions from './activation-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { getFilters, getStatusesValues } from "./activation-worklist.selectors";

describe('ActivationWorklistEffects', () => {
  let actions$: Actions;
  let effects: ActivationWorklistEffects;
  let store: MockStore<ActivationWorklistState>;
  let lookupValueService: jest.Mocked<LookupValueService>;
  let activationViewService: jest.Mocked<ActivationViewService>;

  const lookupValues: LookupValue[] = []; // Mock lookup values array
  const meta = {
    statusCounts: [{ status: 'test', count: 1 }],
    ttuEquivTotal: 1,
    statusCountHeader: 'test'
  }

  beforeEach(() => {
    const storeMock = {
      dispatch: jest.fn()
    };

    const lookupValueServiceMock = {
      find: jest.fn()
    };
    const activationServiceMock = {
      search: jest.fn(),
      getActivationWorklistMeta: jest.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        ActivationWorklistEffects,
        provideMockActions(() => actions$),
        provideMockStore({
          initialState: initialState
        }),
        { provide: LookupValueService, useValue: lookupValueServiceMock },
        { provide: ActivationViewService, useValue: activationServiceMock }
      ]
    });

    effects = TestBed.inject(ActivationWorklistEffects);
    store = TestBed.inject(MockStore);
    lookupValueService = TestBed.inject(LookupValueService) as jest.Mocked<LookupValueService>;
    activationViewService = TestBed.inject(ActivationViewService) as jest.Mocked<ActivationViewService>;
    jest.spyOn(store, 'dispatch');
  });


  describe('onLoadMetaData$', () => {
    test('should dispatch loadMetaDataSuccess action on success', () => {
      const action = actions.loadMetaData();
      const successAction = actions.loadMetaDataSuccess(meta);
      store.overrideSelector(
        getFilters,
        {
        search: '',
          clientServiceId: '',
          scheduledAttemptStatus: '',
          internalTechAssigned: '',
          // @ts-ignore
          scheduledCheckInTime: {
          isEmpty: false,
            dateRange: null
        },
        lastUpdateBy: '',
          clientLocationType: '',
          clientLocationInfo: '',

          offset: 0,
          limit: 25,
          sortDir: '',
          sortField: '',
          format: '',
          fields: '',
          headers: ''
        }
      );

      store.overrideSelector(
        getStatusesValues,
        ['Schedule Date Confirmed']
      );

        actions$ = of(action);
      activationViewService.getActivationWorklistMeta.mockReturnValue(of({
        statusCounts: {
          'Schedule Date Confirmed': 37,
          'Scheduled Date Confirmed': 1
        },
          ttuEquivalentTotal: 42.50
        }
      ));

      effects.onLoadMetaData$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(successAction);
      });
    });

    test('should dispatch loadMetaDataFailure action on error', () => {
      const action = actions.loadMetaDataFailure({  });
      const error = new Error('Some error');
      const failureAction = actions.loadMetaDataFailure(error);

      store.overrideSelector(
        getFilters,
        {
          search: '',
          clientServiceId: '',
          scheduledAttemptStatus: '',
          internalTechAssigned: '',
          // @ts-ignore
          scheduledCheckInTime: {
            isEmpty: false,
            dateRange: null
          },
          lastUpdateBy: '',
          clientLocationType: '',
          clientLocationInfo: '',

          offset: 0,
          limit: 25,
          sortDir: '',
          sortField: '',
          format: '',
          fields: '',
          headers: ''
        }
      );

      store.overrideSelector(
        getStatusesValues,
        ['Schedule Date Confirmed']
      );
      actions$ = of(action);
      activationViewService.getActivationWorklistMeta.mockReturnValue(throwError(error));

      effects.onLoadMetaData$.subscribe(() => {
        expect(store.dispatch).toHaveBeenCalledWith(failureAction);
      });
    });
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
