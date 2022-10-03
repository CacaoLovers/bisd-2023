package listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.ArrayList;
import java.util.List;


@WebListener
public class UserRoomListener implements HttpSessionListener {

    private static final List<HttpSession> userList = new ArrayList<>();
    @Override
    public void sessionCreated(HttpSessionEvent se) {

        HttpSession session = se.getSession();

        session.setAttribute("currentRoom", null);
        session.setAttribute("action", "guest");

        userList.add(session);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();

        userList.remove(session);

    }

    public static List<HttpSession> getUserList(){

        return new ArrayList<> ( userList ) ;

    }
}
