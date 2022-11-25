package dto;

import lombok.Builder;
import lombok.Data;
import models.Missing;
import models.User;

@Data
@Builder
public class NotificationForm {
    private MissingForm missing;
    private String message;
    private UserForm idFrom;
    private UserForm idTo;
}
