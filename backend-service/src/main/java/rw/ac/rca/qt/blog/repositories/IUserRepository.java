package rw.ac.rca.qt.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.qt.blog.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmailOrPhoneNumber(String s, String s1);
    Optional<User> findUserByEmail(String s);
}
