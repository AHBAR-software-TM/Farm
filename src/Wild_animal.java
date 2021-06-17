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
        return random_move(map,x,y);
    }

}