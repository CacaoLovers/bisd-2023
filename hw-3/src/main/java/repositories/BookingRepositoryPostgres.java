package repositories;

import jdbs.ConnectionDataSource;
import models.Booking;
import models.Guest;
import models.Table;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class BookingRepositoryPostgres implements BookingRepository {

    private final ConnectionDataSource dataSource;

    private static final String SQL_SELECT_ALL = "select id_guest, id_table, booking.time, duration, number_person, hw3.guest.phone_number from hw3.booking join hw3.guest on id_guest = hw3.guest.id";
    private static final String SQL_SELECT_BY_TABLE_ID = SQL_SELECT_ALL + " where id_table = ?";

    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " where id_table = ? and id_guest = ?";

    private static final String SQL_INSERT_BOOKING = "insert into hw3.booking values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_BY_ID = "delete from hw3.booking where id_table = ? and id_guest = ? ";

    private static final String SQL_UPDATE = "update hw3.booking set time = ?, duration = ?, number_person = ? where id_guest = ? and id_table = ?";


    private static final String SQL_AND = " and";
    private static final String SQL_WHERE = " where";
    private static final String SQL_WHERE_ID_TABLE = " id_table = ?";
    private static final String SQL_WHERE_ID_GUEST = " id_guest = ?";
    private static final String SQL_WHERE_DATE = " date(time) = ?";

    private static final Function<ResultSet, Booking> dataToBooking = (data) -> {
        try {
            return Booking.builder()
                    .table(data.getInt("id_table"))
                    .guest(data.getInt("id_guest"))
                    .date(data.getTimestamp("time"))
                    .duration(data.getInt("duration"))
                    .numberPerson(data.getInt("number_person"))
                    .phoneNumber(data.getString("phone_number"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };


    public BookingRepositoryPostgres(ConnectionDataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public boolean addBooking(Booking booking) {

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOKING);

            preparedStatement.setInt(1, booking.getGuest());
            preparedStatement.setInt(2, booking.getTable());
            preparedStatement.setTimestamp(3, new Timestamp(booking.getDate().getTime()) );
            preparedStatement.setInt(4, booking.getDuration());
            preparedStatement.setInt(5, booking.getNumberPerson());

            return preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean removeBooking(Long idTable, Long idClient) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);

            preparedStatement.setLong(1, idTable);
            preparedStatement.setLong(2, idClient);

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateBooking(Booking booking) {

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setTimestamp(1, new Timestamp(booking.getDate().getTime()) );
            preparedStatement.setInt(2, booking.getDuration());
            preparedStatement.setInt(3, booking.getNumberPerson());
            preparedStatement.setInt(4, booking.getGuest());
            preparedStatement.setInt(5, booking.getTable());

            return preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Booking findBooking(Long idTable, Long idClient) {

        Booking booking = new Booking();

        try (Connection connection = dataSource.getConnection()){

           PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

           preparedStatement.setLong(1, idTable);
           preparedStatement.setLong(2, idClient);

           ResultSet resultSet = preparedStatement.executeQuery();

           while (resultSet.next()) {
               booking = dataToBooking.apply(resultSet);
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return booking;
    }

    @Override
    public List<Booking> findAllBooking() {

        List<Booking> bookingList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {

                bookingList.add(dataToBooking.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingList;
    }

    @Override
    public List<Booking> findAllBookingByTable(Long idTable) {
        List<Booking> bookingList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_TABLE_ID);

            statement.setLong(1, idTable);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                bookingList.add(dataToBooking.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingList;
    }

    public List<Booking> findByFilter(Long idTable, Long idGuest, Date date){

        List<Booking> bookingList = new ArrayList<>();


        try(Connection connection = dataSource.getConnection()) {

            StringBuilder sqlQuery = new StringBuilder(SQL_SELECT_ALL);

            if (idTable != null || idGuest != null || date != null) {

                sqlQuery.append(SQL_WHERE);

            }

            if (idTable != null){

                sqlQuery.append(SQL_WHERE_ID_TABLE);

            }

            if (idGuest != null){

                if (idTable != null) sqlQuery.append(SQL_AND);
                sqlQuery.append(SQL_WHERE_ID_GUEST);

            }

            if (date != null){

                if (idTable != null || idGuest != null) sqlQuery.append(SQL_AND);
                sqlQuery.append(SQL_WHERE_DATE);

            }

            sqlQuery.append(";");

            PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());


            int numberAttribute = 1;

            if (idTable != null) {
                statement.setLong(numberAttribute, idTable);
                numberAttribute++;
            }

            if (idGuest != null) {
                statement.setLong(numberAttribute, idGuest);
                numberAttribute++;

            }

            if (date != null) {
                statement.setTimestamp(numberAttribute, new Timestamp(date.getTime()));
            }


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                bookingList.add(dataToBooking.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingList;

    }

}