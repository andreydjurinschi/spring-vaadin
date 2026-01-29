package org.cedacri.batch.vaadintutorial.views.admin.post_contract.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import org.cedacri.batch.vaadintutorial.core.models.entity.Post;

public class PostDialogComponent extends Div {
    private final TextField title = new TextField("Title");
    private final TextArea content = new TextArea("Content");
    private final Binder<Post> binder = new Binder<>(Post.class);

    public PostDialogComponent(boolean readonly){

        title.setWidthFull();
        content.setWidthFull();
        content.setMinHeight("160px");
        content.setMaxHeight("60vh");

        if (readonly) {
            readableDialog();
        } else {
            editableDialog();
        }
        add(title, content);
    }

    private void readableDialog(){

        title.setReadOnly(true);
        content.setReadOnly(true);

        binder.forField(title).bind(Post::getTitle, Post::setTitle);
        binder.forField(content).bind(Post::getContent, Post::setContent);
    }

    private void editableDialog(){

        title.setReadOnly(false);
        content.setReadOnly(false);

        binder.forField(title)
                .asRequired("title is required")
                .bind(Post::getTitle, Post::setTitle);

        binder.forField(content)
                .asRequired("content is required")
                .bind(Post::getContent, Post::setContent);
    }

    public void setPost(Post post){
        binder.readBean(post);
    }

    public boolean write(Post post){
        return binder.writeBeanIfValid(post);
    }
}
