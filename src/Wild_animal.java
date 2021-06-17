public abstract class Wild_animal extends Animal {


    int price;
    int volume;
    int speed;
    int disappear_time;
    int cage;
    boolean caged;
    public abstract boolean cage();

    @Override
    public Dir move(Map[][] map,int x,int y){

        //walk random
        // (0,0) location is on top left of screen
        double random = Math.random();
        Dir direction;

        //down right corner
        if(x==5 && y==5) {
            if(random<0.5) direction=Dir.LEFT;
            else direction=Dir.UP;
        }
        //down left corner
        else if(x==0 && y==5) {
            if(random<0.5) direction=Dir.RIGHT;
            else direction=Dir.UP;
        }
        //up right corner
        else if(x==5 && y==0) {
            if(random<0.5) direction=Dir.LEFT;
            else direction=Dir.DOWN;
        }
        //up left corner
        else if(x==0 && y==0) {
            if(random<0.5) direction=Dir.RIGHT;
            else direction=Dir.DOWN;
        }
        //up edge
        else if(y==0) {
            if(random<0.333) direction=Dir.DOWN;
            else if(random<0.666) direction=Dir.RIGHT;
            else direction=Dir.LEFT;
        }
        //right edge
        else if(x==5) {
            if(random<0.333) direction=Dir.DOWN;
            else if(random<0.666) direction=Dir.UP;
            else direction=Dir.LEFT;
        }
        //down edge
        else if(y==5) {
            if(random<0.333) direction=Dir.UP;
            else if(random<0.666) direction=Dir.RIGHT;
            else direction=Dir.LEFT;
        }
        //left edge
        else if(x==0) {
            if(random<0.333) direction=Dir.DOWN;
            else if(random<0.666) direction=Dir.RIGHT;
            else direction=Dir.UP;
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

}