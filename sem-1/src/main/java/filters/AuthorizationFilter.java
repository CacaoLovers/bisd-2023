package filters;

import database.ConnectionDataSource;
import database.DataBaseInit;
import database.DataBasePostgresInit;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import models.User;
import repositories.UsersRepository;
import repositories.UsersRepositoryPostgres;
import services.UserService;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@WebFilter(urlPatterns = "/*")
public class AuthorizationFilter extends HttpFilter {

    private UserService userService;
    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if(session != null && session.getAttribute("status") == null){

            Cookie[] cookies = request.getCookies();

            session.setAttribute("status", "guest");
            session.setAttribute("role", "user");

            if (cookies == null) chain.doFilter(request, response);

            for (Cookie cookie: cookies){

                if (cookie.getName().equals("lastSession")){

                    String lastSession = cookie.getValue();
                    Optional<User> user = userService.findUserBySession(lastSession);

                    if(user.isPresent()){
                        session.setAttribute("status", "login");
                        session.setAttribute("login", user.get().getLogin());
                        session.setAttribute("role", user.get().getRole());
                        session.setAttribute("volunteer", userService.findVolunteer(user.get().getLogin()));
                        Cookie cookieSession = new Cookie("lastSession", session.getId());
                        cookieSession.setMaxAge(60*60*30);
                        response.addCookie(cookieSession);
                        user.get().setLastSession(session.getId());
                        userService.updateUser(user.get());
                    }

                    break;
                }
            }
            chain.doFilter(request,response);

        } else {

            chain.doFilter(request,response);

        }

    }
}
