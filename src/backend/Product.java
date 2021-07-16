package backend;

import javafx.scene.image.ImageView;

public abstract class Product implements Cloneable{

    int price;
    int volume;
    int disappear_time;
    int currentTime=0;
    String imagePath="/res/hen54.png";
    //todo: set picture path in the constructors

    @Override
    protected Product clone() throws CloneNotSupportedException {
        return ((Product) super.clone());
    }

    boolean update(){
        currentTime++;
        return currentTime >= disappear_time;
    }
}
