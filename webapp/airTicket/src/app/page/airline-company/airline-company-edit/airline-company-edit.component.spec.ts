import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirlineCompanyEditComponent } from './airline-company-edit.component';

describe('AirlineCompanyEditComponent', () => {
  let component: AirlineCompanyEditComponent;
  let fixture: ComponentFixture<AirlineCompanyEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirlineCompanyEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirlineCompanyEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
