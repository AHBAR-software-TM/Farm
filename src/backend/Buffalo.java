package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Buffalo extends Domestic_animal{


    Buffalo(){
        super();
        this.life=100;
        this.price=400;
        //this.productToBeConed = new backend.Milk();
        PRODUCE_TIME = 5;
        volume =15;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Animal/buffalo.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }
    @Override
    public int getPrice() {
        return price;
    }


    @Override
    public Product produce() { return new Milk(); }

    @Override
    int getVolume() {
        return volume;
    }
}
