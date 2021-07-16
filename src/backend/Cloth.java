package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Cloth extends Product{

    Cloth(){
        this.price=50;
        this.volume=2;
        this.disappear_time=5;
        try {
            imageview = new ImageView();
            imageview.setImage(new Image(new FileInputStream("/res/Product/Cloth.png")));
            imageview.setFitHeight(this.image_height);
            imageview.setFitWidth(this.image_width);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
