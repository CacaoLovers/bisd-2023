package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jdbs.ConnectionDataSource;
import repositories.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(name = "AdminInfoServlet", value = "/info")
public class AdminInfoServlet extends HttpServlet {

    private static  final ConnectionDataSource dataSource = new ConnectionDataSource();

    private static final TableRepository tableRepository = new TableRepositoryPostgres(dataSource);
    private static final BookingRepository bookingRepository  = new BookingRepositoryPostgres(dataSource);
    private static final GuestRepository guestRepository = new GuestRepositoryPostgres(dataSource);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy");


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        switch (request.getParameter("entity")){

            case "table": {

                request.setAttribute("dateFormat", dateFormat);
                request.setAttribute("bookingListByTable", bookingRepository.findAllBookingByTable(Long.valueOf(request.getParameter("id"))));
                request.setAttribute("entity", tableRepository.findTable(Long.valueOf(request.getParameter("id"))));
                request.getRequestDispatcher("pages/cart.jsp").forward(request, response);

                break;

            }

            case "client": {

                request.setAttribute("entity", guestRepository.findGuest(Long.valueOf(request.getParameter("id"))));
                request.getRequestDispatcher("pages/cart.jsp").forward(request, response);

                break;

            }

            case "booking": {

                request.setAttribute("entity", bookingRepository.findBooking(Long.valueOf(request.getParameter("id_table")), Long.valueOf(request.getParameter("id_client"))));
                request.getRequestDispatcher("pages/cart.jsp").forward(request, response);

                break;

            }

            default: {

                response.sendRedirect("/error");

            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
