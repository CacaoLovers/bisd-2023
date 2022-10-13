package repositories;

import jdbc.DataPostgresSource;
import models.Order;
import models.Product;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OrderListRepositories {


    DataPostgresSource dataSource = new DataPostgresSource();

    private static String SQL_SELECT_ALL = "select * from cw1.orders";

    private static String SQL_INSERT = "insert into cw1.orders values (? ? ? ?)";

    private static Function<ResultSet, Order> dataToOrder = (data) -> {

        try {

            return Order.builder()
                    .client(data.getInt("id_client"))
                    .product(data.getInt("id_product"))
                    .count(data.getInt("count"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public OrderListRepositories(DataPostgresSource dataSource) {

        this.dataSource = dataSource;

    }


    public Order find(Long id){


        return null;
    }

    public List<Order> findAll (){

        List<Order> orderList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {

                orderList.add(dataToOrder.apply(resultSet));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderList;
    }

    public void addOrder (Order order) {

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);

            statement.setInt(1, order.getClient());
            statement.setInt(2, order.getProduct());
            statement.setTimestamp(3, new Timestamp(order.getTime().getTime()));
            statement.setInt(4, order.getCount());

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
