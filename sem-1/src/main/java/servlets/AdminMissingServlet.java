package servlets;

import dto.MissingForm;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Missing;
import services.MissingService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminMissingServlet", value = "/admin/missing")
public class AdminMissingServlet extends HttpServlet {

    private MissingService missingService;

    @Override
    public void init() throws ServletException {
        missingService = (MissingService) getServletContext().getAttribute("missingService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MissingForm> missingForms = missingService.from(missingService.findAllMissing());

        request.setAttribute("missingSet", missingForms);
        request.getRequestDispatcher("/WEB-INF/pages/admin-missing.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
