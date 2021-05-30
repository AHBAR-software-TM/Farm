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
}
