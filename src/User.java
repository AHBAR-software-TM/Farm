public class User {
    String userName;
    String password;
    public boolean equals(User u){
        return this.password.equals(u.password);
    }
    User (String userName, String password){
        this.userName = userName;
        this.password = password;
        //todo: !!!IMPORTANT!!! initialize other properties
    }
}
