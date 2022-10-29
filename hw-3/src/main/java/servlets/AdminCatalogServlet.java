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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet(value = "/")
public class AdminCatalogServlet extends HttpServlet {

    private static  final ConnectionDataSource dataSource = new ConnectionDataSource();

    private static final TableRepository tableRepository = new TableRepositoryPostgres(dataSource);
    private static final BookingRepository bookingRepository  = new BookingRepositoryPostgres(dataSource);

    private static final GuestRepository guestRepository = new GuestRepositoryPostgres(dataSource);


    private final Set<Table> tableSet = new TreeSet<>(Comparator.comparing(Table::getNumber));
    private final Set<Guest> guestSet = new TreeSet<>(Comparator.comparing(Guest::getId));

    private final List<Booking> bookingSet = new ArrayList<>();

    private final static SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm");
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        tableSet.clear();
        guestSet.clear();
        bookingSet.clear();

        tableSet.addAll(tableRepository.findAllTable());
        guestSet.addAll(guestRepository.findAllGuest());

        if (session.getAttribute("bookingSet") == null) {

            bookingSet.addAll(bookingRepository.findAllBooking());

        } else {

            bookingSet.addAll((List<Booking>) session.getAttribute("bookingSet"));
            session.removeAttribute("bookingSet");

        }

        if (session.getAttribute("message") != null){

            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");

        }

        bookingSet.sort(Comparator.comparing(Booking::getTable));


        request.setAttribute("tableSet", tableSet);
        request.setAttribute("guestSet", guestSet);
        request.setAttribute("bookingSet", bookingSet);
        request.setAttribute("timeFormat", simpleHourFormat);


        request.getRequestDispatcher("pages/catalog.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long table = null, guest = null;
        Date date = null;

        if (request.getParameter("table") != null
                && !request.getParameter("table").equals("all")) {
            try {
                table = Long.parseLong(request.getParameter("table"));
            } catch(NumberFormatException e){
                table = null;
            }
        }

        if (request.getParameter("guest") != null
                && !request.getParameter("guest").equals("all")) {
            try {
                guest = Long.parseLong(request.getParameter("guest"));
            } catch(NumberFormatException e){
                guest = null;
            }
        }

        if (request.getParameter("date") != null
                && !request.getParameter("date").equals("")) {

            try {
                date = simpleDateFormat.parse(request.getParameter("date"));
            } catch (ParseException e) {
                date = null;
            }

        }

        session.setAttribute("bookingSet", bookingRepository.findByFilter(table, guest, date));

        response.sendRedirect("/");

    }


    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
