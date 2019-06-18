package edu.nju.wsql.beans;

import edu.nju.wsql.model.Post;
import edu.nju.wsql.model.Topic;
import edu.nju.wsql.model.User;

import java.time.format.DateTimeFormatter;

public class TopicSimpleBean {
    private long id;
    private String title;
    private String firstPoster;
    private String firstPosterPortrait;
    private int num;
    private String lastPoster;
    private String lastPosterPortrait;
    private String lastTime;

    public TopicSimpleBean() {
    }

    public TopicSimpleBean(Topic topic) {
        setId(topic.getId());
        setTitle(topic.getTitle());
        User first = topic.getFirstPost().getPoster();
        setFirstPoster(first.getName());
        setFirstPosterPortrait("/img/portrait/" + first.getPortrait());
        setNum(topic.getPosts().size());
        Post lastPost = topic.getLastPost();
        User last = lastPost.getPoster();
        setLastPoster(last.getName());
        setLastPosterPortrait("/img/portrait/" + last.getPortrait());
        setLastTime(lastPost.getTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 E HH:mm")));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPoster() {
        return firstPoster;
    }

    public void setFirstPoster(String firstPoster) {
        this.firstPoster = firstPoster;
    }

    public String getFirstPosterPortrait() {
        return firstPosterPortrait;
    }

    public void setFirstPosterPortrait(String firstPosterPortrait) {
        this.firstPosterPortrait = firstPosterPortrait;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLastPoster() {
        return lastPoster;
    }

    public void setLastPoster(String lastPoster) {
        this.lastPoster = lastPoster;
    }

    public String getLastPosterPortrait() {
        return lastPosterPortrait;
    }

    public void setLastPosterPortrait(String lastPosterPortrait) {
        this.lastPosterPortrait = lastPosterPortrait;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
