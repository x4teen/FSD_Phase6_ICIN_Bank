import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingAccountDetailsComponent } from './saving-account-details.component';

describe('SavingAccountDetailsComponent', () => {
  let component: SavingAccountDetailsComponent;
  let fixture: ComponentFixture<SavingAccountDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SavingAccountDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SavingAccountDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
