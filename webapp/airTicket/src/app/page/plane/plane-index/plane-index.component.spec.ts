import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneIndexComponent } from './plane-index.component';

describe('PlaneIndexComponent', () => {
  let component: PlaneIndexComponent;
  let fixture: ComponentFixture<PlaneIndexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaneIndexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaneIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
