package itvm.achtungdiekurve;

import itvm.achtungdiekurve.model.Lobby;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

@Configuration
@EnableWebSocket
public class Config implements WebSocketConfigurer {



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        List<Lobby> lobbyList;
        registry.addHandler(new Connection(), "/test")
                .setAllowedOriginPatterns("*");
    }
}
