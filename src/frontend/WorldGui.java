package frontend;

import backend.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WorldGui {
    World world;
    User user;
    int level;
    final UpdateThread ut = new UpdateThread();
    boolean waitThread = false;
    @FXML
    GridPane grid;
    @FXML
    ProgressBar wellBar;


    class UpdateThread extends Thread {
        boolean exit = false;
       volatile boolean waiting=false;
        boolean shallIWait = false;

        @Override
        public void run() {
            while (!exit) {
                if (!shallIWait) {
                    world.update();
                    try {
                        TimeUnit.SECONDS.sleep(1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    waiting = true;
                    synchronized (this){
                        while (shallIWait){
                            try {
                                System.out.println("lets wait...");
                                wait();
                                System.out.println("waiting done");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        public void close() {
            exit = true;

        }


    }

    void wellInit() {
        wellBar.progressProperty().bind(world.well.chargeForBar);
    }

    void threadInit() {
        //ut = new UpdateThread();
        ut.start();
    }

    void makeWait() {

            waitThread = true;
            //System.out.println(1);
            ut.shallIWait = true;
            while (!ut.waiting){
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(2);
//        while (!ut.waiting) {
//            //Logg.LOGGER.warning("");
//        }



//            if (ut.waiting) {
//                try {
//                    while (waitThread) {
//                        ut.wait();
//                    }
//                } catch (InterruptedException interruptedException) {
//                    interruptedException.printStackTrace();
//                }
//            } else {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                    makeWait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

    }

    @FXML
    void buyHen(ActionEvent e) {
        makeWait();
        System.out.println("Hen bought");
        world.buy("Hen");

        synchronized (ut){
            waitThread = false;
            ut.shallIWait = false;
            ut.waiting = false;
            ut.notify();}

    }

    @FXML
    void buyTurkey(MouseEvent e) {

    }

    @FXML
    void buyBuffalo(MouseEvent e) {


    }

    void fillGridPane() {
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                FXMLLoader ij = new FXMLLoader(getClass().getResource("/res/MapCell.fxml"));

                try {
                    grid.add(ij.load(), j, i);
                    world.worldMap[i][j] = ij.getController();
                    world.worldMap[i][j].setCoordinate(i, j);
                    world.worldMap[i][j].world = this.world;
                    //world.worldMap[i][j].
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @FXML
    public void wellClicked(MouseEvent e) throws InterruptedException {
        world.well.charge();
    }

    @FXML
    public void closeGame(ActionEvent e) {
        ut.close();
        System.exit(0);
    }


    public void run() {

        //System.out.println("Game started\nlevel: "+ user.userWantsToPlayLvl );
        Mission mission = Main.getMissionInfoByLvl(level);
        world = new World(mission);
        fillGridPane();

        world.coin += user.coin;
        for (int i = 0; i < 20; i++) {
            world.worldMap[(int) (Math.random() * 6 % 6)][(int) (Math.random() * 6 % 6)].addGrass();
        }
        wellInit();
        threadInit();
        //world.printMapGrass();
        //Command command = getCommandFromConsole();
//        while (command != Command.EXIT) {
//
//            switch (command) {
//                case BUY:
//                    Animal boughtAnimal = world.buy((String) Command.BUY.obj);
//                    if (boughtAnimal == null) {
//                        System.out.printf("backend.Animal not bought! Be sure you have enough money.\nMoney: %d\n", world.coin);
//                        break;
//                    }
//                    System.out.printf("%s bought.\nMoney: %d\n", boughtAnimal.getClass().getSimpleName(), world.coin);
//                    break;
//
//                case PICKUP:
//                    world.pickUp(((int[]) Command.PICKUP.obj)[0], ((int[]) Command.PICKUP.obj)[1]);
//                    break;
//
//                case WELL_CHARGE:
//                    world.wellCharge();
//                    break;
//
//                case PLANT:
//                    if (world.plant(((int[]) Command.PLANT.obj)[0], ((int[]) Command.PLANT.obj)[1])) {
//                        System.out.println(("Planted on: " + ((int[]) Command.PLANT.obj)[0])
//                                +" "+ ((int[]) Command.PLANT.obj)[1]);
//                    } else {
//                        System.out.println("backend.Well was empty.");
//                    }
//                    break;
//
//                case TRUCKLOAD:
//                    String itemName = ((String) Command.TRUCKLOAD.obj);
//                    if (!world.addToTruck(itemName)) {
//                    }
//                    break;
//
//                case TRUCKUNLOAD:
//                    String itemName2 = ((String) Command.TRUCKLOAD.obj);
//                    if (!world.removeFromTruck(itemName2)) {
//                        System.out.printf("Not enough space in inventory, Current space: %s\n",
//                                (world.inventory.size - world.inventory.getFilledVolume()));
//                    }
//                    break;
//
//                case TRUCKGO:
//                    world.truck.go();
//                    break;
//
//                case OPENWORK:
//                    Workshop w = world.openWorkShop(((String) Command.OPENWORK.obj));
//                    if (w != null) {
//                        System.out.printf("%s successfully created.\n", w.getClass().getSimpleName());
//                    }
//                    break;
//
//                case DOWORK:
//                    Workshop w2 = world.getWorkshop(((String) Command.DOWORK.obj));
//                    if (w2 == null) {
//                        System.out.println("backend.Workshop is not built.");
//                        break;
//                    }
//                    w2.startWorking(world.inventory);
//                    break;
//
//                case UPGRADE:
//                    Workshop w3 = world.getWorkshop(((String) Command.UPGRADE.obj));
//                    if (w3 == null) {
//                        System.out.println("backend.Workshop is not built.");
//                        break;
//                    }
//                    if (w3.isWorking){
//                        System.out.println(w3.getClass().getSimpleName()+" is Working. Upgrade not possible.");
//                        break;}
//                    if (world.coin < 300){
//                        System.out.println("Not enough money. 300 coins needed.");
//                        break;}
//                    if (w3.upgrade(world))
//                        System.out.println(w3.getClass().getSimpleName()+" upgraded.");
//                    break;
//
//                case TURN:
//                    for (int i = 0; i < (int) (Command.TURN.obj); i++) {
//                        world.update();
//                    }
//                    world.printMapGrass();
//                    world.taskAccompPrint();
//                    world.printProducts();
//                    world.printAnimals();
//                    break;
//
//
//                case CAGE:
//                    int[] arr = ((int[]) Command.CAGE.obj);
//                    Map m = world.worldMap[arr[0]-1][arr[1]-1];
////                        for (backend.Animal a : m.animalsInside) {
////                            if (a instanceof backend.Wild_animal) {
////                                if (((backend.Wild_animal) a).cage(world.inventory))
////                                    System.out.println("backend.Animal Caged.");
////                            }
////                        }
//                    Iterator<Animal> itr = m.animalsInside.iterator();
//                    //System.out.println(itr);
//                    while (itr.hasNext()){
//                        Animal a = itr.next();
//                        if (a instanceof Wild_animal) {
//                            System.out.printf("Caging done to %s\n",a.getClass().getSimpleName());
//                            int b = ((Wild_animal) a).cage(world.inventory);
//                            if (b==1)
//                                System.out.println("backend.Animal Caged.");
//                            else if(b ==2)
//                                itr.remove();
//                        }
//                    }
//                    break;
//
//                case PLANTALL:
//                    if (world.well.water==0)
//                        System.out.println("Empty well.");
//                    else
//                        for (int i = world.well.water; i>0; i--)
//                            world.plant((int) (Math.random()*6%6)+1,(int) (Math.random()*6%6)+1);
//                    break;
//
//                case INFO:
//                    world.printMapGrass();
//                    world.taskAccompPrint();
//                    world.printProducts();
//                    world.printAnimals();
//                    world.inventory.printInventory();
//                    world.truck.print();
//                    break;
//
//                case NOTRECOG:
//                    System.out.println("I couldn't figure out what you mean.");
//                    break;
//                case MAPANIMALPRINT:
//                    world.printMapAnimal();
//                    break;
//                case FILL:
//                    world.fillHen();
//                    break;
//
//
//
//
//            }
//            int win = world.didUserWin();
//            if (win != 0) {
//                switch (win) {
//                    case 2:
//                        user.coin = mission.speedGift;
//                        break;
//                    case 1:
//                        user.coin = 0;
//                        break;
//
//                }
//                user.level++;
//                Main.updateUser();
//                interMenu.execute.execute(user,stage);
//                return;
//            }
//            command = getCommandFromConsole();
//        }
        //interMenu.execute.execute(user,stage);
    }
    // public void lnc()
}
