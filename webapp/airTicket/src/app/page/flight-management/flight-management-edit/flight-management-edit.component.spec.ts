import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightManagementEditComponent } from './flight-management-edit.component';

describe('FlightManagementEditComponent', () => {
  let component: FlightManagementEditComponent;
  let fixture: ComponentFixture<FlightManagementEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightManagementEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightManagementEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
