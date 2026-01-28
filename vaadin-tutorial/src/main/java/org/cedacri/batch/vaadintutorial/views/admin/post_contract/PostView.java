package org.cedacri.batch.vaadintutorial.views.admin.post_contract;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Param;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.catalina.User;
import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.core.models.service.post.PostService;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

import java.util.List;

@Route(value = "posts", layout = MainView.class)
public class PostView extends Div implements PostViewContract{

    FlexLayout postCards = new FlexLayout();
    private final PostPresenter postPresenter;
    private final PostService postService;

    private Button like;

    public PostView(PostService postService) {
        this.postService = postService;
        this.postPresenter = new PostPresenter(postService, this);

        configureLayout();
        postPresenter.onInit();

    }

    private void configureLayout() {
        postCards.setWidthFull();
        postCards.getStyle().set("flex-wrap", "wrap");
        postCards.getStyle().set("gap", "1rem");
        add(postCards);
    }

    @Override
    public void showPosts(List<Post> posts) {
        postCards.removeAll();

        posts.forEach(post -> {
            VerticalLayout layout = new VerticalLayout();
            layout.setWidth("400px");
            layout.getStyle()
                    .set("border", "1px solid #ccc")
                    .set("padding", "1rem");
            layout.add(new H3(post.getTitle()));
            layout.add(new H5("Created by: " + post.getUserCreator().getRole().name()));
            layout.add(new Paragraph(post.getContent()));
            HorizontalLayout buttonAndLikeCountLayout = new HorizontalLayout();
            buttonAndLikeCountLayout.add(new H3("Likes:" + post.getLikes()));
            like = new Button("Like");
            buttonAndLikeCountLayout.add(like);
            if(AuthService.isAuthenticated()) {
                like.addClickListener(event -> {
                    System.out.println("Clicked like button");
                });
            }else {
                like.setDisableOnClick(true);
            }
            buttonAndLikeCountLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            layout.add(buttonAndLikeCountLayout);
            layout.add(new Paragraph(post.getUserCreator().getFullName()));
            postCards.add(layout);
        });
    }

    @Override
    public void showPost(Post post) {

    }

    @Override
    public void updatePostsTableAfterChange(List<Post> posts) {

    }

    @Override
    public void createPost() {

    }

    @Override
    public void updatePost(Long id, Post post) {

    }

    @Override
    public void deletePost(Long id, Post post) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void navigateHome() {

    }
}
