package edu.nju.wsql.model;

import edu.nju.wsql.model.enums.ApprovalStatus;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course implements Serializable {
    private long id;
    private String name;
    private User creator;
    private Set<Courseware> coursewares;
    private ApprovalStatus status;
    private Set<Topic> forum; // 论坛
    private Set<PublishCourse> publishCourses;

    public Course() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "uid")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @OneToMany(mappedBy = "course")
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @OrderBy(value = "id ASC")
    public Set<Courseware> getCoursewares() {
        return coursewares;
    }

    public void setCoursewares(Set<Courseware> coursewares) {
        this.coursewares = coursewares;
    }

    @Enumerated(value = EnumType.STRING)
    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "course")
    @OrderBy(value = "id DESC")
    public Set<Topic> getForum() {
        return forum;
    }

    public void setForum(Set<Topic> forum) {
        this.forum = forum;
    }

    @OneToMany(mappedBy = "course")
    @OrderBy(value = "id DESC")
    public Set<PublishCourse> getPublishCourses() {
        return publishCourses;
    }

    public void setPublishCourses(Set<PublishCourse> publishCourses) {
        this.publishCourses = publishCourses;
    }

    public void deleteCoursewares(String realPath) {
        File parent = new File(new File(realPath, creator.getId()), name);
        for (Courseware courseware: coursewares) {
            File file = new File(parent, courseware.getLocation());
            file.delete();
        }
    }
}
