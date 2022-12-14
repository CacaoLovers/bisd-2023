package listeners;


import database.ConnectionDataSource;
import database.DataBaseInit;
import database.DataBasePostgresInit;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.io.FileUtils;
import repositories.*;
import services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

@WebListener
public class ContextCreateListener implements ServletContextListener {

    private DataBaseInit dataBase;
    private ConnectionDataSource dataSource;
    private UsersRepository usersRepository;
    private MissingRepository missingRepository;
    private NotificationRepository notificationRepository;
    private MissingService missingService;
    private UserService userService;
    private NotificationService notificationService;
    private SimpleDateFormat dateFormat;

    @Override
    public void contextInitialized(ServletContextEvent event) {

        dataBase = new DataBasePostgresInit();
        dataSource = new ConnectionDataSource(dataBase);
        usersRepository = new UsersRepositoryPostgres(dataSource);
        missingRepository = new MissingRepositoryPostgres(dataSource);
        notificationRepository = new NotificationRepositoryPostgres(dataSource);

        missingService = new MissingControllerService(usersRepository, missingRepository);
        userService = new UserControllerService(usersRepository);
        notificationService = new NotificationControllerService(userService, missingService, notificationRepository);

        dateFormat = new SimpleDateFormat("dd.MM.yy");

        event.getServletContext().setAttribute("missingService", missingService);
        event.getServletContext().setAttribute("userService", userService);
        event.getServletContext().setAttribute("notificationService", notificationService);
        event.getServletContext().setAttribute("dateFormat", dateFormat);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
