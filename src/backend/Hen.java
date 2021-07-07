package backend;

public class Hen extends Domestic_animal{


    Hen(){
        super();
        life=100;
        price=100;
        //System.out.println(this.price);
        PRODUCE_TIME = 2;
        volume = 5;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Product produce(){ return new Egg(); }

//    public backend.Product update(){
//
//        //if some given time passes it returns the product
//        return new backend.Egg();
//        //else return null
//
//    }


    @Override
    int getVolume() {
        return volume;
    }
}
