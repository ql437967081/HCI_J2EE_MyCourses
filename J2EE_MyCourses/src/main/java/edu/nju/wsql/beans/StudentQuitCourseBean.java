package edu.nju.wsql.beans;

import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.SelectCourse;

import java.time.format.DateTimeFormatter;

public class StudentQuitCourseBean {
    private long id;
    private int year;
    private String season;
    private String name;
    private String teacher;
    private String selectTime;
    private String quitTime;

    public StudentQuitCourseBean() {
    }

    public StudentQuitCourseBean(SelectCourse selectCourse) {
        PublishCourse publishCourse = selectCourse.getCourseClass().getPublishCourse();
        setId(publishCourse.getId());
        setYear(publishCourse.getYear());
        setSeason(publishCourse.getSeason().toChinese());
        Course course = publishCourse.getCourse();
        setName(course.getName());
        setTeacher(course.getCreator().getName());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        setSelectTime(selectCourse.getSelectTime().format(dateTimeFormatter));
        setQuitTime(selectCourse.getQuitTime().format(dateTimeFormatter));
    }

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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }

    public String getTerm() {
        return year + "年" + season + "学期";
    }
}
