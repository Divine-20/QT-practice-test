package rw.ac.rca.qt.blog.models;

import jakarta.persistence.*;
import lombok.*;
import rw.ac.rca.qt.blog.enumerations.EUserRole;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EUserRole roleName;

    public Role(EUserRole roleName) {
        this.roleName = roleName;
    }
}
