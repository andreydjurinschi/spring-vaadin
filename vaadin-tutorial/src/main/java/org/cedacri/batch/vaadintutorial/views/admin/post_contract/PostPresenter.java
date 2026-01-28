package org.cedacri.batch.vaadintutorial.views.admin.post_contract;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.core.models.service.post.PostService;

import javax.naming.AuthenticationException;

public class PostPresenter {

    private final PostService postService;
    private final PostViewContract postContract;

    public PostPresenter(PostService postService, PostViewContract postContract) {
        this.postService = postService;
        this.postContract = postContract;
    }

    public void onInit(){
        postContract.showPosts(postService.allPosts());
        System.out.println("post size: " + postService.allPosts().size());
    }

    public void onShowPost(Long id){
        try{
            Post post = postService.getById(id);
            postContract.showPost(post);
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void onCreatePost(Post post){
        try{
            if(isUserCreatorOrIsAdmin()){
                postService.save(post);
                postContract.showPosts(postService.allPosts());
            }else{
                throw new AuthenticationException("method not allowed");
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void onUpdatePostRequest(Long id, Post post){
        try{
            if(isUserCreatorOrIsAdmin()){
                postContract.updatePost(id, post);
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void onUpdatePostSave(Long id, Post post){
        try{
            if(isUserCreatorOrIsAdmin()){
                postService.update(id, post);
                postContract.updatePostsTableAfterChange(postService.allPosts());
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void onDeletePost(Long id){
        try{
            if(isUserCreatorOrIsAdmin()){
                postService.delete(id);
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    private void showError(String message){
        postContract.showError(message);
    }

    private boolean isUserCreatorOrIsAdmin(){
        return AuthService.getCurrentRole() == Role.ADMIN || AuthService.getCurrentRole() == Role.CREATOR;
    }
}
