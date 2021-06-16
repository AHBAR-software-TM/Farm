public class Dog extends Animal{

    int price;

    Dog(){
        this.price=100;
    }

    @Override
    public void walk() {
        //walk random
    }
    void hunted(World world){

        Integer count = world.boughtTillNow.get("Dog");
        if(count != null){
            world.boughtTillNow.replace("Dog",--count);
        }
        this.currentlyIn.animalsInside.remove(this);
    }
}
