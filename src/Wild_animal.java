public abstract class Wild_animal extends Animal {

    int price;
    int volume;
    int speed;
    int disappear_time;
    int cage;
    boolean caged;
    public abstract boolean cage();

    @Override
    public void walk() {
        //walk random

    }

}