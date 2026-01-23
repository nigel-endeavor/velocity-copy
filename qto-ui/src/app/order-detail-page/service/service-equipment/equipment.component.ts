import { Component, ElementRef, EventEmitter, inject, Inject, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ValidatorsModule } from '../../../directives/validators.module';
import { ServiceEquipment } from '../../../models/service-equipment-model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { select, Store } from '@ngrx/store';
import { deleteServiceEquipment, loadLookupValuesByKey, saveServiceEquipment, setSelectedServiceEquipment } from './ngrx/service-equipment.actions';
import { getEquipmentSubTypes, getEquipmentTypes, getOwnerships, getShippingMethods } from './ngrx/service-equipment.selectors';
import { CommonModule } from '@angular/common';
import { MatDivider, MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { LookupValue } from "../../../models/lookup-value.model";

@Component({
  standalone: true,
  selector: 'app-equipment',
  templateUrl: './equipment.component.html',
  styleUrls: ['./equipment.component.scss'],
    imports: [
        CommonModule,
        FormsModule,
        ValidatorsModule,
        MatFormFieldModule,
        MatCheckboxModule,
        MatSelectModule,
        MatIconModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        MatTooltipModule,
        MatInputModule,
        FormsModule,
        MatButtonModule,
        MatDatepickerModule,
        MatDividerModule,
        MatCardModule
    ]
})
export class EquipmentComponent implements OnInit {

  public equipment: ServiceEquipment;
  @Output() equipmentSave = new EventEmitter<ServiceEquipment>();
  @Output() equipmentCancel = new EventEmitter<void>();

  readonly dialogRef = inject(MatDialogRef<EquipmentComponent>);
  @ViewChild('equipmentForm') equipmentForm: NgForm;

  public equipmentTypes$ = this.store.pipe(select(getEquipmentTypes));
  public equipmentSubTypes$ = this.store.pipe(select(getEquipmentSubTypes));
  filteredEquipmentSubTypes: any[] = [];
  public ownerships$ = this.store.pipe(select(getOwnerships));
  public shippingMethods$ = this.store.pipe(select(getShippingMethods));

   public equipmentTypeTooltip = 'Category of equipment/device.';
  public subEquipmentTypeTooltip = 'Sub category of equipment/device, based on the parent equipment type.';
  public makeTooltip = 'Brand or common name of the equipment/device.';
  public modelTooltip = 'Model Number of equipment/device.';
  public serialNumberTooltip = 'Serial # specific to this piece of equipment/device.';
  public macAddressTooltip = 'Primary MAC address of the device or primary port you wish to identify.';
  public ownershipTooltip = 'Entity financially resposible for the equipment';
  public decommissionTooltip = 'Checking this box marks the equipment a inactive with the current date.';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private store: Store) {
    this.equipment = { ...data.equipment };
  }

  ngOnInit(): void {
    this.store.dispatch(loadLookupValuesByKey({ key: "equipmentTypes", lookupKey: 'EQUIPMENT_TYPE' }));
    this.store.dispatch(loadLookupValuesByKey({ key: "equipmentSubTypes", lookupKey: 'EQUIPMENT_SUB_TYPE' }));
    this.store.dispatch(loadLookupValuesByKey({ key: "ownerships", lookupKey: 'OWNERSHIP' }));
    this.store.dispatch(loadLookupValuesByKey({ key: "shippingMethods", lookupKey: 'SHIPPING_METHOD' }));
    if (this.equipment.equipmentType) {
      let foundType: LookupValue | undefined = this.getEquipmentTypeByValue(this.equipment.equipmentType);
      if (foundType) {
        this.equipmentSubTypes$.subscribe(types => {
          // @ts-ignore
          this.filteredEquipmentSubTypes = types.filter(subType => subType.parentId === foundType.id);
        });
      }
    }
  }

  onEquipmentTypeChange(selectedType: string) {
    this.equipment.equipmentType = selectedType;
    const selectedTypeObject = this.getEquipmentTypeByValue(selectedType);
    if (selectedTypeObject) {
      const selectedTypeId = selectedTypeObject.id;
      this.equipmentSubTypes$.subscribe(subTypes => {
        this.filteredEquipmentSubTypes = subTypes.filter(subType => subType.parentId === selectedTypeId);
      });
    }
  }

  private getEquipmentTypeByValue(value: string)  {
    // Implement logic to retrieve equipment type object by its value from equipmentTypes$ list
    let foundType: LookupValue | undefined;
    this.equipmentTypes$.subscribe(types => {
      foundType = types.find(type => type.value === value);
    });
    return foundType;
  }

  saveEquipment(): void {
    if (this.equipmentForm.invalid) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    this.store.dispatch(saveServiceEquipment({ serviceEquipment: this.equipment }));
    this.dialogRef.close();
  }

  deleteEquipment(): void {
    this.store.dispatch(deleteServiceEquipment({ serviceEquipment: this.equipment }));
    this.dialogRef.close();
  }

  cancelDialog(): void {
    this.store.dispatch(setSelectedServiceEquipment({ serviceEquipment: null }));
    this.dialogRef.close();
  }
}
