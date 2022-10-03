package models.chat;

import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.*;

public class ChatRoom {
    private String id;
    private Set<Message> messageSet = new LinkedHashSet<>();


    public ChatRoom(String id) {

        this.id = id;

    }

    public String getId() {

        return id;

    }


    public Set<Message> getMessageSet() {

        return messageSet;

    }

    private void addUserMessage(String authorId, String text, Date date) {

        messageSet.add(new Message(authorId, text, date));

    }

    public void addServerMessage(String text) {
        messageSet.add(new Message("server", text));
    }

    public void addMessage(String message, HttpSession session) {
        if (message != null
                && !message.equals("")
                && session.getAttribute("chatRoom") != null) {

            addUserMessage(session.getAttribute("name").toString(), message, new Date());

        }
    }

    public class Message{
        private String authorId;
        private String message;

        private String date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        public Message(String ID, String message) {

            this.authorId = ID;
            this.message = message;
        }

        public Message(String authorId, String message, Date date) {
            this.authorId = authorId;
            this.message = message;
            this.date = simpleDateFormat.format(date);
        }

        public String getID() {

            return authorId;

        }

        public String getMessage() {

            return message;

        }

        public String getDate() {

            return date;

        }
    }
}
