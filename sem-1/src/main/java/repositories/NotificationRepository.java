package repositories;

import models.Notification;
import models.User;

import java.util.List;
import java.util.Map;

public interface NotificationRepository {
    boolean addNotification(Notification notification);
    boolean removeNotification(Notification notification);
    List<Notification> findNotificationByUser(Long id);

}
