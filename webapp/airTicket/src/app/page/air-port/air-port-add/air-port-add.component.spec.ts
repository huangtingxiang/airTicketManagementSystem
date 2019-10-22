import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirPortAddComponent } from './air-port-add.component';

describe('AirPortAddComponent', () => {
  let component: AirPortAddComponent;
  let fixture: ComponentFixture<AirPortAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirPortAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirPortAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
