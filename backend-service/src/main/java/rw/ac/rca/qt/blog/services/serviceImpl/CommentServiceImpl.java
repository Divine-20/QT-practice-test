package rw.ac.rca.qt.blog.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.rca.qt.blog.dtos.requests.CreateCommentDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.qt.blog.exceptions.CustomException;
import rw.ac.rca.qt.blog.exceptions.InternalServerErrorException;
import rw.ac.rca.qt.blog.exceptions.NotFoundException;
import rw.ac.rca.qt.blog.models.Comment;
import rw.ac.rca.qt.blog.models.Post;
import rw.ac.rca.qt.blog.models.User;
import rw.ac.rca.qt.blog.repositories.ICommentRepository;
import rw.ac.rca.qt.blog.repositories.IPostRepository;
import rw.ac.rca.qt.blog.repositories.IUserRepository;
import rw.ac.rca.qt.blog.services.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;

    @Override
    public Comment createComment(CreateCommentDTO createCommentDTO, UUID postId) {
    try {
        Optional<User> userOptional = userRepository.findUserByEmail(createCommentDTO.getAuthor());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Author not found");
        }
        User author = userOptional.get();

        // Fetch the post by postId
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            throw new RuntimeException("Post not found");
        }
        Post post = postOptional.get();

        // Create and save the Comment
        Comment comment = new Comment(
                createCommentDTO.getContent(),
                author,
                post
        );
        commentRepository.save(comment);

        return comment;

    } catch (Exception e) {
        e.printStackTrace();
        throw new InternalServerErrorException(e.getMessage());
    }
    }

    @Override
    public Comment getCommentById(UUID id) {
        try {
            return commentRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The comment was not found");
            });
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public List<Comment> getAllComments() {
        try {
            return commentRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public Comment deleteComment(UUID id) {
        try {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The Comment was not found");
            });
            commentRepository.deleteById(id);
            return comment;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public List<Comment> getAllCommentsOnPost(UUID id) {
        try {
            return commentRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public Comment updateComment(UpdateCommentDTO updateCommentDTO, UUID id) {
        try {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The comment was not found");
            });
            comment.setContent(updateCommentDTO.getContent());
            commentRepository.save(comment);
            return comment;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}
