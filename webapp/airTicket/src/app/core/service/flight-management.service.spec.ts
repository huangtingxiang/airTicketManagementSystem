import { TestBed } from '@angular/core/testing';

import { FlightManagementService } from './flight-management.service';

describe('FlightManagementService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FlightManagementService = TestBed.get(FlightManagementService);
    expect(service).toBeTruthy();
  });
});
