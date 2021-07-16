package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Ostrich extends Domestic_animal{
    
    Ostrich(){
        this.life=100;
        this.price=200;
        PRODUCE_TIME = 3;
        volume = 5;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Animal/ostrich.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }

    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public Product produce() { return new Feather(); }

//    public backend.Product update(){
//        //
//        //if some given time passes it returns the product
//        return new backend.Feather();
//        //else return null
//
//
//    }

    @Override
    int getVolume() {
        return volume;
    }
}
