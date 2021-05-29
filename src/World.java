import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class World {
    int time = Integer.MIN_VALUE;
    Map[][] worldMap = new Map[6][6];
    LinkedList<Product> inventory = new LinkedList<>(); // todo: implement capacity handling whlie adding object
    int coin;
    LinkedList<Workshop> workshops = new LinkedList<>();
    HashMap<Integer,Wild_animal> WildAttack;

    void update(){
        for (Map[] mapArray :worldMap){
            for (Map m: mapArray)
                m.update();
        }

        workshops.forEach(Workshop::update);

        Wild_animal attacker = WildAttack.get(getTime());
        if(attacker != null){

        }

        time++;

    }

    boolean didUserWin(Mission mission){
        return true; //todo: !!!important!!!! add winnig according to mission task
    }

    World(Mission mission){
        coin = mission.initialCoin;
        this.WildAttack = mission.WildAttack;
    }

    int getTime(){
        return  time - Integer.MIN_VALUE;
    }

}
