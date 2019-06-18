package edu.nju.wsql.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "topic")
public class Topic implements Serializable {
    private long id;
    private Course course;
    private String title;
    private Post firstPost;
    private Post lastPost;
    private Set<Post> posts;

    public Topic() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "cid")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = "fpid")
    public Post getFirstPost() {
        return firstPost;
    }

    public void setFirstPost(Post firstPost) {
        this.firstPost = firstPost;
    }

    @OneToOne
    @JoinColumn(name = "lpid")
    public Post getLastPost() {
        return lastPost;
    }

    public void setLastPost(Post lastPost) {
        this.lastPost = lastPost;
    }

    @OneToMany(mappedBy = "topic")
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @OrderBy(value = "time ASC")
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
