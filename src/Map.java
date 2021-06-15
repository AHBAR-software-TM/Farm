import java.util.LinkedList;

public class Map {


    Map(){
        this.grass=0;
    }

    public int grass;
    LinkedList<Animal> animalsInside = new LinkedList<>();
    LinkedList<Product> productsInside = new LinkedList<>();

    public void plant_grass(){
        this.grass+=1;
    }

    public void where_go(Animal animal, int[] location){
        Game_Processor.map[location[1]][location[2]].animalsInside.add(animal);
        this.animalsInside.remove(animal);
    }
    public LinkedList<Product> pickup() {
        LinkedList<Product> picked = (LinkedList<Product>) animalsInside.clone();
        this.productsInside.clear();
        return picked;
    }

    public void update(boolean eat,LinkedList<Product> produced){

        //removing eated animals
        if(eat) {
            LinkedList<Domestic_animal> eated = new LinkedList<>();
            for (Animal a : this.animalsInside) {
                if (a instanceof Domestic_animal) eated.add((Domestic_animal) a);
            }
            for (Domestic_animal d : eated) {
                this.animalsInside.remove(d);
            }
        }

        //adding products to map
        this.productsInside.addAll(produced);

        //caging wild animals
        for(Animal a: animalsInside){
            if(a instanceof Wild_animal) {
                if(((Wild_animal) a).cage()) {
                    ((Wild_animal) a).caged=true;
                    ((Wild_animal) a).cage=0;
                }
            }
        }



















    }






}





