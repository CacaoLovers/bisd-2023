package servlets;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "enterServlet", value = "/")
public class EnterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            HttpSession session = request.getSession();

            clearSessionAttributes(session);

            selectLanguage(session, request);

            request.getRequestDispatcher("pages/index.jsp").forward(request, response);

        } catch (ServletException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        selectLanguage(session, request);

        response.sendRedirect("/room");

    }

    private void clearSessionAttributes(HttpSession session) {

        session.removeAttribute("currentRoom");
        session.removeAttribute("chatRoom");

        session.setAttribute("action", "guest");

    }


    private void selectLanguage (HttpSession session, HttpServletRequest request) {

        if (session.getAttribute("language") == null) {

            session.setAttribute("language", "en");

        } else if (request.getParameter("language") != null){

            switch (request.getParameter("language")){

                case "ru": {
                    session.setAttribute("language", "ru");
                    break;
                }

                case "en": {
                    session.setAttribute("language", "en");
                    break;
                }

                case "tat": {
                    session.setAttribute("language", "tat");
                    break;
                }

            }
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.resource", new Locale( session.getAttribute("language").toString()));

        session.setAttribute("resourceBundle", resourceBundle);

    }
}