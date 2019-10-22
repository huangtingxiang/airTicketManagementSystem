import { TestBed } from '@angular/core/testing';

import { AirlineCompanyService } from './airline-company.service';

describe('AirlineCompanyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AirlineCompanyService = TestBed.get(AirlineCompanyService);
    expect(service).toBeTruthy();
  });
});
