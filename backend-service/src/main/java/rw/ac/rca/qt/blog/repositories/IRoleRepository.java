package rw.ac.rca.qt.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.qt.blog.enumerations.EUserRole;
import rw.ac.rca.qt.blog.models.Role;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByRoleName(EUserRole roleName);
}
