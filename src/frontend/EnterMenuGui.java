package frontend;

import backend.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class EnterMenuGui {
    @FXML
    Label loginLabel,signupLabel;
    public void loginAction(MouseEvent e){
        try {
            FXMLLoader l = new FXMLLoader (getClass().getResource("/res/LoginView.fxml"));
            LoginMenuGui lo = new LoginMenuGui();

            l.setController(lo);
            Parent p = l.load();

            lo.initialize();
            Main.setSceneRoot(p);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    public void signupAction(MouseEvent e){
        try {
            FXMLLoader l = new FXMLLoader (getClass().getResource("/res/LoginView.fxml"));
            SignupMenuGui sp = new SignupMenuGui();

            l.setController(sp);
            Parent p = l.load();

            sp.initialize();
            Main.setSceneRoot(p);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
