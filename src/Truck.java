import java.util.LinkedList;

public class Truck {
    LinkedList<Product> products = new LinkedList<>();
    LinkedList<Animal> animals = new LinkedList<>();
    boolean go = false;
    final int RETURN_TIME = 15;
    int inTripTime=0;

    int size = 15;

    int getEmpty(){
        int sum = 0;
        if (go)
            return sum;
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
    int update(){
        if(go){
            inTripTime++;
            if(inTripTime==RETURN_TIME) {
                return convertToCoin();
            }
        }
        return 0;
    }
    int convertToCoin(){
        int value = estimateValue();
        animals.clear();
        products.clear();
        return value;
    }

    int estimateValue(){
        int value = 0;
        for (Product p: products){
            value+=p.price;
        }
        for (Animal a: animals){
            value+=a.price;
        }

        return value;
    }

    void go(){
        if(!go){
        go = true;
        inTripTime= 0;}
        System.out.printf("The truck is on the way.\nValue: %d   Time left: %d\n",estimateValue(),RETURN_TIME-inTripTime);
    }

}
