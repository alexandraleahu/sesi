package ro.infoiasi.wad.sesi.service.authentication;

public class DBUser {

    private final String user;
    private final String pass;
    private final String type;
    
    public DBUser(String user, String pass, String type) {
        this.user = user;
        this.pass = pass;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    String getPass() {
        return pass;
    }

    public String getType() {
        return type;
    }
    
    public String toString() {
        return "{user:" + user
                + ", pass:" + pass
                + ", type:" + type + "}";
    }
}
