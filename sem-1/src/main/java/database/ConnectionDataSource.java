package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataSource {
    private Connection connection;
    private final String URL;
    private final String NAME;
    private final String PASSWORD;
    private final String DRIVER;

    private final DataBaseInit dataBase;

    public ConnectionDataSource(DataBaseInit dataBase){

        this.dataBase = dataBase;
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
            connection.createStatement().execute("create schema if not exists sem_1");
            connection.createStatement().execute("set search_path to sem_1");


        } catch (SQLException | ClassNotFoundException e) {

            throw new RuntimeException(e);

        }

        return connection;

    }


}
