public class ClearMemoryMark implements Mark {

    @Override
    public void enter(Player player) {
        player.getMemory().reset();
    }
}
