package org.cedacri.batch.vaadintutorial.core.models.service.post;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostService {
    List<Post> allPosts();
    Post getById(Long id);
    void save(Post post);
    void delete(Long id);
    void update(Long id, Post post);
    void addLike(@Param("post_id") Long postId);
}
