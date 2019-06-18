package edu.nju.wsql.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "course_grade")
public class CourseGrade implements Serializable {
    private long id;
    private PublishCourse publishCourse;
    private HomeworkRequest homeworkRequest;
    private String remark;
    private boolean open;
    private String location;
    private Set<Grade> grades;

    public CourseGrade() {
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
    @JoinColumn(name = "pid")
    public PublishCourse getPublishCourse() {
        return publishCourse;
    }

    public void setPublishCourse(PublishCourse publishCourse) {
        this.publishCourse = publishCourse;
    }

    @ManyToOne
    @JoinColumn(name = "hid")
    public HomeworkRequest getHomeworkRequest() {
        return homeworkRequest;
    }

    public void setHomeworkRequest(HomeworkRequest homeworkRequest) {
        this.homeworkRequest = homeworkRequest;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @OneToMany(mappedBy = "courseGrade")
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }
}
