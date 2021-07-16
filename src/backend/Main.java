package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.util.logging.Level.ALL;

public class Main extends Application{
    public static LinkedList<User> allUsers = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);
    private static Scene mainScene;
    //static Gson gson = new Gson();
    public static ObjectMapper objectMapper = new ObjectMapper();

    static void loadUsers(){

        File f = new File("users.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()){
                //allUsers.add(gson.fromJson(sc.nextLine(),backend.User.class));
                allUsers.add(objectMapper.readValue(sc.nextLine(),User.class));
            }
            sc.close();
        } catch (FileNotFoundException ignored){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void updateUser(){
        FileWriter fw;
        try {
            fw = new FileWriter("users.txt");
            for (User u: allUsers ){
                //fw.write(gson.toJson(u));
                fw.append(objectMapper.writeValueAsString(u)).append("\n");

                //fw.close();
            }
            fw.close();
        }catch (IOException e){
            System.out.println("File users.txt error.");
            e.printStackTrace();

        }

    }
    public static LinkedList<Mission> loadMissions(){
        LinkedList<Mission> ans = new LinkedList<>();
        File f = new File("missions.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()){
                //ans.add(gson.fromJson(sc.nextLine(),backend.Mission.class));
                ans.add(objectMapper.readValue(sc.nextLine(),Mission.class));
            }
            sc.close();
        }catch (FileNotFoundException e){

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }
    public static Mission getMissionInfoByLvl(int lvl){

        // main code, canged for debug
        LinkedList<Mission> m = loadMissions();
        for (Mission M: m){
            if (M.lvl == lvl)
                return M;
        }
        return null;
        //backend.Mission m = new backend.Mission();

        //return m;
    }
    public static void main(String[] a) {

        launch(a);

      //ConsoleReader cr = new ConsoleReader();
      //cr.firstMenu.stage = stage;
      //cr.firstMenu.execute.execute(null,null);

    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Logg.log();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        loadUsers();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/EnterView.fxml"));
        mainScene = new Scene(loader.load());
        stage.setScene(mainScene);
        stage.show();

    }
    public static void setSceneRoot(Parent p){
        mainScene.setRoot(p);
    }
}
