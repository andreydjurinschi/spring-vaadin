package org.cedacri.batch.vaadintutorial.views.admin.post_contract.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.cedacri.batch.vaadintutorial.core.models.entity.Post;


public class PostDialogComponent extends Div {
    private final TextField title = new TextField("Title");
    private final TextField content = new TextField("content");
    private final Binder<Post> binder = new Binder<>(Post.class);

    public PostDialogComponent(){
        binder.forField(title)
                .asRequired("title is required")
                .bind(Post::getTitle, Post::setTitle);

        binder.forField(content)
                .asRequired("content is required")
                .bind(Post::getContent, Post::setContent);

        add(title, content);
    }

    public void setPost(Post post){
        binder.readBean(post);
    }

    public boolean write(Post post){
        return binder.writeBeanIfValid(post);
    }
}
