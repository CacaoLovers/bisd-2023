package resources;

import java.util.ListResourceBundle;

public class resource_en extends ListResourceBundle {

    public static final Object[][] contents =  {

            {"createRoom" , "Create room"},
            {"joinRoom", "Join room"},
            {"enterCode", "Enter room code"},
            {"join", "Enter"},
            {"room", "Room"},
            {"send", "Submit"},
            {"homePage", "Home page"},
            {"chatNamePlaceholder", "Enter a nickname for the chat"},
            {"enterMessage", "Enter a message"},
            {"enterRoom", "The user %s entered the room"},
            {"greetingsUser", "Welcome a new chat participant %s"},
            {"outputRoom", "The user %s left the room"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }

}
