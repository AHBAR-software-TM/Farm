package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Spinnery extends Workshop{

    Spinnery(){
        this.level=1;
        this.build_price=250;
        this.produce_time=5;
        resourceType="backend.Feather";
        try {
            imageview = new ImageView();
            imageview.setImage(new Image(new FileInputStream("/res/Workshops/spinnery1.png")));
            imageview.setFitHeight(this.image_height);
            imageview.setFitWidth(this.image_width);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    Product produce() {
        return new Cloth();
    }
    @Override
    int getPrice() {
        return build_price;
    }

}
