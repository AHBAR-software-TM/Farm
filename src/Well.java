public class Well {
    byte water = 5;
    final byte TIME_OF_CHARGE = 3;
    byte timeToCharge = 0;
    boolean plant(){
        if (water > 0){
            water--;
            return true;
        }
        return false;
    }

    boolean charge(){
        if (water != 0 || timeToCharge != 0){
            //second condition ensures that the well is not currently being charged
            return false;
        }
        timeToCharge = TIME_OF_CHARGE;
        return true;

    }

    void update(){
        if(timeToCharge != 0)
            timeToCharge --;
        else return;

        if (timeToCharge == 0)
            water = 5;


    }
}
