import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class ConsoleReader {

    Menu firstMenu = new Menu(null, "first", new Execute() {
        @Override
        public void execute(User user) {
            System.out.println("============Enter===========");
            Command command;// = getCommandFromConsole();
            do{
                command =getCommandFromConsole();
                if (command == Command.LOGIN)
                    loginMenu.execute.execute(null);
                else if (command == Command.SIGNUP) {
                    signupMenu.execute.execute(null);
                } else if (command == Command.NOTRECOG) {
                    System.out.println("Command not recognized. Try again");
                    firstMenu.execute.execute(null);
                }
            }while (command==Command.NOTRECOG);
        }
    });
    Menu loginMenu = new Menu(firstMenu, "login", new Execute() {
        @Override
        public void execute(User user) {

            // Command command = getCommandFromConsole();
            User user1 = logIn();
            if (user1 == null)
                firstMenu.execute.execute(null);
            else {
                System.out.println("=========Choose level==========");
                Command command;
                do {
                    command = getCommandFromConsole();
                    if (command == Command.START) {
                        startMenu.execute.execute(user1);
                    } else if (command == Command.NOTRECOG) {
                        System.out.println("Command not recognized. Try again");

                    } else if (command == Command.LOGOUT) {
                        firstMenu.execute.execute(null);
                    } else if (command == Command.EXIT) {
                        System.exit(0);
                    }

                } while (command == Command.NOTRECOG);

            }


        }
    });


    Menu interMenu = new Menu(firstMenu, "inter", new Execute() {
        @Override
        public void execute(User user) {
            // Command command = getCommandFromConsole();
            System.out.println("=========Choose level==========");

            Command command;

            do {
                command = getCommandFromConsole();
                if (command == Command.START) {
                    startMenu.execute.execute(user);
                } else if (command == Command.NOTRECOG) {
                    System.out.println("Command not recognized. Try again");
                    command = getCommandFromConsole();
                } else if (command == Command.LOGOUT) {
                    firstMenu.execute.execute(null);
                } else if (command == Command.EXIT) {
                    System.exit(0);
                }

            } while (command == Command.NOTRECOG);


        }
    });

    Menu signupMenu = new Menu(firstMenu, "signup", new Execute() {
        @Override
        public void execute(User user) {
            User user1 = signUp();
            if (user1 == null)
                firstMenu.execute.execute(null);
            else {
                System.out.println("=========Choose level==========");

                Command command;
                do {
                    command = getCommandFromConsole();
                    if (command == Command.START) {
                        startMenu.execute.execute(user1);
                    } else if (command == Command.NOTRECOG) {
                        System.out.println("Command not recognized. Try again");
                        command = getCommandFromConsole();
                    } else if (command == Command.LOGOUT) {
                        firstMenu.execute.execute(null);
                    } else if (command == Command.EXIT) {
                        System.exit(0);
                    }

                } while (command == Command.NOTRECOG);

            }


        }
    });
    Menu startMenu = new Menu(null, "start", new Execute() {
        @Override
        public void execute(User user) {
            //System.out.println("user2"+user.userWantsToPlayLvl);
            int lvl = ((int) Command.START.obj);
            //System.out.println("user1 :"+user.userWantsToPlayLvl);
            if (user.level>=lvl){
                user.userWantsToPlayLvl = lvl;
                //System.out.println("user :"+user.userWantsToPlayLvl);
                gameMenu.execute.execute(user);
            }


        }
    });
    Menu gameMenu = new Menu(null, "game", new Execute() {
        @Override
        public void execute(User user) {
            System.out.println("Game started\nlevel: "+ user.userWantsToPlayLvl );
            Mission mission = Main.getMissionInfoByLvl(user.userWantsToPlayLvl);
            World world = new World(mission);
            world.coin += user.coin;
            Command command = getCommandFromConsole();
            while (command != Command.EXIT) {

                switch (command) {
                    case BUY:
                        Animal boughtAnimal = world.buy((String) Command.BUY.obj);
                        if (boughtAnimal == null) {
                            System.out.printf("Animal not bought! Be sure you have enough money.\nMoney: %d\n", world.coin);
                            break;
                        }
                        System.out.printf("%s bought.\nMoney: %d\n", boughtAnimal.getClass().getSimpleName(), world.coin);
                        break;

                    case PICKUP:
                        world.pickUp(((int[]) Command.PICKUP.obj)[0], ((int[]) Command.PICKUP.obj)[1]);
                        break;

                    case WELL_CHARGE:
                        world.wellCharge();
                        break;

                    case PLANT:
                        if (world.plant(((int[]) Command.PLANT.obj)[0], ((int[]) Command.PLANT.obj)[1])) {
                            System.out.println(("Planted on: " + ((int[]) Command.PLANT.obj)[0])
                                    +" "+ ((int[]) Command.PLANT.obj)[1]);
                        } else {
                            System.out.println("Well was empty.");
                        }
                        break;

                    case TRUCKLOAD:
                        String itemName = ((String) Command.TRUCKLOAD.obj);
                        if (!world.addToTruck(itemName)) {
                            System.out.printf("Not enough space in truck, Current space: %s\n", world.truck.getEmpty());
                        }
                        break;

                    case TRUCKUNLOAD:
                        String itemName2 = ((String) Command.TRUCKLOAD.obj);
                        if (!world.removeFromTruck(itemName2)) {
                            System.out.printf("Not enough space in inventory, Current space: %s\n",
                                    (world.inventory.size - world.inventory.getFilledVolume()));
                        }
                        break;

                    case TRUCKGO:
                        world.truck.go();
                        break;

                    case OPENWORK:
                        Workshop w = world.openWorkShop(((String) Command.OPENWORK.obj));
                        if (w != null) {
                            System.out.printf("%s successfully created.\n", w.getClass().getSimpleName());
                        }
                        break;

                    case DOWORK:
                        Workshop w2 = world.getWorkshop(((String) Command.DOWORK.obj));
                        if (w2 == null) {
                            System.out.println("Workshop is not built.");
                            break;
                        }
                        w2.startWorking(world.inventory);
                        break;

                    case TURN:
                        for (int i = 0; i < (int) (Command.TURN.obj); i++) {
                            world.update();
                        }
                        world.printMapGrass();
                        world.taskAccompPrint();
                        world.printProducts();
                        world.printAnimals();
                        break;


                    case CAGE:
                        int[] arr = ((int[]) Command.CAGE.obj);
                        Map m = world.worldMap[arr[0]-1][arr[1]-1];
//                        for (Animal a : m.animalsInside) {
//                            if (a instanceof Wild_animal) {
//                                if (((Wild_animal) a).cage(world.inventory))
//                                    System.out.println("Animal Caged.");
//                            }
//                        }
                        Iterator<Animal> itr = m.animalsInside.iterator();
                        //System.out.println(itr);
                        while (itr.hasNext()){
                            Animal a = itr.next();
                            if (a instanceof Wild_animal) {
                                System.out.printf("Caging done to %s\n",a.getClass().getSimpleName());
                                int b = ((Wild_animal) a).cage(world.inventory);
                                if (b==1)
                                    System.out.println("Animal Caged.");
                                else if(b ==2)
                                    itr.remove();
                            }
                        }
                        break;

                    case PLANTALL:
                        if (world.well.water==0)
                            System.out.println("Empty well.");
                        else
                            for (int i = world.well.water; i>0; i--)
                                world.plant((int) (Math.random()*6%6)+1,(int) (Math.random()*6%6)+1);
                        break;

                    case INFO:
                        world.printMapGrass();
                        world.taskAccompPrint();
                        world.printProducts();
                        world.printAnimals();
                        world.inventory.printInventory();
                        world.truck.print();
                        break;

                    case NOTRECOG:
                        System.out.println("I couldn't figure out what you mean.");
                    break;
                    case MAPANIMALPRINT:
                        world.printMapAnimal();
                        break;
                    case FILL:
                        world.fillHen();
                        break;

                }
                int win = world.didUserWin();
                if (win != 0) {
                    switch (win) {
                        case 2:
                            user.coin = mission.speedGift;
                            break;
                        case 1:
                            user.coin = 0;
                            break;

                    }
                    user.level++;
                    Main.updateUser();
                    interMenu.execute.execute(user);
                    return;
                }
                command = getCommandFromConsole();
            }
            startMenu.execute.execute(user);

        }
    });


    boolean submitNewUser(User user) {

        try {
            FileWriter f = new FileWriter("users.txt", true);
            //f.write(Main.gson.toJson(user) + "\n");
            //todo
            //f.write(Main.objectMapper.writeValueAsString(user) + "n");
            Main.objectMapper.writeValue(f,user);

            //f.flush();
            f.close();
        } catch (IOException e) {
            return false;
        }
        return true;


    }

    boolean isUserSignedBefore(LinkedList<User> users, String enteredUserName) {
        for (User u : users) {
            if (u.userName.equals(enteredUserName))
                return true;
        }
        return false;
    }

    boolean isUserSignedBefore(String enteredUserName) {
        return isUserSignedBefore(Main.allUsers, enteredUserName);
    }

    //    boolean isPasswordCorrect(String password,User user){
//        return password.equals(user.password);
//    }
//
//    /**
//     * gives user obj according to its id. DOESN'T handle undefine user. You should be
//     * sure that the user exists in the database and then use this.
//     * @param password entered password
//     * @param userName the user name
//     * @return returns if the password is correct or not(boolean)
//     */
//    boolean isPasswordCorrect(String password,String userName){
//        return password.equals(getUserByUserName(userName).password);
//    }
    User getUserByUserName(String userName) {
        for (User u : Main.allUsers) {
            if (u.userName.equals(userName))
                return u;
        }
        return null;
    }


    Command getCommandFromConsole() {
        String[] fullLine = Main.sc.nextLine().trim().split(" ", 0);
        String line = fullLine[0];


        switch (line) {
            case "SIGNUP":
            case "signup":
            case "Signup":
                //System.out.println("Please enter your preferred username.");
                return Command.SIGNUP;


            case "START":
            case "start":
            case "Start":
                try {
                    //System.out.println(Integer.parseInt(fullLine[1]));
                    Command.START.obj = Integer.parseInt(fullLine[1]);
                    //System.out.println(Integer.parseInt(fullLine[1]));
                    return Command.START;
                } catch (Exception r) {
                    Command.START.obj = null;
                    return Command.NOTRECOG;
                }

            case "LOG":
            case "log":
            case "Log":
                if (fullLine[1].equalsIgnoreCase("OUT"))
                    return Command.LOGOUT;
                else if (fullLine[1].equalsIgnoreCase("IN"))
                    return Command.LOGIN;
                else return Command.NOTRECOG;

            case "EXIT":
            case "exit":
            case "Exit":
                return Command.EXIT;

            case "Buy":
            case "BUY":
            case "buy":
                try {
                    Command.BUY.obj = fullLine[1];
                    return Command.BUY;
                } catch (Exception e) {
                    Command.BUY.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }

            case "PICKUP":
            case "Pickup":
            case "pickup":
                try {
                    int[] coord = new int[2];
                    coord[0] = Integer.parseInt(fullLine[1]);
                    coord[1] = Integer.parseInt(fullLine[2]);

                    Command.PICKUP.obj = coord;
                    return Command.PICKUP;
                } catch (Exception e) {
                    Command.PICKUP.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }

            case "WELL":
            case "Well":
            case "well":
                return Command.WELL_CHARGE;


            case "PLANT":
            case "Plant":
            case "plant":
                try {
                    int[] coord = new int[2];
                    coord[0] = Integer.parseInt(fullLine[1]);
                    coord[1] = Integer.parseInt(fullLine[2]);

                    Command.PLANT.obj = coord;
                    return Command.PLANT;
                } catch (Exception e) {
                    Command.PLANT.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }


            case "TRUCK":
            case "Truck":
            case "truck":
                try {
                    switch (fullLine[1]) {
                        case "LOAD":
                        case "load":
                        case "Load":
                            Command.TRUCKLOAD.obj = fullLine[2];
                            return Command.TRUCKLOAD;

                        case "Unload":
                        case "UNLOAD":
                        case "unload":
                            Command.TRUCKUNLOAD.obj = fullLine[2];
                            return Command.TRUCKUNLOAD;

                        case "GO":
                        case "Go":
                        case "go":
                            //Command.TRUCKGO.obj = fullLine[2];
                            return Command.TRUCKGO;
                    }

                } catch (Exception e) {
                    Command.TRUCKLOAD.obj = null;
                    Command.TRUCKUNLOAD.obj = null;
                    Command.TRUCKGO.obj = null;
                    System.out.println("Wrong syntax");
                    return Command.NOTRECOG;
                }
                break;

            case "Build":
            case "BUILD":
            case "build":
                try {
                    Command.OPENWORK.obj = fullLine[1];
                    return Command.OPENWORK;
                } catch (Exception e) {
                    Command.OPENWORK.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }
                //break;


            case "TURN":
            case "Turn":
            case "turn":
                try {
                    Command.TURN.obj = Integer.parseInt(fullLine[1]);
                    return Command.TURN;
                } catch (Exception e) {
                    Command.TURN.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }

            case "WORK":
            case "work":
            case "Work":
                try {
                    Command.DOWORK.obj = fullLine[1];
                    return Command.DOWORK;
                } catch (Exception e) {
                    Command.DOWORK.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }

            case "CAGE":
            case "Cage":
            case "cage":
                try {
                    Command.CAGE.obj = new int[]{Integer.parseInt(fullLine[1]), Integer.parseInt(fullLine[2])};
                    return Command.CAGE;
                } catch (Exception e) {
                    Command.CAGE.obj = null;
                    System.out.println("Wrong syntax.");
                    return Command.NOTRECOG;
                }

            case "plantall":
            case "Plantall":
            case "PLANTALL":
                return Command.PLANTALL;

            case "info":
            case "Info":
            case "INFO":
                return Command.INFO;

            case "mp":
                return Command.MAPANIMALPRINT;

            case "fill":
                return Command.FILL;


                //todo : string index change

        }

        return Command.NOTRECOG;


    }

    boolean doCommand(Command command) {
        switch (command) {


            /*
             * these two cases (signup and login) will return false if user wants
             * to go back or they will return a User obj if the user was successful enough
             * to signup or login correctly.
             */
            case SIGNUP:
                Command.SIGNUP.obj = signUp();
                return Command.SIGNUP.obj != null;

            case LOGIN:
                Command.LOGIN.obj = logIn();
                return Command.LOGIN.obj != null;


        }
        //dummy return. we shouldn't and will never reach here.
        return false;


    }

    /**
     * @return returns null if user types "BACK" for any one of username password.
     */

    User signUp() {
        System.out.println("Please enter your preferred username.\nUSERNAME: ");
        String userName = getNewUserName();
        if (userName == null)
            return null;
        System.out.println("Please enter your preferred password.\nPASSWORD: ");
        String password = getNewPassword();
        if (password == null)
            return null;
        User user = new User(userName, password);
        Main.allUsers.add(user);
        if (submitNewUser(user)) {
            return user;
        }
        System.out.println("User not added to the database due to an error. Please fill form again.");
        return signUp();
    }

    String getNewUserName() {

        String username = Main.sc.nextLine();
        username = username.trim();
        if (username.equals("BACK")) {
            return null;
        }
        if (username.equals("")) {
            System.out.println("Please enter a valid username.\nUSERNAME: ");
            return getNewUserName();
        }
        if (isUserSignedBefore(username)) {
            System.out.println("This username is currently under use, Please choose another one.\nUSERNAME: ");
            return getNewUserName();
        }
        return username;
    }

    String getNewPassword() {
        String password = Main.sc.nextLine().trim();
        if (password.equals("BACK")) {
            return null;
        }
        if (password.equals("")) {
            System.out.println("Please enter a valid password.\nPASSWORD: ");
            return getNewUserName();
        }
        return password;
    }


    User logIn() {
        System.out.println("Please enter username.\nUSERNAME: ");
        String username = getUserName();
        if (username == null)
            return null;
        User user = getUserByUserName(username);
        System.out.println("Now please enter password.\nPASSWORD: ");

        boolean correctPasswordOrBack = checkPassword(user);
        if (correctPasswordOrBack)
            return user;

        return null;

    }

    String getUserName() {
        String username = Main.sc.nextLine();
        username = username.trim();
        if (username.equals("BACK"))
            return null;
        if (username.equals("")) {
            System.out.println("Please enter a valid username.\nUSERNAME: ");
            return getUserName();
        }
        if (!isUserSignedBefore(username)) {
            System.out.println("User is not signed up! Enter a valid username or signup:\nUSERNAME: ");
            return getUserName();
        }
        return username;
    }

    String getPassword() {


        String password = Main.sc.nextLine();
        password = password.trim();
        if (password.equals("BACK"))
            return null;
        if (password.equals("")) {
            System.out.println("Please enter a valid string.\nPASSWORD: ");
            return getPassword();
        }
        return password;
    }

    /**
     * Checks the password for a user. If the password was correct it returns true.
     * If the password was wrong it recurs itself again to get a correct password,or
     * it will be stopped if user typed "BACK" and will return false.
     *
     * @param user the user object
     * @return a boolean :)
     */
    boolean checkPassword(User user) {
        String password = getPassword();
        if (password == null)
            return false;
        if (!user.password.equals(password)) {
            System.out.println("Wrong password! Please enter your password again.\nPASSWORD: ");
            return checkPassword(user);
        }
        return true;

    }

    static class Menu {
        Menu parent;
        //Menu [] destination;
        String name;
        Execute execute;

        public Menu(Menu parent, String name, Execute execute) {
            this.parent = parent;
            this.name = name;
            this.execute = execute;

        }


    }


    // World related commands



}
