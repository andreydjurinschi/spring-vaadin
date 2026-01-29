package org.cedacri.batch.vaadintutorial.core.models.service.post;

import com.vaadin.copilot.shaded.commons.lang3.StringUtils;
import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.repo.PostRepository;
import org.springframework.data.domain.Sort;
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
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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

    @Override
    @Transactional
    public void delete(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    @Override
    @Transactional
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
    public void addLike(Long postId) {
        findPostById(postId);
        postRepository.updatePostAddLike(postId);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("asdasd"));
    }
}
