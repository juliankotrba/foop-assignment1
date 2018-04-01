import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;
import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.*;
import game.Game;
import game.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageHandler implements OnMessageReceivedListener {

    private DTOUtil dtoUtil;
    private ConnectionHandler connectionHandler;


    public MessageHandler(ConnectionHandler connectionHandler) {
        this.dtoUtil = new DTOUtil();
        this.connectionHandler = connectionHandler;
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
        System.out.println("PlayerNameMessage received");

        String name = "NoNameSet";
        if (message.getPayload().isPresent()) {
            name = message.getPayload().get();
        }

        try {
            Game game = connectionHandler.getGame();

            // send the game board to the player
            connectionHandler.getOut().writeObject(new GameDataMessage(dtoUtil.convertGameBoard(game.getGameBoard())));

            // allocate the player to a bot and send the updated player list to all players
            allocatePlayerToBot(name);
            NewPlayerMessage newPlayerMessage = new NewPlayerMessage(dtoUtil.convertPlayers(game.getPlayers()));
            writeAllPlayers(newPlayerMessage);
        } catch (IOException e) {
            // TODO: error handling
            e.printStackTrace();
        }

        connectionHandler.setName(name);
    }

    /**
     * When the server receives a PlayerReadyMessage, all players receive a PlayerReadyServerMessage containing the
     * information, which player set his/her status to ready.
     *
     * If all players are ready, a GameStartMessage will be sent to all players.
     *
     * @param message indicating the ready status of this player
     */
    @Override
    public void onMessageReceived(PlayerReadyMessage message) {
        System.out.println("PlayerreadyMessage received");

        writeAllPlayers(new PlayerReadyServerMessage(dtoUtil.convertPlayer(connectionHandler.getPlayer())));

        boolean allPlayersReady = true;
        for (Player player : connectionHandler.getGame().getPlayers()) {
            if (player.equals(connectionHandler.getPlayer())) {
                player.setReady(true);
                break;
            }

            if (!player.isReady()) {
                allPlayersReady = false;
            }
        }

        if (allPlayersReady) {
            writeAllPlayers(new GameStartMessage());
        }
    }

    @Override
    public void onMessageReceived(GameDataMessage message) { }

    @Override
    public void onMessageReceived(GameStartMessage message) { }

    @Override
    public void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message) { }

    @Override
    public void onMessageReceived(NewPlayerMessage message) { }

    @Override
    public void onMessageReceived(PlayersChangedMessage message) { }

    @Override
    public void onMessageReceived(PlayerReadyServerMessage message) {

    }


    /**
     * Allocates the player to a bot, or add a new bot if all initial bots are already allocated.
     *
     * @param name of the player
     */
    private void allocatePlayerToBot(String name){
        boolean playerAllocated = false;
        for (Player player : connectionHandler.getGame().getPlayers()) {
            if (!player.isOwnedByPlayer()) {
                player.setName(name);
                player.setOwnedByPlayer(true);
                playerAllocated = true;

                connectionHandler.setPlayer(player);
                break;
            }
        }

        if (!playerAllocated) {
            Player player = connectionHandler.getGame().addPlayer(name);
            connectionHandler.setPlayer(player);
        }
    }

    /**
     * Writes {message} to all registered players.
     *
     * @param message which should be send to all registered players
     */
    private void writeAllPlayers(Message message) {
        try {
            for (ObjectOutputStream writer : ConnectionHandler.getWriters()) {
                writer.writeObject(message);
            }
        } catch (IOException e) {
            // TODO: exception handling
            e.printStackTrace();
        }
    }
}
