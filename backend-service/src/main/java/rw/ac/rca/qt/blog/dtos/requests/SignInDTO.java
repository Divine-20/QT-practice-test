package rw.ac.rca.qt.blog.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDTO {

    @NotBlank (message = "Email is required")
    @Email (message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}


