import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirPortEditComponent } from './air-port-edit.component';

describe('AirPortEditComponent', () => {
  let component: AirPortEditComponent;
  let fixture: ComponentFixture<AirPortEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirPortEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirPortEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
