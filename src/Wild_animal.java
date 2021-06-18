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

    public void uncage() {
        if (!caged){
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
            inv.add(this);
            System.out.println("Animal moved to inventory.");
            return 2;
        }
        return 0;

    }

    @Override
    public Dir move(Map[][] map,int x,int y){
        //walk random
        // (0,0) location is on top left of screen
        return random_move(map,x,y);
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

}