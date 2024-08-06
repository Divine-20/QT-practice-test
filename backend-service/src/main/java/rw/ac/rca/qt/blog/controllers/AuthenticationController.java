package rw.ac.rca.qt.blog.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.qt.blog.dtos.requests.CreateUserDTO;
import rw.ac.rca.qt.blog.services.AuthenticationService;
import rw.ac.rca.qt.blog.dtos.requests.ResetPasswordDTO;
import rw.ac.rca.qt.blog.dtos.requests.SignInDTO;
import rw.ac.rca.qt.blog.dtos.responses.ProfileResponseDTO;
import rw.ac.rca.qt.blog.models.User;
import rw.ac.rca.qt.blog.utils.ApiResponse;
import rw.ac.rca.qt.blog.utils.JWTAuthenticationResponse;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping (path = "/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping (path = "/login")
    public ResponseEntity<ApiResponse<JWTAuthenticationResponse>> login(@Valid @RequestBody SignInDTO signInDTO) {
        JWTAuthenticationResponse jwtAuthenticationResponse = authenticationService.login(signInDTO);
        return ApiResponse.success(jwtAuthenticationResponse).toResponseEntity();
    }
    @PostMapping (path = "/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User registeredUser = authenticationService.register(createUserDTO);
        return ApiResponse.success(registeredUser).toResponseEntity();
    }
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getUserProfile() {
        ProfileResponseDTO profileResponseDTO = authenticationService.getUserProfile();
        return ApiResponse.success(profileResponseDTO).toResponseEntity();
    }

}
