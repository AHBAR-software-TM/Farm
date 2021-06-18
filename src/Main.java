import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static LinkedList<User> allUsers = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);
    static Gson gson = new Gson();
    static void loadUsers(){
        File f = new File("users.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()){
                allUsers.add(gson.fromJson(sc.nextLine(),User.class));
            }
            sc.close();
        }catch (FileNotFoundException e){

        }
    }
    static LinkedList<Mission> loadMissions(){
        LinkedList<Mission> ans = new LinkedList<>();
        File f = new File("missions.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()){
                ans.add(gson.fromJson(sc.nextLine(),Mission.class));
            }
            sc.close();
        }catch (FileNotFoundException e){

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
    public static void main(String[] a){
        loadUsers();
        ConsoleReader cr = new ConsoleReader();
        cr.firstMenu.execute.execute(null);
    }
}
