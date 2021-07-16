package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Feather extends Product{

    Feather() {
        this.price=20;
        this.volume=1;
        this.disappear_time=4;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Product/Feather.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }
}
