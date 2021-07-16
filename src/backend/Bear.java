package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bear extends Wild_animal {

    Bear() {
        this.price = 400;
        this.volume = 15;
        this.speed = 1;
        this.disappear_time = 5;
        this.cage = 0;
        this.caged = false;
        cageRequired =4;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Animal/bear.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    int getVolume() {
        return volume;
    }
}