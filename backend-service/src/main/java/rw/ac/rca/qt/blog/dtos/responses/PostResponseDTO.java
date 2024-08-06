package rw.ac.rca.qt.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.qt.blog.models.Post;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Post post;
}