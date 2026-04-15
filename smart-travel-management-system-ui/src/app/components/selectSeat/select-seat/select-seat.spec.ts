import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectSeat } from './select-seat';

describe('SelectSeat', () => {
  let component: SelectSeat;
  let fixture: ComponentFixture<SelectSeat>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectSeat]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectSeat);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
