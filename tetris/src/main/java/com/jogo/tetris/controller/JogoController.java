package com.jogo.tetris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jogo.tetris.dto.FilaDto;
import com.jogo.tetris.model.Tabuleiro;
import com.jogo.tetris.service.JogoService;

@Controller
@RequestMapping("/tetris")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @MessageMapping("/iniciar")
    @SendTo("/topico/tabuleiro")
    public FilaDto iniciarJogo() {
        return jogoService.iniciarNovoJogo();
    }

    @MessageMapping("/mover")
    @SendTo("/topico/tabuleiro")
    public FilaDto mover(String comando) {
        return jogoService.moverPeca(comando);
    }  

    @MessageMapping("/reiniciar")
    @SendTo("/topico/tabuleiro")
    public FilaDto reiniciarJogo() {
        return jogoService.reiniciarPartidaCompleta();
    }

    @MessageMapping("/parar")
    @SendTo("/topico/tabuleiro")
    public FilaDto pararJogo() {
        return jogoService.pararPartidaDefinitivamente();
    }
}