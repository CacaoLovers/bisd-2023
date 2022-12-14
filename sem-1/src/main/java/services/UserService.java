package services;

import dto.UserForm;
import models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserForm from(User user);
    List<UserForm> from(List<User> user);
    boolean addUser (User user);
    boolean updateUser (User user);
    boolean removeUser (User user);
    Optional<User> findUser (Long id);
    Optional<User> findUserByLogin (String login);
    Optional<User> findUserBySession (String Session);
    List<User> findAllUser ();
    Optional<User> findVolunteer (String login);

    List<String> findVolunteerDistrict(Long id);

    boolean addVolunteerDistrict(Long id, String district);

    boolean removeVolunteerDistrict(Long id, String district);

    List<User> findVolunteerByDistrict(String district);

}
