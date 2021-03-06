package backend;

import frontend.WorldGui;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public abstract class Workshop {

    public ImageView imageview;
    public double image_height=100;
    public double image_width=100;

    int level;
    int build_price;
    int produce_time;
    int current_time = 0;
    public boolean isWorking = false;
    String resourceType;
    public DoubleProperty progress = new  SimpleDoubleProperty(0);

    Product update() {
        if (isWorking) {
            current_time++;
            progress.set((float) current_time / produce_time);
            if (current_time == produce_time) {

                Product p = produce();
                Platform.runLater(()-> WorldGui.diring.play());
                current_time = 0;
                progress.set(0);
                isWorking = false;
                return p;

            }
        }
        return null;
    }

    public boolean upgrade(World w){
        if (level!=2){
            level = 2;
            w.coin-=300;
            return true;
        }
        System.out.println("level is 2 already.");
        return false;
    }

    abstract Product produce();

    public boolean startWorking(Inventory inv){
        if(isWorking){
            System.out.println(this.getClass().getSimpleName()+" is already assigned to work.");
            Logg.LOGGER.info(this+" is already assigned to work.");
            return false;
        }
        //System.out.println(resourceType);
        Product p = inv.getProduct(resourceType);
        //System.out.println(resourceType);
        if (p!=null) {
            isWorking = true;
            current_time = 0;
            System.out.println(this.getClass().getSimpleName() + " started working.");
            Logg.LOGGER.info(this+" started working.");
            return true;
        }
        System.out.println("Not enough resource.");
        Logg.LOGGER.info(" Not enough resource for "+this);
        return false;
    }
    int getPrice(){
        return build_price;
    }

}
