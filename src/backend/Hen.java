package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Hen extends Domestic_animal{


    Hen(){
        super();
        life=100;
        price=100;
        //System.out.println(this.price);
        PRODUCE_TIME = 2;
        volume = 5;
        try {
            imageview = new ImageView();
            imageview.setImage(new Image(new FileInputStream("/res/Animal/hen.png")));
            imageview.setFitHeight(this.image_height);
            imageview.setFitWidth(this.image_width);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Product produce(){ return new Egg(); }

//    public backend.Product update(){
//
//        //if some given time passes it returns the product
//        return new backend.Egg();
//        //else return null
//
//    }


    @Override
    int getVolume() {
        return volume;
    }
}
