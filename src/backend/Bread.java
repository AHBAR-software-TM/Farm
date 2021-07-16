package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bread extends Product{

    Bread(){
        this.price=80;
        this.volume=4;
        this.disappear_time=6;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Product/Bread.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }


}
