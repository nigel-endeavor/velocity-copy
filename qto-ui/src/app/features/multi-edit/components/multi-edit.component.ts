import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { MultiEditData, MultiEditField } from '../interfaces/multi-edit-data.interface';
import { InputTypes } from '../enums/input-types.enum';
import { select, Store } from '@ngrx/store';
import { clearStore, updateFormState } from '../store/multie-edit.actions';
import { getFormState } from '../store/multie-edit.selectors';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { map, Observable, take } from 'rxjs';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';

@Component({
  selector: 'app-components',
  templateUrl: './multi-edit.component.html',
  styleUrls: ['./multi-edit.component.scss']
})
export class MultiEditComponent implements OnInit {
  protected readonly InputTypes = InputTypes;

  public form: FormGroup;
  public formState$ = this.store.pipe(select(getFormState));
  public header = 'Multi Edit';

  constructor(
    public dialogRef: MatDialogRef<MultiEditComponent>,
    public store: Store,
    @Inject(MAT_DIALOG_DATA) public data: {
      header: string,
      selectLists: Observable<Record<string, any[]>>,
      searchParams: Record<string, CommonDropdownSearchCriteria>
      editableSections: MultiEditData[],
      updateFunctions: Record<string, (value: string | number, key: string) => void>,
      cantEdit: MultiEditFailureInterface[]
    }
  ) {
    if (data.header) {
      this.header = data.header;
    }
  }

  ngOnInit(): void {
    let controls: Record<string, FormControl> = {
      invisControl: new FormControl({value: null, disabled: false})
    };
    this.formState$.pipe(take(1), map(formState => {
      this.data.editableSections.forEach((section: MultiEditData) => {
        section.fields.forEach((field: MultiEditField)  => {
          if (field.value) {
            controls[field.value] = new FormControl({
              value: formState[field.value] ? formState[field.value] :
                field.value === 'number' ? 0 : '',
              disabled: !formState[field.value]
            });
          } else if (field.type === InputTypes.Form) {
            field.disabled = true;
            field.fields?.forEach(subfield => {
              if (subfield.required) {
                controls[subfield.value] = new FormControl({
                  value: formState[subfield.value] ? formState[subfield.value] :
                    subfield.value === 'number' ? 0 :  '',
                  disabled: true,
                }, [Validators.required]);
              } else {
                controls[subfield.value] = new FormControl({
                  value: formState[subfield.value] ? formState[subfield.value] :
                    subfield.value === 'number' ? 0 :  '',
                  disabled: true,
                });
              }
            })
          }
        })
      });
    })).subscribe();
    this.form = new FormGroup(controls);
  }

  getFormControl(controlName: string): FormControl {
    return this.form.get(controlName) as FormControl
  }

  onSubmit(form: any): void {
    this.dialogRef.close(form);
  }

  close() {
    this.dialogRef.close();
    this.store.dispatch(clearStore());
  }

  updateLocalStore(event?: any) {
    this.store.dispatch(updateFormState({
      formState: this.form.value
    }))
  }


  updateFormState(control: string, event?: any) {
    this.form.controls[control].setValue(event);
    setTimeout(() => this.store.dispatch(updateFormState({
      formState: this.form.value
    })), 200);
  }

  toggleControl(controlName: string): void {
    const control = this.form.get(controlName);
    if (control?.enabled) {
      control.setValue('');
      this.updateLocalStore();

      control.disable();
    } else {
      control?.enable();
    }
  }

  toggleFormControls(control: MultiEditField): void {
    if (control.type === InputTypes.Form) {
      control.fields?.forEach(subfield => {
        if (subfield.required) {
          this.toggleControl(subfield.value);
        }
      });
      control.disabled = !control.disabled;
    }
  }

  isFormEmpty(): boolean {
    return Object.keys(this.form.value).length <= 1;
  }

  getFieldsString(fieldValues: string[]): string {
    let result: string[] = [];
    this.data.editableSections.forEach(section => {
      section.fields.forEach(field => {
        if (fieldValues.includes(field.value)) {
          result.push(field.name);
        }
      })
    });
    return result.join(', ')
  }

  clearDate(control: any) {
    this.form.controls[control].setValue('');
    this.updateLocalStore();
  }

  getSectionChangeCount(section: MultiEditData): number {
    let count = 0;
    section.fields.forEach(field => {
      if (field.type != InputTypes.Form && this.form.controls[field.value].enabled) {
        count++;
      } else if (field.type == InputTypes.Form && !field.disabled) {
        count++;
      }
    });
    return count;
  }

  getPreview(): any[] {
    let result: any[] = [];
    this.data.editableSections.forEach(section => {
      section.fields.forEach(field => {
        if (field.type != InputTypes.Form && this.form.controls[field.value].enabled) {
          if (this.form.value[field.value]) {
            switch (field.type) {
              case InputTypes.NativeSelect:
                result.push({ name: field.name, value: `${JSON.parse(this.form.value[field.value])[field.select!.label]}` });
                break;
              case InputTypes.DateTime:
                result.push({ name: field.name, value: `${new Date(this.form.value[field.value]).toLocaleDateString()}` });
                break;
              default:
                result.push({ name: field.name, value: `${this.form.value[field.value]}` });
            }
          } else {
            let value = ['masterCustomer', 'serviceNote'].includes(field.value) ? 'No Change' : 'No Value';
            result.push({ name: field.name, value });
          }
        } else if (field.type == InputTypes.Form) {
          let subfields: any[] = [];
          field.fields?.forEach(subfield => {
            if (this.form.controls[subfield.value].enabled) {
              if (this.form.value[subfield.value]) {
                switch (subfield.type) {
                  case InputTypes.NativeSelect:
                    subfields.push({ name: subfield.name, value: `${JSON.parse(this.form.value[subfield.value])[subfield.select!.label]}` });
                    break;
                  case InputTypes.DateTime:
                    subfields.push({ name: subfield.name, value: `${new Date(this.form.value[subfield.value]).toLocaleDateString()}` });
                    break;
                  default:
                    subfields.push({ name: subfield.name, value: `${this.form.value[subfield.value]}` });
                }
              } else {
                subfields.push({ name: subfield.name, value: 'No Value' });
              }
            }
          })
          if (subfields.length > 0) {
            result.push({ name: field.name, subfields: subfields });
          }
        }
      })
    });
    return result;
  }

}
