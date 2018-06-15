package communication;

import debug.Log;
import dto.GameData;
import dto.MarkType;
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
import java.util.Collections;

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
	 * If the sent mark is from type REMOVE, a RemoveMarksMessage will be send to all players.
	 *
	 * @param message containing the new mark
	 */
	@Override
	public void onMessageReceived(MarkPlacementMessage message) {
		Log.debug(String.format("MarkPlacementMessage received from player '%s'", connectionHandler.getPlayer().getName()));

		if (message.getPayload().isPresent()) {
			dto.Mark mark = message.getPayload().get();

			if (mark.getMarkType() == MarkType.REMOVE) {
				game.getGameBoard().getTile(mark.getPosition().getHeight(), mark.getPosition().getWidth()).setMark(null);
				writeAllPlayers(new RemoveMarksMessage(Collections.singletonList(mark)));
			} else {
				game.newMark(dtoUtil.convertMarkDto(mark), mark.getPosition().getHeight(), mark.getPosition().getWidth());
				writeAllPlayers(new dto.messages.s2c.MarkPlacementMessage(mark));
			}
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
		String name = "NoNameSet";
		if (message.getPayload().isPresent()) {
			name = message.getPayload().get();
		}

		try {
			Game game = connectionHandler.getGame();

			// allocate the player to a bot and send the updated player list to all players
			Player player = allocatePlayerToBot(name);
			connectionHandler.setPlayer(player);

			// send the game data to the player
			GameData gameData = new GameData(dtoUtil.convertGameBoard(game.getGameBoard()), dtoUtil.convertPlayer(player));
			connectionHandler.getOut().writeObject(new GameDataMessage(gameData));
			writeAllPlayers(new NewPlayerMessage(dtoUtil.convertPlayers(game.getPlayers())));

			Log.debug(String.format("PlayerNameMessage received from player '%s'", name));
		} catch (IOException ignored) {

		}

		connectionHandler.setName(name);
	}

	/**
	 * When the server receives a PlayerReadyMessage, all players receive a PlayerReadyServerMessage containing the
	 * information, which player set his/her status to ready.
	 * <p>
	 * If all players are ready, a GameStartMessage will be sent to all players and the game will start.
	 *
	 * @param message indicating the ready status of this player
	 */
	@Override
	public void onMessageReceived(PlayerReadyMessage message) {
		Log.debug(String.format("PlayerReadyMessage received from player '%s'", connectionHandler.getPlayer().getName()));

		writeAllPlayers(new PlayerReadyServerMessage(dtoUtil.convertPlayer(connectionHandler.getPlayer())));

		boolean allPlayersReady = true;
		for (Player player : connectionHandler.getGame().getPlayers()) {
			if (player.getId() == connectionHandler.getPlayer().getId()) {
				player.setReady(true);
			}

			if (!player.isReady()) {
				allPlayersReady = false;
			}
		}

		if (allPlayersReady) {
			Log.debug("All Players ready, game starts.");
			writeAllPlayers(new GameStartMessage());
			Thread thread = new Thread(game);
			thread.start();
		}
	}

	@Override
	public void onMessageReceived(GameEndMessage message) {

	}

	@Override
	public void onMessageReceived(GameDataMessage message) {
	}

	@Override
	public void onMessageReceived(GameStartMessage message) {
	}

	@Override
	public void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message) {
	}

	@Override
	public void onMessageReceived(NewPlayerMessage message) {
	}

	@Override
	public void onMessageReceived(PlayersChangedMessage message) {
	}

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
	 * @return allocated player
	 */
	private Player allocatePlayerToBot(String name) {
		for (Player player : connectionHandler.getGame().getPlayers()) {
			if (!player.isOwnedByPlayer()) {
				player.setName(name);
				player.setOwnedByPlayer(true);
				player.setReady(false);

				return player;
			}
		}
		return connectionHandler.getGame().addPlayer(name);
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
		} catch (IOException ignored) {

		}
	}
}
