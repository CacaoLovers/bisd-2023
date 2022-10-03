package mappers;

import jakarta.servlet.http.HttpSession;
import models.chat.ChatRoom;

import java.util.function.Function;

public class ChatMessageToHtml {

    private static final String FLOAT_STYLE_OWN = "right";
    private static final String FLOAT_STYLE_FOREIGN = "left";

    private static final String BACKGROUND_STYLE_OWN = "#f5d7d7";
    private static final String BACKGROUND_STYLE_FOREIGN = "white";


    private static final Function<ChatRoom.Message, String> serverMessage = (message) -> {

        return "<div class=\"message-server\">"
                + "<p id = \"text-server\">" + message.getMessage() + "</p>"
                + "</div>";

    };

    private static final Function<UserMessageMapping, String> userMessage = (mapping) -> {
        return "<div class=\"message-block\">"
                + "<div class=\"message-block\">"
                + "<div class=\"message-holder\" style=\"float: " + mapping.floatStyle + "; \">"
                + "<p id = \"author\">" + mapping.message.getID() + "</p>"
                + "<div class = \"message\" style = \" background-color: " + mapping.backgroundStyle + "; \">"
                + "<p id = \"text\">" + mapping.message.getMessage() + "</p>"
                + "</div>"
                + "<p class = \"message-date\"> " + mapping.message.getDate() + " </p>"
                + "</div>"
                + "</div>"
                + "</div>";
    };


    public static String outputMessagesForParticipant(HttpSession session){

        String output = "";

        ChatRoom chatRoom = (ChatRoom) session.getAttribute("chatRoom");

        for (ChatRoom.Message message : chatRoom.getMessageSet()) {

            if (message.getID() == "server") {

                output += serverMessage.apply(message);


            } else {
                output += userMessage.apply(new UserMessageMapping(message, session));
            }

        }
        return output;
    }

    public static String outputMessagesForShow (ChatRoom chatRoom) {

        String output = "";

        for (ChatRoom.Message message : chatRoom.getMessageSet()) {

            if (message.getID() == "server") {

                output += serverMessage.apply(message);


            } else {
                output += userMessage.apply(new UserMessageMapping(message, null));
            }

        }

        return output;
    }

    static class UserMessageMapping{

        private String floatStyle = FLOAT_STYLE_OWN;
        private String backgroundStyle = BACKGROUND_STYLE_OWN;
        ChatRoom.Message message;

        public UserMessageMapping(ChatRoom.Message message, HttpSession session){

            if (session != null && message.getID().equals(session.getAttribute("name"))) {

                floatStyle = FLOAT_STYLE_OWN;
                backgroundStyle = BACKGROUND_STYLE_OWN;

            } else {

                floatStyle = FLOAT_STYLE_FOREIGN;
                backgroundStyle = BACKGROUND_STYLE_FOREIGN;

            }

            this.message = message;
        }

    }


}
