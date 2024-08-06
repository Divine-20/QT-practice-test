package rw.ac.rca.qt.blog.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.qt.blog.dtos.requests.CreateAdminDTO;
import rw.ac.rca.qt.blog.dtos.requests.CreateUserDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdateUserDTO;
import rw.ac.rca.qt.blog.exceptions.*;
import rw.ac.rca.qt.blog.services.RoleService;
import rw.ac.rca.qt.blog.services.UserService;
import rw.ac.rca.qt.blog.enumerations.EUserRole;
import rw.ac.rca.qt.blog.models.Role;
import rw.ac.rca.qt.blog.models.User;
import rw.ac.rca.qt.blog.repositories.IUserRepository;
import rw.ac.rca.qt.blog.security.UserPrincipal;
import rw.ac.rca.qt.blog.services.serviceImpl.RoleServiceImpl;
import rw.ac.rca.qt.blog.utils.HashUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Use final with RequiredArgsConstructor for automatic injection
    private final IUserRepository userRepository;
    private final RoleService roleService;

    @Value("${adminKey}")
    private String adminKey;

    @Value("${invitation.valid.days}")
    private int validityDays;

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User getUserById(UUID uuid) {
        try {
            return userRepository.findById(uuid).orElseThrow(() -> {
                throw new NotFoundException("The Resource was not found");
            });
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return userRepository.findUserByEmail(email).orElseThrow(() -> {
                throw new NotFoundException("The Resource was not found");
            });
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public User createUser(CreateUserDTO createUserDTO) {
        try {
            User user = userRepository.findUserByEmail(createUserDTO.getEmail()).orElseThrow(() -> new NotFoundException("The user with the given email was not found"));
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

        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User createAdmin(CreateAdminDTO createAdminDTO) {
        try {
            Optional<User> optionalUser = userRepository.findUserByEmailOrPhoneNumber(createAdminDTO.getEmail(), createAdminDTO.getPhoneNumber());
            if (optionalUser.isEmpty()) {
                if (createAdminDTO.getRegistrationCode().equals("Admin@123")) {
                    try {
                        Role role = roleService.getRoleByName(EUserRole.ADMIN);
                        createAdminDTO.setPassword(HashUtil.hashPassword(createAdminDTO.getPassword()));
                        User user = new User(
                                createAdminDTO.getUsername(),
                                createAdminDTO.getPhoneNumber(),
                                createAdminDTO.getEmail(),
                                createAdminDTO.getGender(),
                                createAdminDTO.getPassword()
                        );
                        user.setLastName(createAdminDTO.getLastName());
                        user.setFirstName(createAdminDTO.getFirstName());
                        user.setNationalId(createAdminDTO.getNationalId());
                        user.setDateOfBirth(createAdminDTO.getDateOfBirth());
                        Set<Role> roles = new HashSet<Role>();
                        roles.add(role);
                        user.setRoles(roles);
                        userRepository.save(user);
                        return user;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new InternalServerErrorException(e.getMessage());
                    }
                } else {
                    throw new BadRequestAlertException("Unauthorized to perform this action");
                }
            } else {
                throw new BadRequestAlertException("The User with the provided email or phone Already Exists");
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public User updateUser(UUID userId, UpdateUserDTO updateUserDTO) {
        try {
            Optional<User> optionalUser = userRepository.findUserByEmailOrPhoneNumber(updateUserDTO.getEmail(), updateUserDTO.getPhoneNumber());
            if (optionalUser.isEmpty()) {
                User user = userRepository.findById(userId).orElseThrow(() -> {
                    throw new NotFoundException("The Resource was not found");
                });
                try {
                    user.setEmail(updateUserDTO.getEmail());
                    user.setPhoneNumber(updateUserDTO.getPhoneNumber());
                    user.setUsername(updateUserDTO.getUsername());
                    return user;
                } catch (Exception e) {
                    throw new InternalServerErrorException(e.getMessage());
                }
            } else {
                throw new BadRequestAlertException("The email or password is already taken");
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User deleteUser(UUID userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> {
                throw new NotFoundException("The Resource was not found");
            });
            userRepository.deleteById(userId);
            return user;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User getLoggedInUser() {
        try {
            UserPrincipal userSecurityDetails;
            // Retrieve the currently authenticated user from the SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                userSecurityDetails = (UserPrincipal) authentication.getPrincipal();
                return this.userRepository.findUserByEmail(userSecurityDetails.getUsername()).orElseThrow(() -> new UnAuthorizedException("You are not authorized! please login"));
            } else {
                throw new UnAuthorizedException("You are not authorized! please login");
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}
