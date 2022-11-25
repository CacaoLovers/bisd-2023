package servlets;

import database.ConnectionDataSource;
import database.DataBaseInit;
import database.DataBasePostgresInit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import repositories.UsersRepository;
import repositories.UsersRepositoryPostgres;
import services.UserService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.util.Optional;


@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("message") != null){
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {

            byte[] passwordByte = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5password = md.digest(passwordByte);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        if (login == null
                || login.equals("")) {

            session.setAttribute("message", "Введите логин");
            response.sendRedirect("/login");
            return;

        }

        if (password == null
                || password.equals("")) {

            session.setAttribute("message", "Введите пароль");
            response.sendRedirect("/login");
            return;

        }

        if (!session.getAttribute("status").equals("guest")) {

            session.setAttribute("message", "Вы уже авторизованы");
            response.sendRedirect("/");
            return;

        }

        Optional<User> user = userService.findUserByLogin(request.getParameter("login"));

        if (user.isPresent()){

            String hashPassword = DigestUtils.md5Hex(password);

            if(user.get().getPassword().equals(hashPassword)) {

                session.setAttribute("status", "login");
                session.setAttribute("login", user.get().getLogin());
                session.setAttribute("role", user.get().getRole());
                if(userService.findVolunteer(user.get().getLogin()).isPresent()) session.setAttribute("volunteer", true);
                user.get().setLastSession(session.getId());
                userService.updateUser(user.get());
                Cookie cookie = new Cookie("lastSession", session.getId());
                cookie.setMaxAge(60*60*24*7);
                response.addCookie(cookie);
                session.setAttribute("message", "Вы успешно авторизовались как " + user.get().getLogin());
                response.sendRedirect("/");

            } else {

                session.setAttribute("message", "Введен неверный пароль");
                response.sendRedirect("/login");


            }

        } else {

            session.setAttribute("message", "Пользователь с таким логином не найден");
            response.sendRedirect("/login");


        }
    }
}
