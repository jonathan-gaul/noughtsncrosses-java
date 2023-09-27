import { TestBed } from '@angular/core/testing';

import { StompService } from './stomp-service.service';

describe('StompServiceService', () => {
  let service: StompService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StompService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
