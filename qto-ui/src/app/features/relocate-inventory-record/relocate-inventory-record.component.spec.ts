import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelocateInventoryRecordComponent } from './relocate-inventory-record.component';

describe('RelocateInventoryRecordComponent', () => {
  let component: RelocateInventoryRecordComponent;
  let fixture: ComponentFixture<RelocateInventoryRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelocateInventoryRecordComponent]
    });
    fixture = TestBed.createComponent(RelocateInventoryRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
