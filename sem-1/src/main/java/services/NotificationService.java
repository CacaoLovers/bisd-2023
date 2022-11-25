package services;


import dto.NotificationForm;
import models.Notification;

import java.util.List;

public interface NotificationService {

    boolean addNotification(Notification notification);
    boolean removeNotification(Notification notification);
    List<Notification> findNotificationByUser(Long id);

    NotificationForm formNotification(Notification notification);
    List<NotificationForm> formNotification(List<Notification> notifications);
}
