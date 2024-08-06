package rw.ac.rca.qt.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.qt.blog.dtos.requests.CreatePostDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdatePostDTO;
import rw.ac.rca.qt.blog.models.Comment;
import rw.ac.rca.qt.blog.models.Post;
import rw.ac.rca.qt.blog.services.PostService;
import rw.ac.rca.qt.blog.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestBody CreatePostDTO createPostDTO) {
        Post post = postService.createPost(createPostDTO);
        return ApiResponse.success(post).toResponseEntity();
    }

    @GetMapping("/all")
    public  ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ApiResponse.success(posts).toResponseEntity();
    }
    @GetMapping("/id/{postId}")
    public ResponseEntity<ApiResponse<Post>> getPostById(@PathVariable UUID postId) {
        Post post = postService.getPostById(postId);
        return ApiResponse.success(post).toResponseEntity();
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Post>> deletePost(@PathVariable UUID postId) {
        Post post = postService.deletePost(postId);
        return ApiResponse.success(post).toResponseEntity();
    }
    @GetMapping("/comments/{postId}")
    public ResponseEntity<ApiResponse<List<Comment>>> getAllCommentsOnPost(@PathVariable UUID postId) {
        List<Comment> comments = postService.getAllCommentsOnPost(postId);
        return ApiResponse.success(comments).toResponseEntity();
    }
    @PutMapping("/update/{postId}")
    public ResponseEntity<ApiResponse<Post>> updatePost(@RequestBody UpdatePostDTO updatePostDTO, @PathVariable UUID postId) {
        Post post = postService.updatePost(updatePostDTO, postId);
        return ApiResponse.success(post).toResponseEntity();
    }
}
