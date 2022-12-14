package servlets;

import database.ConnectionDataSource;
import database.DataBasePostgresInit;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import repositories.UsersRepository;
import repositories.UsersRepositoryPostgres;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {

    private final ConnectionDataSource dataSource = new ConnectionDataSource(new DataBasePostgresInit());

    private final UsersRepository usersRepository = new UsersRepositoryPostgres(dataSource);

    private final static byte[] salt = {'f', 'u', 'h'};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("/login");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");



        if (login == null || login.equals("")){
            session.setAttribute("message", "Неверно указан логин");
            response.sendRedirect("/login");
            return;
        }

        if (password == null || password.equals("")) {

            session.setAttribute("message", "Введите корректный пароль");
            response.sendRedirect("/login");
            return;

        }

        if (mail == null || mail.equals("") || mail.indexOf('@') == -1){

            session.setAttribute("message", "Введите корректный адрес электронной почты");
            response.sendRedirect("/login");
            return;

        }

        if (!session.getAttribute("status").equals("guest")) {

            session.setAttribute("message", "Вы уже авторизованы");
            response.sendRedirect("/");
            return;

        }

        Optional<User> user = usersRepository.findUserByLogin(login);

        if (user.isPresent()) {

            session.setAttribute("message", "Пользователь с таким логинов уже существует");
            response.sendRedirect("/login");
            return;

        } else {
            String hashPassword = DigestUtils.md5Hex(password);

            User signUser = User.builder()
                    .mail(mail)
                    .password(hashPassword)
                    .login(login)
                    .build();

            usersRepository.addUser(signUser);
            signUser = usersRepository.findUserByLogin(signUser.getLogin()).get();
            signUser.setLastSession(session.getId());
            usersRepository.updateUser(signUser);

            session.setAttribute("status", "login");
            session.setAttribute("login", signUser.getLogin());
            session.setAttribute("role", user);

            Cookie cookie = new Cookie("lastSession", session.getId());
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            session.setAttribute("message", "Вы успешно зарегистрировались как " + signUser.getLogin());
            response.sendRedirect("/profile/edit");

        }

    }
}
