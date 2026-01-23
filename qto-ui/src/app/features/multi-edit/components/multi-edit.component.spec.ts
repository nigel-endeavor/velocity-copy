import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatLegacyDialogRef as MatDialogRef, MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA } from '@angular/material/legacy-dialog';
import { Store } from '@ngrx/store';
import { FormControl, FormGroup } from '@angular/forms';
import { MockStore, provideMockStore } from '@ngrx/store/testing';

import { MultiEditComponent } from './multi-edit.component';
import { clearStore, updateFormState } from '../store/multie-edit.actions';

describe('MultiEditComponent', () => {
  let component: MultiEditComponent;
  let fixture: ComponentFixture<MultiEditComponent>;
  let mockMatDialogRef: Partial<MatDialogRef<MultiEditComponent>>;
  let store: MockStore<unknown>;

  beforeEach(async () => {
    mockMatDialogRef = {
      close: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [MultiEditComponent],
      providers: [
        { provide: MatDialogRef, useValue: mockMatDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: {} },
        provideMockStore({
          initialState: {
            formState: {}
          }
        }),
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MultiEditComponent);
    component = fixture.componentInstance;
    store = TestBed.inject(Store) as MockStore<unknown>;
    fixture.detectChanges();
    store.dispatch = jest.fn();
  });

  test('should create', () => {
    expect(component).toBeTruthy();
  });

  test('should return the form control for a given control name', () => {
    const controlName = 'exampleControl';
    const mockFormControl = new FormControl();

    component.form = new FormGroup({ [controlName]: mockFormControl });

    const result = component.getFormControl(controlName);

    expect(result).toBe(mockFormControl);
  });

  test('should close the dialog and dispatch clearStore action on close()', () => {
    component.close();

    expect(mockMatDialogRef.close).toHaveBeenCalled();
    expect(store.dispatch).toHaveBeenCalledWith(clearStore());
  });

  test('should dispatch updateFormState action with form value on updateLocalStore()', () => {
    const mockFormValue = {
      // Mock form value
    };

    component.form = new FormGroup({});
    component.form.setValue(mockFormValue);
    component.updateLocalStore();

    expect(store.dispatch).toHaveBeenCalledWith(updateFormState({ formState: mockFormValue }));
  });

  test('should close the dialog and emit the form value on onSubmit()', () => {
    const mockFormValue = {
      // Mock form value
    };

    component.form = new FormGroup({});
    jest.spyOn(component.dialogRef, 'close');
    component.onSubmit(mockFormValue);

    expect(component.dialogRef.close).toHaveBeenCalledWith(mockFormValue);
  });
});
