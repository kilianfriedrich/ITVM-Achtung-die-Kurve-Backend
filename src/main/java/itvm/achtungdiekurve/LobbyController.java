package itvm.achtungdiekurve;

import itvm.achtungdiekurve.model.Lobby;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LobbyController {
    private static List<Lobby> lobbyList= new ArrayList();
    public ResponseEntity<Integer> neuLobbyErstellen(){
        Lobby lobby = new Lobby();
        lobbyList.add(lobby);
        return ResponseEntity.ok(lobby.getId());
    }

    public void startLobby(Integer id){
        Lobby lobby = lobbyList.stream().filter(elem -> elem.getId() == id).findFirst().get();
        lobby.start();
    }

    public static List<Lobby> getLobbyList() {
        return lobbyList;
    }
}
