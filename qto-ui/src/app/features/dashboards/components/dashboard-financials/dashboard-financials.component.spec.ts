import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { DashboardFinancialsComponent } from './dashboard-financials.component';
import { WipService } from '../../../../services/wip.service';
import { ExcelService } from '../../../../services/excel-service';
import { LookupValueService } from '../../../../services/lookup-value.service';
import {provideMockStore} from "@ngrx/store/testing";

describe('DashboardFinancialsComponent', () => {
  let component: DashboardFinancialsComponent;
  let fixture: ComponentFixture<DashboardFinancialsComponent>;
  let wipServiceStub: Partial<WipService>;
  let excelServiceSpy: jest.Mocked<ExcelService>;
  let lookupValueServiceStub: Partial<LookupValueService>;

  beforeEach(async () => {
    wipServiceStub = {
      getMonthlySpend: jest.fn().mockReturnValue(of([])),
      getIncrementalNetworkSpend: jest.fn().mockReturnValue(of([])),
      getUnbillableNetworkExpenseAccrual: jest.fn().mockReturnValue(of([]))
    };
    excelServiceSpy = {
      exportToExcel: jest.fn()
    } as unknown as jest.Mocked<ExcelService>;
    lookupValueServiceStub = {
      getValues: jest.fn().mockReturnValue(of([]))
    };

    await TestBed.configureTestingModule({
      declarations: [DashboardFinancialsComponent],
      providers: [
        provideMockStore(),
        {provide: WipService, useValue: wipServiceStub},
        {provide: ExcelService, useValue: excelServiceSpy},
        {provide: LookupValueService, useValue: lookupValueServiceStub}
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardFinancialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize carrierOpts on ngOnInit', () => {
    const carrierOpts = ['carrier1', 'carrier2'];
    jest.spyOn(lookupValueServiceStub, 'getValues').mockReturnValue(of(carrierOpts));
    component.ngOnInit();
    expect(lookupValueServiceStub.getValues).toHaveBeenCalledWith('CARRIER', null);
    expect(component.carrierOpts).toEqual(carrierOpts);
  });

  it('should create monthly spend chart on ngOnChanges', () => {
    const wipServiceViews = [{carrier: 'carrier1', dataProvisioningCompleteDate: new Date(), serviceMrc: 100}];
    jest.spyOn(wipServiceStub, 'getMonthlySpend').mockReturnValue(of(wipServiceViews));
    jest.spyOn(component, 'groupBy').mockReturnValue({carrier1: [wipServiceViews[0]]});
    jest.spyOn(component, 'getMonthlySpend').mockReturnValue([{label: 'carrier1', data: [100]}]);
    jest.spyOn(component, 'createBarChart');

    component.selectedCompanies = 'company1';
    component.ngOnChanges();

    expect(wipServiceStub.getMonthlySpend).toHaveBeenCalledWith('company1', undefined, undefined);
    expect(component.groupBy).toHaveBeenCalledWith('carrier', wipServiceViews);
    expect(component.getMonthlySpend).toHaveBeenCalledWith({carrier1: [wipServiceViews[0]]});
    expect(component.createBarChart).toHaveBeenCalledWith({"chartContainerId": "monthlySpendChart", "chartJSConfig": {}, "chartType": "bar", "expanded": false}, [{
      label: 'carrier1',
      data: [100]
    }], undefined, 'y');
  });
});
