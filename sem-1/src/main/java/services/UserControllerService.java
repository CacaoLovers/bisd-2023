package services;

import dto.UserForm;
import models.User;
import repositories.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserControllerService implements UserService {

    private UsersRepository usersRepository;

    public UserControllerService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserForm from(User user) {
        return UserForm.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .city(user.getCity())
                .mail(user.getMail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public List<UserForm> from(List<User> users) {

        return users.stream().map(this::from).collect(Collectors.toList());

    }

    @Override
    public boolean addUser(User user) {

        return usersRepository.addUser(user);

    }

    @Override
    public boolean updateUser(User user) {

        return usersRepository.updateUser(user);
    }

    @Override
    public boolean removeUser(User user) {

        return usersRepository.removeUser(user);
    }

    @Override
    public Optional<User> findUser(Long id) {

        return usersRepository.findUser(id);

    }

    @Override
    public Optional<User> findUserByLogin(String login) {

        return usersRepository.findUserByLogin(login);
    }

    @Override
    public Optional<User> findUserBySession(String session) {

        return usersRepository.findUserBySession(session);

    }

    @Override
    public List<User> findAllUser() {

        return usersRepository.findAllUser();

    }

    @Override
    public Optional<User> findVolunteer(String login){
        return usersRepository.findVolunteer(login);
    }

    @Override
    public List<String> findVolunteerDistrict(Long id) {
        return usersRepository.findVolunteerDistrict(id);
    }

    @Override
    public boolean addVolunteerDistrict(Long id, String district){
        return usersRepository.addVolunteerDistrict(id, district);
    }

    public boolean removeVolunteerDistrict(Long id, String district){
        return usersRepository.removeVolunteerDistrict(id, district);
    }

    @Override
    public List<User> findVolunteerByDistrict(String district) {
        return usersRepository.findVolunteerByDistrict(district);
    }
}
