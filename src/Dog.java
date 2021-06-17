public class Dog extends Animal{

    int price;

    Dog(){
        this.price=100;
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
        this.currentlyIn.animalsInside.remove(this);
    }
}
