import java.util.LinkedList;
import java.util.PriorityQueue;

public class Map {


    Map(World world) {
        this.grass = 0;
        this.world = world;
    }
    World world;
    public int grass;
    LinkedList<Animal> animalsInside = new LinkedList<>();
    LinkedList<Product> productsInside = new LinkedList<>();


    public void move(Map[][] map) {
        //todo
   }

    public void pickup() {
         world.pickUp(this);

    }

//    public void update(boolean eat, LinkedList<Product> produced) {
//
//        //removing eated animals
//        if (eat) {
//            LinkedList<Domestic_animal> eated = new LinkedList<>();
//            for (Animal a : this.animalsInside) {
//                if (a instanceof Domestic_animal) eated.add((Domestic_animal) a);
//            }
//            for (Domestic_animal d : eated) {
//                this.animalsInside.remove(d);
//            }
//        }
//
//        //adding products to map
//        this.productsInside.addAll(produced);
//
//        //caging wild animals
//        for (Animal a : animalsInside) {
//            if (a instanceof Wild_animal) {
//                if (((Wild_animal) a).cage()) {
//                    ((Wild_animal) a).caged = true;
//                    ((Wild_animal) a).cage = 0;
//                }
//            }
//        }
//
//
//    }
    void update(){
        //Wild_animal w=null;

        productsInside.removeIf(Product::update);

        giveFood();

        for (Animal a: animalsInside){


            if(a instanceof Wild_animal){
                //w = ((Wild_animal) a);
                if (!this.hunt()){
                    this.animalsInside.remove(a);
                    //w = null;
                    continue;
                }
            }


            Product p = a.update();
            if (p != null && !(p instanceof Dead)){
                productsInside.add(p);
            }else if (p != null){
                ((Domestic_animal) a).hunted(world);
            }


        }

        if(gurbaExist()){
            this.pickup();
        }



    }
    boolean hunt(){
        Dog d = dogExist();
        if(d == null){
            for (Animal a: animalsInside){
                if(a instanceof Domestic_animal)
                    ((Domestic_animal) a).hunted(this.world);
            }
            return true;
        }
        else {
            d.hunted(world);
            return false;
        }

    }
    void putAnimalIn(Animal animal){
        animal.currentlyIn.animalsInside.remove(animal);
        animal.currentlyIn = this;
        this.animalsInside.add(animal);
        animal.wannaMove = false;

    }
    Dog dogExist(){
        for (Animal animal:animalsInside){
            if(animal.getClass().getSimpleName().equals("Dog"))
                return (Dog) animal;
        }
        return null;
    }

    void giveFood(){
        PriorityQueue<Domestic_animal> allDoms = giveAllDomesticsIn();
        while (grass > 0 && !allDoms.isEmpty()){
            grass --;
             allDoms.poll().eat();
        }

    }
    PriorityQueue<Domestic_animal> giveAllDomesticsIn(){
        PriorityQueue<Domestic_animal> allDomesticIn = new PriorityQueue<>();
        for (Animal a: animalsInside){
            if (a instanceof Domestic_animal && ((Domestic_animal) a).life<=50){
                allDomesticIn.add(((Domestic_animal) a));
            }
        }
        return allDomesticIn;
    }

    boolean gurbaExist(){
        for (Animal animal:animalsInside){
            if(animal.getClass().getSimpleName().equals("Cat"))
                return true;
        }
        return false;
    }
    boolean hunt(){

        //todo
        return true;
    }


}
