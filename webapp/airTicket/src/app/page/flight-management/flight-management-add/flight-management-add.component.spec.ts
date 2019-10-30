import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightManagementAddComponent } from './flight-management-add.component';

describe('FlightManagementAddComponent', () => {
  let component: FlightManagementAddComponent;
  let fixture: ComponentFixture<FlightManagementAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightManagementAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightManagementAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
