package backend;

public abstract class Product implements Cloneable{

    int price;
    int volume;
    int disappear_time;
    int currentTime=0;

    @Override
    protected Product clone() throws CloneNotSupportedException {
        return ((Product) super.clone());
    }

    boolean update(){
        currentTime++;
        return currentTime >= disappear_time;
    }
}
