import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightManagementIndexComponent } from './flight-management-index.component';

describe('FlightManagementIndexComponent', () => {
  let component: FlightManagementIndexComponent;
  let fixture: ComponentFixture<FlightManagementIndexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightManagementIndexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightManagementIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
