package repositories;

import models.Booking;

import java.util.Date;
import java.util.List;

public interface BookingRepository {

    boolean addBooking(Booking booking);
    boolean removeBooking(Long idTable, Long idClient);
    boolean updateBooking(Booking booking);
    Booking findBooking(Long idTable, Long idClient);
    List<Booking> findAllBooking();
    List<Booking> findAllBookingByTable(Long idTable);

    List<Booking> findByFilter(Long idTable, Long idGuest, Date date);

}