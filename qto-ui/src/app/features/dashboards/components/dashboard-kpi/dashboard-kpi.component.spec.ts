import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardKpiComponent } from './dashboard-kpi.component';
import { CarriersService } from '../../../../services/providers.service';
import { HttpClientModule } from '@angular/common/http';
import {provideMockStore} from "@ngrx/store/testing";


describe('DashboardKpiComponent', () => {
  let component: DashboardKpiComponent;
  let fixture: ComponentFixture<DashboardKpiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      declarations: [ DashboardKpiComponent ],
      providers: [
        provideMockStore(),
        CarriersService
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardKpiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
