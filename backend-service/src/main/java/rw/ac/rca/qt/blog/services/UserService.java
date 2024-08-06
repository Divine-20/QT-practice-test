package rw.ac.rca.qt.blog.services;

import rw.ac.rca.qt.blog.dtos.requests.CreateAdminDTO;
import rw.ac.rca.qt.blog.dtos.requests.CreateUserDTO;
import rw.ac.rca.qt.blog.dtos.requests.InviteUserDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdateUserDTO;
import rw.ac.rca.qt.blog.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(UUID uuid);
    public User getUserByEmail(String email);
    public User createUser(CreateUserDTO createUserDTO);
    public User createAdmin(CreateAdminDTO createAdminDTO);
    public User updateUser(UUID userId , UpdateUserDTO updateUserDTO);
    public User deleteUser(UUID userId);
    // inviting the user
    public User getLoggedInUser();
}
