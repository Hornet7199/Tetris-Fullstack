package com.jogo.tetris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jogo.tetris.model.Tabuleiro;

@SpringBootApplication
public class TetrisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TetrisApplication.class, args);

		System.out.println("\n--- INICIANDO O TABULEIRO DO TETRIS ---");
		Tabuleiro meuTabuleiro = new Tabuleiro();
		meuTabuleiro.imprimirTabuleiro();
		System.out.println("---------------------------------------\n");
	}

}
