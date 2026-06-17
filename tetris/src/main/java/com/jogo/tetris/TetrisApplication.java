package com.jogo.tetris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jogo.tetris.model.Tabuleiro;

@SpringBootApplication
@EnableScheduling
public class TetrisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TetrisApplication.class, args);
	}

}
