package com.jogo.tetris.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registro) {// Habilita um "servidor de mensagens" interno.
        // O Angular vai escutar as novidades do jogo se inscrevendo em canais que
        // começam com "/topico"
        registro.enableSimpleBroker("/topico");
        // Quando o Angular quiser enviar um comando (ex: "mover peça"), ele vai mandar
        // para caminhos que começam com "/app"
        registro.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registro) {
        registro.addEndpoint("/tetris-websocket") // O endpoint do Angular deve ser esse
                .setAllowedOriginPatterns("*") // Permite que o Angular acesse mesmo vindo de outra porta
                .withSockJS(); // Garante compatibilidade se o navegador do usuário for antigo
    }

}
