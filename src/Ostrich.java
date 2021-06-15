public class Ostrich extends Domestic_animal{
    
    Ostrich(){
        this.life=100;
        this.price=200;
    }

    
    @Override
    public Product produce() { return new Feather(); }

    public Product update(){
        //todo
        //if some given time passes it returns the product
        return new Feather();
        //else return null


    }
}
