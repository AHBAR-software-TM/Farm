package backend;

import javafx.scene.image.ImageView;

public abstract class Animal {

    //public static enum backend.Dir { RIGHT,LEFT,UP,DOWN };
    public abstract Dir move(Map[][] map, int x, int y);
    public Product update() {
        return null;
    }

    public int getPrice() {
        return price;
    }

    public ImageView imageview;
    public double image_height=60;
    public double image_width=60;

    int price=-1;
    Map currentlyIn;
    public int x;
    public int y;
    boolean wannaMove;
    int volume;
    public String imagePath="/res/hen54.png";
    AnimalColor col = AnimalColor.values()[(int)(Math.random()*5)];

    public Dir random_move(Map[][] map, int x, int y) {

        double random = Math.random();
        Dir direction;

        //down right corner
        if (x == 5 && y == 5) {
            if (random < 0.5) direction = Dir.LEFT;
            else direction = Dir.UP;
        }
        //down left corner
        else if (x == 0 && y == 5) {
            if (random < 0.5) direction = Dir.RIGHT;
            else direction = Dir.UP;
        }
        //up right corner
        else if (x == 5 && y == 0) {
            if (random < 0.5) direction = Dir.LEFT;
            else direction = Dir.DOWN;
        }
        //up left corner
        else if (x == 0 && y == 0) {
            if (random < 0.5) direction = Dir.RIGHT;
            else direction = Dir.DOWN;
        }
        //up edge
        else if (y == 0) {
            if (random < 0.333) direction = Dir.DOWN;
            else if (random < 0.666) direction = Dir.RIGHT;
            else direction = Dir.LEFT;
        }
        //right edge
        else if (x == 5) {
            if (random < 0.333) direction = Dir.DOWN;
            else if (random < 0.666) direction = Dir.UP;
            else direction = Dir.LEFT;
        }
        //down edge
        else if (y == 5) {
            if (random < 0.333) direction = Dir.UP;
            else if (random < 0.666) direction = Dir.RIGHT;
            else direction = Dir.LEFT;
        }
        //left edge
        else if (x == 0) {
            if (random < 0.333) direction = Dir.DOWN;
            else if (random < 0.666) direction = Dir.RIGHT;
            else direction = Dir.UP;
        }
        //rest of map
        else {
            if (random < 0.25) direction = Dir.RIGHT;
            else if (random < 0.5) direction = Dir.LEFT;
            else if (random < 0.75) direction = Dir.UP;
            else direction = Dir.DOWN;
        }
        return direction;
    }
    int getVolume(){
        return volume;
    }
    String printToMap(){
        return col.s+"."+AnimalColor.RESET.s;
    }
}
enum AnimalColor{
    //WHITE("\033[0;37m"),
    //BLACK  ("\033[0;30m"),
    GREEN  ("\033[0;32m"),
    YELLOW ("\033[0;33m"),
    BLUE ("\033[0;34m"),
    PURPLE ("\033[0;35m"),
    CYAN("\033[0;36m"),
    RED ( "\033[0;31m"),
    RESET ("\033[0m")
    ;
    String s;
    AnimalColor(String s){
        this.s = s;
    }

}

enum Dir { RIGHT,LEFT,UP,DOWN };