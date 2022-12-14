package repositories;

import database.ConnectionDataSource;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UsersRepositoryPostgres implements UsersRepository {

    private static final String SQL_INSERT_USER = "insert into sem_1.users (first_name, last_name, login, password, city, mail, phone_number, last_session, role) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "update sem_1.users set first_name = ?, last_name = ?, login = ?, password = ?, city = ?, mail = ?, phone_number = ?, last_session = ?, role = ? where id = ?";
    private static final String SQL_DELETE_USER = "delete from sem_1.users where id = ?";
    private static final String SQL_SELECT = "select * from sem_1.users";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " where id = ?";
    private static final String SQL_SELECT_BY_LOGIN = SQL_SELECT + " where login = ?";

    private static final String SQL_SELECT_BY_SESSION = SQL_SELECT + " where last_session = ?";

    private static final String SQL_SELECT_VOLUNTEER = "select * from sem_1.volunteer";
    private static final String SQL_SELECT_VOLUNTEER_BY_ID = SQL_SELECT_VOLUNTEER + " where id_user = ?";
    private static final String SQL_SELECT_VOLUNTEER_BY_DISTRICT = SQL_SELECT_VOLUNTEER + " join users on volunteer.id_user = users.id where district = ?";

    private static final String SQL_SELECT_VOLUNTEER_DISTRICT =  "select district from sem_1.volunteer where id_user = ?";

    private static final String SQL_ADD_VOLUNTEER_DISTRICT = "insert into sem_1.volunteer (id_user, district, city) values (?, ?, ?)";
    private static final String SQL_REMOVE_VOLUNTEER_DISTRICT = "delete from sem_1.volunteer where id_user = ? and district = ? and city = ?";

    private final Function<ResultSet, User> dataToUser = (data) -> {

        try {

            return User.builder()
                    .id(data.getLong("id"))
                    .login(data.getString("login"))
                    .firstName(data.getString("first_name"))
                    .lastName(data.getString("last_name"))
                    .password(data.getString("password"))
                    .city(data.getString("city"))
                    .mail(data.getString("mail"))
                    .phoneNumber(data.getString("phone_number"))
                    .lastSession(data.getString("last_session"))
                    .role(data.getString("role"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };

    private final ConnectionDataSource dataSource;

    public UsersRepositoryPostgres(ConnectionDataSource dataSource){

        this.dataSource = dataSource;

    }


    @Override
    public boolean addUser(User user) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getCity());
            preparedStatement.setString(6, user.getMail());
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.setString(8, user.getLastSession());
            preparedStatement.setString(9, user.getRole());

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateUser(User user) {

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getCity());
            preparedStatement.setString(6, user.getMail());
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.setString(8, user.getLastSession());
            preparedStatement.setString(9, user.getRole());
            preparedStatement.setLong(10, user.getId());

            if (preparedStatement.executeUpdate() == 1) return true;
            else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean removeUser(User user) {
        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER);

            preparedStatement.setLong(1, user.getId());

            if (preparedStatement.executeUpdate() == 1) return true;
            else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findUser(Long id) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {

                return Optional.of(dataToUser.apply(resultSet));

            } else {

                return Optional.empty();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> findUserByLogin(String login) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_LOGIN);

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(dataToUser.apply(resultSet));

            } else {

                return Optional.empty();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findUserBySession(String session) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_SESSION);

            preparedStatement.setString(1, session);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(dataToUser.apply(resultSet));

            } else {

                return Optional.empty();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<User> findAllUser() {

        List<User> userList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                userList.add(dataToUser.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;

    }

    public Optional<User> findVolunteer(String login){

        Optional<User> user = findUserByLogin(login);
        if (user.isEmpty()) return Optional.empty();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_VOLUNTEER_BY_ID);
            preparedStatement.setLong(1, user.get().getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return user;

            } else {

                return Optional.empty();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findVolunteerByDistrict(String district){
        List<User> userList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_VOLUNTEER_BY_DISTRICT);
            preparedStatement.setString(1, district);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                userList.add(dataToUser.apply(resultSet));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public List<String> findVolunteerDistrict(Long id) {
        List<String> districtList = new ArrayList<>();
        Optional<User> user = findUser(id);
        if (user.isEmpty()) return Collections.emptyList();

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_VOLUNTEER_DISTRICT);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                districtList.add(resultSet.getString("district"));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return districtList;
    }

    public boolean addVolunteerDistrict(Long id, String district){
        try(Connection connection = dataSource.getConnection()){
            User user = findUser(id).get();
            String city = user.getCity();
            PreparedStatement preparedStatement = connection.prepareStatement("select city, name from sem_1.district where city = ? and name = ?");
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, district);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                return false;
            }

            preparedStatement = connection.prepareStatement(SQL_ADD_VOLUNTEER_DISTRICT);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, district);
            preparedStatement.setString(3, city);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeVolunteerDistrict(Long id, String district){
        try(Connection connection = dataSource.getConnection()){
            User user = findUser(id).get();
            String city = user.getCity();
            PreparedStatement preparedStatement = connection.prepareStatement("select city, name from sem_1.district where city = ? and name = ?");
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, district);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                return false;
            }

            preparedStatement = connection.prepareStatement(SQL_REMOVE_VOLUNTEER_DISTRICT);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, district);
            preparedStatement.setString(3, city);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
