package rw.ac.rca.qt.blog.services;

import rw.ac.rca.qt.blog.dtos.requests.CreatePostDTO;
import rw.ac.rca.qt.blog.dtos.requests.UpdatePostDTO;
import rw.ac.rca.qt.blog.models.Comment;
import rw.ac.rca.qt.blog.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    public Post createPost(CreatePostDTO createPostDTO);
    public Post getPostById(UUID id);
    public List<Post> getAllPosts();
    public Post deletePost(UUID id);

    public List<Comment> getAllCommentsOnPost(UUID id);
    public Post updatePost(UpdatePostDTO updatePostDTO, UUID id);


}
