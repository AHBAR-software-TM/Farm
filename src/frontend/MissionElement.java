package frontend;

import backend.Main;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MissionElement {
    @FXML
    Label label;
    @FXML
    HBox main;
    @FXML
    ImageView i;
    int level;
    User user;
    @FXML
    public void PlayMission(MouseEvent ee){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/GameMap.fxml"));
        try {
            Main.setSceneRoot(loader.load());
            WorldGui cnt = loader.getController();
            cnt.level = level;
            cnt.user = user;

            cnt.run();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
