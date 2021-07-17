package frontend;

import backend.Main;
import backend.Mission;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.util.LinkedList;

public class LevelMenu {
    @FXML
    ScrollPane scrollpane;
    @FXML
    VBox vbox;

    User user;




    void showMissionElements(){
        LinkedList<Mission> missions = Main.loadMissions();
        for (Mission m: missions){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/MissionElement.fxml"));
                vbox.getChildren().add(loader.load());

                ((MissionElement) loader.getController()).label.setText("Level "+m.lvl);
                ((MissionElement) loader.getController()).level = m.lvl;
                ((MissionElement) loader.getController()).user = user;
                if (this.user.level<m.lvl){
                    ((MissionElement) loader.getController()).i.setVisible(false);
                    ((MissionElement) loader.getController()).i.setDisable(true);
                }


                //scrollpane.setContent(new ImageView("/res/cell-01.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void initialize(){

        showMissionElements();

    }



}
