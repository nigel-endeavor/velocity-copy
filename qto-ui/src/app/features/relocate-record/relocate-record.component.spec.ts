import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelocateRecordComponent } from './relocate-record.component';

describe('RelocateRecordComponent', () => {
  let component: RelocateRecordComponent;
  let fixture: ComponentFixture<RelocateRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelocateRecordComponent]
    });
    fixture = TestBed.createComponent(RelocateRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
