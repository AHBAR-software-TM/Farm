package backend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class World   {
    public Mission mission;
    int time = Integer.MIN_VALUE;

    public Map[][] worldMap = new Map[6][6];
    LinkedList<Animal> allDomestics = new LinkedList<>();

    //LinkedList<backend.Product> inventory = new LinkedList<>();

    public Inventory inventory = new Inventory();
    public int coin;
    LinkedList<Workshop> workshops = new LinkedList<>();


    HashMap<Integer, Wild_animal> WildAttack;

    //task related data
    public HashMap<String, Integer> producedTillNow = new HashMap<>();
    public HashMap<String, Integer> boughtTillNow = new HashMap<>();

    public Well well = new Well();
    public Truck truck = new Truck();


    public void update() {

        System.out.printf("%d time units passed since start.\n", getTime());
        coin += truck.update();
        well.update();

        workshops.forEach(W -> {
            Product p = W.update();
            if (p != null) {
                int i = (int) (Math.random() * 6 % 6);
                int j = (int) (Math.random() * 6 % 6);
                worldMap[i][j].getProductsInside().add(p);
                if (W.level == 2) {
                    worldMap[i][j].getProductsInside().add(W.produce());
                }
            }
        });

        for (Map[] mapArray : worldMap) {
            for (Map m : mapArray) {
                m.update();

            }
        }


        Wild_animal attacker = WildAttack.get(getTime());
        if (attacker != null) {
            int x = (int) (Math.random() * 6) % 6;
            int y = (int) (Math.random() * 6) % 6;
            worldMap[x][y].getAnimalsInside().add(attacker);
            attacker.currentlyIn = worldMap[x][y];

        }


        time++;

        move();

    }

    int didUserWin() {
        //return 0 if not, return 1 if won, return 2 if won fast

        //boolean doneIt = true;
        for (Entry<String, Integer> ent : mission.animalTask.entrySet()) {
            if (boughtTillNow.get(ent.getKey()) < ent.getValue()) {
                return 0;
            }
        }
        for (Entry<String, Integer> ent : mission.productionTask.entrySet()) {
            if (producedTillNow.get(ent.getKey()) < ent.getValue())
                return 0;
        }

        if (coin < mission.coinTask)
            return 0;

        if (mission.MaximumTime < time)
            return 1;
        return 2;

    }

    public World(Mission mission) {
        this.mission = mission;
        coin = mission.initialCoin;
        this.WildAttack = mission.WildAttack;
        for (Entry<String, Integer> entry : mission.productionTask.entrySet()) {
            producedTillNow.put(entry.getKey(), 0);
        }
        for (Entry<String, Integer> entry : mission.animalTask.entrySet()) {
            boughtTillNow.put(entry.getKey(), 0);
        }


    }

    void initMap(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++)
                worldMap[i][j] = new Map(this);
        }
    }

//    public World(){
//
//    }

    public void setMission(Mission mission){
        this.mission = mission;
        coin = mission.initialCoin;
        this.WildAttack = mission.WildAttack;
        for (Entry<String, Integer> entry : mission.productionTask.entrySet()) {
            producedTillNow.put(entry.getKey(), 0);
        }
        for (Entry<String, Integer> entry : mission.animalTask.entrySet()) {
            boughtTillNow.put(entry.getKey(), 0);
        }
    }

    int getTime() {
        return time - Integer.MIN_VALUE + 1;
    }

    public void printMapGrass() {
        System.out.println("           ===== Map =====\n");
        System.out.println("+-----+-----+-----+-----+-----+-----+");
        for (Map[] mapArray : worldMap) {
            for (Map m : mapArray) {
                System.out.printf("| %-4d", m.getGrass());

            }
            System.out.println("|");

        }
        System.out.println("+-----+-----+-----+-----+-----+-----+");
    }


    void taskAccompPrint() {
        System.out.println("     ====== Tasks ======");
        for (Entry<String, Integer> entry : mission.productionTask.entrySet()) {
            System.out.printf("%s: %d/%d\n", entry.getKey(),
                    producedTillNow.get(entry.getKey()),
                    entry.getValue());
        }
        for (Entry<String, Integer> entry : mission.animalTask.entrySet()) {
            System.out.printf("%s: %d/%d\n", entry.getKey(),
                    boughtTillNow.get(entry.getKey()),
                    entry.getValue());
        }
        if(mission.coinTask>0)
            System.out.printf("Coin: %d/%d\n", coin, mission.coinTask);
    }

    Animal checkMoneyToBuy(Animal animal) {
        if (animal == null)
            return null;
        if (animal.getPrice() <= coin) {
            coin -= animal.getPrice();
            //System.out.println("coin -:"+coin+ " kk "+ animal.price);
            return animal;
        }
        return null;

    }

    Workshop checkMoneyToBuy(Workshop workshop) {
        if (workshop == null)
            return null;
        if (workshop.getPrice() <= coin) {
            coin -= workshop.getPrice();
            return workshop;
        }
        return null;

    }

    public Animal buy(String animalName) {

        Animal dm = null;
        switch (animalName) {
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

            case "Ostrich":
            case "OSTRICH":
            case "ostrich":
                dm = new Ostrich();
                break;

        }


        if (dm == null) {
            return null;
        }

        //System.out.println("anim p1:"+ dm.price);
        dm = checkMoneyToBuy(dm);
        //System.out.println("anim p1:"+ dm.price);

        Integer bought = boughtTillNow.get(dm.getClass().getSimpleName());
        if (bought != null) {
            bought++;
            boughtTillNow.replace(dm.getClass().getSimpleName(), bought);
        }
        dm.currentlyIn = worldMap[(int) (Math.random() * 6) % 6][(int) (Math.random() * 6)%6];

        dm.currentlyIn.getAnimalsInside().add(dm);
        return dm;


    }

    void pickUp(int i, int j) {
        if (i > 6 || i < 1 || j > 6 || j < 1) {
            System.out.println("Invalid location!");
            return;
        }
        if (worldMap[i - 1][j - 1].getProductsInside().isEmpty()) {
            System.out.println("Location is empty.");
            return;
        }
        LinkedList<Product> toBeRemoved = new LinkedList<>();
        for (Product p : worldMap[i - 1][j - 1].getProductsInside()) {
            if (inventory.add(p)) {

                //worldMap[i - 1][j - 1].productsInside.remove(p);
                toBeRemoved.add(p);

                Integer produced = producedTillNow.get(p.getClass().getSimpleName());
                if (produced != null) {
                    produced++;
                    producedTillNow.replace(p.getClass().getSimpleName(), produced);
                }

                System.out.printf("%s moved to inventory.\n", p.getClass().getSimpleName());
            } else {
                System.out.printf("Not enough space for %s\n", p.getClass().getSimpleName());
            }
        }
        for (Product p : toBeRemoved) {
            worldMap[i - 1][j - 1].getProductsInside().remove(p);
        }

    }

    void pickUp(Map map) {
        LinkedList<Product> toBeRemoved = new LinkedList<>();
        for (Product p : map.getProductsInside()) {
            if (inventory.add(p)) {

                //map.productsInside.remove(p);
                toBeRemoved.add(p);

                Integer produced = producedTillNow.get(p.getClass().getSimpleName());
                if (produced != null) {
                    produced++;
                    producedTillNow.replace(p.getClass().getSimpleName(), produced);
                }

                System.out.printf("%s moved to inventory.\n", p.getClass().getSimpleName());
                Logg.LOGGER.config("product "+p+" moved to inventory");
            } else {
                System.out.printf("Not enough space for %s", p.getClass().getSimpleName());
                Logg.LOGGER.config("Not enough space for product "+p);
            }


        }
        for (Product p : toBeRemoved) {
            map.getProductsInside().remove(p);
            Logg.LOGGER.config("product "+p+" removed from "+map);
        }
    }

    boolean wellCharge() {
        boolean ans = well.charge();
        if (ans) {
            System.out.println("The well will be filled soon.");
            return true;
        } else {
            System.out.println("The well was not empty , or is being filled.");
            return false;
        }
    }

    public boolean plant(int x, int y) {
        if (well.plant()) {
            //assert worldMap[x-1][y-1] != null;
            if (x < 7 && y < 7)
                worldMap[x - 1][y - 1].setGrass(worldMap[x - 1][y - 1].getGrass() + 1);
                //worldMap[x - 1][y - 1].updatePic();
            else
                Logg.LOGGER.warning("index out of bound");
            return true;
        } else {
            return false;
        }
    }

    public boolean addToTruck(String itemName) {
        Product p = getProductFromInventoryByName(itemName);
        Animal a = getAnimalFromInventoryByName(itemName);
        Animal a2 = getAnimalFromMapByName(itemName);
        //a = getAnimalFromMapByName(itemName);

        if (p == null && a == null && a2 == null) {
            System.out.printf("There is no %s in the farm.\n", itemName);
            return false;
        }

        if (p != null) {
            if (truck.add(p)) {
                System.out.printf("%s added to truck.\n", itemName);
                inventory.products.remove(p);
                return true;
            }
            System.out.println("Not enough space in truck");
            return false;
        }

        if (a != null) {
            if (truck.add(a)) {
                System.out.printf("%s added to truck.\n", itemName);
                inventory.wild_animals.remove(a);
                return true;
            }
            System.out.println("Not enough space in truck");
            return false;
        }

        if (a2 != null) {
            if (truck.add(a2)) {
                System.out.printf("%s added to truck.\n", itemName);
                a2.currentlyIn.getAnimalsInside().remove(a2);
                a2.currentlyIn = null;
                allDomestics.remove(a2);
                return true;
            }
            System.out.println("Not enough space in truck");
            return false;
        }


        return false;
        //shouldn reach here
    }

    Product getProductFromInventoryByName(String productName) {
        //Product p = null;
        for (Product P : inventory.products) {
            if (productName.equalsIgnoreCase(P.getClass().getSimpleName())) {
                Logg.LOGGER.info(P+" has gotten FromInventory");
                return P;
            }
        }
        return null;
    }

    Animal getAnimalFromInventoryByName(String animalName) {
        for (Animal a : inventory.wild_animals) {
            if (animalName.equalsIgnoreCase(a.getClass().getSimpleName())) {
                Logg.LOGGER.info(a+" has gotten FromInventory");
                return a;
            }
        }
        return null;
    }

    Animal getAnimalFromMapByName(String animalName) {
        LinkedList<Domestic_animal> dAnimals = getAllDomestic();
        for (Animal a : dAnimals) {
            if (animalName.equalsIgnoreCase(a.getClass().getSimpleName()))
                return a;
        }
        return null;
    }

    LinkedList<Domestic_animal> getAllDomestic() {
        LinkedList<Domestic_animal> a = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++)
                for (Animal aa : worldMap[i][j].getAnimalsInside())
                    if (aa instanceof Domestic_animal)
                        a.add(((Domestic_animal) aa));

        }
        return a;
    }

    public boolean removeFromTruck(String itemName) {
        Product p = getProductFromTruckByName(itemName);
        Animal a = getAnimalFromTruckByName(itemName);
        if (p == null && a == null) {
            System.out.printf("There is no %s in the truck.\n", itemName);
            return false;
        }
        if (p != null) {
            if (inventory.add(p)) {
                System.out.printf("%s removed from truck.\n", itemName);
                Logg.LOGGER.info(p + "removed from truck.");
                truck.removeObj(p);

                return true;

            }
        }
        if (a != null) {
            if (a instanceof Wild_animal) {
                if (inventory.add((Wild_animal) a)) {
                    System.out.printf("%s removed from truck.\n", itemName);
                    truck.removeObj(a);
                    Logg.LOGGER.info(p + "removed from truck.");
                    return true;

                }
            } else if (a instanceof Domestic_animal) {
                int i = (int) (Math.random() * 6) % 6;
                int j = (int) (Math.random() * 6) % 6;
                worldMap[i][j].getAnimalsInside().add(a);
                a.currentlyIn = worldMap[i][j];
                System.out.printf("%s removed from truck.\n", itemName);
                truck.removeObj(a);
                Logg.LOGGER.info(p + "removed from truck.");
                return true;
            }
        }

        return false;
    }

    Product getProductFromTruckByName(String productName) {
        for (Product P : truck.products) {
            if (productName.equalsIgnoreCase(P.getClass().getSimpleName()))
                return P;
        }
        return null;
    }

    Animal getAnimalFromTruckByName(String animalName) {
        for (Animal A : truck.animals) {
            if (animalName.equalsIgnoreCase(A.getClass().getSimpleName()))
                return A;
        }
        return null;
    }

    boolean isWorkshopOpened(String workshopName) {
        for (Workshop w : workshops) {
            if (w.getClass().getSimpleName().equalsIgnoreCase(workshopName))
                return true;
        }
        return false;
    }

    Workshop openWorkShop(String workshopName) {
        Workshop w = null;
        if (workshopName.equalsIgnoreCase("bakery"))
            w = new Bakery();
        else if (workshopName.equalsIgnoreCase("eggpowderplant") ||
                workshopName.equalsIgnoreCase("eggpdr"))
            w = new EggPowderPlant();
        else if (workshopName.equalsIgnoreCase("icecreamfactory")
        || workshopName.equalsIgnoreCase("icefq"))
            w = new IcecreamFactory();
        else if (workshopName.equalsIgnoreCase("milkpacking")||
                workshopName.equalsIgnoreCase("milkpq"))
            w = new MilkPacking();
        else if (workshopName.equalsIgnoreCase("sewingfactory")||
        workshopName.equalsIgnoreCase("sewfq"))
            w = new SewingFactory();
        else if (workshopName.equalsIgnoreCase("spinnery"))
            w = new Spinnery();

        if (isWorkshopOpened(workshopName)) {
            System.out.println("This workshop is created before.");
            return null;
        }

        w = checkMoneyToBuy(w);

        if (w == null) {
            System.out.printf("Not enough money.\nMoney: %d\n", coin);
            return null;
        }

        workshops.add(w);
        Logg.LOGGER.info("Workshop" + w + " created.");

        return w;
    }

    Workshop getWorkshop(String workName) {
        //Workshop w = null;
        if (workName.equalsIgnoreCase("eggpdr")) {
            workName = "eggpowderplant";
        }
        for (Workshop W : workshops) {
            if (workName.equalsIgnoreCase(W.getClass().getSimpleName()))
                return W;
        }
        return null;
    }

    void printProducts() {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                for (Product p : worldMap[i][j].getProductsInside())
                    System.out.printf("%s [%d %d]\n", p.getClass().getSimpleName(), i + 1, j + 1);


    }

    void printAnimals() {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                for (Animal a : worldMap[i][j].getAnimalsInside())
                    if (a instanceof Wild_animal)
                        System.out.printf("%s %d [%d %d]\n",
                                a.getClass().getSimpleName(),
                                ((Wild_animal) a).cageRequired - ((Wild_animal) a).cage,
                                i + 1, j + 1);
                    else if (a instanceof Domestic_animal)
                        System.out.printf("%s %d%% [%d %d]\n",
                                a.getClass().getSimpleName(), ((Domestic_animal) a).life, i + 1, j + 1);
                    else System.out.printf("%s [%d %d]\n",
                                a.getClass().getSimpleName(), i + 1, j + 1);
    }

    LinkedList<Animal> getAllAnimal() {
        LinkedList<Animal> a = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++)
                a.addAll(worldMap[i][j].getAnimalsInside());

        }
        return a;
    }

    void move() {
        LinkedList<Animal> anm = getAllAnimal();
        anm.forEach(a -> a.wannaMove = true);
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                worldMap[i][j].move(worldMap, i, j);
    }

    void printMapAnimal() {
        System.out.println("    ===== Map-Animals =====\n");
        System.out.println("+-----+-----+-----+-----+-----+-----+");
        for (Map[] mapArray : worldMap) {
            for (Map m : mapArray) {
                //System.out.printf("| %-4d", m.grass);
                int size = m.getAnimalsInside().size();
                System.out.print("|");
                StringBuilder s = new StringBuilder();
                for (Animal a1 : m.getAnimalsInside()) {
                    s.append(a1.printToMap());
                }
                s.append(" ".repeat(Math.max(0, 5 - size)));
                System.out.print(s.toString());

            }
            System.out.println("|");

        }
        System.out.println("+-----+-----+-----+-----+-----+-----+");


    }
    void fillHen(){

        for(int i=0;i<6;i++)
            for (int j = 0; j<6 ; j++){
                Hen dm = new Hen();
                dm.currentlyIn = worldMap[i][j];
                dm.currentlyIn.getAnimalsInside().add(dm);
                dm = new Hen();
                dm.currentlyIn = worldMap[i][j];
                dm.currentlyIn.getAnimalsInside().add(dm);
                dm = new Hen();
                dm.currentlyIn = worldMap[i][j];
                dm.currentlyIn.getAnimalsInside().add(dm);
            }
    }

    public void info(){
        //printMapGrass();
        //taskAccompPrint();
        printProducts();
        printAnimals();
        inventory.printInventory();
        //truck.print();
    }


}
