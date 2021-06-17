public class Tiger extends Wild_animal{

    Tiger(){
        this.price=500;
        this.volume=15;
        this.speed=2;
        this.disappear_time=5;
        this.cage=0;
        this.caged=false;
        cageRequired=4;
    }

    public boolean cage(){
        this.cage+=1;
        return this.cage==3;
    }


}
