package repositories;

import models.chat.ChatRoom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChatRoomMap implements ChatRoomRepository{

    private static final Map<String, ChatRoom> chatRooms = new HashMap<>();

    @Override
    public ChatRoom findChat(String id) {

        return chatRooms.get(id);

    }

    @Override
    public boolean addChat(String id) {

        if(!chatRooms.containsKey(id)){

            chatRooms.put(id, new ChatRoom(id));
            return true;

        } else{

            return false;

        }
    }

    @Override
    public boolean removeChat(String id) {

        if(!chatRooms.containsKey(id)){

            return false;

        } else{

            chatRooms.remove(id);
            return true;

        }
    }

    @Override
    public String generateId() {
        String id = "";

        int[] symbol = Arrays.stream(new int[4])
                .map(x -> (int) (x + 97 + Math.random() * 25 + 1)).toArray();

        for(int i: symbol) {  id += (char)i; }

        if (chatRooms.containsKey(id)) {

            return generateId();

        } else{

            return id;

        }
    }


}
