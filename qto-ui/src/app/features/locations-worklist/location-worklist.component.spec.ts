import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LocationWorklistComponent } from './location-worklist.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Store } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { updateFilters, updateLocaion, toggleColumn, toggleView } from './ngrx/location-worklist.actions';
import { CommonColumn } from '../../interfaces/columns.interface';
import { MsalService } from '@azure/msal-angular';
import { LocationView } from '../../models/location-view.model';
import * as locationWorklistActions from "./ngrx/location-worklist.actions";
import {locationTableActions, locationTableSelectors} from "./configs/ordering-table.config";
import * as locationWorklistSelectors from "./ngrx/location-worklist.selectors";

describe('LocationWorklistComponent', () => {
  let component: LocationWorklistComponent;
  let fixture: ComponentFixture<LocationWorklistComponent>;
  let store: MockStore;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [LocationWorklistComponent],
      providers: [
        provideMockStore(),
        {provide: MsalService, useValue: {logout: jest.fn()}},

      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationWorklistComponent);
    component = fixture.componentInstance;
    store = TestBed.inject<Store>(Store) as MockStore;
    store.overrideSelector('getIsLoading', false);
    store.overrideSelector('getFilters', {});
    store.overrideSelector('getColumns', []);
    store.overrideSelector('getUpdatedItem', null);
    store.overrideSelector('getStatusesValues', []);
    jest.spyOn(store, 'dispatch');

    component.worklistActions = locationWorklistActions;
    component.tableActions = locationTableActions;
    component.worklistSelectors = locationWorklistSelectors;
    component.tableSelectors = locationTableSelectors;
  });

  test('should create the component', () => {
    expect(component).toBeTruthy();
  });

  test('should navigate to the order page with location query param on onLocationDblClicked', () => {
    const location = { id: 1, orderId: 1 } as LocationView;
    jest.spyOn(component.router, 'navigate');

    component.onLocationDblClicked(location);

    expect(component.router.navigate).toHaveBeenCalledWith(['order', location.orderId, 'location', location.id]);
  });

  test('should reset selectedLocation on onClosePreviewPaneClicked', () => {
    component.selectedLocation = { id: 1, orderId: 1 };

    component.onClosePreviewPaneClicked();

    expect(component.selectedLocation).toBeNull();
  });

  test('should dispatch updateFilters action on onFilterChanged', () => {
    const key = 'key';
    const value = 'value';

    component.onFilterChanged(value, key);

    expect(component.selectedLocation).toBeNull();
    expect(store.dispatch).toHaveBeenCalledWith(updateFilters({ key, value }));
  });

  test('should dispatch updateFilters action on onSearch', () => {
    jest.spyOn(component['subject'], 'next');

    component.onSearch({target: { value: 'test'}});

    expect(component.selectedLocation).toBeNull();
    expect(component['subject'].next).toHaveBeenCalledTimes(1);
  });

  test('should dispatch updateFilters action on onAssignedToChanged', () => {
    const value = 'value';

    component.onAssignedToChanged(value);

    expect(component.selectedLocation).toBeNull();
    expect(store.dispatch).toHaveBeenCalledWith(
      updateFilters({ key: 'provisioner', value: value ? value.split(', ') : [] })
    );
  });

  test('should dispatch note save and updateLocaion actions on clickUpdate', () => {
    component.note = {
      save: jest.fn()
    };

    component.clickUpdate();

    expect(component.note.save).toHaveBeenCalled();
    expect(store.dispatch).toHaveBeenCalledWith(updateLocaion({ location: component.selectedLocation }));
  });

  test('should open context menu and set contextMenuLocation on onLocationRightClicked', () => {
    const event = { clientX: 100, clientY: 200 };
    const location = { id: 1, orderId: 1 };

    component.onLocationRightClicked({ event, row: location });

    expect(component.contextMenuStyle).toEqual({
      display: 'block',
      position: 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    });
    expect(component.contextMenuLocation).toEqual(location);
  });

  test('should return true if the user is readonly', () => {
    jest.spyOn(component.securityUtils, 'userHasPermission').mockReturnValue(false);

    const isReadonlyUser = component.getIsReadonlyUser();

    expect(isReadonlyUser).toBe(true);
    expect(component.securityUtils.userHasPermission).toHaveBeenCalledWith('user');
  });

  test('should dispatch updateFilters action on onFilter', () => {
    const event = { target: { value: 'value' } };
    jest.spyOn(component['subject'], 'next');
    const col: CommonColumn = { id: 'id', name: 'Name', selected: true };

    component.onFilter(event, col);

    expect(component.selectedLocation).toBeNull();
    expect(component['subject'].next).toHaveBeenCalledTimes(1);
  });

  test('should dispatch updateFilters action on onDateComparisonFilter', () => {
    const value = 'BEFORE';
    const searchCriteria = { test: {} };
    const col: CommonColumn = { id: 'id', name: 'Name', selected: true, propertyName: 'test' };

    component.onDateComparisonFilter(value, searchCriteria, col);

    expect(component.selectedLocation).toBeUndefined();
    expect(store.dispatch).toHaveBeenCalledWith(
      updateFilters({ key: col.propertyName, value: { comparison: 'BEFORE'} })
    );
  });

  test('should dispatch toggleColumn action on onColumnToggle', () => {
    const col: CommonColumn = { id: 'id', name: 'Name', selected: true, propertyName: 'Test' };

    component.onColSelectClicked(col);

    expect(component.selectedLocation).toBeUndefined();
    expect(store.dispatch).toHaveBeenCalledWith(toggleColumn({ columnName: col.propertyName }));
  });

  test('should dispatch toggleView action on onViewToggle', () => {

    component.toggleSelectedView();

    expect(component.selectedLocation).toBeUndefined();
    expect(store.dispatch).toHaveBeenCalledWith(toggleView());
  });
});
