import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckingAccountDetailsComponent } from './checking-account-details.component';

describe('CheckingAccountDetailsComponent', () => {
  let component: CheckingAccountDetailsComponent;
  let fixture: ComponentFixture<CheckingAccountDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckingAccountDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckingAccountDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
