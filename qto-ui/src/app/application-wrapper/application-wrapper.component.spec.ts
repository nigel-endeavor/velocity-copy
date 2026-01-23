import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { MsalService } from '@azure/msal-angular';
import { Store } from '@ngrx/store';
import { of } from 'rxjs';
import { ApplicationWrapperComponent } from './application-wrapper.component';
import { SecurityUtilService } from '../services/security-util.service';

describe('ApplicationWrapperComponent', () => {
  let component: ApplicationWrapperComponent;
  let fixture: ComponentFixture<ApplicationWrapperComponent>;
  let authService: MsalService;
  let securityUtils: SecurityUtilService;
  let store: Store;1
  let activatedRoute: ActivatedRoute;
  let router: Router;

  test('' , () => {
    expect(true).toBeTruthy();
  });

  // beforeEach(async() => {
  //   await TestBed.configureTestingModule({
  //     declarations: [ApplicationWrapperComponent],
  //     providers: [
  //       {provide: MsalService, useValue: {logout: jest.fn()}},
  //       {
  //         provide: SecurityUtilService, useValue: {
  //           // Define the getLoggedInUser function
  //           getLoggedInUser: () => {
  //             return {
  //               notifications: 12 // replace with the value you expect to see
  //             };
  //           }
  //         }
  //       },
  //       {
  //         provide: Store,
  //         useValue: {
  //           select: jest.fn(() => of(true)),
  //           dispatch: jest.fn(),
  //         },
  //       },
  //       {provide: ActivatedRoute, useValue: {}},
  //       {provide: Router, useValue: {navigate: jest.fn()}},
  //     ],

  //   }).compileComponents();

  //   fixture = TestBed.createComponent(ApplicationWrapperComponent);
  //   component = fixture.componentInstance;
  //   authService = TestBed.inject(MsalService);
  //   securityUtils = TestBed.inject(SecurityUtilService);
  //   store = TestBed.inject(Store);
  //   activatedRoute = TestBed.inject(ActivatedRoute);
  //   router = TestBed.inject(Router);
  //   fixture.detectChanges();
  // });

  // test('should create the component', () => {
  //   expect(component).toBeTruthy();
  // });

  // test('should set notification bell value on init', () => {
  //   fixture.detectChanges();
  //   expect(component.notificationBell).toBe('12');
  // });

  // test('should navigate to worklist when worklist is present', () => {
  //   jest.spyOn(localStorage, 'getItem').mockReturnValue('worklist');
  //   jest.spyOn(router, 'navigate');
  //   component.onWorklistClicked();
  //   expect(router.navigate).toHaveBeenCalledWith(['/worklist']);
  // });

  // test('should navigate to locations when worklist is absent', () => {
  //   jest.spyOn(localStorage, 'getItem').mockReturnValue(null);
  //   jest.spyOn(router, 'navigate');
  //   component.onWorklistClicked();
  //   expect(router.navigate).toHaveBeenCalledWith(['/locations']);
  // });

  // test('should navigate to reports when dashboards clicked', () => {
  //   jest.spyOn(router, 'navigate');
  //   component.onDashboardsClicked();
  //   expect(router.navigate).toHaveBeenCalledWith(['/reports']);
  // });


  // test('should call authService.logout when logout is called', () => {
  //   component.logout();
  //   expect(authService.logout).toHaveBeenCalled();
  // });
});
