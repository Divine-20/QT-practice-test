package rw.ac.rca.qt.blog.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.qt.blog.dtos.requests.CreateUserDTO;
import rw.ac.rca.qt.blog.dtos.requests.ResetPasswordDTO;
import rw.ac.rca.qt.blog.dtos.requests.SignInDTO;
import rw.ac.rca.qt.blog.dtos.responses.ProfileResponseDTO;
import rw.ac.rca.qt.blog.enumerations.EStatus;
import rw.ac.rca.qt.blog.exceptions.BadRequestAlertException;
import rw.ac.rca.qt.blog.exceptions.CustomException;
import rw.ac.rca.qt.blog.exceptions.NotFoundException;
import rw.ac.rca.qt.blog.models.User;
import rw.ac.rca.qt.blog.repositories.IUserRepository;
import rw.ac.rca.qt.blog.security.JwtTokenProvider;
import rw.ac.rca.qt.blog.security.UserPrincipal;
import rw.ac.rca.qt.blog.services.AuthenticationService;
import rw.ac.rca.qt.blog.services.UserService;
import rw.ac.rca.qt.blog.utils.HashUtil;
import rw.ac.rca.qt.blog.utils.JWTAuthenticationResponse;
import rw.ac.rca.qt.blog.utils.UserUtils;
import rw.ac.rca.qt.blog.utils.Utility;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final IUserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JWTAuthenticationResponse login(SignInDTO signInDTO) {
        try {
        String jwt = null;
        UserPrincipal userPrincipal = null;
        User user = null;
        // Create a UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());

       // Set the authentication in the SecurityContext
            Authentication authentication = authenticationProvider.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = jwtTokenProvider.generateToken(authentication);
            userPrincipal = UserUtils.getLoggedInUser();
            user = userService.getUserById(userPrincipal.getId());
            return  new JWTAuthenticationResponse(jwt , user );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
    @Override
    public User register(CreateUserDTO createUserDTO) {
        try {
            User user = new User();
            Optional<User> optionalUser = userRepository.findUserByEmail(createUserDTO.getEmail());
            if (optionalUser.isPresent()){
                throw new BadRequestAlertException("Email is already in use!");
            } else {
                user.setFirstName(createUserDTO.getFirstName());
                user.setLastName(createUserDTO.getLastName());
                user.setNationalId(createUserDTO.getNationalId());
                user.setDateOfBirth(createUserDTO.getDateOfBirth());
                user.setGender(createUserDTO.getGender());
                user.setPhoneNumber(createUserDTO.getPhoneNumber());
                user.setEmail(createUserDTO.getEmail());
                user.setPassword(HashUtil.hashPassword(createUserDTO.getPassword()));
                userRepository.save(user);
                return user;
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
    @Override
    public ProfileResponseDTO getUserProfile()  {
        try {
            User user = this.userService.getLoggedInUser();
                return new ProfileResponseDTO(user , user.getRoles());
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
