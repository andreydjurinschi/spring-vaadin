package org.cedacri.batch.vaadintutorial.core.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String content;

    private Integer likes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_creator_id")
    private User userCreator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_user_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();

    public Post(String title, String content, Integer likes, User userCreator) {
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.userCreator = userCreator;
    }

    public boolean isLikedByCurrentUser() {
        return AuthService.getCurrentUser() != null
                && likedBy != null
                && likedBy.contains(AuthService.getCurrentUser());
    }


    public Post() {}

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikes()
    {
        return this.likedBy.size();
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }
}
