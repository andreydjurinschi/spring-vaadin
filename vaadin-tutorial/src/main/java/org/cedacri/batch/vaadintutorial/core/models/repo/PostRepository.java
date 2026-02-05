package org.cedacri.batch.vaadintutorial.core.models.repo;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
    SELECT DISTINCT p
    FROM Post p
    LEFT JOIN FETCH p.likedBy
    ORDER BY p.id DESC
""")
    List<Post> findAllWithLikes();

    @Query("""
    SELECT COUNT(p) > 0
    FROM Post p
    JOIN p.likedBy u
    WHERE p.id = :postId AND u.id = :userId
""")
    boolean isPostLikedByUser(Long postId, Long userId);


}
