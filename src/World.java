import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class World {
    Mission mission;
    int time = Integer.MIN_VALUE;

    Map[][] worldMap = new Map[6][6];
    LinkedList<Animal> allDomestics = new LinkedList<>();

    //LinkedList<Product> inventory = new LinkedList<>();

    Inventory inventory = new Inventory();
    int coin;
    LinkedList<Workshop> workshops = new LinkedList<>();


    HashMap<Integer, Wild_animal> WildAttack;

    //task related data
    HashMap<String, Integer> producedTillNow = new HashMap<>();
    HashMap<String, Integer> boughtTillNow = new HashMap<>();

    Well well = new Well();
    Truck truck = new Truck();


    //todo: !!!IMPORTANT!!! handle production from animal and workshop to map
    //todo: !!!IMPORTANT!!! check if animal is dead or alive
    void update() {

        System.out.printf("%d time units passed.\n", getTime());

        for (Map[] mapArray : worldMap) {
            for (Map m : mapArray) {
                m.update();

            }
        }

        workshops.forEach(Workshop::update);

        Wild_animal attacker = WildAttack.get(getTime());
        if (attacker != null) {
            worldMap[(int) (Math.random() * 6) % 6][(int) (Math.random() * 6) % 6].animalsInside.add(attacker);
        }

        printMapGrass();

        taskAccompPrint();

        //todo: !!!IMPORTANT!!! print the other mentioned thing (page:9)


        time++;

    }

    boolean didUserWin(Mission mission) {
        return true; //todo: !!!important!!!! add winning according to mission task
    }

    World(Mission mission) {
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
        return time - Integer.MIN_VALUE;
    }

    void printMapGrass() {
        System.out.println("           ===== Map =====\n");
        System.out.println("+-----+-----+-----+-----+-----+-----+");
        for (Map[] mapArray : worldMap) {
            for (Map m : mapArray) {
                System.out.printf("| %-4d", m.grass);

            }
            System.out.println("|\n");

        }
        System.out.println("+-----+-----+-----+-----+-----+-----+");
    }

    ///todo: !!!IMPORTANT!!! handle task accomplishment change
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
        System.out.printf("Coin: %d/%d\n", coin, mission.coinTask);
    }

    Animal checkMoneyToBuy(Animal animal) {
        if (animal == null)
            return null;
        if (animal.price <= coin) {
            coin -= animal.price;
            return animal;
        }
        return null;

    }

    Workshop checkMoneyToBuy(Workshop workshop) {
        if (workshop == null)
            return null;
        if (workshop.build_price <= coin) {
            coin -= workshop.build_price;
            return workshop;
        }
        return null;

    }
    Animal buy(String animalName) {
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

            case "Turkey":
            case "TURKEY":
            case "turkey":
                dm = new Ostrich();
                break;

        }
        dm = checkMoneyToBuy(dm);

        if (dm == null) {
            return null;
        }

        Integer bought = boughtTillNow.get(dm.getClass().getSimpleName());
        if (bought != null) {
            bought++;
            boughtTillNow.replace(dm.getClass().getSimpleName(), bought);
        }

        worldMap[(int) (Math.random() * 6) % 6][(int) (Math.random() * 6)].animalsInside.add(dm);

        return dm;


    }

    void pickUp(int i, int j) {
        if (i > 6 || i < 1 || j > 6 || j < 1) {
            System.out.println("Invalid location!");
            return;
        }
        if (worldMap[i - 1][j - 1].productsInside.isEmpty()) {
            System.out.println("Location is empty.");
            return;
        }
        for (Product p : worldMap[i - 1][j - 1].productsInside) {
            if (inventory.add(p)) {

                worldMap[i - 1][j - 1].productsInside.remove(p);

                Integer produced = producedTillNow.get(p.getClass().getSimpleName());
                if (produced != null) {
                    produced++;
                    producedTillNow.replace(p.getClass().getSimpleName(), produced);
                }

                System.out.printf("%s moved to inventory.\n", p.getClass().getSimpleName());
            } else {
                System.out.printf("Not enough space for %s", p.getClass().getSimpleName());
            }
        }

    }
    void pickUp(Map map){
        for (Product p : map.productsInside) {
            if (inventory.add(p)) {

                map.productsInside.remove(p);

                Integer produced = producedTillNow.get(p.getClass().getSimpleName());
                if (produced != null) {
                    produced++;
                    producedTillNow.replace(p.getClass().getSimpleName(), produced);
                }

                System.out.printf("%s moved to inventory.\n", p.getClass().getSimpleName());
            } else {
                System.out.printf("Not enough space for %s", p.getClass().getSimpleName());
            }
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

    boolean plant(int x, int y) {
        if (well.plant()) {
            worldMap[x][y].grass++;
            return true;
        } else {
            return false;
        }
    }

    boolean addToTruck(String itemName) {
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
        }

        if (a != null) {
            if (truck.add(a)) {
                System.out.printf("%s added to truck.\n", itemName);
                inventory.wild_animals.remove(a);
                return true;
            }
        }

        if (a2 != null) {
            if (truck.add(a2)) {
                System.out.printf("%s added to truck.\n", itemName);
                a2.currentlyIn.animalsInside.remove(a2);
                allDomestics.remove(a2);
                return true;
            }
        }


        return false;
        //shouldn reach here
    }

    Product getProductFromInventoryByName(String productName) {
        //Product p = null;
        for (Product P : inventory.products) {
            if (productName.equalsIgnoreCase(P.getClass().getSimpleName()))
                return P;
        }
        return null;
    }

    Animal getAnimalFromInventoryByName(String animalName) {
        for (Animal a : inventory.wild_animals) {
            if (animalName.equalsIgnoreCase(a.getClass().getSimpleName()))
                return a;
        }
        return null;
    }

    Animal getAnimalFromMapByName(String animalName) {
        for (Animal a : allDomestics) {
            if (animalName.equalsIgnoreCase(a.getClass().getSimpleName()))
                return a;
        }
        return null;
    }

    boolean removeFromTruck(String itemName) {
        Product p = getProductFromTruckByName(itemName);
        Animal a = getAnimalFromTruckByName(itemName);
        if (p == null && a == null) {
            System.out.printf("There is no %s in the truck.\n", itemName);
            return false;
        }
        if (p != null) {
            if (inventory.add(p)) {
                System.out.printf("%s removed from truck.\n", itemName);
                truck.products.remove(p);
                return true;

            }
        }
        if (a != null){
            if(a instanceof Wild_animal){
                if (inventory.add((Wild_animal) a)) {
                    System.out.printf("%s removed from truck.\n", itemName);
                    truck.animals.remove(a);
                    return true;

                }
            }
            else if(a instanceof Domestic_animal){
                worldMap[(int) (Math.random()*6)][(int) (Math.random()*6)].animalsInside.add(a);
                System.out.printf("%s removed from truck.\n", itemName);
                truck.animals.remove(a);
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

    boolean isWorkshopOpened(String workshopName){
        for (Workshop w:workshops){
            if(w.getClass().getSimpleName().equalsIgnoreCase(workshopName))
                return true;
        }
        return false;
    }

    Workshop openWorkShop(String workshopName){
        Workshop w=null;
        if(workshopName.equalsIgnoreCase("bakery"))
            w = new Bakery();
        else if(workshopName.equalsIgnoreCase("eggpowderplant"))
            w = new EggPowderPlant();
        else if(workshopName.equalsIgnoreCase("icecreamfactory"))
            w = new IcecreamFactory();
        else if(workshopName.equalsIgnoreCase("milkpacking"))
            w = new MilkPacking();
        else if(workshopName.equalsIgnoreCase("sewingfactory"))
            w = new SewingFactory();
        else if(workshopName.equalsIgnoreCase("spinnery"))
            w = new Spinnery();

        if(isWorkshopOpened(workshopName)){
            System.out.println("This workshop is created before.");
            return null;}

        w = checkMoneyToBuy(w);

        if (w == null){
            System.out.printf("Not enough money.\nMoney: %d\n",coin);
            return null;}

        workshops.add(w);

        return w;
    }
}
