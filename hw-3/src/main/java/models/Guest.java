package models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Guest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String comment;

}
