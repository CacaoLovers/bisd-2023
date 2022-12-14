package dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissingForm {

    private Long id;
    private Long idOwner;
    private String name;
    private String description;
    private String date;
    private String pathImage;
    private String status;
    private String side;
    private Double posX;
    private Double posY;
    private String city;
    private String street;
    private String district;
    private String mail;
    private String phoneNumber;
    private String loginOwner;

}
