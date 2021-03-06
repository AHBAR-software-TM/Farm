package backend;

import frontend.WorldGui;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Map {

    @FXML
    ImageView microGrass, macroGrass;
    @FXML
    StackPane stackpane;
    @FXML
    HBox hbox;

    public ImageView battle_image_view;
    public ImageView cage_image_view;



    int x, y;

    public Map(World world) {
        this.setGrass(0);
        this.world = world;
        battle_image_view = new ImageView();
        cage_image_view = new ImageView();
        battle_image_view.setImage(new Image("/res/Animal/battle.png"));
        cage_image_view.setImage(new Image("/res/Animal/cage.png"));


    }

    public Map() {
        grass=0;
//        try {
////            battle_image_view = new ImageView();
////            cage_image_view = new ImageView();
////            battle_image_view.setImage(new Image(new FileInputStream("/res/Animal/battle.png")));
////            cage_image_view.setImage(new Image(new FileInputStream("/res/Animal/cage.png")));
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public World world;
    int grass = 0;
    private LinkedList<Animal> animalsInside = new LinkedList<>();
    private LinkedList<Product> productsInside = new LinkedList<>();

    synchronized public LinkedList<Animal> getAnimalsInside() {
        return animalsInside;
    }


    synchronized public LinkedList<Product> getProductsInside() {
        return productsInside;
    }


    public void move(Map[][] map, int x, int y) {
        LinkedList<Animal> toBeRemoved = new LinkedList<>();
        for (Animal a : getAnimalsInside()) {
            Dir direction = a.move(map, x, y);

            if (direction == null || !a.wannaMove) {
                Logg.LOGGER.info("Animal " + a + " did not not move!");
                continue;
            }
            if (a instanceof Tiger) {
                switch (direction) {
                    case RIGHT:
                        if (map[x + 1][y].hunt())
                            map[Math.min((x + 2), 5)][y].putAnimalIn(a);
                        //else animalsInside.remove(a);
                        break;
                    case LEFT:
                        if (map[x - 1][y].hunt())
                            map[Math.max(0, x - 2)][y].putAnimalIn(a);
                        //else animalsInside.remove(a);
                        break;
                    case UP:
                        if (map[x][y - 1].hunt())
                            map[x][Math.max(0, y - 2)].putAnimalIn(a);
                        //else animalsInside.remove(a);
                        break;
                    case DOWN:
                        if (map[x][y + 1].hunt())
                            map[x][Math.min((y + 2), 5)].putAnimalIn(a);
                        //else animalsInside.remove(a);
                        break;

                }

            } else {
                switch (direction) {
                    case RIGHT:
                        map[x + 1][y].putAnimalIn(a);
                        break;
                    case LEFT:
                        map[x - 1][y].putAnimalIn(a);
                        break;
                    case UP:
                        map[x][y - 1].putAnimalIn(a);
                        break;
                    case DOWN:
                        map[x][y + 1].putAnimalIn(a);
                        break;
                }

            }
            toBeRemoved.add(a);
        }
        for (Animal a : toBeRemoved) {
            this.getAnimalsInside().remove(a);
            //Logg.LOGGER.info("backend.Animal "+a+" dead!");
        }

    }

    public void pickup() {
        world.pickUp(this);

    }


    void update() {
        //backend.Wild_animal w=null;

        getProductsInside().removeIf(Product::update);

        giveFood();

        Wild_animal wild_animal = wildExist();
        if (wild_animal != null) {
            Dog d = dogExist();
            if (d != null) {
                getAnimalsInside().remove(d);
                getAnimalsInside().remove(wild_animal);
                Logg.LOGGER.info("Animal " + d + " removed from " + this);
                Logg.LOGGER.info("Animal " + wild_animal + " removed from " + this);
            } else {
                Iterator<Animal> itr = getAnimalsInside().descendingIterator();
                while (itr.hasNext()) {
                    Animal a = itr.next();
                    if (a instanceof Domestic_animal) {
                        Logg.LOGGER.info("Animal " + a + " removed from " + this);
                        itr.remove();
                    }
                }
            }
        }
        Iterator<Animal> itr = getAnimalsInside().descendingIterator();
        //for (backend.Animal a: animalsInside){
        while (itr.hasNext()) {
            Animal a = itr.next();
            if (a instanceof Wild_animal){
                a.update();
                continue;}

            Product p = a.update();
            if (p != null && !(p instanceof Dead)) {
                getProductsInside().add(p);
                Logg.LOGGER.config("Product " + p + " produced by " + a + " in " + a.currentlyIn);
            } else if (p != null) {
                ((Domestic_animal) a).hunted(world);
                //a.currentlyIn.animalsInside.remove(a);
                itr.remove();
            }


        }

        if (gurbaExist()) {
            this.pickup();
        }


    }

    boolean hunt() {
        Dog d = dogExist();
        if (d == null) {
            Iterator<Animal> itr = getAnimalsInside().descendingIterator();
            while (itr.hasNext()) {
                Animal a = itr.next();
                if (a instanceof Domestic_animal) {
                    ((Domestic_animal) a).hunted(this.world);
                    Logg.LOGGER.info("Animal " + a + " hunted!");
                    itr.remove();
                }

            }
//            for (backend.Animal a: animalsInside){
//                if(a instanceof backend.Domestic_animal)
//                    ((backend.Domestic_animal) a).hunted(this.world);
//            }
            return true;
        } else {
            d.hunted(world);
            //this.animalsInside.remove(d);
            return false;
        }

    }

    void putAnimalIn(Animal animal) {
        //animal.currentlyIn.animalsInside.remove(animal);
        animal.currentlyIn = this;
        this.getAnimalsInside().add(animal);
        animal.wannaMove = false;
        Logg.LOGGER.info("Animal " + animal + " added to " + this);

    }

    Dog dogExist() {
        for (Animal animal : getAnimalsInside()) {
            if (animal.getClass().getSimpleName().equals("Dog")) {
                Logg.LOGGER.info(animal + " exists in " + this);
                return (Dog) animal;
            }

        }
        return null;
    }

    void giveFood() {
        PriorityQueue<Domestic_animal> allDoms = giveAllDomesticsIn();
        while (getGrass() > 0 && !allDoms.isEmpty()) {
            setGrass(getGrass() - 1);
            allDoms.poll().eat();
            Logg.LOGGER.info("food has been given to animals");

        }

    }

    PriorityQueue<Domestic_animal> giveAllDomesticsIn() {
        PriorityQueue<Domestic_animal> allDomesticIn = new PriorityQueue<>();
        for (Animal a : getAnimalsInside()) {
            if (a instanceof Domestic_animal && ((Domestic_animal) a).life <= 50) {
                allDomesticIn.add(((Domestic_animal) a));
            }
        }
        return allDomesticIn;
    }

    boolean gurbaExist() {
        for (Animal animal : getAnimalsInside()) {
            if (animal.getClass().getSimpleName().equals("Cat")) {
                Logg.LOGGER.info(animal + " exists in " + this);
                return true;
            }
        }
        return false;
    }

    Wild_animal wildExist() {

        for (Animal a : getAnimalsInside()) {
            if (a instanceof Wild_animal) {
                Logg.LOGGER.info(a + " exists in " + this);
                return ((Wild_animal) a);
            }
        }
        return null;
    }

    public void updatePic() {
        //System.out.println(grass);
        if (grass <= 0) {
//            microGrass.setVisible(false);
//            macroGrass.setVisible(false);
            microGrass.setOpacity(0);
            macroGrass.setOpacity(0);
        } else if (grass == 1) {
//            microGrass.setVisible(true);
//            microGrass.setOpacity(1);
//            macroGrass.setVisible(false);
            microGrass.setOpacity(1);
            macroGrass.setOpacity(0);
        } else {
//            microGrass.setVisible(false);
//            macroGrass.setVisible(true);
//            macroGrass.setOpacity(1);
            microGrass.setOpacity(0);
            macroGrass.setOpacity(1);

        }
    }


    public int getGrass() {
        return grass;
    }

    public void setGrass(int grass) {
        this.grass = grass;
        updatePic();
    }

    public void addGrass() {
        grass++;
        updatePic();
    }

    public void cage() {
        Wild_animal a =wildExist();
        if (a != null) {
            System.out.printf("Caging done to %s\n", a.getClass().getSimpleName());
            int b = a.cage(world.inventory);
            if (b == 1){
                Logg.LOGGER.info(a+" Caged");
                System.err.println(a+" caged");
            }
            else if (b == 2)
                if (animalsInside.remove(a))
                    Logg.LOGGER.info(a+" removed");
                else
                    Logg.LOGGER.warning(a+" was not in list");

        }
    }

    @FXML
    public void clicked(MouseEvent e) {
        WorldGui.makeUpdateThreadWait();
        world.plant(x + 1, y + 1);
        cage();
        pickup();
        WorldGui.releaseUpdateThread();

    }



    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void show_animals(){

        double b=25;
        stackpane.getChildren().clear();
        for(Animal a:animalsInside) {
            stackpane.getChildren().add(a.imageview);
            stackpane.setMargin(a.imageview,new Insets(0,0,b,0));
            b+=10;

        }

    }

    public void show_products(){

        //double b=5;
        hbox.getChildren().clear();
        for(Product p:productsInside){
            hbox.getChildren().add(p.imageview);
            //hbox.setMargin(p.imageview,new Insets(0,,0,0));
            //b+=10;

        }
    }
}
