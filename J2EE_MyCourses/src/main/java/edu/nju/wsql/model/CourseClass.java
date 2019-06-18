package edu.nju.wsql.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "course_class")
public class CourseClass implements Serializable {
    private long id;
    private int classId;
    private int limitNum;
    private PublishCourse publishCourse;
    private Set<SelectCourse> selectCourses;

    public CourseClass() {
    }

    public CourseClass(int classId, int limitNum) {
        setClassId(classId);
        setLimitNum(limitNum);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "class_id")
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Column(name = "limit_num")
    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public PublishCourse getPublishCourse() {
        return publishCourse;
    }

    public void setPublishCourse(PublishCourse publishCourse) {
        this.publishCourse = publishCourse;
    }

    @OneToMany(mappedBy = "courseClass")
    public Set<SelectCourse> getSelectCourses() {
        return selectCourses;
    }

    public void setSelectCourses(Set<SelectCourse> selectCourses) {
        this.selectCourses = selectCourses;
    }

    public int calcSelectedNum() {
        int selectedNum = 0;
        for (SelectCourse selectCourse: selectCourses)
            if (selectCourse.getQuitTime() == null)
                selectedNum ++;
        return selectedNum;
    }

    public double calcSelectedRate() {
        return (double) calcSelectedNum() / limitNum;
    }

    public int calcStudentNumOfAType(String typeOfStudent) {
        int selectedNum = 0;
        for (SelectCourse selectCourse: selectCourses)
            if (selectCourse.getQuitTime() == null && selectCourse.getStudent().calcStudentType().equals(typeOfStudent))
                selectedNum ++;
        return selectedNum;
    }
}
