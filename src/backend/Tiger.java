package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Tiger extends Wild_animal{

    Tiger(){
        this.price=500;
        this.volume=15;
        this.speed=2;
        this.disappear_time=5;
        this.cage=0;
        this.caged=false;
        cageRequired=4;
        uncagedImg.setImage(new Image("/res/Animal/tiger.png"));
        uncagedImg.setFitHeight(this.image_height);
        uncagedImg.setFitWidth(this.image_width);

        cagedImg.setImage(new Image("/res/Animal/tigercaged.png"));
        cagedImg.setFitHeight(this.image_height);
        cagedImg.setFitWidth(this.image_width);

        imageview = uncagedImg;

    }
    @Override
    public int getPrice() {
        return price;
    }
//    public boolean cage(){
//        this.cage+=1;
//        return this.cage==3;
//    }


    @Override
    int getVolume() {
        return volume;
    }
}
