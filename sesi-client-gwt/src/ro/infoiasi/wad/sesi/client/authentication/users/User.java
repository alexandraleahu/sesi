package ro.infoiasi.wad.sesi.client.authentication.users;

public abstract class User {

    private String id = null;
    private String password = null;
    private String email = null;

    public static final User UNKNOWN_USER = new User("UNKNOWN_USER", "UNKNOWN_USER", "UNKNOWN_USER@infoiasi.ro"){};

    public User(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedIn() {
        return System.currentTimeMillis() % 2 == 0;
    }
}
