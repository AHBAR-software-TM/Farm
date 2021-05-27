import java.util.LinkedList;
import java.util.Scanner;

public class ConsoleReader {






    //todo:Code this func :  User submitNewUser() for file;

    boolean isUserSignedBefore(LinkedList<User> users,String enteredUserName){
        for (User u:users){
            if(u.userName.equals(enteredUserName))
                return true;
        }
        return false;
    }
    boolean isUserSignedBefore(String enteredUserName){
        return isUserSignedBefore(Main.allUsers,enteredUserName);
    }

    boolean isPasswordCorrect(String password,User user){
        return password.equals(user.password);
    }

    /**
     * gives user obj according to its id. DOESN'T handle undefine user. You should be
     * sure that the user exists in the database and then use this.
     * @param password entered password
     * @param userName the user name
     * @return returns if the password is correct or not(boolean)
     */
    boolean isPasswordCorrect(String password,String userName){
        return password.equals(getUserByUserName(userName).password);
    }
    User getUserByUserName(String userName){
        for (User u: Main.allUsers) {
            if (u.userName.equals(userName))
                return u;
        }
        return null;
    }


    Command getCommandFromConsole(){
        String line = Main.sc.nextLine();


        switch (line){
            case "SIGNUP":
                //System.out.println("Please enter your preferred username.");
                return Command.SIGNUP;

            case "LOG IN":
                return Command.LOGIN;


        }

        return Command.NOTRECOG;


    }

    boolean doCommand(Command command){
        switch (command){


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

    User signUp(){
        System.out.println("Please enter your preferred username.\nUSERNAME: ");
        String userName = getNewUserName();
        if (userName == null)
            return null;
        System.out.println("Please enter your preferred password.\nPASSWORD: ");
        String password = getNewPassword();
        if (password == null)
            return null;
        User user = new User(userName,password);
        Main.allUsers.add(user);
        //todo: !!!IMPORTANT!!! add user in the file users.txt , and handle other things the may be necessary...
        return user;
    }
    String getNewUserName(){

        String username = Main.sc.nextLine();
        username = username.trim();
        if (username.equals("BACK")){
            return null;
        }
        if (username.equals("")) {
            System.out.println("Please enter a valid username.\nUSERNAME: ");
            return getNewUserName();
        }
        if (isUserSignedBefore(username)){
            System.out.println("This username is currently under use, Please choose another one.\nUSERNAME: ");
            return getNewUserName();
        }
        return username;
    }
    String getNewPassword(){
        String password = Main.sc.nextLine().trim();
        if (password.equals("BACK")){
            return null;
        }
        if (password.equals("")){
            System.out.println("Please enter a valid password.\nPASSWORD: ");
            return getNewUserName();
        }
        return password;
    }


    User logIn(){
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
    String getUserName(){
        String username = Main.sc.nextLine();
        username = username.trim();
        if(username.equals("BACK"))
            return null;
        if (username.equals("")) {
            System.out.println("Please enter a valid username.\nUSERNAME: ");
            return getUserName();
        }
        if (!isUserSignedBefore(username)){
            System.out.println("User is not signed up! Enter a valid username or signup:\nUSERNAME: ");
            return getUserName();
        }
        return username;
    }
    String getPassword(){


        String password = Main.sc.nextLine();
        password = password.trim();
        if(password.equals("BACK"))
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
     * @param user the user object
     * @return a boolean :)
     */
    boolean checkPassword(User user){
        String password = getPassword();
        if (password == null)
            return false;
        if (!user.password.equals(password))
        {
            System.out.println("Wrong password! Please enter your password again.\nPASSWORD: ");
            return checkPassword(user);
        }
        return true;

    }



}
