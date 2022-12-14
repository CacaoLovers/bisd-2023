package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Missing;
import models.User;
import services.MissingService;
import services.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "volunteerServlet", value = "/volunteer")
public class VolunteerServlet extends HttpServlet {

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
        List<String> districts;
        List<String> currentDistrict;
        Map<String, List<Missing>> missingMap = new HashMap<>();

        Optional<User> user = userService.findVolunteer(session.getAttribute("login").toString());

        if (session.getAttribute("message") != null){
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        if (user.isPresent() && request.getParameter("action") == null){

            currentDistrict = userService.findVolunteerDistrict(user.get().getId());
            for(String district: currentDistrict){
                missingMap.put(district, missingService.findAllMissingByLocate(district, user.get().getCity()).stream().filter(missing -> missing.getSide().equals("lost")).collect(Collectors.toList()));
            }


            request.setAttribute("districtSet", currentDistrict);
            request.setAttribute("districtMap", missingMap);
            request.getRequestDispatcher("/WEB-INF/pages/volunteer.jsp").forward(request,response);

        } else {

            user = userService.findUserByLogin(session.getAttribute("login").toString());

            currentDistrict = userService.findVolunteerDistrict(user.get().getId());
            districts = missingService.findDistrictByCity(user.get().getCity());
            districts.removeAll(currentDistrict);

            request.setAttribute("currentDistrict", currentDistrict);
            request.setAttribute("districtSet", districts);
            request.getRequestDispatcher("/WEB-INF/pages/volunteeredit.jsp").forward(request, response);

        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if(!request.getRequestURI().equals("/volunteer") || request.getParameter("action") == null){

            session.setAttribute("message", "Ошибка доступа");
            response.sendRedirect("/profile");
            return;

        } else {

            User user = userService.findUserByLogin(session.getAttribute("login").toString()).get();

            if (request.getParameter("action").equals("add")){

                userService.addVolunteerDistrict(user.getId(), request.getParameter("district"));
                response.sendRedirect("/volunteer?action=edit");

            } else if (request.getParameter("action").equals("remove")) {

                userService.removeVolunteerDistrict(user.getId(), request.getParameter("district"));
                response.sendRedirect("/volunteer?action=edit");
            }

        }

    }
}
