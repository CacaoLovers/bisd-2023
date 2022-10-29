package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdbs.ConnectionDataSource;
import models.Booking;
import repositories.*;

import java.io.IOException;
import java.util.*;

@WebServlet(value = "/remove")
public class AdminCatalogRemoveServlet extends HttpServlet {

    private static  final ConnectionDataSource dataSource = new ConnectionDataSource();

    private static final TableRepository tableRepository = new TableRepositoryPostgres(dataSource);
    private static final BookingRepository bookingRepository  = new BookingRepositoryPostgres(dataSource);
    private static final GuestRepository guestRepository = new GuestRepositoryPostgres(dataSource);

    private final List<Booking> bookingSet = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        bookingSet.clear();
        bookingSet.addAll(bookingRepository.findAllBooking());
        bookingSet.sort(Comparator.comparing(Booking::getTable));

        request.setAttribute("bookingSet", bookingSet);
        request.getRequestDispatcher("pages/remove.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        switch (request.getParameter("entity")){

            case "table": {

                tableRepository.removeTable(Long.valueOf(request.getParameter("id")));

                session.setAttribute("message", "Столик # " + request.getParameter("id") + " успешно удален");
                request.getRequestDispatcher("/").forward(request, response);
                break;

            }

            case "client": {

                guestRepository.removeGuest(Long.valueOf(request.getParameter("id")));

                session.setAttribute("message", "Пользователь " + request.getParameter("id") + " успешно удален");
                response.sendRedirect("/");

                break;

            }

            case "booking": {

                try {

                    String[] bookings = request.getParameterValues("id");
                    Long idTable = null, idGuest = null;
                    Booking booking = null;

                    for (int i = 0; i < bookings.length; i++) {


                        idTable = Long.parseLong(bookings[i].substring(0, bookings[i].indexOf('_')));
                        idGuest = Long.parseLong(bookings[i].substring(bookings[i].indexOf('_') + 1, bookings[i].length()));


                        booking = bookingRepository.findBooking(idTable, idGuest);


                        if (booking == null){

                            throw new NumberFormatException();

                        } else {

                            bookingRepository.removeBooking(Long.valueOf(booking.getTable()), Long.valueOf(booking.getGuest()));

                        }


                    }

                    if (bookings.length != 0) session.setAttribute("message", "Бронь удалена");
                    response.sendRedirect("/");

                } catch (NumberFormatException e){

                    session.setAttribute("message", "Ошибка в удалении");
                    response.sendRedirect("/");

                }



                break;

            }

            default: {
                response.sendRedirect("/error");
            }

        }

    }
}
