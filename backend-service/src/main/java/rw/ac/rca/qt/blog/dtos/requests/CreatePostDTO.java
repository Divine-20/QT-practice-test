package rw.ac.rca.qt.blog.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import rw.ac.rca.qt.blog.models.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreatePostDTO {
    @NotBlank(message = "Post title cannot be blank")
    private String title;
    @NotBlank(message = "Post content cannot be blank")
    private String content;
    @NotBlank(message = "Post author cannot be blank")
    private String author;
}
