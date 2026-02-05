package org.cedacri.batch.vaadintutorial.core.models.service.post;

import com.vaadin.copilot.shaded.commons.lang3.StringUtils;
import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.repo.PostRepository;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> allPosts() {
        return postRepository.findAllWithLikes();
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    @Transactional
    @Override
    public void update(Long id, Post post) {
        Post postToUpdate = findPostById(id);
        if(StringUtils.isNotBlank(post.getTitle())) {
            System.out.println(post.getTitle());
            postToUpdate.setTitle(post.getTitle());
        }
        if(StringUtils.isNotBlank(post.getContent())) {
            System.out.println(post.getContent());
            postToUpdate.setContent(post.getContent());
        }
        postRepository.save(postToUpdate);
    }

    @Override
    @Transactional
    public void toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();

        if (postRepository.isPostLikedByUser(postId, userId)) {
            post.getLikedBy().remove(AuthService.getCurrentUser());
            post.setLikes(post.getLikes() - 1);
        } else {
            post.getLikedBy().add(AuthService.getCurrentUser());
            post.setLikes(post.getLikes() + 1);
        }
    }


    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("asdasd"));
    }
}
