import { TestBed } from '@angular/core/testing';

import { TripScheduleService } from './trip-schedule-service';

describe('TripScheduleService', () => {
  let service: TripScheduleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TripScheduleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
