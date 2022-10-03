package resources;

import java.util.ListResourceBundle;

public class resource_ru extends ListResourceBundle {

    public static final Object[][] contents =  {

            {"createRoom" , "Создать комнату"},
            {"joinRoom", "Войти в комнату"},
            {"enterCode", "Введите код комнаты"},
            {"join", "Войти"},
            {"room", "Комната"},
            {"send", "Отправить"},
            {"homePage", "Домашняя страница"}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
