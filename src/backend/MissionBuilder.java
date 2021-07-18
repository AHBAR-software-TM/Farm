package backend;

import java.io.FileWriter;
import java.io.IOException;

public class MissionBuilder {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String str = sc.next();
//        while (!str.equals("esc")){
//            switch (str){
//                case "new":
//
//            }
//        }
        FileWriter fw;
        try {
            fw = new FileWriter("missions.txt",true);
            Mission m = new Mission();
            m.WildAttack.put(5,new Lion());
            m.productionTask.put("Feather",7);
            m.productionTask.put("Cloth",3);
            m.lvl=2;
            m.animalTask.put("Ostrich",2);
            m.MaximumTime = Integer.MAX_VALUE;
            m.speedGift = 1000;
            fw.append(Main.objectMapper.writeValueAsString(m)).append("\n");
            //System.out.println("ll");
            fw.close();

        }catch (IOException e){}
//        File f = new File("missions.txt");
//        try{
//            Scanner sc = new Scanner(f);
//            backend.Mission m =  backend.Main.objectMapper.readValue(sc.nextLine(),backend.Mission.class);
//            System.out.println("kk");
//            System.out.println(m.productionTask);
//        }catch (IOException ignored){
//            ignored.printStackTrace();
//        }
    }
}
