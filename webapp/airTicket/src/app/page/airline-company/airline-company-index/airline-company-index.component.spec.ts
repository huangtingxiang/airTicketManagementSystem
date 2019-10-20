import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirlineCompanyIndexComponent } from './airline-company-index.component';

describe('AirlineCompanyIndexComponent', () => {
  let component: AirlineCompanyIndexComponent;
  let fixture: ComponentFixture<AirlineCompanyIndexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirlineCompanyIndexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirlineCompanyIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
