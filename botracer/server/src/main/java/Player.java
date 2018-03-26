public class Player {
    private Memory memory;
    private Algorithm algorithm;
    private int x;
    private int y;

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
        Step step = algorithm.nextStep(memory,gameBoard,x,y);

    }
}
