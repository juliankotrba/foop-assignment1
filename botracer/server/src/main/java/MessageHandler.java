import dto.messages.c2s.*;
import dto.messages.s2c.GameDataMessage;
import dto.messages.s2c.NewPlayerMessage;
import game.Game;
import game.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

public class MessageHandler implements OnMessageReceivedListener {

    private DTOUtil dtoUtil;
    private ConnectionHandler connectionHandler;
    private Set<ObjectOutputStream> writers;
    private ObjectOutputStream out;
    private Game game;


    public MessageHandler(ConnectionHandler connectionHandler, Set<ObjectOutputStream> writers, ObjectOutputStream out, Game game) {
        this.dtoUtil = new DTOUtil();
        this.connectionHandler = connectionHandler;
        this.writers = writers;
        this.out = out;
        this.game = game;
    }


    @Override
    public void onMessageReceived(ChangeStrategyMessage message) {
    }

    @Override
    public void onMessageReceived(MarkPlacementMessage message) {

    }

    /**
     * When the server receives a PlayerNameMessage, this player will be allocated to a bot
     * and all players receive a NewPlayerMessage containing all updated player info.
     *
     * @param message containing the name of the new player
     */
    @Override
    public void onMessageReceived(PlayerNameMessage message) {
        System.out.println("PlayerNameMessageReceived");

        String name = "NoNameSet";
        if (message.getPayload().isPresent()) {
            name = message.getPayload().get();
        }

        try {
            // send the game board to the player
            System.out.println(game.getGameBoard());
            out.writeObject(new GameDataMessage(dtoUtil.convertGameBoard(game.getGameBoard())));

            // allocate the player to a bot and send the updated player list to all players
            allocatePlayerToBot(name);
            NewPlayerMessage newPlayerMessage = new NewPlayerMessage(dtoUtil.convertPlayers(game.getPlayers()));
            for (ObjectOutputStream writer : writers) {
                writer.writeObject(newPlayerMessage);
            }
        } catch (IOException e) {
            // TODO: error handling
            e.printStackTrace();
        }

        connectionHandler.setName(name);
    }

    @Override
    public void onMessageReceived(PlayerReadyMessage message) {

    }

    @Override
    public void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message) { }

    /**
     * Allocates the player to a bot, or add a new bot if all initial bots are already allocated.
     *
     * @param name of the player
     */
    private void allocatePlayerToBot(String name){
        boolean playerAllocated = false;
        for (Player player : game.getPlayers()) {
            if (!player.isOwnedByPlayer()) {
                player.setName(name);
                player.setOwnedByPlayer(true);
                playerAllocated = true;
                break;
            }
        }

        if (!playerAllocated) {
            game.addPlayer(name);
        }
    }
}
