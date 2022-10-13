package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jdbc.DataPostgresSource;
import models.Order;
import models.Product;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;
import repositories.OrderListRepositories;
import repositories.ProductListRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ActiveOrderServlet", value = "/active")
public class ActiveOrderServlet extends HttpServlet {

    private DataPostgresSource dataPostgresSource = new DataPostgresSource();
    private List<Product> productList = new ArrayList<>();
    private ProductListRepository productListRepository = new ProductListRepository(dataPostgresSource);
    private OrderListRepositories orderListRepositories = new OrderListRepositories(dataPostgresSource);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        productList.addAll(productListRepository.findAll());
        for(Product product: productList){
            if (request.getParameter(product.getName()) != null){
                ((List<Product>)session.getAttribute("basket")).add(product);
                orderListRepositories.addOrder(Order.builder()
                        .client(Integer.valueOf(session.getAttribute("id").toString()))
                        .product(product.getId())
                        .count(1)
                        .time(new Date())
                        .build());
            }
        }


        request.setAttribute("orderList", orderListRepositories.findAll());

        request.getRequestDispatcher("pages/active.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
