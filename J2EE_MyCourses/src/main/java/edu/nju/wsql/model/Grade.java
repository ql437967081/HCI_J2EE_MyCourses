package edu.nju.wsql.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "grade")
public class Grade implements Serializable {
    private long id;
    private CourseGrade courseGrade;
    private SelectCourse selectCourse;
    private double score;

    public Grade() {
    }

    public Grade(CourseGrade courseGrade, SelectCourse selectCourse, double score) {
        this.courseGrade = courseGrade;
        this.selectCourse = selectCourse;
        this.score = score;
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
    public CourseGrade getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(CourseGrade courseGrade) {
        this.courseGrade = courseGrade;
    }

    @ManyToOne
    @JoinColumn(name = "sid")
    public SelectCourse getSelectCourse() {
        return selectCourse;
    }

    public void setSelectCourse(SelectCourse selectCourse) {
        this.selectCourse = selectCourse;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
