public class Buffalo extends Domestic_animal{


    Buffalo(){
        this.milk=0;
        this.life=100;
        this.price=400;
    }


    public int milk;

    @Override
    public void produce(int level) {
        this.milk+=level;
    }
}
