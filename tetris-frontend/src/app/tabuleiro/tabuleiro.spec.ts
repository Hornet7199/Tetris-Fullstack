import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tabuleiro } from './tabuleiro';

describe('Tabuleiro', () => {
  let component: Tabuleiro;
  let fixture: ComponentFixture<Tabuleiro>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Tabuleiro]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Tabuleiro);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
