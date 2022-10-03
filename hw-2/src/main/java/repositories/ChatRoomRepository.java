package repositories;

import models.chat.ChatRoom;

public interface ChatRoomRepository{

    ChatRoom findChat(String id);

    boolean addChat(String id);

    boolean removeChat(String id);

    String generateId();

}
