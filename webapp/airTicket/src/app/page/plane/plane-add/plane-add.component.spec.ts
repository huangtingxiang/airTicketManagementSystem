import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneAddComponent } from './plane-add.component';

describe('PlaneAddComponent', () => {
  let component: PlaneAddComponent;
  let fixture: ComponentFixture<PlaneAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaneAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaneAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
