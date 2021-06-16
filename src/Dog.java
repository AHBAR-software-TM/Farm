public class Dog extends Animal{

    int price;

    Dog(){
        this.price=100;
    }

    @Override
    public Dir move(Map[][] map,int x,int y){

        //walk random
        double random = Math.random();
        if(random<0.25) return Dir.RIGHT;
        else if(random<0.5) return Dir.LEFT;
        else if(random<0.75) return Dir.UP;
        else return Dir.DOWN;

    }
    void hunted(World world){

        Integer count = world.boughtTillNow.get("Dog");
        if(count != null){
            world.boughtTillNow.replace("Dog",--count);
        }
        this.currentlyIn.animalsInside.remove(this);
    }
}
