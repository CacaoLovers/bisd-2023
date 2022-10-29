package repositories;

import jdbs.ConnectionDataSource;
import models.Guest;
import models.Table;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TableRepositoryPostgres implements TableRepository {

    private static final String SQL_SELECT_ALL = "select * from hw3.desk";

    private static final String SQL_SELECT_BY_ID = "select * from hw3.desk where number = ?";

    private static final String SQL_DELETE_BY_ID = "delete from hw3.desk where number = ? ";

    private static final String SQL_INSERT = "insert into hw3.desk (number, capacity, place, description) values (?, ?, ?, ?)";

    private static final String SQL_UPDATE = "update hw3.desk set capacity = ?, place = ?, description = ? where number = ?";

    private static final Function<ResultSet, Table> dataToTable = (data) -> {
        try {
            return Table.builder()
                    .number(data.getInt("number"))
                    .placement(data.getString("place"))
                    .capacity(data.getInt("capacity"))
                    .description(data.getString("description"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private final ConnectionDataSource dataSource;


    public TableRepositoryPostgres(ConnectionDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean addTable(Table table) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setLong(1, table.getNumber());
            preparedStatement.setInt(2, table.getCapacity());
            preparedStatement.setObject(3, table.getPlacement());
            preparedStatement.setString(4, table.getDescription());

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean removeTable(Long id) {

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);

            preparedStatement.setLong(1, id);

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTable(Table table) {


        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setInt(1, table.getCapacity());
            preparedStatement.setString(2, table.getPlacement());
            preparedStatement.setString(3, table.getDescription());
            preparedStatement.setLong(4, table.getNumber());

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Table findTable(Long id) {

        Table table = new Table();

        try (Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                table = dataToTable.apply(resultSet);
            }

            if (table.getNumber() == null) return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return table;


    }

    @Override
    public List<Table> findAllTable() {

        List<Table> tableList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {

                tableList.add(dataToTable.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableList;

    }
}
