package jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataPostgresSource {

    private Connection connection = null;
    private final String URL = "jdbc:postgresql://localhost:5432/javalab-2023";
    private final String USER = "postgres";
    private final String PASSWORD = "marsel55";
    private final String DRIVER = "org.postgresql.Driver";

    private final String SCHEMA = "cw1";

    public Connection getConnection(){

        try {

            if (connection == null || connection.isClosed()){
                    Class.forName(DRIVER);
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }

        } catch (SQLException | ClassNotFoundException e) {

            throw new RuntimeException(e);

        }

        return connection;

    }

}
