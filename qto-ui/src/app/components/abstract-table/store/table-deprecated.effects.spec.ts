import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable, of, throwError } from 'rxjs';
import { TableDeprecatedEffects } from './table-deprecated.effects';
import * as actions from './table-deprecated.actions';
import { SubjectService } from '../../../services/subject.service';
import { LookupValueService } from '../../../services/lookup-value.service';
import { MultiEditService } from '../../../services/multiEdit.service';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { LocalStoreService } from '../../../features/local-store/local-store.service';
import { HttpClientModule } from '@angular/common/http';

describe('TableDeprecatedEffects', () => {
  let actions$: Observable<any>;
  let effects: TableDeprecatedEffects;
  let subjectService: SubjectService;
  let lookupValueService: LookupValueService;
  let multiEditService: MultiEditService;
  let store: MockStore;

  beforeEach(() => {
    const localStorageServiceMock: Partial<LocalStoreService> = {
      setItem: jest.fn(),
    };
    class MockLookupValueService {
      find = jest.fn(() => of())
    };
    class MockSubjectService {
      getSubjects() {
        return of(/* mock response */);
      }
    };
    class MockMultiEditService {
      sendMultiEdit() {
        return of(/* mock response */);
      }
    };


    TestBed.configureTestingModule({
      providers: [
        TableDeprecatedEffects,
        SubjectService,
        MultiEditService,
        HttpClientModule,
        provideMockStore({
          initialState: {
              cantUpdate: [],
              selectedItems: [
                { id: 1 },
                { id: 2 },
                { id: 3 },
              ],
              provisioners: [
                {
                  id: 1,
                  username: 'john_doe',
                  displayName: 'John Doe',
                  emailAddress: 'john.doe@example.com',
                  lastLoginTime: new Date('2023-05-16T09:30:00'),
                  version: 0
                },
                // Add more provisioner objects here
              ],
              clientManagers: [
                {
                  id: 1,
                  display: 'Client Manager 1',
                  value: 'client_manager_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more client manager objects here
              ],
              serviceJeopardy: [
                {
                  id: 1,
                  display: 'Service Jeopardy 1',
                  value: 'service_jeopardy_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more service jeopardy objects here
              ],
              jeopardyResponsibility: [
                {
                  id: 1,
                  display: 'Jeopardy Responsibility 1',
                  value: 'jeopardy_responsibility_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more jeopardy responsibility objects here
              ],
              providers: [
                {
                  id: 1,
                  display: 'provider 1',
                  value: 'provider_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more provider objects here
              ],
              speed: [
                {
                  id: 1,
                  display: 'Speed 1',
                  value: 'speed_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more speed objects here
              ],
              protocols: [
                {
                  id: 1,
                  display: 'Protocol 1',
                  value: 'protocol_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more protocol objects here
              ],
              mediaTypes: [
                {
                  id: 1,
                  display: 'Media Type 1',
                  value: 'media_type_1',
                  active: true,
                  sortSequence: 1,
                  parentId: 1,
                  version: 0
                },
                // Add more media type objects here
              ],
              isLoading: false,
            }
        }),
        provideMockActions(() => actions$),
        { provide: SubjectService, useClass: MockSubjectService },
        { provide: LocalStoreService, useValue: localStorageServiceMock },
        { provide: MultiEditService, useClass: MockMultiEditService },
        { provide: LookupValueService, useValue: MockLookupValueService }
      ]
    });

    effects = TestBed.inject(TableDeprecatedEffects);
    subjectService = TestBed.inject(SubjectService);
    store = TestBed.inject(MockStore);
    lookupValueService = TestBed.inject(LookupValueService);
    multiEditService = TestBed.inject(MultiEditService);
  });

  describe('onLoadSubjects', () => {
    test('should dispatch loadProvisionersSuccess action on success', () => {
      const subjects = [{ id: 1, name: 'Subject 1' }];
      const action = actions.loadProvisioners();

      jest.spyOn(subjectService, 'getSubjects').mockReturnValue(of(subjects));

      actions$ = of(action);

      effects.onLoadSubjects.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.loadProvisionersSuccess({ subjects }));
      });
    });

    test('should dispatch loadProvisionersFailure action on error', () => {
      const error = new Error('Failed to load subjects');
      const action = actions.loadProvisioners();

      jest.spyOn(subjectService, 'getSubjects').mockReturnValue(throwError(error));

      actions$ = of(action);

      effects.onLoadSubjects.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.loadProvisionersFailure(error));
      });
    });
  });

  describe('onLoadLookupValue', () => {
    test('should dispatch loadLookupValuesByKeySuccess action on success', () => {
      const lookupKey = 'key';
      const lookupValues = [{ id: 1, value: 'Value 1' }];
      const action = actions.loadLookupValuesByKey({ lookupKey });
      lookupValueService.find = jest.fn();

      jest.spyOn(lookupValueService, 'find').mockReturnValue(of(lookupValues));

      actions$ = of(action);

      effects.onLoadLookupValue.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.loadLookupValuesByKeySuccess({
          key: lookupKey,
          values: lookupValues
        }));
      });
    });

    test('should dispatch loadLookupValuesByKeyFailure action on error', () => {
      const lookupKey = 'key';
      const error = new Error('Failed to load lookup values');
      const action = actions.loadLookupValuesByKey({ lookupKey });
      lookupValueService.find = jest.fn();

      jest.spyOn(lookupValueService, 'find').mockReturnValue(throwError(error));

      actions$ = of(action);

      effects.onLoadLookupValue.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.loadLookupValuesByKeyFailure(error));
      });
    });
  });

  describe('onSendMuliedit', () => {
    test('should dispatch sendMultieEditSuccess action on success', () => {
      const selectedIds = [1, 2, 3];
      const formResult = { field1: 'Value 1', field2: 'Value 2' };
      const action = actions.sendMultieEdit({ formResult });

      jest.spyOn(multiEditService, 'sendMultiEdit').mockReturnValue(of({ cantEdit: [] }));

      actions$ = of(action);
      effects.store.pipe = jest.fn(() => of(selectedIds));

      effects.onSendMuliedit.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.sendMultieEditSuccess());
      });
    });

    test('should dispatch sendMultieEditFailure action when there are failures', () => {
      const selectedIds = [1, 2, 3];
      const formResult = { field1: 'Value 1', field2: 'Value 2' };
      const cantEdit = [{ id: 1, error: 'Error 1' }];
      const action = actions.sendMultieEdit({ formResult });

      jest.spyOn(multiEditService, 'sendMultiEdit').mockReturnValue(of({ cantEdit }));

      actions$ = of(action);
      effects.store.pipe = jest.fn(() => of(selectedIds));

      effects.onSendMuliedit.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.sendMultieEditFailure({ cantUpdate: cantEdit }));
      });
    });
  });

  describe('onMultiEditSuccess', () => {
    test('should dispatch clearStore action', () => {
      const action = actions.sendMultieEditSuccess();

      actions$ = of(action);

      effects.onMultiEditSuccess.subscribe((resultAction) => {
        expect(resultAction).toEqual(actions.clearStore());
      });
    });
  });

  // Other test cases for remaining effects...

});
