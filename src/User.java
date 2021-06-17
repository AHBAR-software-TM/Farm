public class User {
    String userName;
    String password;
    int level = 1;
    int coin = 0;
    int userWantsToPlayLvl = 0;
    public boolean equals(User u){
        return this.password.equals(u.password);
    }
    User (String userName, String password){
        this.userName = userName;
        this.password = password;

    }
}
