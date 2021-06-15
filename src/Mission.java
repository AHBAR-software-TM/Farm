import java.util.HashMap;

public class Mission {
    int lvl;
    int initialCoin;
    HashMap<Integer,Wild_animal> WildAttack= new HashMap<>();
    int MaximumTime;
    int speedGift;

    //for the amounts required to finish task
    // -1 shows there is no task for that product
    HashMap<String,Integer> productionTask = new HashMap<>();
    HashMap<String,Integer> animalTask = new HashMap<>();
    int coinTask;
}
