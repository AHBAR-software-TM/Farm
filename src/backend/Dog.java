package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Dog extends Animal{

    int price;

    Dog(){
        this.price=100;
        volume = 10;
        try {
            imageview = new ImageView();
            imageview.setImage(new Image(new FileInputStream("/res/Animal/dog.png")));
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

    @Override
    public Dir move(Map[][] map,int x,int y){
        //walk random
        //(0,0) location is on top left of screen
        return random_move(map,x,y);
    }

    void hunted(World world){

        Integer count = world.boughtTillNow.get("backend.Dog");
        if(count != null){
            world.boughtTillNow.replace("backend.Dog",--count);
        }
        Logg.LOGGER.config("backend.Animal "+this.hashCode()+" hunted!");
        this.currentlyIn.getAnimalsInside().remove(this);
    }

    @Override
    int getVolume() {
        return volume;
    }
}
