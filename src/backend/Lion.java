package backend;

public class Lion extends Wild_animal{

    Lion(){
        this.price=300;
        this.volume=15;
        this.speed=1;
        this.disappear_time=5;
        this.cage=0;
        this.caged=false;
        cageRequired=3;
    }
//    public boolean cage(){
//        this.cage+=1;
//        return this.cage == 3;
//    }
    @Override
    public int getPrice() {
        return price;
    }

    @Override
    int getVolume() {
        return volume;
    }
}
