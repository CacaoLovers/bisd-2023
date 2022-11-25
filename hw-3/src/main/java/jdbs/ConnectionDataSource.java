package jdbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataSource {
    private Connection connection;
    private final String URL;
    private final String NAME;
    private final String PASSWORD;
    private final String DRIVER;

    DataBaseInit dataBase = new DataBasePostgresInit();

    public ConnectionDataSource(){

        this.URL = dataBase.getURL();
        this.NAME = dataBase.getUsername();
        this.PASSWORD = dataBase.getPassword();
        this.DRIVER = dataBase.getDriver();

    }

    public Connection getConnection (){

        Connection connection;

        try {

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            connection.createStatement().execute("create schema if not exists hw3");
            connection.createStatement().execute("set search_path to hw3");


        } catch (SQLException | ClassNotFoundException e) {

            throw new RuntimeException(e);

        }

        return connection;

    }


}
