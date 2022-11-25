package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdbs.ConnectionDataSource;
import models.Booking;
import models.Guest;
import models.Table;
import repositories.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(value = "/edit")

public class AdminCatalogEditServlet extends HttpServlet {

    private static  final ConnectionDataSource dataSource = new ConnectionDataSource();

    private static final TableRepository tableRepository = new TableRepositoryPostgres(dataSource);
    private static final BookingRepository bookingRepository  = new BookingRepositoryPostgres(dataSource);

    private static final GuestRepository guestRepository = new GuestRepositoryPostgres(dataSource);



    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (request.getParameter("entity") == null) {
            if (session.getAttribute("bookingAddNumber") != null) {

                request.setAttribute("entity", guestRepository.findByPhone(session.getAttribute("bookingAddNumber").toString()));
                request.setAttribute("bookingAddNumber", session.getAttribute("bookingAddNumber"));

            } else {

                System.out.println("sd");
                session.setAttribute("message", "Ошибка доступа");
                response.sendRedirect("/");

            }
        } else {

            switch (request.getParameter("entity")){

                case "table": {

                    try {

                        Long idTable = Long.parseLong(request.getParameter("id"));
                        Table table = tableRepository.findTable(idTable);

                        if (table == null) throw new NumberFormatException();

                        request.setAttribute("entity", table);
                        request.getRequestDispatcher("pages/edit.jsp").forward(request, response);

                    } catch (NumberFormatException e){

                        session.setAttribute("message", "Неверное объявление данных");
                        response.sendRedirect("/");

                    }
                    break;

                }

                case "client": {

                    try {

                        Long idGuest = Long.parseLong(request.getParameter("id"));
                        Guest guest = guestRepository.findGuest(idGuest);

                        if (guest == null) throw new NumberFormatException();

                        request.setAttribute("entity", guest);
                        request.getRequestDispatcher("pages/edit.jsp").forward(request, response);;

                    } catch (NumberFormatException e){

                        session.setAttribute("message", "Неверное объявление данных");
                        response.sendRedirect("/");

                    }
                    break;

                }

                case "booking": {

                    try {

                        Long idGuest = Long.parseLong(request.getParameter("id_client"));
                        Long idTable = Long.parseLong(request.getParameter("id_guest"));

                        Booking booking = bookingRepository.findBooking(idTable, idGuest);

                        if (booking == null) throw new NumberFormatException();

                        request.setAttribute("entity", booking);
                        request.getRequestDispatcher("pages/edit.jsp").forward(request, response);;

                    } catch (NumberFormatException e){

                        session.setAttribute("message", "Неверное объявление данных");
                        response.sendRedirect("/");

                    }
                    break;

                } default: {

                    session.setAttribute("message", "Ошибка доступа");
                    response.sendRedirect("/");

                }


            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        switch (request.getParameter("entity")){

            case "table": {

                try {



                if (request.getParameter("id") == null
                        || request.getParameter("id").length() > 10
                        || request.getParameter("place") == null
                        || request.getParameter("capacity") == null
                        || request.getParameter("capacity").length() > 3
                        || request.getParameter("comment") == null
                        || request.getParameter("comment").length() > 100) throw new NumberFormatException("Ошибка иннициализации");

                if (!request.getParameter("place").equals("lounge")
                        && !request.getParameter("place").equals("VIP-zone")
                        && !request.getParameter("place").equals("veranda")) throw new NumberFormatException("Ошибка иннициализации");


                    Long idTable = Long.parseLong(request.getParameter("id"));
                    Table table = tableRepository.findTable(idTable);


                    if (table != null){

                        table.setCapacity(Integer.parseInt(request.getParameter("capacity")));
                        table.setDescription(request.getParameter("comment"));
                        table.setPlacement(request.getParameter("place"));

                        tableRepository.updateTable(table);

                        session.setAttribute("message", "Столик обновлен");
                        response.sendRedirect("/");

                    } else {

                        throw new NumberFormatException();

                    }

                } catch (NumberFormatException e){

                    session.setAttribute("message", "Ошибка доступа к данным");
                    response.sendRedirect("/");

                }

                break;

            }

            case "client": {

                System.out.println(request.getParameter("first_name"));
                System.out.println( request.getParameter("last_name"));
                System.out.println( request.getParameter("comment_guest"));
                System.out.println(request.getParameter("phone_number"));
                System.out.println(request.getParameter("id"));

                try {

                    if (request.getParameter("first_name") == null
                            || request.getParameter("first_name").length() > 15
                            || request.getParameter("last_name") == null
                            || request.getParameter("last_name").length() > 15
                            || request.getParameter("comment_guest") == null
                            || request.getParameter("comment_guest").length() > 100) throw new IllegalArgumentException();

                    Long.parseLong(request.getParameter("phone_number").substring(1));
                    Long idGuest = Long.parseLong(request.getParameter("id"));
                    Guest guest = guestRepository.findGuest(idGuest);

                    if (guest != null) {

                        guest.setFirstName(request.getParameter("first_name"));
                        guest.setLastName(request.getParameter("last_name"));
                        guest.setComment(request.getParameter("comment_guest"));
                        guest.setPhoneNumber(request.getParameter("phone_number"));

                        guestRepository.updateGuest(guest);

                        session.setAttribute("message", "Клиент обновлен");
                        response.sendRedirect("/");

                    } else throw new NumberFormatException();


                } catch (NumberFormatException e) {

                    session.setAttribute("message", "Ошибка доступа к данным");
                    response.sendRedirect("/");

                }

                break;

            }


            case "booking": {

                break;

            } default: {

                session.setAttribute("message", "Ошибка доступа");
                response.sendRedirect("/");

            }

        }


    }
}
