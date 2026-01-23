import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivationWorklistComponent } from './activation-worklist.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Store } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { updateFilters, toggleColumn, } from './ngrx/activation-worklist.actions';
import { CommonColumn } from '../../interfaces/columns.interface';
import { MsalService } from '@azure/msal-angular';

describe('ActivationWorklistComponent', () => {
  let component: ActivationWorklistComponent;
  let fixture: ComponentFixture<ActivationWorklistComponent>;
  let store: MockStore;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ActivationWorklistComponent],
      providers: [
        provideMockStore(),
        {provide: MsalService, useValue: {logout: jest.fn()}},

      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivationWorklistComponent);
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

  test('should dispatch updateFilters action on onFilterChanged', () => {
    const key = 'key';
    const value = 'value';

    component.onFilterChanged(value, key);

    expect(store.dispatch).toHaveBeenCalledWith(updateFilters({ key, value }));
  });

  test('should dispatch updateFilters action on onSearch', () => {
    jest.spyOn(component['subject'], 'next');

    component.onSearch({target: { value: 'test'}});

    expect(component['subject'].next).toHaveBeenCalledTimes(1);
  });

  test('should open context menu and set contextMenuActivation on onActivationRightClicked', () => {
    const event = { clientX: 100, clientY: 200 };
    const activation = { id: 1, orderId: 1 };

    component.onActivationRightClicked({ event, row: activation });

    expect(component.contextMenuStyle).toEqual({
      display: 'block',
      position: 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    });
    expect(component.contextMenuActivation).toEqual(activation);
  });

  test('should dispatch updateFilters action on onFilterChanged', () => {
    const event = 'value';
    const col = 'Name'

    component.onFilterChanged(event, col);

    expect(store.dispatch).toHaveBeenCalledWith(updateFilters({ key: col, value: event  }));
  });

  test('should dispatch updateFilters action on onDateRangeFilter', () => {
    const value = {startDate: '2001-11-30', endDate: '2001-11-31'};
    const searchCriteria = { test: {} };
    const col = 'test'

    component.onDateRangeFilter(value, searchCriteria, col);

    expect(store.dispatch).toHaveBeenCalledWith(
      updateFilters({
        key: col,
        value: {
          isEmpty: false,
          dateCohort: "",
          dateRange: {
            endDate: '2001-12-01',
            startDate: '2001-11-30',
          }
        }
      })
    );
  });

  test('should dispatch toggleColumn action on onColumnToggle', () => {
    const col: CommonColumn = { id: 'id', name: 'Name', selected: true, propertyName: 'Test' };

    component.onColSelectClicked(col);

    expect(store.dispatch).toHaveBeenCalledWith(toggleColumn({ columnName: col.propertyName }));
  });

});
