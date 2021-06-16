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
        double random = Math.random();
        if(random<0.25) return Dir.RIGHT;
        else if(random<0.5) return Dir.LEFT;
        else if(random<0.75) return Dir.UP;
        else return Dir.DOWN;

    }

}