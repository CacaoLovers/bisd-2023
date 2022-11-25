package servlets;


import database.ConnectionDataSource;
import database.DataBasePostgresInit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import repositories.UsersRepository;
import repositories.UsersRepositoryPostgres;
import services.MissingService;
import services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/profile/edit")
public class ProfileEditServlet extends HttpServlet {

    private static UserService userService;
    private static MissingService missingService;

    @Override
    public void init() throws ServletException {

        userService = (UserService) getServletContext().getAttribute("userService");
        missingService = (MissingService) getServletContext().getAttribute("missingService");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<String, List<String>> districts = missingService.findAllDistrictWithCity();


        if (session.getAttribute("message") != null){
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        request.setAttribute("cities", missingService.findAllCities());
        request.setAttribute("districtSet", districts);
        request.setAttribute("entity", userService.findUserByLogin(session.getAttribute("login").toString()).get());
        request.getRequestDispatcher("/WEB-INF/pages/editprofile.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (request.getParameter("first_name") == null
                || request.getParameter("last_name") == null
                || request.getParameter("phone_number") == null
                || request.getParameter("city") == null
                || request.getParameter("mail") == null) {

            session.setAttribute("message", "Ошибка ввода данных");
            response.sendRedirect("/profile");
            return;

        }

        User user = userService.findUserByLogin(session.getAttribute("login").toString()).get();


        if (user != null) {

            user.setFirstName(request.getParameter("first_name"));
            user.setLastName(request.getParameter("last_name"));
            user.setPhoneNumber(request.getParameter("phone_number"));
            user.setCity(request.getParameter("city"));
            user.setMail(request.getParameter("mail"));

            userService.updateUser(user);
            user =  userService.findUserByLogin(user.getLogin()).get();

            response.sendRedirect("/profile");

            session.setAttribute("message", "Аккаунт успешно изменен");
            response.sendRedirect("/profile");
            return;
        } else {

            session.setAttribute("message", "Ошибка доступа");
            response.sendRedirect("/profile");
            return;

        }


    }
}
