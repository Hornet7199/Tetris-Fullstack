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

    //Método final de varredura de linha
    public void verificarELimparLinhas() {
        for (int linha = 19; linha >= 0; linha--) {
            
            if (isLinhaCompleta(linha)) {
                eliminarLinha(linha);

                //linha++ para quando uma linha cair, o for volta 
                //para rever se a linha agora foi concluída
                linha++; 
            }
        }
    }

    //Verifica conclusão de linhas
    private boolean isLinhaCompleta(int indiceLinha) {
        for (int coluna = 0; coluna < 10; coluna++) {
            //Qualquer 0, Linha não concluída
            if (grade[indiceLinha][coluna] == 0) { 
                return false;
            }
        }
        return true; // Se o loop terminar sem achar 0, está completa!
    }

    //Método auxiliar para puxar as linhas de cima pra baixo
    private void eliminarLinha(int linhaAlvo) {
        // Puxa as linhas de cima para baixo
        for (int i = linhaAlvo; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                
                grade[i][j] = grade[i - 1][j]; 
            }
        }
        
        // A linha 0 (o teto) fica vazia após tudo descer
        for (int j = 0; j < 10; j++) {
            grade[0][j] = 0;
        }
    }

}
