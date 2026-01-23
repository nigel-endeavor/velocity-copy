import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner.component';
import { CustomField, CustomFieldTabValue, CustomFieldType } from 'src/app/models/custom-field.model';
import { CustomFieldService } from 'src/app/services/custom-field.service';
import { ServiceCustomFieldValueService } from 'src/app/services/service-custom-field-value.service';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { VtkCurrencyPipe } from 'src/app/pipes/vtk-currency.pipe';
import { ServiceCustomFieldValue } from 'src/app/models/service-custom-field-value.model';
import { LocationCustomFieldValueService } from 'src/app/services/location-custom-field-value.service';
import { LocationCustomFieldValue } from 'src/app/models/location-custom-field-value.model';

@Component({
  standalone: true,
  selector: 'app-custom-fields',
  templateUrl: './custom-fields.component.html',
  styleUrls: ['./custom-fields.component.scss', '../../order-detail-page/form-styles.scss'],
  imports: [
    CommonModule,
    FormsModule,
    LoadingSpinnerComponent,
    MatCheckboxModule,
    VtkCurrencyPipe
  ]
})
export class CustomFieldsComponent implements OnChanges {

  @Input() type: string; //location or service
  @Input() id: number; //locationId or serviceId
  @Input() tab: CustomFieldTabValue;
  @Input() companyId: number;
  @Input() editing: boolean;

  fields: CustomField[] = [];
  lookups: Map<string, string[]> = new Map();
  values: Map<number, {}>;
  isLoading: boolean = false;

  public CustomFieldType = CustomFieldType;

  constructor(
    private customFieldService: CustomFieldService,
    private serviceCustomFieldValueService: ServiceCustomFieldValueService,
    private locationCustomFieldValueService: LocationCustomFieldValueService,
    private lookupValueServicee: LookupValueService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['type'] || changes['tab'] || changes['id'] || changes['companyId']) {
      this.isLoading = true;
      this.values = new Map();
      //load custom fields
      this.customFieldService.findByTab(this.tab).subscribe(fields => {
        this.fields = fields;
        this.fields.forEach(field => {
          //load lookup values
          if (field.type === CustomFieldType.DROPDOWN) {
            this.lookupValueServicee.getValues(field.name, this.companyId).subscribe(values => {
              this.lookups.set(field.name, values);
            });
          }
        });
      });
      //load custom field values
      if (this.type == 'service') {
        this.serviceCustomFieldValueService.findByServiceId(this.id).subscribe(values => {
          values.forEach(value => {
            this.values.set(value.customFieldId, value);
          });
          this.isLoading = false;
        });
      } else if (this.type == 'location') {
        this.locationCustomFieldValueService.findByLocationId(this.id).subscribe(values => {
          values.forEach(value => {
            this.values.set(value.customFieldId, value);
          });
          this.isLoading = false;
        });
      } else if (this.type == 'newOrder') {
        this.isLoading = false;
      }
    }
  }

  saveValues(): void {
    this.isLoading = true;
    if (this.type == 'service') {
      let values = this.fields.map(field => {
        let v = this.values.get(field.id) as ServiceCustomFieldValue;
        return {
          id: v?.id || null,
          serviceId: this.id,
          customFieldId: field.id,
          value: v?.value !== undefined ? v.value : '',
          version: v?.version || 0
        } as ServiceCustomFieldValue;
      });
      values = values.filter(value => value.value !== '');
      this.serviceCustomFieldValueService.saveValues(values).subscribe(res => {
        res.forEach(value => {
          this.values.set(value.customFieldId, value);
        });
        this.isLoading = false;
      });
    } else if (this.type == 'location') {
      let values = this.fields.map(field => {
        let v = this.values.get(field.id) as LocationCustomFieldValue;
        return {
          id: v?.id || null,
          locationId: this.id,
          customFieldId: field.id,
          value: v?.value !== undefined ? v.value : '',
          version: v?.version || 0
        } as LocationCustomFieldValue;
      });
      values = values.filter(value => value.value !== '');
      this.locationCustomFieldValueService.saveValues(values).subscribe(res => {
        res.forEach(value => {
          this.values.set(value.customFieldId, value);
        });
        this.isLoading = false;
      });
    }
  }

  getValue(field: CustomField): string {
    if (field.type === CustomFieldType.BOOLEAN) {
      //@ts-ignore
      return this.values.get(field.id)?.value === 'true' || false;
    }
    //@ts-ignore
    return this.values.get(field.id)?.value || '';
  }

  setValue(field: CustomField, value: any): void {
    let v = this.values.get(field.id) as ServiceCustomFieldValue;
    this.values.set(field.id, { value, id: v?.id || null, version: v?.version || 0 });
  }

  getOptions(field: CustomField): string[] {
    return this.lookups.get(field.name) || [];
  }

}
