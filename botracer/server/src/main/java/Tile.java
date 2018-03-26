public abstract class Tile{
    private Mark mark;

    public abstract boolean isWalkable();

    public void enters(Player player){
        mark.enter(player);
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
