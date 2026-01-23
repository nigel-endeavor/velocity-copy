import { TestBed } from '@angular/core/testing';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { Observable, of } from 'rxjs';
import { MultieEditEffects } from './multie-edit.effects';
import * as actions from './multie-edit.actions';
import { LocalStoreService } from '../../local-store/local-store.service';
import { provideMockActions } from '@ngrx/effects/testing';
import { selectMultiEditState } from "./multie-edit.selectors";

describe('MultieEditEffects', () => {
  let actions$: Observable<any>;
  let effects: MultieEditEffects;
  let localStorageService: LocalStoreService;
  let store: MockStore;

  beforeEach(() => {
    const localStorageServiceMock: Partial<LocalStoreService> = {
      setItem: jest.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        MultieEditEffects,
        provideMockStore({
          initialState: {
            formState: {}
          }
        }),
        provideMockActions(() => actions$),
        { provide: LocalStoreService, useValue: localStorageServiceMock },
      ],
    });

    effects = TestBed.inject(MultieEditEffects);
    store = TestBed.inject(MockStore);
    localStorageService = TestBed.inject(LocalStoreService);
  });

  test('should update the form state and save it to local storage', () => {

    const formState = {
      field1: 'value1',
      field2: 'value2',
    };
    store.overrideSelector(selectMultiEditState, {formState});

    actions$ = of(actions.updateFormState({ formState }));

    effects.onMultieEditState.subscribe();

    expect(localStorageService.setItem).toHaveBeenCalledWith('multieEditState', JSON.stringify({formState}));
  });

  test('should clear the store and save an empty form state to local storage', () => {
    store.overrideSelector(selectMultiEditState, {formState: {}});

    actions$ = of(actions.clearStore());

    effects.onMultieEditState.subscribe();

    expect(localStorageService.setItem).toHaveBeenCalledWith('multieEditState', JSON.stringify({ formState: {} }));
  });
});
