public class Buffalo extends Domestic_animal{


    Buffalo(){
        super();
        this.life=100;
        this.price=400;
        //this.productToBeConed = new Milk();
        PRODUCE_TIME = 5;
    }



    @Override
    public Product produce() { return new Milk(); }


}
