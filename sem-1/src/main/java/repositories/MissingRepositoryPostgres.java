package repositories;

import database.ConnectionDataSource;
import models.Missing;
import models.MissingPosition;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MissingRepositoryPostgres implements MissingRepository {

    private static final String SQL_INSERT_MISSING = "insert into sem_1.missing (id_owner, name, description, pos_x, pos_y, city, street, district, date, path_image, status, side) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_MISSING = "update sem_1.missing set id_owner = ?, name = ?, description = ?, pos_x = ?, pos_y = ?, city = ?, street = ?, district = ?, date = ?, path_image = ?, status = ?, side = ? where id = ?";
    private static final String SQL_DELETE_MISSING = "delete from sem_1.missing where id = ?";


    private static final String SQL_SELECT = "select * from sem_1.missing";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " where id = ?";
    private static final String SQL_SELECT_BY_CITY = SQL_SELECT + " where city = ?";
    private static final String SQL_SELECT_BY_OWNER = SQL_SELECT + " where id_owner = ?";
    private static final String SQL_SELECT_BY_STATUS = SQL_SELECT + " where status = ?";
    private static final String SQL_SELECT_BY_LOCATE = SQL_SELECT + " where district = ? and city = ?";

    private static final String SQL_SELECT_CITY = "select name from sem_1.city where name = ?";

    private static final String SQL_SELECT_ALL_CITY = "select name from sem_1.city";
    private static final String SQL_SELECT_DISTRICTS_BY_CITY = "select name from sem_1.district where city = ?";



    private final Function<ResultSet, Missing> dataToMissing = (data) -> {

        try {

            return Missing.builder()
                    .id(data.getLong("id"))
                    .idOwner(data.getLong("id_owner"))
                    .name(data.getString("name"))
                    .description(data.getString("description"))
                    .position(MissingPosition.builder()
                            .city(data.getString("city"))
                            .district(data.getString("district"))
                            .posX(data.getDouble("pos_x"))
                            .posY(data.getDouble("pos_y"))
                            .street(data.getString("street"))
                            .build())
                    .date(data.getDate("date"))
                    .pathImage(data.getString("path_image"))
                    .side(data.getString("side"))
                    .status(data.getString("status"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };

    private final ConnectionDataSource dataSource;

    public MissingRepositoryPostgres(ConnectionDataSource dataSource){

        this.dataSource = dataSource;

    }

    @Override
    public boolean addMissing(Missing missing) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement("select * from district where name = ? and city = ?");

            statement.setString(1, missing.getPosition().getDistrict());
            statement.setString(2, missing.getPosition().getCity());

            if (!statement.executeQuery().next()){
                statement = connection.prepareStatement("select name from city where name = ?");
                statement.setString(1, missing.getPosition().getCity());

                if(!statement.executeQuery().next()){
                        statement = connection.prepareStatement("insert into city (name, pos_center_x, pos_center_y) values (?,?,?)");
                        statement.setString(1, missing.getPosition().getCity());
                        statement.setDouble(2,  missing.getPosition().getPosX());
                        statement.setDouble(3,  missing.getPosition().getPosX());
                        statement.execute();
                }

                statement = connection.prepareStatement("insert into district (name, city) values (?, ?)");
                statement.setString(1, missing.getPosition().getDistrict());
                statement.setString(2, missing.getPosition().getCity());
                statement.execute();
            }

            statement = connection.prepareStatement(SQL_INSERT_MISSING);

            statement.setLong(1, missing.getIdOwner());
            statement.setString(2, missing.getName());
            statement.setString(3, missing.getDescription());
            statement.setDouble(4, missing.getPosition().getPosX());
            statement.setDouble(5, missing.getPosition().getPosY());
            statement.setString(6, missing.getPosition().getCity());
            statement.setString(7, missing.getPosition().getStreet());
            statement.setString(8, missing.getPosition().getDistrict());
            statement.setTimestamp(9, new Timestamp(missing.getDate().getTime()));
            statement.setString(10, missing.getPathImage());
            statement.setString(11, missing.getStatus());
            statement.setString(12, missing.getSide());

            return statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMissing(Missing missing) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MISSING);

            statement.setLong(1, missing.getIdOwner());
            statement.setString(2, missing.getName());
            statement.setString(3, missing.getDescription());
            statement.setDouble(4, missing.getPosition().getPosX());
            statement.setDouble(5, missing.getPosition().getPosY());
            statement.setString(6, missing.getPosition().getCity());
            statement.setString(7, missing.getPosition().getStreet());
            statement.setString(8, missing.getPosition().getDistrict());
            statement.setTimestamp(9, new Timestamp(missing.getDate().getTime()));
            statement.setString(10, missing.getPathImage());
            statement.setString(11, missing.getStatus());
            statement.setString(12, missing.getSide());
            statement.setLong(13, missing.getId());

            return statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean removeMissing(Missing missing) {

        try (Connection connection = dataSource.getConnection()){

            if (findMissingById(missing.getId()).isPresent()){

                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MISSING);
                statement.setLong(1, missing.getId());
                statement.execute();
                return true;

            } else {

                return false;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Missing> findMissingById(Long id) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                return Optional.of(dataToMissing.apply(resultSet));

            } else {

                return Optional.empty();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Missing> findAllMissingByOwner(Long id) {

        List<Missing> missingList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_OWNER);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                missingList.add(dataToMissing.apply(resultSet));

            }

            return missingList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Missing> findAllMissing() {

        List<Missing> missingList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                missingList.add(dataToMissing.apply(resultSet));

            }

            return missingList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Missing> findAllMissingByStatus(String status) {

        List<Missing> missingList = new ArrayList<>();

        if (!status.equals("found") || !status.equals("lost")) return null;


        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_STATUS);

            statement.setString(1, status);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                missingList.add(dataToMissing.apply(resultSet));

            }

            return missingList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    @Override
    public List<Missing> findAllMissingByCity(Integer city) {

        List<Missing> missingList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CITY);
            statement.setInt(1, city);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                statement = connection.prepareStatement(SQL_SELECT_BY_CITY);
                statement.setInt(1, city);
                resultSet = statement.executeQuery();

                while (resultSet.next()){

                    missingList.add(dataToMissing.apply(resultSet));

                }

                return missingList;

            } else {

                return null;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> findAllCities(){
        List<String> cities = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_CITY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                cities.add(resultSet.getString("name"));
            }

            return cities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> findDistrictByCity(String city){
        List<String> districts = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DISTRICTS_BY_CITY);
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                districts.add(resultSet.getString("name"));
            }
            return districts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Missing> findAllMissingByLocate(String district, String city){
        List<Missing> missingList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement =  connection.prepareStatement(SQL_SELECT_BY_LOCATE);
            statement.setString(1, district);
            statement.setString(2, city);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                missingList.add(dataToMissing.apply(resultSet));

            }

            return missingList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
