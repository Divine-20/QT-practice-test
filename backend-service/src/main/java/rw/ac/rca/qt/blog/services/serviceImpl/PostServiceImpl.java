package rw.ac.rca.qt.blog.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.rca.qt.blog.dtos.requests.CreatePostDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdatePostDTO;
import rw.ac.rca.qt.blog.exceptions.CustomException;
import rw.ac.rca.qt.blog.exceptions.InternalServerErrorException;
import rw.ac.rca.qt.blog.exceptions.NotFoundException;
import rw.ac.rca.qt.blog.models.Comment;
import rw.ac.rca.qt.blog.models.Post;
import rw.ac.rca.qt.blog.models.User;
import rw.ac.rca.qt.blog.repositories.IPostRepository;
import rw.ac.rca.qt.blog.repositories.IUserRepository;
import rw.ac.rca.qt.blog.services.PostService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    @Override
    public Post createPost(CreatePostDTO createPostDTO) {
        try {
            Optional<User> userOptional = userRepository.findUserByEmail(createPostDTO.getAuthor());
            if (!userOptional.isPresent()) {
                throw new RuntimeException("Author not found");
            }

            User author = userOptional.get();

            Post post = new Post(
                    createPostDTO.getTitle(),
                    createPostDTO.getContent(),
                    author
            );
            post.setTitle(createPostDTO.getTitle());
            post.setContent(createPostDTO.getContent());
            post.setAuthor(author);
            postRepository.save(post);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    ;

    @Override
    public Post getPostById(UUID id) {
        try {
            return postRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The Post was not found");
            });
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public Post deletePost(UUID id) {
        try {
            Post post = postRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The Post was not found");
            });
            postRepository.deleteById(id);
            return post;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public List<Comment> getAllCommentsOnPost(UUID id) {
        try {
            Post post = postRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The Post was not found");
            });
            return post.getComments();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public Post updatePost(UpdatePostDTO updatePostDTO, UUID id) {
        try {
            Post post = postRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("The Resource was not found");
            });
            post.setTitle(updatePostDTO.getTitle());
            post.setContent(updatePostDTO.getContent());
            return post;

        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

}
