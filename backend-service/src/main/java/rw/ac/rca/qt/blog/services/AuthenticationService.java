package rw.ac.rca.qt.blog.services;


import rw.ac.rca.qt.blog.dtos.requests.CreateUserDTO;
import rw.ac.rca.qt.blog.dtos.requests.SignInDTO;
import rw.ac.rca.qt.blog.dtos.responses.ProfileResponseDTO;
import rw.ac.rca.qt.blog.models.User;
import rw.ac.rca.qt.blog.dtos.requests.ResetPasswordDTO;
import rw.ac.rca.qt.blog.utils.JWTAuthenticationResponse;

public interface AuthenticationService {
    public JWTAuthenticationResponse login (SignInDTO dto);
    // other methods
    public ProfileResponseDTO getUserProfile() ;
    public User register(CreateUserDTO createUserDTO);
}
