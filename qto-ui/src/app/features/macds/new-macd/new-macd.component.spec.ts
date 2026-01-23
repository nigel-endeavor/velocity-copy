import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewMacdComponent } from './new-macd.component';

describe('NewMacdComponent', () => {
  let component: NewMacdComponent;
  let fixture: ComponentFixture<NewMacdComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewMacdComponent]
    });
    fixture = TestBed.createComponent(NewMacdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
