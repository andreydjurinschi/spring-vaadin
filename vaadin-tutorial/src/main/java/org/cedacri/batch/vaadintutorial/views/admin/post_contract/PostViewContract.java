package org.cedacri.batch.vaadintutorial.views.admin.post_contract;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;

import java.util.List;

public interface PostViewContract {

    void showPosts(List<Post> posts);
    void showPost(Post post);

    void updatePostsTableAfterChange(List<Post> posts);

    void createPost();
    void updatePost(Long id, Post post);

    void deletePost(Long id, Post post);

    void onLikePost(Long postId);
    void onLikePostRemove(Long postId);

    void showError(String error);
    void navigateHome();


}
