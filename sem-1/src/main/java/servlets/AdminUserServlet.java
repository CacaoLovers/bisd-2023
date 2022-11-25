package servlets;

import dto.UserForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class AdminUserServlet extends HttpServlet {

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

        List<UserForm> userForms = userService.from(userService.findAllUser());

        request.setAttribute("userSet", userForms);
        request.getRequestDispatcher("/WEB-INF/pages/admin-user.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("action") != null && request.getParameter("action").equals("remove")){
            try {
                User user = userService.findUser(Long.parseLong(request.getParameter("user_id"))).get();

                if(user != null){
                    if(!session.getAttribute("login").equals(user.getLogin())) {
                        userService.removeUser(user);
                        session.setAttribute("message", "Пользователь успешно удален");
                        response.sendRedirect("/admin/user");
                    } else {
                        session.setAttribute("message", "Нельзя удалить собственный аккаунт");
                        response.sendRedirect("/admin/user");
                    }
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e){
                session.setAttribute("message", "Пользователь не найден");
                response.sendRedirect("/admin/user");
            }

        }
    }


}
