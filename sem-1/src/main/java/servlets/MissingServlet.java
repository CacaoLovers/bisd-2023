package servlets;


import database.ConnectionDataSource;
import database.DataBasePostgresInit;
import dto.MissingForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Missing;
import models.User;
import repositories.MissingRepository;
import repositories.MissingRepositoryPostgres;
import repositories.UsersRepository;
import repositories.UsersRepositoryPostgres;
import services.MissingRegistration;

import java.io.IOException;

@WebServlet("/missing")
public class MissingServlet extends HttpServlet {

    private final ConnectionDataSource dataSource = new ConnectionDataSource(new DataBasePostgresInit());

    private final UsersRepository usersRepository = new UsersRepositoryPostgres(dataSource);
    private final MissingRepository missingRepository = new MissingRepositoryPostgres(dataSource);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String foundMessage = null;
        if (session.getAttribute("message") != null){
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }
        if (session.getAttribute("login") == null) {
            response.sendRedirect("/login");
        } else {
            if (request.getParameter("action") != null
                    && (request.getParameter("action").equals("lost")
                    || request.getParameter("action").equals("found"))) {
                switch (request.getParameter("action")){
                    case "found": {
                        foundMessage = "Нашли";
                        break;
                    }
                    case "lost": {
                        foundMessage = "Ищем";
                        break;
                    }
                }
                request.setAttribute("action", request.getParameter("action"));
                request.setAttribute("foundMessage", foundMessage);
                request.setAttribute("addStep", "description");
                request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);
            } else {
                session.setAttribute("message", "Ошибка доступа");
                response.sendRedirect("/login");
            }
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MissingForm missingForm;
        if (request.getParameter("addStep") == null
                || request.getParameter("action") == null
                || session.getAttribute("login") == null){
            session.setAttribute("message", "Ошибка доступа");
            response.sendRedirect("/");
            return;
        }
        if (session.getAttribute("missingForm") == null){
            missingForm = MissingForm.builder()
                    .idOwner(usersRepository.findUserByLogin(session.getAttribute("login").toString()).get().getId())
                    .side(request.getParameter("side"))
                    .status("active")
                    .build();
            session.setAttribute("missingForm", missingForm);
        }
        missingForm = (MissingForm) session.getAttribute("missingForm");
        String addStep = request.getParameter("addStep");
        String side = request.getParameter("action");
        switch (addStep) {
            case "description": {
                if (request.getParameter("name") != null) missingForm.setName(request.getParameter("name"));
                if (request.getParameter("description") != null) missingForm.setDescription(request.getParameter("description"));
                if (request.getParameter("date") != null) missingForm.setDate(request.getParameter("date"));
                if (side.equals("next")) {
                    request.setAttribute("addStep", "location");
                    request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);
                }
                break;
            }
            case "location": {

                if (request.getParameter("address") != null) missingForm.setStreet(request.getParameter("address"));
                if (request.getParameter("city") != null) missingForm.setCity(request.getParameter("city"));
                if (request.getParameter("district") != null) missingForm.setDistrict(request.getParameter("district"));
                try {
                    if (request.getParameter("pos_x") != null)
                        missingForm.setPosX(Double.parseDouble(request.getParameter("pos_x")));
                    if (request.getParameter("pos_y") != null)
                        missingForm.setPosY(Double.parseDouble(request.getParameter("pos_y")));
                } catch (NumberFormatException e){
                    session.setAttribute("message", "Ошибка ввода данных");
                    response.sendRedirect("/");
                    return;
                }
                if (side.equals("back")) {
                    request.setAttribute("addStep", "description");
                } else {
                    request.setAttribute("addStep", "photo");
                }
                request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);
                break;
            }
            case "photo": {
                if (side.equals("back")) {
                    request.setAttribute("addStep", "location");
                } else {
                    User user = usersRepository.findUserByLogin(session.getAttribute("login").toString()).get();
                    if (missingForm.getMail() == null) missingForm.setMail(user.getMail());
                    if (missingForm.getPhoneNumber() == null) missingForm.setPhoneNumber(user.getPhoneNumber());
                    request.setAttribute("addStep", "contact");
                }
                request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);
                break;
            }
            case "contact": {
                if (request.getParameter("phone_number") != null
                        && !request.getParameter("phone_number").equals(missingForm.getPhoneNumber())) missingForm.setPhoneNumber(request.getParameter("phone_number"));
                if (request.getParameter("mail") != null
                        && !request.getParameter("mail").equals(missingForm.getMail())) missingForm.setMail(request.getParameter("mail"));
                if (side.equals("back")) {
                    request.setAttribute("addStep", "photo");
                    request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);
                } else if (side.equals("create")) {
                    if (missingForm != null) {
                        Missing missing = MissingRegistration.createMissing(missingForm);
                        if (missing != null) {
                            missingRepository.addMissing(missing);
                            session.removeAttribute("missingForm");
                            response.sendRedirect("/map?action=" + missing.getSide());
                        } else {
                            session.setAttribute("message", "Ошибка создания объявления");
                            response.sendRedirect("/");
                            return;
                        }
                    } else {
                        session.setAttribute("message", "Ошибка ввода данных");
                        response.sendRedirect("/");
                        return;
                    }
                }
                break;
            }
            default: {
                session.setAttribute("message", "Неверное обращение с формой");
                response.sendRedirect("/");
            }

        }
    }

}
