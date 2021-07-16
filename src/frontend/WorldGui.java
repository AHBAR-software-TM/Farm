package frontend;

import backend.*;
import com.sun.javafx.binding.StringFormatter;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class WorldGui {
    static World world;
    User user;
    int level;
    static IntegerProperty coinProperty;
    static final UpdateThread ut = new UpdateThread();
    HashMap<Label, Object> invLabelToObj = new HashMap<>();
    HashMap<Label, Object> truckLabelToObj = new HashMap<>();
    boolean isTruckMenuShowingInv = true;


    final int GRID_W=5,GRID_H=3;
    @FXML
    GridPane grid,invGrid;
    @FXML
    ProgressBar wellBar, truckBar;
    @FXML
    Label truckLoad,invOrFarmAnimal,coinLabel;
    @FXML
    VBox invVbox, truckVbox;
    @FXML
    Button invCloseButt;



    static class UpdateThread extends Thread {
        boolean exit = false;
        volatile boolean waiting = false;
        boolean shallIWait = false;

        @Override
        public void run() {
            while (!exit) {
                if (!shallIWait) {
                    world.update();
                    world.info();
                    Platform.runLater( () ->  { coinGuiUpdate(); world.show(); });
                    try {
                        TimeUnit.SECONDS.sleep(2);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    waiting = true;
                    synchronized (this) {
                        while (shallIWait) {
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

    void truckInit() {
        truckBar.progressProperty().bind(world.truck.timeProperty);
        truckLoad.textProperty().bind(world.truck.loadProperty);
    }

    void coinInit(){
        coinProperty = new SimpleIntegerProperty(world.coin);
        coinLabel.textProperty().bind(coinProperty.asString());

    }

    static public void coinGuiUpdate(){
        coinProperty.setValue(world.coin);
    }

    void threadInit() {
        //ut = new UpdateThread();
        ut.start();
    }

    public static void makeUpdateThreadWait() {
        //System.out.println(1);
        if(!ut.shallIWait) {
            ut.shallIWait = true;
            while (!ut.waiting) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void releaseUpdateThread() {
        if (ut.shallIWait) {
            synchronized (ut) {

                ut.shallIWait = false;
                ut.waiting = false;
                ut.notify();
            }
        }
    }

    @FXML
    void buyHen(MouseEvent e) {
        makeUpdateThreadWait();

        if (world.buy("Hen") != null) {
            Logg.LOGGER.warning("Hen bought.");
        } else {
            Logg.LOGGER.warning("Hen not bought.");
        }

        releaseUpdateThread();


    }

    @FXML
    void buyTurkey(MouseEvent e) {
        makeUpdateThreadWait();

        if (world.buy("Ostrich") != null) {
            Logg.LOGGER.warning("Ostrich/Turkey bought.");
        } else {
            Logg.LOGGER.warning("Ostrich/Turkey not bought.");
        }

        releaseUpdateThread();
    }

    @FXML
    void buyBuffalo(MouseEvent e) {
        makeUpdateThreadWait();

        if (world.buy("Buffalo") != null) {
            Logg.LOGGER.warning("Buffalo bought.");
        } else {
            Logg.LOGGER.warning("Buffalo not bought.");
        }

        releaseUpdateThread();

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
    public void wellClicked(MouseEvent e) {
        world.well.charge();
    }

    @FXML
    public void closeGame(ActionEvent e) {
        ut.close();
        System.exit(0);
    }

    @FXML
    public void stopStart(ActionEvent e) {
        if (ut.waiting) {
            releaseUpdateThread();
        } else {
            makeUpdateThreadWait();
        }
    }

    @FXML
    public void openTruck(MouseEvent e) {
        makeUpdateThreadWait();
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/inventoryToTruck.fxml"));
            loader.setController(this);
            s.setScene(new Scene(loader.load()));
            initSellMenu();
            s.showAndWait();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            releaseUpdateThread();
        }
    }

    @FXML
    void showInv(ActionEvent e){
        makeUpdateThreadWait();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/InvView.fxml"));
        loader.setController(this);
        try {
            Stage s = new Stage();
            s.setScene(new Scene(loader.load()));


            for (int i = 0; i < GRID_W*GRID_H; i++) {
                String imgPath = world.inventory.getPathForNthElement(i);
                if(imgPath != null)
                    ((ImageView) ((AnchorPane) invGrid.getChildren().get(i)).getChildren().get(0)).setImage(new Image(imgPath));
            }
            invCloseButt.setOnAction(ee -> s.close());
            s.showAndWait();

            releaseUpdateThread();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    void initSellMenu() {

        if (isTruckMenuShowingInv)
            initInvVbox();
        else
            initFarmAnimalSellVbox();

        initTruckVbox();
    }

    void initTruckVbox() {
        int i = 0;

        //truckLabelToObj.clear();

        for (Animal a : world.truck.animals) {
            Label l = ((Label) truckVbox.getChildren().get(2 * i));
            l.setText(a.getClass().getSimpleName());
            l.setDisable(false);
            i++;
            //invLabelToObj.put(l,a);
        }
        for (Product p : world.truck.products) {
            Label l = ((Label) truckVbox.getChildren().get(2 * i));
            l.setText(p.getClass().getSimpleName());
            l.setDisable(false);
            i++;
            //invLabelToObj.put(l,p);
        }
        for (; i < 15; i++) {
            (truckVbox.getChildren().get(2 * i)).setDisable(true);

        }
    }

    void initInvVbox() {

        invOrFarmAnimal.setText("Inventory");

        int i = 0;
        for (Wild_animal w : world.inventory.wild_animals) {
            Label l = ((Label) invVbox.getChildren().get(2 * i));
            l.setText(w.getClass().getSimpleName());
            l.setDisable(false);
            i++;
            //invLabelToObj.put(l,w);
        }
        for (Product p : world.inventory.products) {
            Label l = ((Label) invVbox.getChildren().get(2 * i));
            l.setText(p.getClass().getSimpleName());
            l.setDisable(false);
            i++;
            // invLabelToObj.put(l,p);
        }
        for (; i < 15; i++) {
            ((Label) invVbox.getChildren().get(2 * i)).setDisable(true);

        }
    }

    void initFarmAnimalSellVbox(){
        invOrFarmAnimal.setText("Animals");

        LinkedList<String> animalNamesInTheMap = new LinkedList<>();
        for (int n = 0; n < 6; n++)
            for (int m = 0; m < 6; m++)
                for (Animal a : world.worldMap[n][m].getAnimalsInside())
                    if (!animalNamesInTheMap.contains(a.getClass().getSimpleName())&& !(a instanceof Wild_animal))
                        animalNamesInTheMap.add(a.getClass().getSimpleName());

        int i = 0;

        for (String s : animalNamesInTheMap) {
            Label l = ((Label) invVbox.getChildren().get(2 * i));
            l.setText(s);
            l.setDisable(false);
            i++;
            //invLabelToObj.put(l,w);
        }
        for (; i < 15; i++) {
            ((Label) invVbox.getChildren().get(2 * i)).setDisable(true);

        }
    }

    @FXML
    void invElementClicked(MouseEvent e) {
        if (world.addToTruck(((Label) e.getSource()).getText())) {
            initSellMenu();
        }

    }

    @FXML
    void truckElementClicked(MouseEvent e) {
        if (!world.removeFromTruck(((Label) e.getSource()).getText())) {
//            System.out.printf("Not enough space in inventory, Current space: %s\n",
//                    (world.inventory.size - world.inventory.getFilledVolume()));
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Not enough");
            a.setContentText("The inventory empty space is not enough to bring this item back.");
            //(world.inventory.size - world.inventory.getFilledVolume())+"")
            a.showAndWait();
        }else {

            initSellMenu();
        }
    }

    @FXML
    void changeInvToFarmAnimal(MouseEvent e){

        if (isTruckMenuShowingInv) {
            isTruckMenuShowingInv = false;
            initFarmAnimalSellVbox();

        }else {
            isTruckMenuShowingInv = true;
            initInvVbox();
        }

    }

    @FXML
    void sendTruck(ActionEvent e){
        makeUpdateThreadWait();
        world.truck.go();
        releaseUpdateThread();
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
        truckInit();
        coinInit();
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
