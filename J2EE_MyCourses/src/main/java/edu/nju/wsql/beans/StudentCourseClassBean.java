package edu.nju.wsql.beans;

import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.SelectCourse;

public class StudentCourseClassBean {
    private String name;
    private long id;
    private int classId;
    private int year;
    private String season;
    private String teacher;

    public StudentCourseClassBean() {
    }

    public StudentCourseClassBean(SelectCourse selectCourse) {
        setId(selectCourse.getId());
        CourseClass courseClass = selectCourse.getCourseClass();
        setClassId(courseClass.getClassId());
        PublishCourse publishCourse = courseClass.getPublishCourse();
        setYear(publishCourse.getYear());
        setSeason(publishCourse.getSeason().toChinese());
        Course course = publishCourse.getCourse();
        setName(course.getName());
        setTeacher(course.getCreator().getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTerm() {
        return year + "年" + season + "学期";
    }
}
