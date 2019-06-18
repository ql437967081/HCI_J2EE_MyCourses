package edu.nju.wsql.beans;

import edu.nju.wsql.model.enums.Season;

import java.util.List;

public class PublishCourseBean {
    private int year;
    private String season;
    private List<CourseBean> courseList;

    public PublishCourseBean() {
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

    public List<CourseBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseBean> courseList) {
        this.courseList = courseList;
    }
}
