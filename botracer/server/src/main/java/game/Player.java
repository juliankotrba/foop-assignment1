package game;

import algorithms.Algorithm;
import algorithms.Step;

public class Player {
    private Memory memory;
    private Algorithm algorithm;
    private int x;
    private int y;
    private String name;

    public Player(){

    }
    public Player(int x,int y){
        this.x=x;
        this.y=y;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void nextStep(GameBoard gameBoard){
        y++;
        //Step step = algorithm.nextStep(memory,gameBoard,x,y);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Player{" +
                "memory=" + memory +
                ", algorithm=" + algorithm +
                ", x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
