package services;

import dto.NotificationForm;
import models.Missing;
import models.Notification;
import models.User;
import repositories.MissingRepository;
import repositories.NotificationRepository;
import repositories.UsersRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationControllerService implements NotificationService {

    private final UserService userService;
    private final MissingService missingService;
    private final NotificationRepository notificationRepository;

    public NotificationControllerService(UserService userService, MissingService missingService, NotificationRepository notificationRepository) {
        this.userService = userService;
        this.missingService = missingService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationForm formNotification(Notification notification){
        return NotificationForm.builder()
                .idFrom(userService.from(userService.findUser(notification.getUserIdFrom()).get()))
                .idTo(userService.from(userService.findUser(notification.getUserIdTo()).get()))
                .missing(missingService.from(missingService.findMissingById(notification.getMissingId()).get()))
                .build();
    }

    @Override
    public List<NotificationForm> formNotification(List<Notification> notifications) {
        return notifications.stream().map(this::formNotification).collect(Collectors.toList());
    }

    @Override
    public boolean addNotification(Notification notification) {
        return notificationRepository.addNotification(notification);
    }

    @Override
    public boolean removeNotification(Notification notification) {
        return notificationRepository.removeNotification(notification);
    }

    @Override
    public List<Notification> findNotificationByUser(Long id) {
        return notificationRepository.findNotificationByUser(id);
    }
}
