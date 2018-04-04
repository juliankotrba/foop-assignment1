package communication;

import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;
import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.*;
import game.Game;
import game.Player;
import util.DTOUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageHandler implements OnMessageReceivedListener {

    private DTOUtil dtoUtil;
    private ConnectionHandler connectionHandler;
    private Game game;


    public MessageHandler(ConnectionHandler connectionHandler, Game game) {
        this.dtoUtil = new DTOUtil();
        this.connectionHandler = connectionHandler;
        this.game = game;
    }


    @Override
    public void onMessageReceived(ChangeStrategyMessage message) {
    }

    /**
     * Adds a new Mark to the game and sends a s2c.MarkPlacementMessage to all players with the new mark.
     *
     * @param message containing the new mark
     */
    @Override
    public void onMessageReceived(MarkPlacementMessage message) {
        System.out.println("MarkPlacementMessage received");

        if (message.getPayload().isPresent()) {
            dto.Mark mark = message.getPayload().get();
            game.newMark(dtoUtil.convertMarkDto(mark), mark.getPosition().getHeight(), mark.getPosition().getWidth());
            writeAllPlayers(new dto.messages.s2c.MarkPlacementMessage(mark));
        }
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
     * If all players are ready, a GameStartMessage will be sent to all players and the game will start.
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
            Thread thread = new Thread(game);
            thread.start();
        }
    }

    @Override
    public void onMessageReceived(GameEndMessage message) {
        // TODO: Send winners to the client
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

    @Override
    public void onMessageReceived(RemoveMarksMessage message) {

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
                player.setReady(false);
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
    public static void writeAllPlayers(Message message) {
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
