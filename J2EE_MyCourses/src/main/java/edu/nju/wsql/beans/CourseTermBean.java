package edu.nju.wsql.beans;

import edu.nju.wsql.model.PublishCourse;

public class CourseTermBean {
    private long id;
    private int year;
    private String season;

    public CourseTermBean() {
    }

    public CourseTermBean(PublishCourse publishCourse) {
        setId(publishCourse.getId());
        setYear(publishCourse.getYear());
        setSeason(publishCourse.getSeason().toChinese());
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

    public String getTerm() {
        return year + "年" + season + "学期";
    }
}
