package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissingPosition {

    private Double posX;
    private Double posY;
    private String city;
    private String street;
    private String district;

}
