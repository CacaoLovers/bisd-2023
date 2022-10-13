package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jdbc.DataPostgresSource;
import models.Product;
import repositories.ProductListRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "OrderPageServlet", value = "/order")
public class OrderPageServlet extends HttpServlet {

    private DataPostgresSource dataPostgresSource = new DataPostgresSource();
    private List<Product> productList = new ArrayList<>();
    private ProductListRepository productListRepository = new ProductListRepository(dataPostgresSource);

    @Override
    public void init() throws ServletException {
//        Properties properties = new Properties();
//
//        try {
//            properties.load(OrderPageServlet.class.getResourceAsStream("/properties/sql/schema.sql"));
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//
//        try (Connection connection = dataPostgresSource.getConnection()){
//
//            connection.createStatement().execute("create schema if not exists cw1;\n" +
//                    "\n" +
//                    "set search_path to cw1;\n" +
//                    "\n" +
//                    "drop table if exists product;\n" +
//                    "drop table if exists client;\n" +
//                    "drop table if exists order;\n" +
//                    "\n" +
//                    "create table product (\n" +
//                    "\n" +
//                    "    id bigserial primary key,\n" +
//                    "    name varchar(30)\n" +
//                    "\n" +
//                    "\n" +
//                    ");\n" +
//                    "\n" +
//                    "create table client (\n" +
//                    "\n" +
//                    "    id bigserial primary key,\n" +
//                    "    first_name varchar(30)\n" +
//                    "\n" +
//                    ");\n" +
//                    "\n" +
//                    "create table order (\n" +
//                    "\n" +
//                    "    id_client bigint,\n" +
//                    "    id_product bigint,\n" +
//                    "    time_order timestamp,\n" +
//                    "    foreign key (id_client) references product(id),\n" +
//                    "    foreign key (id_product) references client(id)\n" +
//                    "\n" +
//                    ");\n");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        productList.addAll(productListRepository.findAll());

        request.setAttribute("productList", productList);

        request.getRequestDispatcher("pages/order.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }
}
