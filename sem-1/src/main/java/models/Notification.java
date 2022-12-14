package models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private Long userIdTo;
    private Long userIdFrom;
    private String type;
    private String status;
    private Long missingId;
}
