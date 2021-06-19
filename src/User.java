public class User {
    public String userName;
    public String password;
    public int level = 1;
    public int coin = 0;
    public int userWantsToPlayLvl = 0;
    public boolean equals(User u){
        return this.password.equals(u.password);
    }
    User (String userName, String password){
        this.userName = userName;
        this.password = password;

    }
}
