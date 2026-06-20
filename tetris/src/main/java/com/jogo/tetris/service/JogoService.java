package com.jogo.tetris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jogo.tetris.dto.FilaDto;
import com.jogo.tetris.model.Peca;
import com.jogo.tetris.model.PecaFila;
import com.jogo.tetris.model.Tabuleiro;

@Service
public class JogoService {
    
    private Tabuleiro tabuleiro;
    private PecaFila fila;
    private Peca pecaAtual;
    private boolean jogoAtivo = false;

    // VARIÁVEIS DO PLACAR (Novas)
    private int pontuacao = 0;
    private int linesEliminadas = 0;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; 

    // Inicializa o jogo e zera o placar
    public FilaDto iniciarNovoJogo() {
        this.tabuleiro = new Tabuleiro();
        this.fila = new PecaFila();
        this.fila.preencherBag();
        
        this.pecaAtual = fila.dequeue();
        this.jogoAtivo = true; 
        
        // Zera o placar no início
        this.pontuacao = 0;
        this.linesEliminadas = 0;
        
        System.out.println("Partida iniciada! Primeira peça instanciada.");
        return obterEstadoJogoDto();
    }

    // Gerador oficial do pacote de dados atualizado com os pontos e linhas
    public FilaDto obterEstadoJogoDto() {
        Tabuleiro estadoAtual = new Tabuleiro();
        Integer[][] gradeTemporaria = new Integer[20][10];

        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                gradeTemporaria[i][j] = this.tabuleiro.getGrade()[i][j];
            }
        }

        if (this.pecaAtual != null) {
            int[][] matrizPeca = this.pecaAtual.getMatriz();
            int px = this.pecaAtual.getX();
            int py = this.pecaAtual.getY();

            for(int i = 0; i < matrizPeca.length; i++) {
                for(int j = 0; j < matrizPeca[i].length; j++) {
                    if(matrizPeca[i][j] != 0) { 
                        if(py + i >= 0 && py + i < 20 && px + j >= 0 && px + j < 10) {
                            gradeTemporaria[py + i][px + j] = matrizPeca[i][j];
                        }
                    }
                }
            }
        }

        estadoAtual.setGrade(gradeTemporaria);
        
        // Retorna o DTO passando também a pontuação e as linhas atuais para o Angular
        return new FilaDto(
            estadoAtual, 
            this.fila.getFormatosFila(), 
            this.jogoAtivo, 
            this.pontuacao, 
            this.linesEliminadas
        );
    }

    public FilaDto moverPeca(String direcao) {
        if (!this.jogoAtivo) return obterEstadoJogoDto();
        if (pecaAtual == null) return obterEstadoJogoDto();

        direcao = direcao.replace("\"", "")
                         .replace("'", "")
                         .trim()
                         .toUpperCase(); 

        System.out.println("Comando processado no Java: [" + direcao + "]"); 

        switch (direcao) {
            case "ESQUERDA":
                if (validarPosicao(pecaAtual.getMatriz(), pecaAtual.getX() - 1, pecaAtual.getY())) {
                    this.pecaAtual.moverParaEsquerda();
                }
                break;
            case "DIREITA":
                if (validarPosicao(pecaAtual.getMatriz(), pecaAtual.getX() + 1, pecaAtual.getY())) {
                    this.pecaAtual.moverParaDireita();
                }
                break;
            case "BAIXO":
                avancarGravidade();
                break;
            case "GIRAR":
                int xOriginal = this.pecaAtual.getX();
                int yOriginal = this.pecaAtual.getY();
                this.pecaAtual.rotacionar();
                
                if (validarPosicao(pecaAtual.getMatriz(), pecaAtual.getX(), pecaAtual.getY())) {
                    break; 
                }

                boolean conseguiuChutar = false;
                int[][] testesDeslocamento = { {-1, 0}, {1, 0}, {0, -1}, {-2, 0}, {2, 0} };

                for (int[] offset : testesDeslocamento) {
                    int novoX = xOriginal + offset[0];
                    int novoY = yOriginal + offset[1];

                    if (validarPosicao(pecaAtual.getMatriz(), novoX, novoY)) {
                        this.pecaAtual.setX(novoX);
                        this.pecaAtual.setY(novoY);
                        conseguiuChutar = true;
                        break;
                    }
                }

                if (!conseguiuChutar) {
                    this.pecaAtual.setX(xOriginal);
                    this.pecaAtual.setY(yOriginal);
                    this.pecaAtual.rotacionar();
                    this.pecaAtual.rotacionar();
                    this.pecaAtual.rotacionar();
                }
                break;
        }
        return obterEstadoJogoDto();
    }

    private void avancarGravidade() {
        if (!this.jogoAtivo || this.pecaAtual == null) {
            return;
        }

        if (validarPosicao(pecaAtual.getMatriz(), pecaAtual.getX(), pecaAtual.getY() + 1)) {
            this.pecaAtual.moverParaBaixo();
        } else {
            congelarPecaNoTabuleiro();
            
            // LOGICA DA PONTUAÇÃO RETRO:
            // Pegamos quantas linhas o método da classe Tabuleiro acabou de explodir
            int linhasLimpasNestaRodada = this.tabuleiro.verificarELimparLinhas();
            
            if (linhasLimpasNestaRodada > 0) {
                this.linesEliminadas += linhasLimpasNestaRodada;
                
                // Sistema clássico de recompensa por combos
                switch (linhasLimpasNestaRodada) {
                    case 1 -> this.pontuacao += 100;   // 1 Linha
                    case 2 -> this.pontuacao += 300;   // Double
                    case 3 -> this.pontuacao += 500;   // Triple
                    case 4 -> this.pontuacao += 800;   // TETRIS!
                    default -> this.pontuacao += 1000;
                }
            }
            
            if (fila.isEmpty()) {
                System.out.println("A fila esvaziou! Preenchendo uma nova bag com 7 peças.");
                fila.preencherBag();
            }

            Peca proximaPeca = fila.dequeue();
            
            if (proximaPeca != null && validarPosicao(proximaPeca.getMatriz(), proximaPeca.getX(), proximaPeca.getY())) {
                this.pecaAtual = proximaPeca; 
            } else {
                this.jogoAtivo = false; 
                this.pecaAtual = null;  
                System.out.println("=== GAME OVER DETECTADO (TETO ATINGIDO) ===");
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    public void processarGravidade() {
        if (jogoAtivo && pecaAtual != null) {
            avancarGravidade();
            messagingTemplate.convertAndSend("/topico/tabuleiro", obterEstadoJogoDto());
        }
    }

    private boolean validarPosicao(int[][] matrizPeca, int novoX, int novoY) {
        for (int i = 0; i < matrizPeca.length; i++) {
            for (int j = 0; j < matrizPeca[i].length; j++) {
                if (matrizPeca[i][j] != 0) { 
                    int xTabuleiro = novoX + j;
                    int yTabuleiro = novoY + i;

                    if (xTabuleiro < 0 || xTabuleiro >= 10 || yTabuleiro >= 20) {
                        return false;
                    }

                    if (yTabuleiro >= 0 && this.tabuleiro.getGrade()[yTabuleiro][xTabuleiro] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void congelarPecaNoTabuleiro() {
        int[][] matrizPeca = this.pecaAtual.getMatriz();
        int px = this.pecaAtual.getX();
        int py = this.pecaAtual.getY();

        for (int i = 0; i < matrizPeca.length; i++) {
            for (int j = 0; j < matrizPeca[i].length; j++) {
                if (matrizPeca[i][j] != 0) {
                    if (py + i >= 0 && py + i < 20 && px + j >= 0 && px + j < 10) {
                        this.tabuleiro.getGrade()[py + i][px + j] = matrizPeca[i][j];
                    }
                }
            }
        }
    }

    public FilaDto reiniciarPartidaCompleta() {
        this.tabuleiro = new Tabuleiro();
        this.fila = new PecaFila();
        this.fila.preencherBag();
        
        this.pecaAtual = this.fila.dequeue();
        this.jogoAtivo = true; 

        // Garante que zera os pontos ao reiniciar também
        this.pontuacao = 0;
        this.linesEliminadas = 0;

        return obterEstadoJogoDto();
    }

    public FilaDto pararPartidaDefinitivamente() {
        this.jogoAtivo = false;
        return obterEstadoJogoDto();
    }
}