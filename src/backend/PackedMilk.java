package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PackedMilk extends Product{

    PackedMilk(){
        this.price=60;
        this.volume=2;
        this.disappear_time=5;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Product/PackedMilk.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }
}
