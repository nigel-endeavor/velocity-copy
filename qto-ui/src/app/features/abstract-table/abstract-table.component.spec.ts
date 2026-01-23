import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { Store } from '@ngrx/store';
import { of } from 'rxjs';
import { AbstractTableComponent } from './abstract-table.component';
import { SecurityUtilService } from '../../services/security-util.service';
import { BaseSearchCriteria } from '../../models/base-search-criteria.model';

describe('AbstractTableComponent', () => {
  let component: AbstractTableComponent<any>;
  let fixture: ComponentFixture<AbstractTableComponent<any>>;

  const storeStub = {
    pipe: () => of([]),
    dispatch: () => {}
  };

  const dialogStub = {};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AbstractTableComponent],
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
    fixture = TestBed.createComponent(AbstractTableComponent);
    component = fixture.componentInstance;
    component.searchCriteria = {} as BaseSearchCriteria;
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

  test('should toggle selected view', () => {
    component.cardViewSelected = false;

    component.toggleSelectedView();

    expect(component.cardViewSelected).toBe(true);
  });

  test('should calculate progress bar width', () => {
    const row = { progress: 50 };

    const width = component.getProgressBarWidth(row);

    expect(width).toBe('50%');
  });
});
