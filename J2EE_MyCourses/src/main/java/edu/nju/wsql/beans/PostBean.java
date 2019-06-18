package edu.nju.wsql.beans;

import edu.nju.wsql.model.Post;
import edu.nju.wsql.model.User;

import java.time.format.DateTimeFormatter;

public class PostBean {
    private String poster;
    private String posterPortrait;
    private String content;
    private String time;

    public PostBean() {
    }

    public PostBean(Post post) {
        User poster = post.getPoster();
        setPoster(poster.getName());
        setPosterPortrait("/img/portrait/" + poster.getPortrait());
        setContent(post.getContent());
        setTime(post.getTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 E HH:mm")));
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterPortrait() {
        return posterPortrait;
    }

    public void setPosterPortrait(String posterPortrait) {
        this.posterPortrait = posterPortrait;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
