package servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jdbc.DataPostgresSource;
import models.Order;
import repositories.OrderListRepositories;

@WebServlet(name = "homePage", value = "/")
public class HomePage extends HttpServlet {

    private DataPostgresSource dataPostgresSource = new DataPostgresSource();



    @Override
    public void init() throws ServletException {
        try(Connection connection = dataPostgresSource.getConnection()){

            connection.createStatement().execute("create schema if not exists cw1" );
            connection.createStatement().execute("set search_path to cw1");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



        try{

            request.getRequestDispatcher("pages/home.jsp").forward(request, response);

        } catch ( ServletException e ) {

            throw new RuntimeException(e);

        }

    }
}