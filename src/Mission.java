import java.util.HashMap;

public class Mission {
    int lvl=1;
    int initialCoin=1000;
    HashMap<Integer,Wild_animal> WildAttack= new HashMap<>();
    int MaximumTime=0;
    int speedGift=0;

    //for the amounts required to finish task
    // -1 shows there is no task for that product
    HashMap<String,Integer> productionTask = new HashMap<>();
    HashMap<String,Integer> animalTask = new HashMap<>();
    int coinTask=1100;
}
