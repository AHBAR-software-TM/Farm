import java.util.HashMap;

public class Mission {
    public int lvl=1;
    public int initialCoin=1000;
    public HashMap<Integer,Wild_animal> WildAttack= new HashMap<>();
    public int MaximumTime=0;
    public int speedGift=0;

    //for the amounts required to finish task
    // -1 shows there is no task for that product
    public HashMap<String,Integer> productionTask = new HashMap<>();
    public HashMap<String,Integer> animalTask = new HashMap<>();
    public int coinTask=1100;
    public Mission(){
        WildAttack.put(2,new Tiger());
    }
}
