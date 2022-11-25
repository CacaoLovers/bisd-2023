package repositories;

import database.ConnectionDataSource;
import models.Missing;
import models.Notification;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NotificationRepositoryPostgres implements NotificationRepository {

    private static final String SQL_INSERT_NOTIFICATION = "insert into notification (id_user_from, id_user_to, id_missing, type, status) values (?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_DELETE = "delete from notification where id_user_from = ? and id_user_to = ? and id_missing = ?";

    private static final String SQL_SELECT_BY_USER = "select * from notification where id_user_to = ?";


    private final Function<ResultSet, Notification> dataToNotification = (data) -> {

        try {

            return Notification.builder()
                    .userIdTo(data.getLong("id_user_to"))
                    .userIdFrom(data.getLong("id_user_from"))
                    .missingId(data.getLong("id_missing"))
                    .status(data.getString("type"))
                    .type(data.getString("status"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };

    private final ConnectionDataSource dataSource;

    public NotificationRepositoryPostgres(ConnectionDataSource dataSource){

        this.dataSource = dataSource;

    }

    @Override
    public boolean addNotification(Notification notification) {

            try (Connection connection = dataSource.getConnection()){

                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NOTIFICATION);
                preparedStatement.setLong(1, notification.getUserIdFrom());
                preparedStatement.setLong(2, notification.getUserIdTo());
                preparedStatement.setLong(3, notification.getMissingId());
                preparedStatement.setString(4, notification.getType());
                preparedStatement.setString(5, notification.getStatus());

                return preparedStatement.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    @Override
    public boolean removeNotification(Notification notification) {
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_DELETE);
            preparedStatement.setLong(1, notification.getUserIdFrom());
            preparedStatement.setLong(2, notification.getUserIdTo());
            preparedStatement.setLong(3, notification.getMissingId());

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> findNotificationByUser(Long id) {
        List<Notification> notificationList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_USER);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                notificationList.add(dataToNotification.apply(resultSet));
            }
            return notificationList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
