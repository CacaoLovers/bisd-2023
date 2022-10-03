package repositories;

import java.util.HashSet;
import java.util.Set;

public class UsersSet implements UsersRepository {

    private static final Set<String> userSet = new HashSet<>();

    @Override
    public boolean containsUser(String name) {

        return userSet.contains(name);

    }

    @Override
    public boolean addUser(String name) {

        return userSet.add(name);

    }

    @Override
    public boolean removeUser(String name) {

        return userSet.remove(name);

    }
}
