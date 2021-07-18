package backend;

import frontend.WorldGui;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Well {
    private byte water = 5;
    public DoubleProperty chargeForBar = new SimpleDoubleProperty(water/5.0);
    final byte TIME_OF_CHARGE = 3;
    byte timeToCharge = 0;
    boolean plant(){
        if (water > 0){
            water--;
            chargeForBar.set(water/5.0);
            return true;
        }
        return false;
    }

    public boolean charge(){
        if (water != 0 || timeToCharge != 0){
            //second condition ensures that the well is not currently being charged
            Logg.LOGGER.warning("backend.Well has no water!");
            return false;
        }
        timeToCharge = TIME_OF_CHARGE;
        return true;

    }

    void update(){
        if(timeToCharge != 0)
            timeToCharge --;
        else return;

        if (timeToCharge == 0){
            water = 5;
            chargeForBar.set(1.0);
            Platform.runLater(()-> WorldGui.diring.play());
        }


    }
}
