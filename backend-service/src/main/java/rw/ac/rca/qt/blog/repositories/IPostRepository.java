package rw.ac.rca.qt.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.qt.blog.models.Post;

import java.util.UUID;

@Repository
public interface IPostRepository extends JpaRepository<Post, UUID> {
}
