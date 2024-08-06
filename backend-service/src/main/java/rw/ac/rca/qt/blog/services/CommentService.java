package rw.ac.rca.qt.blog.services;

import rw.ac.rca.qt.blog.dtos.requests.CreateCommentDTO;
import rw.ac.rca.qt.blog.dtos.requests.CreatePostDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.qt.blog.models.Comment;
import rw.ac.rca.qt.blog.models.Post;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    public Comment createComment(CreateCommentDTO createCommentDTO, UUID postId);
    public Comment getCommentById(UUID id);
    public List<Comment> getAllComments();
    public Comment deleteComment(UUID id);

    public List<Comment> getAllCommentsOnPost(UUID id);
    public Comment updateComment(UpdateCommentDTO updateCommentDTO, UUID id);
}
