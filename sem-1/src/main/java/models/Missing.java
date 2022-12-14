package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Missing {

    private Long id;
    private Long idOwner;
    private String name;
    private String description;
    private Date date;
    private String pathImage;
    private String status;
    private String side;
    private MissingPosition position;

}
