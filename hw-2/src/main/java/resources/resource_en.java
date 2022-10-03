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
            {"homePage", "Home page"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }

}
