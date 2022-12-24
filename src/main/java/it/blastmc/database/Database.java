package it.blastmc.database;
import java.sql.*;
public class Database {
    private final String ip, port, database, user, password;

    public Database(String ip, String port, String database, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public Connection connection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, user, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return con;
    }
}
