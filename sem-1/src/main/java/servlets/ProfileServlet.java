package servlets;

import dto.NotificationForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Missing;
import models.Notification;
import models.User;
import services.MissingService;
import services.NotificationService;
import services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {


    private UserService userService;
    private MissingService missingService;
    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        missingService = (MissingService) getServletContext().getAttribute("missingService");
        notificationService = (NotificationService) getServletContext().getAttribute("notificationService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Optional<User> user = Optional.empty();
        String login = request.getParameter("login");
        List<NotificationForm> notificationForms = new ArrayList<>();

        if (login == null && session.getAttribute("login") != null){

            login = session.getAttribute("login").toString();

        }

        if (session.getAttribute("message") != null){
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        user = userService.findUserByLogin(login);


        if (user.isPresent()){

            if(user.get().getLogin().equals(session.getAttribute("login"))){
                notificationForms = notificationService.formNotification(notificationService.findNotificationByUser(user.get().getId()));
                request.setAttribute("notificationSet", notificationForms);
            }

            request.setAttribute("missingSet", missingService.findAllMissingByOwner(user.get().getId()));
            request.setAttribute("entity", user.get());
            request.setAttribute("loginEnter", login);
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);

        } else {

            session.setAttribute("message", "Ошибка подтверждения");
            response.sendRedirect("/");
        }




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getParameter("action") != null){
            if (request.getParameter("action").equals("exit")){
                session.setAttribute("status", "guest");
                session.setAttribute("role", "user");
                session.removeAttribute("volunteer");
                session.removeAttribute("login");
                response.sendRedirect("/");
            } else if (request.getParameter("action").equals("confirm")){
                try {
                    Missing missing = missingService.findMissingById(Long.parseLong(request.getParameter("missing_id"))).get();
                    User user = userService.findUser(missing.getIdOwner()).get();
                    missing.setStatus("closed");
                    missingService.removeMissing(missing);
                    List<Notification> notifications = notificationService.findNotificationByUser(user.getId());
                    for(Notification notification: notifications) {
                        if (notification.getMissingId().equals(missing.getId())) {
                            notificationService.removeNotification(notification);
                        }
                    }
                    session.setAttribute("message", "Ура! Питомец найден!");
                    response.sendRedirect("/profile?login=" + user.getLogin());
                } catch (NumberFormatException e){
                    session.setAttribute("message", "Ошибка подтверждения");
                    response.sendRedirect("/");
                }
            }
        }
    }
}
