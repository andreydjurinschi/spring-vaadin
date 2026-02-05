package org.cedacri.batch.vaadintutorial.core.models.service.post;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> allPosts();
    Post getById(Long id);
    void save(Post post);
    void delete(Long id);
    void update(Long id, Post post);
    void toggleLike(Long postId, Long userId);

}
