package rw.ac.rca.qt.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity // Add this annotation to mark it as an entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Use AUTO for compatibility with UUID
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne // Specify relationship with User
    @JoinColumn(name = "author_id", nullable = false) // Define foreign key column
    private User author; // Change author to a User entity

    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE) // Ensure cascading delete
    private List<Comment> comments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt = new Date(); // Initialize updatedAt

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = new Date(); // Set creation date
        this.updatedAt = new Date(); // Set update date
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date(); // Automatically update the timestamp before updating
    }
}
