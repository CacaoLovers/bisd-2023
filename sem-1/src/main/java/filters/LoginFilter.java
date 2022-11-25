package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/login", "/signup"})
public class LoginFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("status").equals("guest") && session.getAttribute("login") == null) {
            chain.doFilter(request, response);
        } else {
            session.setAttribute("message", "Ошибка доступа");
            response.sendRedirect("/");
            return;

        }
    }
}
