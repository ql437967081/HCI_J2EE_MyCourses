package edu.nju.wsql.model;

import edu.nju.wsql.model.enums.ApprovalStatus;
import edu.nju.wsql.model.enums.Season;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "publish_course")
public class PublishCourse implements Serializable {
    private long id;
    private int year;
    private Season season;
    private Course course;
    private Set<CourseClass> classes;
    private ApprovalStatus status;
    private Set<HomeworkRequest> homeworkRequests;
    private Set<CourseGrade> courseGrades;

    public PublishCourse() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Enumerated(EnumType.STRING)
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @ManyToOne
    @JoinColumn(name = "cid")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @OneToMany(mappedBy = "publishCourse")
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @OrderBy(value = "classId ASC")
    public Set<CourseClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<CourseClass> classes) {
        this.classes = classes;
    }

    @Enumerated(value = EnumType.STRING)
    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "publishCourse")
    @OrderBy(value = "ddl ASC")
    public Set<HomeworkRequest> getHomeworkRequests() {
        return homeworkRequests;
    }

    public void setHomeworkRequests(Set<HomeworkRequest> homeworkRequests) {
        this.homeworkRequests = homeworkRequests;
    }

    @OneToMany(mappedBy = "publishCourse")
    @OrderBy(value = "id ASC")
    public Set<CourseGrade> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseGrades(Set<CourseGrade> courseGrades) {
        this.courseGrades = courseGrades;
    }

    public int calcSelectedNum() {
        int sum = 0;
        for (CourseClass courseClass: classes)
            sum += courseClass.calcSelectedNum();
        return sum;
    }

    public String calcTerm() {
        return year + "年" + season.toChinese() + "学期";
    }

    public int calcLimit() {
        int sum = 0;
        for (CourseClass courseClass: classes)
            sum += courseClass.getLimitNum();
        return sum;
    }

    public int calcStudentNumOfOneType(String typeOfStudent) {
        int sum = 0;
        for (CourseClass courseClass: classes)
            sum += courseClass.calcStudentNumOfAType(typeOfStudent);
        return sum;
    }
}
