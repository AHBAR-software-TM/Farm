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

        uncagedImg.setImage(new Image("/res/Animal/bear.png"));
        uncagedImg.setFitHeight(this.image_height);
        uncagedImg.setFitWidth(this.image_width);

        cagedImg.setImage(new Image("/res/Animal/bearcaged.png"));
        cagedImg.setFitHeight(this.image_height);
        cagedImg.setFitWidth(this.image_width);

        imageview = uncagedImg;

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