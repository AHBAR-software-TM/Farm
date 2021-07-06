import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@JsonDeserialize(using = WildAnimalDeserial.class)
public abstract class Wild_animal extends Animal {


    int price;
    int volume;
    int speed;
    int disappear_time;
    int cagedTime=0;
    int cage;
    boolean caged;
    boolean isCagePutThisTurn=false;
    int cageRequired;
    Wild_animal(){}

    public void uncage() {
        if (!caged && cage>0){
            this.cage -= 1;
        }
    }

    public int cage(Inventory inv) {
        //0 for not caged, 1 for stocked in cage, 2 for moving to inv
        if (!caged){
            isCagePutThisTurn = true;
            this.cage += 1;
            if (this.cage == cageRequired) {
                caged = true;
                cagedTime = 0;
                return 1;

            }
        }else {
            if(inv.add(this)){
            System.out.println("Animal moved to inventory.");
                return 2;
            }
            return 1;
        }
        return 0;

    }

    @Override
    public Dir move(Map[][] map,int x,int y){
        //walk random
        // (0,0) location is on top left of screen
        if(!caged)
            return random_move(map,x,y);
        else
            return null;
    }
    public Product update(){
        if (!caged){
            if (isCagePutThisTurn){
                isCagePutThisTurn=false;
            }
            else {
                uncage();
            }
            return null;
        }
        else {
            cagedTime++;
            if (cagedTime==disappear_time){
                caged=false;
                cagedTime=0;
                cage=0;
                isCagePutThisTurn = false;
            }
        }
        return null;
    }

    @Override
    String printToMap() {
        return AnimalColor.RED.s+"."+AnimalColor.RESET.s;
    }
}