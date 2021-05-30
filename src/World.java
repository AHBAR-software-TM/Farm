import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class World {
    Mission mission;
    int time = Integer.MIN_VALUE;

    Map[][] worldMap = new Map[6][6];

    //LinkedList<Product> inventory = new LinkedList<>(); // todo: implement capacity handling whlie adding object

    Inventory inventory = new Inventory();
    int coin;
    LinkedList<Workshop> workshops = new LinkedList<>();


    HashMap<Integer,Wild_animal> WildAttack;

    //task related data
    HashMap<String,Integer> producedTillNow = new HashMap<>();
    HashMap<String, Integer> boughtTillNow = new HashMap<>();


    //todo: !!!IMPORTANT!!! handle production from animal and workshop to map
    //todo: !!!IMPORTANT!!! check if animal is dead or alive
    void update(){

        System.out.printf("%d time units passed.\n",getTime());

        for (Map[] mapArray : worldMap){
            for (Map m: mapArray){
                m.update();

            }
        }

        workshops.forEach(Workshop::update);

        Wild_animal attacker = WildAttack.get(getTime());
        if(attacker != null){
            worldMap[(int) (Math.random()*6) %6][(int) (Math.random()*6) %6].animalsInside.add(attacker);
        }

        printMapGrass();

        taskAccompPrint();

        //todo: !!!IMPORTANT!!! print the other mentioned thing (page:9)


        time++;

    }

    boolean didUserWin(Mission mission){
        return true; //todo: !!!important!!!! add winning according to mission task
    }

    World(Mission mission){
        this.mission = mission;
        coin = mission.initialCoin;
        this.WildAttack = mission.WildAttack;
        for (Entry<String,Integer> entry:mission.productionTask.entrySet()){
            producedTillNow.put(entry.getKey(),0);
        }
        for (Entry<String,Integer> entry:mission.animalTask.entrySet()){
            boughtTillNow.put(entry.getKey(),0);
        }

    }

    int getTime(){
        return  time - Integer.MIN_VALUE;
    }

    void printMapGrass(){
        System.out.println("           ===== Map =====\n");
        System.out.println("+-----+-----+-----+-----+-----+-----+");
        for (Map[] mapArray : worldMap){
            for (Map m: mapArray){
                System.out.printf("| %-4d",m.grass);

            }
            System.out.println("|\n");;
        }
        System.out.println("+-----+-----+-----+-----+-----+-----+");
    }

    ///todo: !!!IMPORTANT!!! handle task accomplishment change
    void taskAccompPrint(){
        System.out.println("     ====== Tasks ======");
        for (Entry<String,Integer> entry:mission.productionTask.entrySet()){
            System.out.printf("%s: %d/%d\n",entry.getKey(),
                    producedTillNow.get(entry.getKey()),
                    entry.getValue());
        }
        for (Entry<String,Integer> entry:mission.animalTask.entrySet()){
            System.out.printf("%s: %d/%d\n",entry.getKey(),
                    boughtTillNow.get(entry.getKey()),
                    entry.getValue());
        }
        System.out.printf("Coin: %d/%d\n",coin,mission.coinTask);
    }

    Animal checkMoneyToBuy(Animal animal){
        if(animal == null)
            return null;
        if (animal.price <= coin )
        {
            coin -= animal.price;
            return animal;
        }
        return null;

    }
    Animal buy(String animalName){
        Animal dm=null;
        switch (animalName){
            case "Hen":
            case "HEN":
            case "hen":
                dm = new Hen();
                break;

            case "Dog":
            case "DOG":
            case "dog":
                dm = new Dog();
                break;

            case "Buffalo":
            case "BUFFALO":
            case "buffalo":
                dm = new Buffalo();
                break;

            case "Cat":
            case "CAT":
            case "cat":
                dm = new Cat();
                break;

            case "Turkey":
            case "TURKEY":
            case "turkey":
                dm = new Ostrich();
                break;

        }
        dm = checkMoneyToBuy(dm);

        if (dm == null){
            return null;
        }

        Integer bought = boughtTillNow.get(dm.getClass().getSimpleName());
        if(bought != null){
            bought++;
            boughtTillNow.replace(dm.getClass().getSimpleName(),bought);
        }

        worldMap[(int)(Math.random()*6)%6][(int)(Math.random()*6)].animalsInside.add(dm);

        return dm;


    }

    void pickUp(int i, int j){
        if(i>6 || i<1 || j>6 || j<1 ){
            System.out.println("Invalid location!");
            return;
        }
        if(worldMap[i-1][j-1].productsInside.isEmpty())
        {
            System.out.println("Location is empty.");
            return;
        }
        for (Product p : worldMap[i-1][j-1].productsInside){
            if (inventory.add(p)){

                worldMap[i-1][j-1].productsInside.remove(p);

                Integer produced = producedTillNow.get(p.getClass().getSimpleName());
                if(produced != null){
                    produced++;
                    producedTillNow.replace(p.getClass().getSimpleName(),produced);
                }

                System.out.printf("%s moved to inventory.\n",p.getClass().getSimpleName());
            }
            else {
                System.out.printf("Not enough space for %s",p.getClass().getSimpleName());
            }
        }

    }

}
