package backend;


import javafx.application.Platform;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Truck {
    public LinkedList<Product> products = new LinkedList<>();
    public LinkedList<Animal> animals = new LinkedList<>();
    boolean go = false;
    final int RETURN_TIME = 10;
    int inTripTime = 0;
    public DoubleProperty timeProperty = new SimpleDoubleProperty(0);
    public StringProperty loadProperty = new SimpleStringProperty();

    int size = 15;

    int getEmpty() {
        int sum = 0;
        if (go)
            return sum;
        for (Product p : products) {
            sum += p.volume;
        }
        for (Animal p : animals) {
            sum += p.getVolume();
        }

        return size - sum;
    }

    public void removeObj(Object obj){
        if (obj instanceof Product)
            products.remove(obj);
        else if (obj instanceof Animal)
            animals.remove(obj);

        loadProperty.set(buildString());

    }

    boolean add(Product p) {
        if (p.volume <= getEmpty()) {
            products.add(p);
            loadProperty.set(buildString());
            return true;
        }
        return false;
    }

    boolean add(Animal p) {
        if (p.getVolume() <= getEmpty()) {
            animals.add(p);
            loadProperty.set(buildString());
            return true;
        }
        return false;
    }

    int update() {
        if (go) {
            inTripTime++;
            timeProperty.set((double) inTripTime / RETURN_TIME);
            if (inTripTime == RETURN_TIME) {
                return convertToCoin();
            }
        }
        return 0;
    }

    int convertToCoin() {
        int value = estimateValue();
        animals.clear();
        products.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadProperty.set("");
            }
        });
        //loadProperty.set("");
        go = false;
        return value;
    }

    int estimateValue() {
        int value = 0;
        for (Product p : products) {
            value += p.price;
        }
        for (Animal a : animals) {
            value += a.getPrice();
        }

        return value;
    }

    public void go() {
        if (!go) {
            go = true;
            inTripTime = 0;
            timeProperty.set(0);
        }
        System.out.printf("The truck is on the way.\nValue: %d   Time left: %d\n", estimateValue(), RETURN_TIME - inTripTime);
    }

    void print() {
        System.out.println("TRUCK:");
        products.forEach(p -> System.out.println(p.getClass().getSimpleName()));
        animals.forEach(p -> System.out.println(p.getClass().getSimpleName()));
        System.out.println("Empty space: " + getEmpty());
        if (go) {
            System.out.printf("The truck is on the way.\nValue: %d   Time left: %d\n", estimateValue(), RETURN_TIME - inTripTime);
        }
    }

    public String buildString(){

        HashMap<String,Integer> thingToCount = new HashMap<>();
        for (Product p :products){
            if (thingToCount.containsKey(p.getClass().getSimpleName())){
                int a = thingToCount.get(p.getClass().getSimpleName());
                a++;
                thingToCount.replace(p.getClass().getSimpleName(),a);
            }
            else {
                thingToCount.put(p.getClass().getSimpleName(),1);
            }
        }
        for (Animal a :animals){
            if (thingToCount.containsKey(a.getClass().getSimpleName())){
                int p = thingToCount.get(a.getClass().getSimpleName());
                p++;
                thingToCount.replace(a.getClass().getSimpleName(),p);
            }
            else {
                thingToCount.put(a.getClass().getSimpleName(),1);
            }
        }
        StringBuilder ans = new StringBuilder();
        for (Map.Entry<String,Integer> ent: thingToCount.entrySet()){
            ans.append(ent.getKey()).append(": ").append(ent.getValue()).append('\n');
        }
        ans.append("Earn: ").append(estimateValue());
        return ans.toString();
    }

}
