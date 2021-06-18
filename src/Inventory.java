import java.util.LinkedList;

public class Inventory {
    int size = 15;
    LinkedList<Product> products = new LinkedList<>();
    LinkedList<Wild_animal> wild_animals = new LinkedList<>();
    int getFilledVolume(){
        int sum=0;

        for (Product p: products){
            sum += p.volume;
        }

        sum += wild_animals.size()*15;

        return sum;

    }

    boolean add(Product p){
        if(( size-getFilledVolume()) >= p.volume){
            products.add(p);
            return true;
        }
        return false;
    }
    boolean add(Wild_animal a){
        if(( size-getFilledVolume()) >= 15){
            wild_animals.add(a);
            return true;
        }
        return false;
    }
    Product getProduct(String productName){
        Product goingOut=null;
        for (Product p: products){
            if(p.getClass().getSimpleName().equals(productName)){
                goingOut = p;
                products.remove(p);
                break;
            }
        }
        return goingOut;
    }
    Wild_animal getWildAnimal(String animalName){
        Wild_animal goingOut=null;
        for (Wild_animal w: wild_animals){
            if(w.getClass().getSimpleName().equals(animalName)){
                goingOut = w;
                wild_animals.remove(w);
                break;
            }
        }
        return goingOut;
    }
    void printInventory(){
        System.out.println("INVENTORY:");
        products.forEach(product -> System.out.println(product.getClass().getSimpleName()));
        wild_animals.forEach(animal -> System.out.println(animal.getClass().getSimpleName()));
        System.out.println("Empty inv space: " + (size - getFilledVolume()));
    }

}
