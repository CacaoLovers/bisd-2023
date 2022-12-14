package servlets;

import database.ConnectionDataSource;
import database.DataBasePostgresInit;
import dto.MissingForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Missing;
import models.Notification;
import repositories.MissingRepository;
import repositories.MissingRepositoryPostgres;
import repositories.UsersRepository;
import repositories.UsersRepositoryPostgres;
import services.MissingControllerService;
import services.MissingService;
import services.NotificationService;
import services.UserService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/map")
public class MapServlet extends HttpServlet {

    private MissingService missingService;
    private NotificationService notificationService;
    private UserService userService;
    private SimpleDateFormat dateFormat;


    @Override
    public void init() throws ServletException {
        missingService = (MissingService) getServletContext().getAttribute("missingService");
        userService = (UserService) getServletContext().getAttribute("userService");
        dateFormat = (SimpleDateFormat) getServletContext().getAttribute("dateFormat");
        notificationService = (NotificationService) getServletContext().getAttribute("notificationService");
    }






    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String foundMessage = null;
        String foundDate = null;
        Long id;
        List<MissingForm> missingForms;

        if (request.getParameter("action") != null
                && (request.getParameter("action").equals("lost")
                || request.getParameter("action").equals("found"))){

            switch (request.getParameter("action")){
                case "found": {
                    foundMessage = "Нашли ";
                    foundDate = "Найден";
                    break;
                }
                case "lost": {
                    foundMessage = "Ищем";
                    foundDate = "Потерян";
                    break;
                }
            }

            if(request.getParameter("missing_id") != null){
                try {

                    id = Long.parseLong(request.getParameter("missing_id"));
                    request.setAttribute("missingId", id);

                } catch (NumberFormatException e){
                    session.setAttribute("message", "Ошибка: объявления не существует");
                    response.sendRedirect("/map?action=lost");
                    return;
                }
            }

            List<Missing> missings = missingService.findAllMissing();

            missings = missings.stream().filter(missing -> missing.getSide().equals(request.getParameter("action"))).collect(Collectors.toList());

            missingForms = missingService.from(missings);

            missingForms.sort(Comparator.comparing(MissingForm::getDate));

            if (session.getAttribute("message") != null){
                request.setAttribute("message", session.getAttribute("message"));
                session.removeAttribute("message");
            }

            request.setAttribute("missingSet", missingForms);
            request.setAttribute("foundMessage", foundMessage);
            request.setAttribute("foundDate", foundDate);
            request.setAttribute("action", request.getParameter("action"));
            request.getRequestDispatcher("/WEB-INF/pages/map.jsp").forward(request, response);


        } else {

            session.setAttribute("message", "Ошибка доступа");
            response.sendRedirect("/");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("action") != null){
            if (request.getParameter("action").equals("remove")){
                try {
                    Missing missing = missingService.findMissingById(Long.parseLong(request.getParameter("missing_id"))).get();
                    if (missing != null){
                        missingService.removeMissing(missing);
                        session.setAttribute("message", "Объявление удалено");
                        response.sendRedirect("/admin/missing");
                        return;
                    } else {
                        session.setAttribute("message", "Ошибка: объявления не существует");
                        response.sendRedirect("/map?action=lost");
                        return;
                    }
                } catch (NumberFormatException e) {
                    session.setAttribute("message", "Ошибка: объявления не существует");
                    response.sendRedirect("/map?action=lost");
                    return;
                }
            }
            if (request.getParameter("action").equals("found")){
                try {

                    Missing missing = missingService.findMissingById(Long.parseLong(request.getParameter("missing_id"))).get();

                    if (session.getAttribute("login") == null){
                        session.setAttribute("message", "Необходима авторизация");
                        response.sendRedirect("/login");
                        return;
                    }

                    if (session.getAttribute("login").equals(userService.findUser(missing.getIdOwner()).get().getLogin())){
                        missingService.removeMissing(missing);
                        session.setAttribute("message", "Объявление удалено");
                        response.sendRedirect("/map?action=lost");
                        return;
                    }

                    if (missing != null){
                        notificationService.addNotification(Notification.builder()
                                .status("active")
                                .type("missing")
                                .missingId(missing.getId())
                                .userIdFrom(userService.findUserByLogin(session.getAttribute("login").toString()).get().getId())
                                .userIdTo(missing.getIdOwner())
                                .build());
                        session.setAttribute("message", "Уведомление отправлено");
                        response.sendRedirect("/map?action=found");
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e){
                    session.setAttribute("message", "Объявление не найдено");
                    response.sendRedirect("/map?action=lost");
                    return;
                }
            }
        }
    }
}
