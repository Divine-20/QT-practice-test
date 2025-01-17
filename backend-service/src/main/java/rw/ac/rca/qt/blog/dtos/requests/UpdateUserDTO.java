package rw.ac.rca.qt.blog.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private String username;
    private String phoneNumber;
    private String email;
    private String gender;
    private String registrationCode;
    private String password;
}
