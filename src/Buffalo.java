public class Buffalo extends Domestic_animal{


    Buffalo(){
        this.life=100;
        this.price=400;
    }



    @Override
    public Product produce() { return new Milk(); }

    public Product update(){
        //todo
        //if some given time passes it returns the product
        return new Milk();
        //else return null
    }
}
