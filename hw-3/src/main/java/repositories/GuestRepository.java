package repositories;

import models.Guest;
import models.Table;

import java.util.List;

public interface GuestRepository {

    boolean addGuest(Guest guest);
    boolean removeGuest(Long id);
    boolean updateGuest(Guest guest);
    Guest findGuest(Long id);
    List<Guest> findAllGuest();

    Guest findByPhone(String phoneNumber) ;
}
