import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSelectedData } from './user-selected-data';

describe('UserSelectedData', () => {
  let component: UserSelectedData;
  let fixture: ComponentFixture<UserSelectedData>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserSelectedData]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserSelectedData);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
