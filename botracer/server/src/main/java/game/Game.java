package game;

import algorithms.RandomAlgorithm;
import communication.MessageHandler;
import dto.Position;
import dto.messages.s2c.PlayersChangedMessage;
import dto.messages.s2c.RemoveMarksMessage;
import marks.Mark;
import util.DTOUtil;

import java.io.IOException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game implements Runnable{

    private final GameBoard gameBoard;
    private final int movementDelayMillis = 500;

    private Boolean gameRunning = true;
    private List<Player> players;
    private MazeLoader mazeLoader;

    public Game(String path) throws IOException, URISyntaxException {
        mazeLoader = new MazeLoader();
        gameBoard = mazeLoader.createGameBoard(path);
        players = new ArrayList<>();
    }


    /**
     * Starts the game.
     * All players move every {movementsDelayMillis}.
     * If a player moves on a mark, its effect will affect the player.
     * If multiple player step on a mark in one round, all these players will gain the effect of the mark.
     */
    public void runGame() {
        DTOUtil dtoUtil = new DTOUtil();
        while(gameRunning){
            //drawBoard();
            //System.out.print(players);
            try {
                Thread.sleep(movementDelayMillis);

                List<dto.Mark> marksToRemove = new ArrayList<>();
                synchronized (gameBoard) {
                    for (Player player : players) {
                        player.nextStep(gameBoard);

                        Mark mark = gameBoard.getTile(player.getHeight(), player.getWidth()).getMark();
                        if (mark != null) {
                            mark.enter(player);
                            marksToRemove.add(new dto.Mark(new Position(player.getWidth(), player.getHeight()), null));
                        }
                    }

                    MessageHandler.writeAllPlayers(new PlayersChangedMessage(dtoUtil.convertPlayers(players)));
                    if (!marksToRemove.isEmpty()) {
                        MessageHandler.writeAllPlayers(new RemoveMarksMessage(marksToRemove));
                    }
                }
            } catch (InterruptedException e) {
                this.gameRunning = false;
            }
        }
    }

    /**
     * @return the current game board
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Adds a new mark to the game.
     *
     * @param mark which should be added
     * @param height of the mark
     * @param width of the mark
     */
    public void newMark(Mark mark, int height, int width){
        synchronized (gameBoard){
            gameBoard.newMark(mark, height, width);
        }
    }

    @Override
    public void run() {
        runGame();
    }

    /**
     * Stops the game.
     */
    public void stop() {
        this.gameRunning = false;
    }

    public Boolean getGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(Boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * @return all players (and bots) in this game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Adds a new Player with a generated name.
     *
     * @return the added player
     */
    public Player addPlayer() {
        return addPlayer("Bot" + (players.size() + 1));
    }

    /**
     * Adds a new Player with {name}.
     *
     * @param name of the player
     * @return the added player
     */
    public Player addPlayer(String name){
        Position position = mazeLoader.getNewStartingPosition(players, gameBoard);
        Player player = new Player(players.size() + 1, name, position.getHeight(), position.getWidth(), new RandomAlgorithm());
        this.players.add(player);
        return player;
    }

    public void drawBoard(){
        System.out.print("\033[H\033[2J");
        StringBuilder stringBuilder = new StringBuilder(gameBoard.toString());
        for (Player player : players){
            stringBuilder.setCharAt(player.getHeight() * 62 + player.getWidth(),'o');
        }
        System.out.print(stringBuilder.toString());
    }
}
