package rw.ac.rca.qt.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.qt.blog.dtos.requests.CreateCommentDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.qt.blog.models.Comment;
import rw.ac.rca.qt.blog.services.CommentService;
import rw.ac.rca.qt.blog.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
   @PostMapping("/create/{post_id}")
   public ResponseEntity<ApiResponse<Comment>> createComment(@RequestBody CreateCommentDTO createCommentDTO, @PathVariable UUID post_id) {
        Comment comment = commentService.createComment(createCommentDTO, post_id);
        return ApiResponse.success(comment).toResponseEntity();
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Comment>>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ApiResponse.success(comments).toResponseEntity();
    }
    @GetMapping("/id/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> getCommentById(@PathVariable UUID commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ApiResponse.success(comment).toResponseEntity();
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> deleteComment(@PathVariable UUID commentId) {
        Comment comment = commentService.deleteComment(commentId);
        return ApiResponse.success(comment).toResponseEntity();
    }
    @PutMapping("/update/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> updateComment(@RequestBody UpdateCommentDTO updateCommentDTO, @PathVariable UUID commentId) {
        Comment comment = commentService.updateComment(updateCommentDTO, commentId);
        return ApiResponse.success(comment).toResponseEntity();
    }
}
