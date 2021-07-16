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
        try {
            imageview = new ImageView();
            imageview.setImage(new Image(new FileInputStream("/res/Animal/tiger.png")));
            imageview.setFitHeight(this.image_height);
            imageview.setFitWidth(this.image_width);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
