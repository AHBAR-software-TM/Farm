public abstract class Animal {

    //public static enum Dir { RIGHT,LEFT,UP,DOWN };
    public abstract Dir move(Map[][] map,int x,int y);
    public  Product update(){
        return null;
    }
    public int price;
    Map currentlyIn;
    public int x;
    public int y;
    boolean wannaMove;

}
enum Dir { RIGHT,LEFT,UP,DOWN };