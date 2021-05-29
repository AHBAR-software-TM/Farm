public class Hen extends Domestic_animal{


    Hen(){
        this.egg=0;
        this.life=100;
        this.price=100;
    }

    public int egg;

    @Override
    public void produce(int level) {
        this.egg+=level;
    }
}
