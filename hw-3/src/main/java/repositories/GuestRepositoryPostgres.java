package repositories;

import jdbs.ConnectionDataSource;
import models.Booking;
import models.Guest;
import models.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GuestRepositoryPostgres implements GuestRepository {
    private static final String SQL_SELECT_ALL = "select * from hw3.guest";
    private static final String SQL_SELECT_BY_ID = "select * from hw3.guest where id = ?";

    private static final String SQL_SELECT_BY_PHONE = "select * from hw3.guest where phone_number = ?";

    private static final String SQL_DELETE_BY_ID = "delete from hw3.guest where id = ? ";

    private static final String SQL_INSERT = "insert into hw3.guest (first_name, last_name, phone_number, comment) values (?, ?, ?, ?)";

    private static final String SQL_UPDATE = "update hw3.guest set first_name = ?, last_name = ?, phone_number = ?, comment = ? where id = ?";


    private static final Function<ResultSet, Guest> dataToGuest = (data) -> {
        try {
            return Guest.builder()
                    .id(data.getInt("id"))
                    .firstName(data.getString("first_name"))
                    .lastName(data.getString("last_name"))
                    .phoneNumber(data.getString("phone_number"))
                    .comment(data.getString("comment"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private final ConnectionDataSource dataSource;


    public GuestRepositoryPostgres(ConnectionDataSource dataSource) {

        this.dataSource = dataSource;

    }

    @Override
    public boolean addGuest(Guest guest) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setString(1, guest.getFirstName());
            preparedStatement.setString(2, guest.getLastName());
            preparedStatement.setString(3, guest.getPhoneNumber());
            preparedStatement.setString(4, guest.getComment());

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeGuest(Long id) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);

            preparedStatement.setLong(1, id);

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateGuest(Guest guest) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, guest.getFirstName());
            preparedStatement.setString(2, guest.getLastName());
            preparedStatement.setString(3, guest.getPhoneNumber());
            preparedStatement.setString(4, guest.getComment());
            preparedStatement.setInt(5, guest.getId());

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Guest findGuest(Long id) {
        Guest guest = new Guest();

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                guest = dataToGuest.apply(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return guest;
    }

    @Override
    public List<Guest> findAllGuest() {

        List<Guest> guestList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {

                guestList.add(dataToGuest.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return guestList;

    }

    @Override
    public Guest findByPhone(String phoneNumber) {

        Guest guest = new Guest();

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_PHONE);

            preparedStatement.setString(1, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();



            while (resultSet.next()) {
                guest = dataToGuest.apply(resultSet);
            }

            if (guest.getId() == null) return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return guest;
    }
}
