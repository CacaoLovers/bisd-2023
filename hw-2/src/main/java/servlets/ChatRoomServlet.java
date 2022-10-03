package servlets;

import models.chat.ChatRoom;
import mappers.ChatMessageToHtml;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import repositories.ChatRoomMap;
import repositories.ChatRoomRepository;
import repositories.UsersRepository;
import repositories.UsersSet;

import java.io.IOException;


@WebServlet(name = "roomServlet", value = "/room")
public class ChatRoomServlet extends HttpServlet {

    ChatRoomRepository chatRoomRepository;
    UsersRepository usersRepository;

    private enum Status{
       GUEST , CREATOR, PARTICIPANT;

    }

    @Override
    public void init() throws ServletException {

        chatRoomRepository = new ChatRoomMap();
        usersRepository = new UsersSet();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            HttpSession session = request.getSession();

            switch (Status.valueOf(session.getAttribute("action").toString().toUpperCase())) {


                case GUEST: {     //  Joining user to a room

                    if (request.getParameter("id_room") != null && request.getParameter("id_room") != "") {

                        String idRoom = request.getParameter("id_room").trim();

                        ChatRoom chatRoom = chatRoomRepository.findChat(idRoom);

                        if (chatRoom == null) {

                            sendError("Комната " + idRoom + " не существует", session, response);

                        } else {

                            session.setAttribute("currentRoom", chatRoom.getId());
                            session.setAttribute("action", "participant");
                            session.setAttribute("chatRoom", chatRoom);

                            sendJoinRoom(session, chatRoom);

                            response.sendRedirect("/room");

                        }

                    } else{

                        response.sendRedirect("/");

                    }

                    break;

                }


                case PARTICIPANT: {   // Send message and load block of messagesSet

                    if (request.getParameter("text_message") != null) {

                        String message = request.getParameter("text_message");

                        if ( sendMessage(message, session, request, response) ) {

                            response.sendRedirect("/room");

                        }

                    } else {

                        checkSession(session, request, response);

                        request.setAttribute("chatBlock", ChatMessageToHtml.outputMessagesForParticipant(session));
                        request.setAttribute("placeholder", selectPlaceholderStatus(session, request));

                        request.getRequestDispatcher("pages/room.jsp").forward(request, response);

                    }

                    break;

                }
            }

        } catch (ServletException e) {

            throw new RuntimeException(e);

        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  // Creating a room

        HttpSession session = request.getSession();

        String roomId = chatRoomRepository.generateId();
        chatRoomRepository.addChat(roomId);
        session.setAttribute("chatRoom", chatRoomRepository.findChat(roomId));
        session.setAttribute("currentRoom", roomId);
        session.setAttribute("action", "participant");

        sendJoinRoom(session, chatRoomRepository.findChat(roomId));

        response.sendRedirect("/room");

    }

    private void checkSession ( HttpSession session, HttpServletRequest request, HttpServletResponse response) {

            Object idRoom = session.getAttribute("currentRoom");

            if (idRoom == null) {

                sendError("Сбой в работе с сессией", session, response);

            }

    }


    private static String selectPlaceholderStatus(HttpSession session, HttpServletRequest request){

        if ( session.getAttribute("name") == null ){

            return "Введите ник для чата";

        } else{

            return "Введите сообщение";

        }

    }

    private boolean sendMessage (String message, HttpSession session, HttpServletRequest request, HttpServletResponse response){

        ChatRoom chatRoom = (ChatRoom) session.getAttribute("chatRoom");


        if (session.getAttribute("name") == null) {

            if (!usersRepository.containsUser(message)) {

                session.setAttribute("name", message);
                chatRoom.addServerMessage("Приветствуем нового участника чата " + message);
                usersRepository.addUser(message);

            } else {

                    sendError("Пользователь с таким именем уже существует", session, response);
                    return false;

            }

        } else {

            chatRoom.addMessage(message, session);

        }

        return true;
    }

    private static void sendJoinRoom (HttpSession session, ChatRoom chatRoom){
        if (session.getAttribute("name") != null) {

            chatRoom.addServerMessage("Пользователь " + session.getAttribute("name").toString() + " вошел в комнату");

        }
    }

    private static void sendError(String message, HttpSession session, HttpServletResponse response){



        try {

            session.setAttribute("messageError", message);
            response.sendRedirect("/error");

        } catch ( IOException e) {

            throw new RuntimeException(e);

        }

    }

}