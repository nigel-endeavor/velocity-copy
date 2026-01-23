import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ToggleComponent } from './toggle.component';

describe('ToggleComponent', () => {
  let component: ToggleComponent;
  let fixture: ComponentFixture<ToggleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ToggleComponent, FormsModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToggleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  test('should create the component', () => {
    expect(component).toBeTruthy();
  });

  test('should initialize with active set to false', () => {
    expect(component.active).toBeFalsy();
  });

  test('should emit activeChange event on statusChanged', () => {
    jest.spyOn(component.activeChange, 'emit');
    component.active = true;
    component.statusChanged();
    expect(component.activeChange.emit).toHaveBeenCalledWith(false);
  });
});
