package com.jogo.tetris.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tabuleiro {
    private final Integer linhas = 20;
    private final Integer colunas = 10;

    private Integer[][] grade;

    public Tabuleiro() {
        this.grade = new Integer[linhas][colunas];
    }

    public void imprimirTabuleiro() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(grade[i][j] + " ");
            }
            System.out.println();
        }
    }

}
