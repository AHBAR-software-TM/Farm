public class Ostrich extends Domestic_animal{
    
    Ostrich(){
        this.feather=0;
        this.life=100;
        this.price=200;
    }

    public int feather;
    
    @Override
    public void produce(int level) {
        this.feather+=level;
    }
}
