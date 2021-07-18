package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EggPowderPlant extends Workshop{

    EggPowderPlant(){
        this.level=1;
        this.build_price=150;
        this.produce_time=4;
        resourceType="Egg";
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Workshops/eggpowder1.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }

    @Override
    Product produce() {
        return new Flour();
    }
    @Override
    int getPrice() {
        return build_price;
    }
}
