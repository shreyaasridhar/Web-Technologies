import { TestBed } from '@angular/core/testing';

import { SubmitweatherService } from './submitweather.service';

describe('SubmitweatherService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SubmitweatherService = TestBed.get(SubmitweatherService);
    expect(service).toBeTruthy();
  });
});
