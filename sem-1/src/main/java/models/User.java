package models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String login;
    private String mail;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String lastSession;
    private String role;

}
