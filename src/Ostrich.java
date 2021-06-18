public class Ostrich extends Domestic_animal{
    
    Ostrich(){
        this.life=100;
        this.price=200;
        PRODUCE_TIME = 3;
    }

    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public Product produce() { return new Feather(); }

//    public Product update(){
//        //
//        //if some given time passes it returns the product
//        return new Feather();
//        //else return null
//
//
//    }
}
