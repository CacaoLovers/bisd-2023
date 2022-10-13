package listeners;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Product;

import java.util.ArrayList;
import java.util.List;

@WebListener
public class SessionListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private List<Product> basket = new ArrayList<>();

    public SessionListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        session.setAttribute("basket", basket);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        basket.clear();

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }

    public List<Product> getBasket() {
        List <Product> productList = new ArrayList<>(basket);
        return productList;
    }
}
