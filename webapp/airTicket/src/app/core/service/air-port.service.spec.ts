import { TestBed } from '@angular/core/testing';

import { AirPortService } from './air-port.service';

describe('AirPortService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AirPortService = TestBed.get(AirPortService);
    expect(service).toBeTruthy();
  });
});
