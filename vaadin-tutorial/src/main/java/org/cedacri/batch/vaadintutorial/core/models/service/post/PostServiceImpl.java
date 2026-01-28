package org.cedacri.batch.vaadintutorial.core.models.service.post;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.repo.PostRepository;
import org.springframework.stereotype.Service;

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
        return postRepository.findAll();
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    // todo: post update logic
    @Override
    public void update(Long id, Post post) {
        findPostById(id);
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
