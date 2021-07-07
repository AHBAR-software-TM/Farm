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

import java.io.IOException;

public class LoginMenuGui {
    @FXML
    TextField usr,pass;
    @FXML
    Button enterButt;

    void initialize(){
        enterButt.setOnAction(this::enterAction);
    }

    void enterAction(ActionEvent e){
        if(usr.getText().isBlank()||pass.getText().isBlank()){
            showError();
            return;
        }

        User user =logIn();
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


    User logIn() {
        String username = usr.getText();
        if (username == null)
            return null;
        User user = getUserByUserName(username);
        if(user==null){
            showWrong();
            return null;
        }

        if (checkPassword(user)){
            return user;}
        //backend.Logg.LOGGER.warning("wrong password.");

        return null;

    }
    User getUserByUserName(String userName) {
        for (User u : Main.allUsers) {
            if (u.userName.equals(userName))
                return u;
        }
        return null;
    }

    boolean checkPassword(User user) {
        String password = pass.getText();
        if (password == null)
            return false;
        if (!user.password.equals(password)) {
            Logg.LOGGER.warning("wrong password.");
            showWrongPass();
            return false;
        }
        return true;

    }
    void showError(){
        Alert a = new Alert(Alert.AlertType.ERROR,"Error");
        a.setHeaderText("Fill all of the fields please.");
        a.showAndWait();
    }
    void showWrong(){
        Alert a = new Alert(Alert.AlertType.ERROR,"Error");
        a.setHeaderText("Something went wrong.");
        a.setContentText("Are you sure you have signed up before?");
        a.showAndWait();
    }
    void showWrongPass(){
        Alert a = new Alert(Alert.AlertType.ERROR,"Error");
        a.setHeaderText("Something went wrong.");
        a.setContentText("Are you sure you entered the right password?");
        a.showAndWait();
    }
}
