package backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Lion extends Wild_animal{

    Lion(){
        this.price=300;
        this.volume=15;
        this.speed=1;
        this.disappear_time=5;
        this.cage=0;
        this.caged=false;
        cageRequired=3;
        imageview = new ImageView();
        imageview.setImage(new Image("/res/Animal/lion.png"));
        imageview.setFitHeight(this.image_height);
        imageview.setFitWidth(this.image_width);

    }
//    public boolean cage(){
//        this.cage+=1;
//        return this.cage == 3;
//    }
    @Override
    public int getPrice() {
        return price;
    }

    @Override
    int getVolume() {
        return volume;
    }
}
