package edu.nju.wsql.beans;

import java.util.List;

public class TopicBean {
    private String title;
    private List<PostBean> posts;

    public TopicBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PostBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostBean> posts) {
        this.posts = posts;
    }
}
