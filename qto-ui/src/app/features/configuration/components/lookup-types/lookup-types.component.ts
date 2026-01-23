import { Component, ElementRef, ViewChild } from '@angular/core';
import { BaseSearchCriteria } from 'src/app/models/base-search-criteria.model';
import { LookupTypeService } from 'src/app/services/lookup-type.service';
import { setSelectedTab } from '../../ngrx/configuration.actions';
import { Store } from '@ngrx/store';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { LookupType } from 'src/app/models/lookup-type.model';
import { ROYGBIV } from 'src/app/utilities';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { plainToClass } from 'class-transformer';
import { LookupValueSearchCriteria } from 'src/app/models/lookup-value-search-criteria.model';

@Component({
  selector: 'app-lookup-types',
  templateUrl: './lookup-types.component.html',
  styleUrls: ['./lookup-types.component.scss']
})
export class LookupTypesComponent {

  lookupTypeCategories: { name: string, expanded: boolean, lookupTypes: LookupType[] }[] = [];
  selectedLookupType: LookupType | null = null;
  selectedLookupTypeValues: LookupValue[] = [];

  parentLookupType: LookupType | null = null;
  parentLookupValues: LookupValue[] = [];
  selectedParentLookupValue: LookupValue | null = null;

  grandParentLookupType: LookupType | null = null;
  grandParentLookupValues: LookupValue[] = [];
  selectedGrandParentLookupValue: LookupValue | null = null;

  saving = false;

  constructor(
    private store: Store,
    private lookupTypeService: LookupTypeService,
    private lookupValueService: LookupValueService
  ) {}

  ngOnInit(): void {
    this.store.dispatch(setSelectedTab({ tab: 'Lookups' }));
    let criteria = new BaseSearchCriteria();
    this.lookupTypeService.search(criteria).subscribe((result: PaginatedResult<LookupType>) => {
      this.lookupTypeCategories = [{ name: 'Uncategorized', expanded: false, lookupTypes: []}];
      result.collection.forEach(lookupType => {
        if (lookupType.category) {
          if (!this.lookupTypeCategories.some(category => category.name == lookupType.category)) {
            this.lookupTypeCategories.push({ name: lookupType.category, expanded: false, lookupTypes: [] });
          }
          this.lookupTypeCategories.find(category => category.name == lookupType.category)!.lookupTypes.push(lookupType);
        } else {
          this.lookupTypeCategories.find(category => category.name == 'Uncategorized')!.lookupTypes.push(lookupType);
        }
      });
      this.lookupTypeCategories.sort((a, b) => a.name.localeCompare(b.name));
    });
  }

  ngOnDestroy(): void {
    this.selectedLookupType = null;
    this.selectedLookupTypeValues = [];
  }

  onCategoryClicked(category: { name: string, expanded: boolean, lookupTypes: LookupType[] }): void {
    category.expanded = !category.expanded;
    this.lookupTypeCategories.filter(c => c != category).forEach(category => category.expanded = false);
  }

  filteredLookupTypes(category: any) {
    return category?.lookupTypes?.filter((l: any) => l.typeCode !== 'TENANT_SERVICE_TYPES') || [];
  }

  getFilteredCount(category: any): number {
    if (category.name === 'Product Catalog') {
      return category?.lookupTypes?.filter((l: any) => l.typeCode !== 'TENANT_SERVICE_TYPES')?.length || 0;
    }
    return category?.lookupTypes?.length || 0;
  }


  onLookupTypeClicked(lookupType: LookupType): void {
    this.selectedLookupType = plainToClass(LookupType, lookupType);
    this.selectedLookupTypeValues = [];
    this.parentLookupType = null;
    this.parentLookupValues = [];
    this.selectedParentLookupValue = null;
    this.grandParentLookupType = null;
    this.grandParentLookupValues = [];
    this.selectedGrandParentLookupValue = null;
    if (lookupType.parentLookupTypeId) {
      this.parentLookupType = Array.from(this.lookupTypeCategories.values()).map(category => category.lookupTypes).flat().find(lookupType => lookupType.id == this.selectedLookupType!.parentLookupTypeId)!;
      if (this.parentLookupType.parentLookupTypeId) {
        //grandparent
        this.grandParentLookupType = Array.from(this.lookupTypeCategories.values()).map(category => category.lookupTypes).flat().find(lookupType => lookupType.id == this.parentLookupType!.parentLookupTypeId)!;
        this.lookupValueService.find(this.grandParentLookupType.typeCode).subscribe(values => {
          this.grandParentLookupValues = values;
        });
      } else {
        this.lookupValueService.find(this.parentLookupType.typeCode).subscribe(values => {
          this.parentLookupValues = values;
        });
      }
    } else {
      this.lookupValueService.find(lookupType.typeCode).subscribe(values => {
        this.selectedLookupTypeValues = values;
      });
    }
  }

  onGrandParentLookupValueSelected(lookupValue: LookupValue): void {
    this.selectedParentLookupValue = null;
    this.parentLookupValues = [];
    this.selectedLookupTypeValues = [];
    this.selectedGrandParentLookupValue = lookupValue;
    let criteria = new LookupValueSearchCriteria();
    criteria.typeCode = this.parentLookupType!.typeCode;
    criteria.parentId = lookupValue.id;
    this.lookupValueService.search(criteria).subscribe(values => {
      this.parentLookupValues = values.collection;
    });
  }

  onParentLookupValueSelected(lookupValue: LookupValue): void {
    this.selectedParentLookupValue = lookupValue;
    let criteria = new LookupValueSearchCriteria();
    criteria.typeCode = this.selectedLookupType!.typeCode;
    criteria.parentId = lookupValue.id;
    this.lookupValueService.search(criteria).subscribe(values => {
      this.selectedLookupTypeValues = values.collection;
    });
  }

  onAddClicked(): void {
    if (this.parentLookupType && !this.selectedParentLookupValue || this.grandParentLookupType && !this.selectedGrandParentLookupValue) {
      return;
    }
    let value = new LookupValue();
    value.active = true;
    this.selectedLookupTypeValues.unshift(value);
  }

  onRemoveClicked(lookupValue: LookupValue): void {
    this.selectedLookupTypeValues = this.selectedLookupTypeValues.filter(value => value != lookupValue);
  }

  @ViewChild('changesSaved') changesSaved: ElementRef;
  onSaveClicked(): void {
    //validate
    if (this.selectedLookupTypeValues.some(value => !value.display)) {
      throw new Error('All empty fields must be populated.');
    }
    if (this.selectedLookupTypeValues.map(v => v.display.toLocaleLowerCase()).filter((value, index, arr) => arr.indexOf(value) !== index).length > 0) {
      throw new Error('Duplicate values are not allowed.');
    }
    let valuesOverLimit = this.selectedLookupTypeValues.filter(value => value.display.length > 100);
    if (valuesOverLimit.length > 0) {
      throw new Error('Display values must be less than 100 characters. The following values are over the limit: ' + valuesOverLimit.map(value => value.display).join(', '));
    }
    //save
    if (this.selectedParentLookupValue) {
      this.selectedLookupTypeValues.forEach(value => value.parentId = this.selectedParentLookupValue!.id);
    }
    this.saving = true;

    this.lookupTypeService.setValues(this.selectedLookupType!, this.selectedLookupTypeValues).subscribe(() => {
      if (this.saving) {
        let criteria = new LookupValueSearchCriteria();
        criteria.typeCode = this.selectedLookupType!.typeCode;
        if (this.selectedParentLookupValue) {
          criteria.parentId = this.selectedParentLookupValue.id;
        }
        this.lookupValueService.search(criteria).subscribe(values => {
          this.saving = false;
          this.selectedLookupTypeValues = values.collection;
          //display changes saved icon
          this.changesSaved.nativeElement.style.display = 'flex';
          //fade out icon
          setTimeout(() => {
            if (this.changesSaved) {
              this.changesSaved.nativeElement.style.opacity = '0';
              this.changesSaved.nativeElement.style['-webkit-transition'] = 'opacity 2s ease-in-out';
            }
          }, 1000)
          //reset icon
          setTimeout(() => {
            if (this.changesSaved) {
              this.changesSaved.nativeElement.style.opacity = 1;
              this.changesSaved.nativeElement.style.display = 'none';
            }
          }, 3000);
        });
      }
    }, error => {
      this.saving = false;
      alert(error.error);
    });
  }

  //Drag and Drop Functionality
  @ViewChild('placeholder') placeholder: ElementRef;
  @ViewChild('dropzone') dropzone: ElementRef;
  placeholderStyles: {} = { 'visibility': 'hidden', 'border': 'none', 'height': '0px' };
  draggedValue: any;
  draggedEl: any;

  onDragStart(event: Event, value: LookupValue): void {
    this.draggedValue = value;
    this.draggedEl = event.target;
    this.draggedEl.style.opacity = '0.4';
  }

  onParentDragOver(event: Event): void {
    event.preventDefault();
  }

  onChildDragOver(event: Event): void {
    let target = (event.target as HTMLElement);
    while (!target.classList.contains('value') && target.parentElement) {
      target = target.parentElement;
    }
    if (!target) {
      return;
    }
    this.placeholderStyles = {'visibility': '', 'height': '30px', 'background-color': '#5DCADE'};
    if (this.isBefore(this.draggedEl!, target)) {
      target.parentElement?.insertBefore(this.placeholder.nativeElement, target);
    } else {
      target.parentElement?.insertBefore(this.placeholder.nativeElement, target.nextSibling);
    }
  }

  isBefore(el1: HTMLElement, el2: HTMLElement): boolean {
    if (el1 && el2 && el1.parentNode === el2.parentNode) {
      for (let current = el1.previousSibling; current && current.nodeType !== 9; current = current.previousSibling) {
        if (current === el2) {
          return true;
        }
      }
    }
    return false;
  }

  onDrop(event: Event): void {
    if (this.draggedEl) {
      const placeholderIndex = [...this.dropzone.nativeElement.children].indexOf(this.placeholder.nativeElement) - 1;
      this.selectedLookupTypeValues.splice(placeholderIndex, 0, this.draggedValue);
      const draggedIndex = [...this.dropzone.nativeElement.children].indexOf(this.draggedEl) - 1;
      if (draggedIndex != -1) {
        this.selectedLookupTypeValues.splice(draggedIndex, 1);
      }
      this.draggedValue = null;
      if(this.draggedEl) {
        this.draggedEl.style.opacity = '1';
        this.draggedEl = null;
      }
      this.placeholderStyles = { 'visibility': 'hidden', 'border': 'none', 'height': '0px' }
    }
  }

  onDragEnd(event: Event): void {
    if(this.draggedEl) {
      this.draggedEl.style.opacity = '1';
      this.draggedEl = null;
    }
    this.placeholderStyles = { 'visibility': 'hidden', 'border': 'none', 'height': '0px' };
  }
  //End Drag and Drop Functionality
}
