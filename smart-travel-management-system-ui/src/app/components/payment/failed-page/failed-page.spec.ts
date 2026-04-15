import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FailedPage } from './failed-page';

describe('FailedPage', () => {
  let component: FailedPage;
  let fixture: ComponentFixture<FailedPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FailedPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FailedPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
