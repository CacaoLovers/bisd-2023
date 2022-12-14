package database;



import java.io.IOException;
import java.util.Properties;

public class DataBasePostgresInit implements DataBaseInit{

    private static String URL;
    private static String PASSWORD;
    private static String USERNAME;
    private static String DRIVER;

    public DataBasePostgresInit(){

        Properties properties = new Properties();

        try {
            properties.load(DataBasePostgresInit.class.getResourceAsStream("/properties/db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        this.URL = properties.getProperty("url");
        this.USERNAME = properties.getProperty("username");
        this.PASSWORD = properties.getProperty("password");
        this.DRIVER = properties.getProperty("driver");

    }


    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public String getDriver() {
        return DRIVER;
    }
}
