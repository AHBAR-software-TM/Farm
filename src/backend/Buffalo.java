package backend;

public class Buffalo extends Domestic_animal{


    Buffalo(){
        super();
        this.life=100;
        this.price=400;
        //this.productToBeConed = new backend.Milk();
        PRODUCE_TIME = 5;
        volume =15;
    }
    @Override
    public int getPrice() {
        return price;
    }


    @Override
    public Product produce() { return new Milk(); }

    @Override
    int getVolume() {
        return volume;
    }
}
