package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IcecreamFactory extends Workshop{

    IcecreamFactory(){
        this.level=1;
        this.build_price=550;
        this.produce_time=7;
        resourceType = "PackedMilk";
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Workshops/icecreamfactory1.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }

    @Override
    Product produce() {
        return new Icecream();
    }
    @Override
    int getPrice() {
        return build_price;
    }
}
