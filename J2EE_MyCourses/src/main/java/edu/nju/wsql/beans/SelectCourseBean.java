package edu.nju.wsql.beans;

import edu.nju.wsql.model.SelectCourse;
import edu.nju.wsql.model.User;

import java.time.format.DateTimeFormatter;

public class SelectCourseBean {
    private String studentName;
    private String portrait;
    private String email;
    private String selectTime;

    public SelectCourseBean() {
    }

    public SelectCourseBean(SelectCourse selectCourse) {
        User student = selectCourse.getStudent();
        setStudentName(student.getName());
        setPortrait("/img/portrait/" + student.getPortrait());
        setEmail(student.getEmail());
        setSelectTime(selectCourse.getSelectTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }
}
