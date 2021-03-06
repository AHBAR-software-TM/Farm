package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MilkPacking extends Workshop{

    MilkPacking(){
        this.level=1;
        this.build_price=400;
        this.produce_time=6;
        resourceType = "Milk";
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Workshops/milkpacking1.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }

    @Override
    Product produce() {
        return new PackedMilk();
    }
    @Override
    int getPrice() {
        return build_price;
    }
}
