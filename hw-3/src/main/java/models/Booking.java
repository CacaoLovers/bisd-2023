package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Booking {

    private Integer guest;
    private Integer table;
    private String phoneNumber;
    private Date date;
    private Integer duration;
    private Integer numberPerson;


}
