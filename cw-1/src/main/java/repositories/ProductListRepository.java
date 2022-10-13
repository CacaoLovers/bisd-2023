package repositories;

import jdbc.DataPostgresSource;
import models.Order;
import models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProductListRepository {

    Connection connection;
    DataPostgresSource dataSource = new DataPostgresSource();

    private static String SQL_SELECT_ALL = "select * from cw1.product";

    private static Function<ResultSet, Product> dataToProduct= (data) -> {

        try {

            return Product.builder()
                    .id(data.getInt("id"))
                    .name(data.getString("name"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public ProductListRepository(DataPostgresSource dataSource) {

        this.dataSource = dataSource;

    }

    public Product find(Long id){


        return null;
    }

    public List<Product> findAll (){

        List<Product> tableList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {

                tableList.add(dataToProduct.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableList;
    }

}
