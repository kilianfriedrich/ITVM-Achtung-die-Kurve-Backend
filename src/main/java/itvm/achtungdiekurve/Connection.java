package itvm.achtungdiekurve;

import itvm.achtungdiekurve.model.Kurve;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Connection extends TextWebSocketHandler {

    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());
    List<Kurve> spieler = new ArrayList<Kurve>();
    int id = 0;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        int _id = id++;
        webSocketSessions.add(session);
        spieler.add(new Kurve(session,_id,new Color((int)(Math.random() * 0x1000000))));
        session.sendMessage(new TextMessage(String.valueOf(_id)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        Point pt = extractMessage(message);
        Kurve actPly = spieler.stream().filter(kurve -> kurve.getSession() == session).collect(Collectors.toList()).get(0);
        actPly.addPoint(pt);
        String broadCastStringMessage = createBroadCastString(actPly.getId(),pt);

        webSocketSessions.forEach(_session -> {
            if(!(_session == session)){
                try {
                    _session.sendMessage(new TextMessage(broadCastStringMessage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Point extractMessage(WebSocketMessage<?> message){
        String[] s = message.getPayload().toString().split("/");
        Point p = new Point();
        p.setLocation(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
        return p;
    }

    public String createBroadCastString(int id, Point p){
        return id + "/" + (int)p.getX() + "/" + (int)p.getY();
    }
}
