package rw.ac.rca.qt.blog.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import rw.ac.rca.qt.blog.models.Post;
import rw.ac.rca.qt.blog.models.User;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreateCommentDTO {
    @NotBlank(message = "Comment content cannot be blank")
    private String content;
    @NotBlank(message = "Author cannot be blank")
    private String author;

}
