package edu.nju.wsql.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "select_course")
public class SelectCourse implements Serializable {
    private long id;
    private CourseClass courseClass;
    private User student;
    private LocalDateTime selectTime;
    private LocalDateTime quitTime;
    private Set<Grade> grades;
    private Set<HomeworkSubmit> submits;

    public SelectCourse() {
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
    public CourseClass getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    @ManyToOne
    @JoinColumn(name = "uid")
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    @Column(name = "select_time")
    public LocalDateTime getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(LocalDateTime selectTime) {
        this.selectTime = selectTime;
    }

    @Column(name = "quit_time")
    public LocalDateTime getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(LocalDateTime quitTime) {
        this.quitTime = quitTime;
    }

    @OneToMany(mappedBy = "selectCourse")
    @OrderBy(value = "id ASC")
    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    @OneToMany(mappedBy = "selectCourse")
    public Set<HomeworkSubmit> getSubmits() {
        return submits;
    }

    public void setSubmits(Set<HomeworkSubmit> submits) {
        this.submits = submits;
    }
}
