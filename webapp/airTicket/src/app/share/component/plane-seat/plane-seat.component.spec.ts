import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneSeatComponent } from './plane-seat.component';

describe('PlaneSeatComponent', () => {
  let component: PlaneSeatComponent;
  let fixture: ComponentFixture<PlaneSeatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaneSeatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaneSeatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
