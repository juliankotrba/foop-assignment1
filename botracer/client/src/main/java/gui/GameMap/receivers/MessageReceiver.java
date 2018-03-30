package gui.GameMap.receivers;

import debug.Log;
import dto.Player;
import dto.messages.OnMessageReceivedListener;
import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.GameDataMessage;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.NewPlayerMessage;
import dto.messages.s2c.PlayersChangedMessage;
import gui.GameMap.GameMap;
import gui.MainController;
import gui.Sprites;

import java.util.Objects;

public class MessageReceiver implements OnMessageReceivedListener {

    private MainController mainController;
    private static GameMap gameMap;

    public MessageReceiver(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void onMessageReceived(GameDataMessage message) {
        if (message.getPayload().isPresent()) {
            gameMap = mainController.loadMap(message.getPayload().get());
        }
    }

    @Override
    public void onMessageReceived(GameStartMessage message) {

    }

    @Override
    public void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message) {
        Log.verbose("MarkPlacement message received");
        if (gameMap == null) {
            return;
        }
        message.getPayload().ifPresent(mark -> gameMap.set(mark));
    }

    @Override
    public void onMessageReceived(NewPlayerMessage message) {
        Log.debug("NewPlayer message received");
        if (gameMap == null) {
            return;
        }
        // set bots on map
        message.getPayload().ifPresent(players ->
                players.stream()
                        .filter(Objects::nonNull)
                        .forEach(gameMap::set));

        // TODO: set player names in UI player list
        message.getPayload().ifPresent(
                players -> {
                    for (Player player : players) {
                        Sprites.setHighlight(player.getNumber());
                    }
                }
        );
    }

    @Override
    public void onMessageReceived(PlayersChangedMessage message) {
        Log.verbose("PlayersChanged message received");
        if (gameMap == null) {
            return;
        }
        message.getPayload().ifPresent(players ->
                players.stream()
                        .filter(Objects::nonNull)
                        .forEach(gameMap::set));
    }


    /*
        Following methods can be left out, because only the server handles these messages.
     */

    @Override
    public void onMessageReceived(ChangeStrategyMessage message) { }

    @Override
    public void onMessageReceived(MarkPlacementMessage message) { }

    @Override
    public void onMessageReceived(PlayerNameMessage message) { }

    @Override
    public void onMessageReceived(PlayerReadyMessage message) { }
}
