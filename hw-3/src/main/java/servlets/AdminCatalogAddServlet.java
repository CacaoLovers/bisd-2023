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

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/add")

public class AdminCatalogAddServlet extends HttpServlet {

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

                request.setAttribute("entity", "guest");
                request.setAttribute("bookingAddNumber", session.getAttribute("bookingAddNumber"));
                request.getRequestDispatcher("pages/add.jsp").forward(request, response);

            } else {

                session.setAttribute("message", "Ошибка доступа");
                response.sendRedirect("/");

            }
        } else {

            request.setAttribute("entity", request.getParameter("entity"));
            request.getRequestDispatcher("pages/add.jsp").forward(request, response);

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

                    System.out.println("gfd");
                    Integer idTable =  Integer.parseInt(request.getParameter("id"));
                    Integer capacity = Integer.parseInt(request.getParameter("capacity"));

                    if (tableRepository.findTable(Long.valueOf(idTable)) != null) throw new NumberFormatException("Такой столик уже существует");

                    Table table = Table.builder()
                            .number(idTable)
                            .capacity(capacity)
                            .description(request.getParameter("comment"))
                            .placement(request.getParameter("place"))
                            .build();


                    tableRepository.addTable(table);



                } catch (NumberFormatException e){

                    if (e.getMessage().equals("") || e.getMessage() == null) session.setAttribute("message", "Неверный формат ввода");
                    else session.setAttribute("message", e.getMessage());
                    response.sendRedirect("/");

                }

                session.setAttribute("message", "Столик добавлен");
                response.sendRedirect("/");

                break;

            }

            case "guest": {

                try  {

                    if (request.getParameter("first_name") == null
                            || request.getParameter("first_name").length() > 15
                            || request.getParameter("last_name") == null
                            || request.getParameter("last_name").length() > 15
                            || request.getParameter("comment_guest") == null
                            || request.getParameter("comment_guest").length() > 100) throw new IllegalArgumentException();


                    if (request.getParameter("phone_number") != null) {
                        Long.parseLong(request.getParameter("phone_number").substring(1));
                        if (request.getParameter("update") != null
                                && request.getParameter("phone_number").equals(request.getParameter("update"))) {

                            Guest guest = guestRepository.findByPhone(request.getParameter("phone_number"));

                            System.out.println(guest);

                            if (guest != null) {



                                guest.setFirstName(request.getParameter("first_name"));
                                guest.setLastName(request.getParameter("last_name"));
                                guest.setComment(request.getParameter("comment_guest"));
                                guestRepository.updateGuest(guest);

                            }
                        } else {

                            Guest guest = Guest.builder()
                                    .firstName(request.getParameter("first_name"))
                                    .lastName(request.getParameter("last_name"))
                                    .phoneNumber(request.getParameter("phone_number"))
                                    .comment(request.getParameter("comment_guest"))
                                    .build();

                            guestRepository.addGuest(guest);

                        }
                        session.setAttribute("message", "Клиент добавлен");
                        response.sendRedirect("/");
                    }

                } catch (IllegalArgumentException e){

                    session.setAttribute("message", "Ошибка инницализации");
                    response.sendRedirect("/");

                }

                break;
            }

            case "booking": {


                Integer idTable = null;
                Integer idGuest = null;
                Date date = null;
                Integer duration = null;
                Integer numberPerson = null;

                try {

                   if (request.getParameter("id_table") == null) throw new NumberFormatException();
                   idTable = Integer.parseInt(request.getParameter("id_table"));
                   if (tableRepository.findTable(Long.valueOf(idTable)) == null) throw new SQLException();


                } catch (NumberFormatException | SQLException e){

                    session.setAttribute("message", "Такого столика не существует");
                    response.sendRedirect("/");

                }


                try {

                    if (request.getParameter("date") == null && request.getParameter("time") == null) throw new SQLException();

                    date = simpleDateFormat.parse(request.getParameter("date") + " " + correctTime(request.getParameter("time")));


                } catch (ParseException | SQLException e){

                    session.setAttribute("message", "Неверный формат ввода даты и времени");
                    response.sendRedirect("/");

                }

                try {


                    numberPerson = Integer.parseInt(request.getParameter("number_person"));

                }  catch (NumberFormatException e){

                    session.setAttribute("message", "Неверный формат ввода количества человек");
                    response.sendRedirect("/");

                }

                try {

                    duration = Integer.parseInt(request.getParameter("duration"));

                }  catch (NumberFormatException e){

                    session.setAttribute("message", "Неверный формат ввода продолжительности");
                    response.sendRedirect("/");

                }


                try {

                    String phoneNumber = request.getParameter("phone_number");

                    if (phoneNumber == null) throw new NumberFormatException();
                    checkPhoneNumber(phoneNumber);
                    Guest guest = guestRepository.findByPhone(phoneNumber);



                    Booking booking = Booking.builder()
                            .table(idTable)
                            .duration(duration)
                            .numberPerson(numberPerson)
                            .date(date)
                            .build();

                    if (guest == null) {

                        Guest guestBooking = Guest.builder()
                                .comment("booking")
                                .firstName("booking")
                                .lastName("booking")
                                .phoneNumber(phoneNumber)
                                .build();

                        guestRepository.addGuest(guestBooking);

                        booking.setGuest(0);
                        booking.setGuest(guestRepository.findByPhone(phoneNumber).getId());
                        booking.setPhoneNumber(phoneNumber);
                        bookingRepository.addBooking(booking);
                        session.setAttribute("bookingAddNumber", phoneNumber);
                        response.sendRedirect("/add");

                    } else {

                        idGuest = Integer.valueOf(guest.getId());
                        booking.setGuest(idGuest);
                        booking.setPhoneNumber(guest.getPhoneNumber());
                        bookingRepository.addBooking(booking);
                        session.setAttribute("message", "Бронь добавлена");
                        response.sendRedirect("/");

                    }


                } catch (NumberFormatException e){

                    session.setAttribute("message", "Неверный формат ввода телефона");
                    response.sendRedirect("/");

                }



                break;

            }

        }

    }

    private void checkPhoneNumber(String number) throws NumberFormatException {

        if (number.length() > 11 ) {
            throw new NumberFormatException();
        }

        Long.parseLong(number.substring(1));

    }

    private String correctTime(String time) throws SQLException{


        if (time.length() != 5) throw new SQLException();

        return time;

    }

}
