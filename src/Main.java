import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    static LinkedList<User> allUsers = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);
    //static Gson gson = new Gson();
    static ObjectMapper objectMapper = new ObjectMapper();

    static void loadUsers(){

        File f = new File("users.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()){
                //allUsers.add(gson.fromJson(sc.nextLine(),User.class));
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
                objectMapper.writeValue(fw,u);
                fw.write("\n");
            }
        }catch (IOException e){
            System.out.println("File users.txt error.");
        }

    }
    static LinkedList<Mission> loadMissions(){
        LinkedList<Mission> ans = new LinkedList<>();
        File f = new File("missions.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()){
                //ans.add(gson.fromJson(sc.nextLine(),Mission.class));
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
    static Mission getMissionInfoByLvl(int lvl){

        // main code, canged for debug
//        LinkedList<Mission> m = loadMissions();
//        for (Mission M: m){
//            if (M.lvl == lvl)
//                return M;
//        }
//        return null;
        Mission m = new Mission();

        return m;
    }
    public static void main(String[] a) throws JsonProcessingException {
        loadUsers();
        ConsoleReader cr = new ConsoleReader();
        cr.firstMenu.execute.execute(null);
       // System.out.println(objectMapper.writeValueAsString(new User("rr","4")));
    }
}
