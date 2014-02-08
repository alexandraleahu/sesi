package ro.infoiasi.wad.sesi.service.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsersTable {

    Log LOG = LogFactory.getLog(getClass());
    
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
            connection = DriverManager.getConnection("jdbc:sqlite:ro-accounts.db");
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                LOG.warn(e);
            }
        }
        return true;
    }

    public boolean removeUser(DBUser user) {

        Connection connection = null;
        Statement statement = null;
        try {
            String sql = "DELETE FROM USERS WHERE USER='" + user.getUser() + "'";
            connection = DriverManager.getConnection("jdbc:sqlite:ro-accounts.db");
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                LOG.warn(e);
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
    
    public String getUserType (String user) {
        String query = "SELECT TYPE FROM USERS WHERE USER='" + user + "'";
        String type = executeSelectQuery(query);
        return type;
    }
    
    private boolean exists(DBUser user) {
        String query = "SELECT COUNT(*) FROM USERS WHERE USER='" + user.getUser() + "'";
        String result = executeSelectQuery(query);
        if(Integer.parseInt(result) > 0) {
            return true;
        }
        return false;
    }
    
    public static void main(String args[]) {

//        UsersTable usersTable = new UsersTable();
//        usersTable.createTable();
//        System.out.println(usersTable.removeUser(new DBUser("annouk", null, null)));

//        DBUser user = new DBUser("userrr", "pas", "typ");
//        UsersTable users = new UsersTable();
//        users.createTable();
//        DBUser ion = new DBUser("ionpopescu", "ionpopescu", "student");
//        DBUser virt = new DBUser("virtualcomp", "virtualcomp", "company");
//        boolean b = users.addUser(ion);
//        boolean c = users.addUser(virt);
//        System.out.println(b + " " + c);
//        System.out.println(users.getUserType("ionpopescu"));
//        System.out.println(users.getUserType("virtualcomp"));


//        System.out.println(users.login("userrrR", "pas"));
//        System.out.println(users.login("userrr", "pas"));
//        System.out.println(users.login("userrr", "pasS"));
//        System.out.println(users.login("userrrR", "pasS"));
    }
    
    private synchronized String executeSelectQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        String result = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:ro-accounts.db");
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            if(results != null && results.next()) {
                result = results.getString(1);
            } else {
                result = null;
            }
        } catch (Exception e) {
            LOG.warn(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                LOG.warn(e);
            }
        }
        return result;
    }

    public void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          connection = DriverManager.getConnection("jdbc:sqlite:ro-accounts.db");
          System.out.println("Opened database successfully");

          stmt = connection.createStatement();
          String sql = "CREATE TABLE USERS " +
                       "(USER CHAR(20) PRIMARY KEY     NOT NULL," +
                       " PASS           CHAR(200), " +
                       " TYPE         CHAR(10) NOT NULL)"; 
          stmt.executeUpdate(sql);
          stmt.close();
          connection.close();
        } catch ( Exception e ) {
            LOG.warn(e);
        }
    }
}
