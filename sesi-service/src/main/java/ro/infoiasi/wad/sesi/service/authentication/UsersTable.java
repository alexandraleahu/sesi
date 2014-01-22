package ro.infoiasi.wad.sesi.service.authentication;

import java.sql.*;

public class UsersTable {

    public boolean addUser(DBUser user) {
        if(exists(user)) {
            return false;
        }
        Connection connection = null;
        Statement statement = null;
        try {
            String sql = "INSERT INTO USERS VALUES('" + user.getUser()
                    + "', '" + user.getPass()
                    + "', '" + user.getType()
                    + "')";
            connection = DriverManager.getConnection("jdbc:sqlite:sesi-accounts.db");
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    public DBUser login (String user, String pass) {
        String query = "SELECT TYPE FROM USERS WHERE USER='" + user + "' AND PASS='" + pass + "'";
        String type = executeSelectQuery(query);
        if(type != null) {
            return new DBUser(user, pass, type);
        }
        return null;
    }
    
    private boolean exists(DBUser user) {
        String query = "SELECT COUNT(*) FROM USERS WHERE USER='" + user.getUser() + "'";
        String result = executeSelectQuery(query);
        if(Integer.parseInt(result) > 0) {
            return true;
        }
        return false;
    }
    
//    public static void main(String args[]) {
//        User user = new User("userrr", "pas", "typ");
//        UsersTable users = new UsersTable();
////        users.createTable();
//        System.out.println(users.addUser(user));
//        System.out.println(users.login("userrrR", "pas"));
//        System.out.println(users.login("userrr", "pas"));
//        System.out.println(users.login("userrr", "pasS"));
//        System.out.println(users.login("userrrR", "pasS"));
//    }
    
    private synchronized String executeSelectQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        String result = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sesi-accounts.db");
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            if(results != null && results.next()) {
                result = results.getString(1);
            } else {
                result = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          connection = DriverManager.getConnection("jdbc:sqlite:sesi-accounts.db");
          System.out.println("Opened database successfully");

          stmt = connection.createStatement();
          String sql = "CREATE TABLE USERS " +
                       "(USER CHAR(20) PRIMARY KEY     NOT NULL," +
                       " PASS           CHAR(200)    NOT NULL, " + 
                       " TYPE         CHAR(10) NOT NULL)"; 
          stmt.executeUpdate(sql);
          stmt.close();
          connection.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }

}
