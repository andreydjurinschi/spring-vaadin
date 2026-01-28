package org.cedacri.batch.vaadintutorial.core.models.repo;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery = true, value = "update POSTS p set LIKES = p.LIKES+1 where p.ID = :post_id")
    void updatePostAddLike(@Param("post_id") Long id);


}
