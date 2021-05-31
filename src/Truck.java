import java.util.LinkedList;

public class Truck {
    LinkedList<Product> products = new LinkedList<>();
    LinkedList<Animal> animals = new LinkedList<>();
    int size = 15;

    int getEmpty(){
        int sum = 0;
        for (Product p : products){
            sum += p.volume;
        }
        sum += 15 * animals.size();

        return size - sum;
    }

    boolean add(Product p){
        if (p.volume <= getEmpty()){
            products.add(p);
            return true;
        }
        return false;
    }
    boolean add(Animal p){
        if (15 <= getEmpty()){
            animals.add(p);
            return true;
        }
        return false;
    }

}
