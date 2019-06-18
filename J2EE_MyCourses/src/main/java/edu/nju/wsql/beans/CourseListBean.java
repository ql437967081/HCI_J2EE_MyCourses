package edu.nju.wsql.beans;

import java.util.HashMap;
import java.util.Map;

public class CourseListBean {
    private Map<Long, String> courses;

    public CourseListBean() {
    }

    public CourseListBean(HashMap<Long, String> courses) {
        setCourses(courses);
    }

    public Map<Long, String> getCourses() {
        return courses;
    }

    public void setCourses(Map<Long, String> courses) {
        this.courses = courses;
    }
}
