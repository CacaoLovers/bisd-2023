package listeners;

import models.chat.ChatRoom;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

import java.util.Locale;
import java.util.ResourceBundle;

@WebListener
public class ChatRoomListener implements HttpSessionAttributeListener {


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
       HttpSession session = event.getSession();

       if (event.getName().equals("chatRoom")){

            if (session.getAttribute("action") != null
                    && session.getAttribute("action").toString() != "participant") {

                ChatRoom chatRoom = (ChatRoom) event.getValue();

                System.out.println( "Создана комната " + chatRoom.getId());

            }

       }

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();

        if (event.getName() == "currentRoom"){   // Обработка выхода пользователя из чата

            String nameUser = session.getAttribute("name").toString();
            ChatRoom chatRoom = (ChatRoom)session.getAttribute("chatRoom");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.resource", new Locale("en"));

            chatRoom.addServerMessage( String.format( resourceBundle.getString("outputRoom"), nameUser ) );

        }


    }
}
