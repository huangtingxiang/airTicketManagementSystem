import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirlineCompanyAddComponent } from './airline-company-add.component';

describe('AirlineCompanyAddComponent', () => {
  let component: AirlineCompanyAddComponent;
  let fixture: ComponentFixture<AirlineCompanyAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirlineCompanyAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirlineCompanyAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
