import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServiceWorklistComponent } from './service-worklist.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Store } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { updateFilters, updateService, toggleColumn, toggleView } from './ngrx/service-worklist.actions';
import { CommonColumn } from '../../interfaces/columns.interface';
import { MsalService } from '@azure/msal-angular';
import { ServiceView } from '../../models/service-view.model';
import { MatLegacyDialogModule as MatDialogModule } from '@angular/material/legacy-dialog';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ServiceWorklistComponent', () => {
  let component: ServiceWorklistComponent;
  let fixture: ComponentFixture<ServiceWorklistComponent>;
  let store: MockStore;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, MatDialogModule, HttpClientTestingModule ],
      declarations: [ServiceWorklistComponent],
      providers: [
        provideMockStore(),
        { provide: MsalService, useValue: {logout: jest.fn()} },

      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceWorklistComponent);
    component = fixture.componentInstance;
    store = TestBed.inject<Store>(Store) as MockStore;
    store.overrideSelector('getIsLoading', false);
    store.overrideSelector('getFilters', {});
    store.overrideSelector('getColumns', []);
    store.overrideSelector('getUpdatedItem', null);
    store.overrideSelector('getStatusesValues', []);
    jest.spyOn(store, 'dispatch');
  });

  test('should create the component', () => {
    expect(component).toBeTruthy();
  });

  test('should navigate to the order page with location query param on onLocationDblClicked', () => {
    const service = { id: 1, orderId: 1,  locationId:1 } as ServiceView;
    jest.spyOn(component.router, 'navigate');

    component.onServiceDblClicked(service);

    expect(component.router.navigate).toHaveBeenCalledWith(['order', service.orderId, 'location', service.locationId, 'service', service.id]);
  });

  test('should reset selectedLocation on onClosePreviewPaneClicked', () => {
    component.selectedService = { id: 1, orderId: 1 };

    component.onClosePreviewPaneClicked();

    expect(component.selectedService).toBeNull();
  });

  test('should dispatch updateFilters action on onFilterChanged', () => {
    const key = 'key';
    const value = 'value';

    component.onFilterChanged(value, key);

    expect(component.selectedService).toBeNull();
    expect(store.dispatch).toHaveBeenCalledWith(updateFilters({ key, value }));
  });

  test('should dispatch updateFilters action on onSearch', () => {
    jest.spyOn(component['subject'], 'next');

    component.onSearch({target: { value: 'test'}});

    expect(component.selectedService).toBeNull();
    expect(component['subject'].next).toHaveBeenCalledTimes(1);
  });

  test('should dispatch note save and updateLocaion actions on clickUpdate', () => {
    component.note = {
      save: jest.fn()
    };

    component.clickUpdate();

    expect(component.note.save).toHaveBeenCalled();
    expect(store.dispatch).toHaveBeenCalledWith(updateService({ service: component.selectedService }));
  });

  test('should open context menu and set contextMenuLocation on onLocationRightClicked', () => {
    const event = { clientX: 100, clientY: 200 };
    const location = { id: 1, orderId: 1 };

    component.onServiceRightClicked({ event, row: location });

    expect(component.contextMenuStyle).toEqual({
      display: 'block',
      position: 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    });
    expect(component.contextMenuService).toEqual(location);
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

    expect(component.selectedService).toBeNull();
    expect(component['subject'].next).toHaveBeenCalledTimes(1);
  });

  test('should dispatch toggleColumn action on onColumnToggle', () => {
    const col: CommonColumn = { id: 'id', name: 'Name', selected: true, propertyName: 'Test' };

    component.onColSelectClicked(col);

    expect(component.selectedService).toBeUndefined();
    expect(store.dispatch).toHaveBeenCalledWith(toggleColumn({ columnName: col.propertyName }));
  });

  test('should dispatch toggleView action on onViewToggle', () => {

    component.toggleSelectedView();

    expect(component.selectedService).toBeUndefined();
    expect(store.dispatch).toHaveBeenCalledWith(toggleView());
  });
});
