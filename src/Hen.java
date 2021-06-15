public class Hen extends Domestic_animal{


    Hen(){
        this.life=100;
        this.price=100;
    }


    @Override
    public Product produce(){ return new Egg(); }

    public Product update(){
        //todo
        //if some given time passes it returns the product
        return new Egg();
        //else return null

    }
}
