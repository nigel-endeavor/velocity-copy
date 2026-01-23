import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { Store } from '@ngrx/store';
import { of } from 'rxjs';
import { TableDeprecatedComponent, Column } from './table-deprecated.component';
import { SecurityUtilService } from '../../services/security-util.service';

describe('TableDeprecatedComponent', () => {
  let component: TableDeprecatedComponent<any>;
  let fixture: ComponentFixture<TableDeprecatedComponent<any>>;

  const storeStub = {
    pipe: () => of([]),
    dispatch: () => {}
  };

  const dialogStub = {};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TableDeprecatedComponent],
      providers: [
        { provide: Store, useValue: storeStub },
        { provide: MatDialog, useValue: dialogStub },
        {
          provide: SecurityUtilService, useValue: {
            // Define the getLoggedInUser function
            userHasPermission: () => {
              return true;
            }
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableDeprecatedComponent);
    component = fixture.componentInstance;
    component.getModelService = jest.fn(() => (
      {
        path: 'test',
        search: jest.fn(() => of({
          offset: 0,
          limit: 10,
          total: 1,
          collection: [],
          linkRels: {}
        })),
        ['export']: jest.fn(() => of(new Blob()))
      }));
    fixture.detectChanges();
  });

  test('should create', () => {
    expect(component).toBeTruthy();
  });

  test('should fetch data', () => {
   jest. spyOn(component.getModelService(), 'search').mockReturnValue(of({ collection: [] }));

    component.fetch();

    expect(component.rows).toEqual([]);
    expect(component.cards).toEqual([]);
    expect(component.offset).toBe(0);
    expect(component.limit).toBe(10);
    expect(component.total).toBe(1);
  });

  test('should toggle selected view', () => {
    component.cardViewSelected = false;

    component.toggleSelectedView();

    expect(component.cardViewSelected).toBe(true);
  });

  it('should get cell value', () => {
    const row = { id: 1, name: 'John Doe' };
    const col: Column = { name: 'ID', propertyName: 'id' };

    const value = component.getCellValue(row, col);

    expect(value).toBe(1);
  });

  test('should get criteria model value', () => {
    const col: Column = { name: 'ID', propertyName: 'id' };
    component.searchCriteria = { id: 1 };

    const value = component.getCriteriaModelValue(col);

    expect(value).toBe(1);
  });

  test('should get sort field column name', () => {
    const col: Column = { name: 'ID', propertyName: 'id' };
    component.columns = [col];
    component.searchCriteria = { sortField: 'id' };

    const columnName = component.getSortFieldColName();

    expect(columnName).toBe('ID');
  });

  test('should calculate progress bar width', () => {
    const row = { progress: 50 };

    const width = component.getProgressBarWidth(row);

    expect(width).toBe('50%');
  });
});
