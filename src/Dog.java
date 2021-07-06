public class Dog extends Animal{

    int price;

    Dog(){
        this.price=100;
        volume = 10;
    }
    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Dir move(Map[][] map,int x,int y){
        //walk random
        //(0,0) location is on top left of screen
        return random_move(map,x,y);
    }

    void hunted(World world){

        Integer count = world.boughtTillNow.get("Dog");
        if(count != null){
            world.boughtTillNow.replace("Dog",--count);
        }
        Logg.LOGGER.config("Animal "+this.hashCode()+" hunted!");
        this.currentlyIn.animalsInside.remove(this);
    }

    @Override
    int getVolume() {
        return volume;
    }
}
