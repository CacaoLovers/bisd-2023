package repositories;

public interface UsersRepository {
    boolean containsUser(String name);
    boolean addUser(String name);
    boolean removeUser(String name);
}
