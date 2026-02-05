package org.cedacri.batch.vaadintutorial.views.admin.post_contract;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.core.models.service.post.PostService;
import org.cedacri.batch.vaadintutorial.views.admin.post_contract.components.PostDialogComponent;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

import java.util.List;
import java.util.Objects;

@Route(value = "posts", layout = MainView.class)
public class PostView extends Div implements PostViewContract {

    private final FlexLayout postCards = new FlexLayout();
    private final PostPresenter postPresenter;
    private final Button createButton;

    private final Dialog createDialog;
    private final Dialog infoDialog;
    private final Dialog updateDialog;
    private final Dialog deleteDialog;

    public PostView(PostService postService) {
        this.postPresenter = new PostPresenter(postService, this);

        createDialog = new Dialog();
        infoDialog = new Dialog();
        updateDialog = new Dialog();
        deleteDialog = new Dialog();
        createButton = new Button("Create", e -> createPost());
        add(createButton);

        configureLayout();
        postPresenter.onInit();
    }

    private void configureLayout() {
        setSizeFull();

        postCards.setWidthFull();

        postCards.getStyle().set("display", "flex");
        postCards.getStyle().set("flex-direction", "column");
        postCards.getStyle().set("gap", "1rem");

        postCards.getStyle().set("align-items", "stretch");
        postCards.getStyle().remove("justify-content");

        add(postCards);
    }


    private boolean canEditOrDelete(Post post) {
        if (AuthService.getCurrentUser() == null || post.getUserCreator() == null) {
            return false;
        }

        if (AuthService.getCurrentRole() == Role.ADMIN) {
            return true;
        }

        return Objects.equals(AuthService.getCurrentUser().getId(), post.getUserCreator().getId());
    }

    @Override
    public void showPosts(List<Post> posts) {
        postCards.removeAll();

        posts.forEach(post -> {
            VerticalLayout card = new VerticalLayout();
            card.setPadding(true);
            card.setSpacing(true);
            card.setWidth("100%");
            card.getStyle()
                    .set("border", "2px solid #e0e0e0")
                    .set("border-radius", "8px")
                    .set("padding", "1rem")
                    .set("box-shadow", "0 1px 4px rgba(0,0,0,0.06)")
                    .set("max-width", "800px")
                    .set("margin", "0 auto");

            card.add(new H3(post.getTitle()));

            String author = post.getUserCreator() != null ? post.getUserCreator().getFullName() : "Unknown";
            String role = (post.getUserCreator() != null && post.getUserCreator().getRole() != null)
                    ? post.getUserCreator().getRole().name()
                    : "Unknown";
            card.add(new H5("Created by: " + author + " (" + role + ")"));

            card.add(new Paragraph(post.getContent()));

            HorizontalLayout actions = new HorizontalLayout();
            actions.setAlignItems(FlexComponent.Alignment.CENTER);
            actions.setWidthFull();

            actions.add(new H5("Likes: " + post.getLikes()));

            Button infoBtn = new Button("Read more", e -> showPost(post));

            if (canEditOrDelete(post)) {
                Button editBtn = new Button("Edit", e -> postPresenter.onUpdatePostRequest(post.getId(), post));
                Button deleteBtn = new Button("Delete", e -> postPresenter.onDeletePostRequest(post.getId()));
                actions.add(editBtn, deleteBtn);
            }

            if (AuthService.isAuthenticated()) {
                boolean likedByMe = post.getLikedBy()
                        .contains(AuthService.getCurrentUser());

                Button likeBtn = new Button(likedByMe ? "Unlike" : "Like");
                likeBtn.addClickListener(e ->
                        postPresenter.onToggleLike(post.getId())
                );
                actions.add(likeBtn);
            }

            actions.add(infoBtn);
            card.add(actions);

            postCards.add(card);
        });
    }


    @Override
    public void showPost(Post post) {
        infoDialog.removeAll();

        infoDialog.setHeaderTitle(post.getTitle());
        infoDialog.setResizable(true);
        infoDialog.setWidth("800px");
        infoDialog.setMaxHeight("80vh");

        Button close = new Button("Close", e -> infoDialog.close());
        infoDialog.getFooter().removeAll();
        infoDialog.getFooter().add(close);

        PostDialogComponent postDialogComponent = new PostDialogComponent(true);
        postDialogComponent.setPost(post);
        postDialogComponent.getStyle().set("overflow", "auto");

        infoDialog.add(postDialogComponent);
        infoDialog.open();

    }

    @Override
    public void updatePostsTableAfterChange(List<Post> posts) {
        showPosts(posts);
    }

    @Override
    public void createPost() {
        createDialog.removeAll();

        Post post = new Post();
        PostDialogComponent postDialogComponent = new PostDialogComponent(false);
        postDialogComponent.setPost(post);

        Button create = new Button("Create", e -> {
            if (postDialogComponent.write(post)) {
                post.setLikes(0);
                post.setUserCreator(AuthService.getCurrentUser());
                postPresenter.onCreatePost(post);
                createDialog.close();
            }
        });

        Button cancel = new Button("Close", e -> createDialog.close());

        createDialog.setHeaderTitle("Create Post");
        createDialog.add(postDialogComponent);
        createDialog.getFooter().add(create, cancel);
        createDialog.setResizable(true);
        createDialog.setWidth("800px");

        createDialog.open();
    }

    @Override
    public void updatePost(Long id, Post post) {
        updateDialog.removeAll();

        PostDialogComponent postDialogComponent = new PostDialogComponent(false);
        postDialogComponent.setPost(post);

        Button save = new Button("Save", e -> {
            if (postDialogComponent.write(post)) {
                post.setId(id);
                post.setUserCreator(AuthService.getCurrentUser());
                postPresenter.onUpdatePostSave(id, post);
                updateDialog.close();
            } else {
                Notification.show("Please fill all required fields", 3000, Notification.Position.MIDDLE);
            }
        });

        Button exit = new Button("Exit", e -> {
            updateDialog.close();
        });

        updateDialog.setHeaderTitle("Update Post");
        updateDialog.setResizable(true);
        updateDialog.add(postDialogComponent);
        updateDialog.add(save, exit);
        updateDialog.setResizable(true);
        updateDialog.open();
    }

    @Override
    public void deletePost(Long id, Post post) {
        deleteDialog.removeAll();

        Button exit = new Button("Exit", e -> {
            deleteDialog.close();
        });
        PostDialogComponent postDialogComponent = new PostDialogComponent(true);
        postDialogComponent.setPost(post);
        Button confirm = new Button("Confirm", e -> {
            postPresenter.onDeletePostExecuted(id);
            deleteDialog.close();
        });

        deleteDialog.setHeaderTitle("Delete Post");
        deleteDialog.add(postDialogComponent, exit, confirm);
        deleteDialog.setResizable(true);
        deleteDialog.open();

    }

    @Override
    public void onLikePost(Long postId) {
        Notification.show("Post liked", 3000, Notification.Position.MIDDLE);
    }

    @Override
    public void onLikePostRemove(Long postId) {
        Notification.show("Like removed", 3000, Notification.Position.MIDDLE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void navigateHome() {

    }
}
