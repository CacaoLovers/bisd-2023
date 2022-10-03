package resources;

import java.util.ListResourceBundle;

public class resource_tat extends ListResourceBundle {
    public static final Object[][] contents =  {

            {"createRoom" , "Бүлмә булдыру"},
            {"joinRoom", "Бүлмәгә керү"},
            {"enterCode", "Бүлмә кодын кертегез"},
            {"join", "Керү"},
            {"room", "Бүлмә"},
            {"send", "Җибәрү"},
            {"homePage", "Баш бит"},
            {"chatNamePlaceholder", "Чат өчен ник кертегез"},
            {"enterMessage", "Хәбәр языгыз"},
            {"enterRoom", "Кулланучы %s бүлмәгә керде"},
            {"greetingsUser", "Чатның яңа әгъзасын сәламлибез %s"},
            {"outputRoom", "Кулланучы %s бүлмәдән чыкты"}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
