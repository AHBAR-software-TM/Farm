package frontend;

import backend.Logg;
import backend.Main;
import backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class SignupMenuGui {
    //todo: check logic
    @FXML
    TextField usr,pass;
    @FXML
    Button enterButt;

    void initialize(){
        enterButt.setOnAction(this::enterAction);
    }

    public void enterAction(ActionEvent e){
        if(usr.getText().isBlank()||pass.getText().isBlank()){
            showError();
            return;
        }
        User user = signup();
        if(user == null)
            return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/LevelView.fxml"));
            LevelMenu lv = new LevelMenu();
            lv.user = user;
            loader.setController(lv);
            Main.setSceneRoot(loader.load());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    User signup() {

        String userName = usr.getText();
        if (userName == null)
            return null;
        if (isUserSignedBefore(userName)){
            showWrong();
            Logg.LOGGER.warning("tried to sign up using a previously defined username.");
            return null;
        }
        String password = pass.getText();
        if (password == null)
            return null;
        User user = new User(userName, password);
        Main.allUsers.add(user);
        if (submitNewUser(user)) {
            //System.out.println("wtf");
            return user;
        }

        //Logg.LOGGER.warning("users file un accessible.");
        return null;
    }
    boolean submitNewUser(User user) {

        try {
            //System.out.println(backend.Main.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
            FileWriter f = new FileWriter("users.txt", true);
            //f.write(backend.Main.gson.toJson(user) + "\n");

            //f.write(backend.Main.objectMapper.writeValueAsString(user) + "n");
            f.append(Main.objectMapper.writeValueAsString(user)).append("\n");

            //f.flush();
            f.close();
            Logg.LOGGER.info(usr +" submitted.");
        } catch (IOException e) {
            Logg.LOGGER.warning(usr +" not submitted due to an error.");
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

    void showError(){
        Alert a = new Alert(Alert.AlertType.ERROR,"Error");
        a.setHeaderText("Fill all of the fields please.");
        a.showAndWait();
    }
    void showWrong(){
        Alert a = new Alert(Alert.AlertType.ERROR,"Error");
        a.setHeaderText("Something went wrong.");
        a.setContentText("This username is used before!");
        a.showAndWait();
    }
}
