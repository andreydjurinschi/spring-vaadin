package org.cedacri.batch.vaadintutorial.core.models.entity;

import com.vaadin.copilot.shaded.commons.lang3.RandomStringUtils;
import com.vaadin.frontendtools.internal.commons.codec.digest.DigestUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// todo: additional entity logic and relationships
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String passwordSalt;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    // relations

    @OneToMany(mappedBy = "userCreator")
    private List<Post> postList;

    @ManyToMany(mappedBy = "likedBy")
    private Set<Post> likedPosts = new HashSet<>();

    public User() {}

    public User(String fullName, String email, String login, String rawPassword, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.login = login;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha256Hex(rawPassword + passwordSalt);
        this.role = role;
    }

    @PrePersist
    private void onCreate() {
        if (passwordSalt == null) {
            passwordSalt = RandomStringUtils.random(32);
        }
        if (passwordHash == null) {
            throw new IllegalStateException("Password hash must be set before persisting");
        }
    }

    public boolean checkPassword(String rawPassword) {
        return DigestUtils.sha256Hex(rawPassword + passwordSalt)
                .equals(passwordHash);
    }

    public void setPassword(String rawPassword) {
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha256Hex(rawPassword + passwordSalt);
    }

    public Role getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

