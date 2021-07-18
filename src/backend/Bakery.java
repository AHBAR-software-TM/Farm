package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bakery extends Workshop{

    Bakery(){
        this.level=1;
        this.build_price=250;
        this.produce_time=5;
        resourceType = "Flour";
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Workshops/bakery1.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }

    @Override
    Product produce() {
        return new Bread();
    }

    @Override
    int getPrice() {
        return build_price;
    }
}
