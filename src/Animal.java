public abstract class Animal {

    public static enum Dir { RIGHT,LEFT,UP,DOWN };
    public abstract Dir move(Map[][] map,int x,int y);
    public int price;
    Map currentlyIn;
    public int x;
    public int y;
    boolean wannaMove;

}
