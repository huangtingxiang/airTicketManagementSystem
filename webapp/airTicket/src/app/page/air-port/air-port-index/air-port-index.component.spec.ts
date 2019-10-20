import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirPortIndexComponent } from './air-port-index.component';

describe('AirPortIndexComponent', () => {
  let component: AirPortIndexComponent;
  let fixture: ComponentFixture<AirPortIndexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirPortIndexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirPortIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
