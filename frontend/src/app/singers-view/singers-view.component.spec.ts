import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingersViewComponent } from './singers-view.component';

describe('SingersViewComponent', () => {
  let component: SingersViewComponent;
  let fixture: ComponentFixture<SingersViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingersViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingersViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
