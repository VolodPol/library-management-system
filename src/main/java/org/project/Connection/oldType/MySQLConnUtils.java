package org.project.Connection.oldType;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnUtils {

    public static Connection getMySQLConnection()
            throws ClassNotFoundException, SQLException {
        // Note: Change the connection parameters accordingly.
//        String hostName = "localhost";
//        String dbName = "mytest";
//        String userName = "root";
//        String password = "12345";
        Properties prop = new Properties();
        try(FileInputStream inStream = new FileInputStream("db.properties")) {
            prop.load(inStream);
        } catch (IOException exception){
            exception.printStackTrace();
        }


        String dbName = prop.getProperty("DatabaseName");
        String url = prop.getProperty("URL");
        String userName = prop.getProperty("Username");
        String password = prop.getProperty("password");


        return getMySQLConnection(url, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String url, String dbName,
                                                String userName, String password) throws SQLException, ClassNotFoundException {
        Class.forName(dbName);

        // URL Connection for MySQL:
        // Example:
        // jdbc:mysql://localhost:3306/simplehr

        return DriverManager.getConnection(url, userName, password);
    }
}
