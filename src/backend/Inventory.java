package backend;

import javafx.scene.image.ImageView;

import java.util.LinkedList;

public class Inventory {
    public int size = 15;
    public LinkedList<Product> products = new LinkedList<>();
    public LinkedList<Wild_animal> wild_animals = new LinkedList<>();
    public int getFilledVolume(){
        int sum=0;

        for (Product p: products){
            sum += p.volume;
        }

        sum += wild_animals.size()*15;

        return sum;

    }
    public void removeObj(Object obj){
        if(obj instanceof Wild_animal)
            wild_animals.remove(obj);
        else if (obj instanceof Product)
            products.remove(obj);

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

    public String getPathForNthElement(int n){
        try {
            Product p = products.get(n);
            return p.imagePath;
        }catch (IndexOutOfBoundsException e){
            try {
                Animal a = wild_animals.get(n - products.size());
                return a.imagePath;
            }catch (IndexOutOfBoundsException ee){
                return null;
            }
        }

    }

    public ImageView getImageViewForNthElement(int n){
        try {
            Product p = products.get(n);
            return p.imageview;
        }catch (IndexOutOfBoundsException e){
            try {
                Animal a = wild_animals.get(n - products.size());
                return a.imageview;
            }catch (IndexOutOfBoundsException ee){
                return null;
            }
        }

    }

}
