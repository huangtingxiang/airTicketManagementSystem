import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirlineCompanySelectComponent } from './airline-company-select.component';

describe('AirlineCompanySelectComponent', () => {
  let component: AirlineCompanySelectComponent;
  let fixture: ComponentFixture<AirlineCompanySelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirlineCompanySelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirlineCompanySelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
