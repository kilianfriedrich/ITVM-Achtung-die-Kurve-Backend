package itvm.achtungdiekurve;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        spieler.removeIf(kurve -> kurve.getSession().equals(session));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        Point pt = extractMessage(message);
        if(pt != null && webSocketSessions.contains(session)){
            Kurve actPly = spieler.stream().filter(kurve -> kurve.getSession() == session).findAny().orElseThrow();
            if(actPly.getPoint().size()-1 >= 0){
                Point last = actPly.getPoint().get(actPly.getPoint().size()-1);
                if(pt.getX() != last.getX() || pt.getY() != last.getY()){
                    actPly.addPoint(pt);
                }
            } else {
                actPly.addPoint(pt);
            }

            String broadCastStringMessage = createBroadCastString(actPly.getId(),pt);

            Kurve kurve = Utils.detectCollsion(spieler);
            if(kurve != null){
                killPlayer(kurve);
                webSocketSessions.remove(kurve.getSession());
                spieler.remove(kurve);
                System.out.println("kill");
            }

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

    }

    public Point extractMessage(WebSocketMessage<?> message) throws JsonProcessingException {
        try{
            Point point = new ObjectMapper().readValue(message.getPayload().toString(), Point.class);
            System.out.println(point.toString());
            return point;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    public String createBroadCastString(int id, Point p){
        return id + "/" + (int)p.getX() + "/" + (int)p.getY();
    }

    public void killPlayer(Kurve dead){
        keep_alive_check(spieler,webSocketSessions);
        String s = dead.getId() + "/-1/-1";
        spieler.forEach(kurve -> {
            try {
                kurve.getSession().sendMessage(new TextMessage(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void keep_alive_check(List<Kurve> kurven,List<WebSocketSession> sessions){
        kurven.forEach(kurve -> {
            if(!kurve.getSession().isOpen()){
                sessions.remove(kurve.getSession());
                kurven.remove(kurve);
            }
        });
    }
}

