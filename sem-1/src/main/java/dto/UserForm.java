package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserForm {

    private Long id;
    private String login;
    private String mail;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String city;

}
