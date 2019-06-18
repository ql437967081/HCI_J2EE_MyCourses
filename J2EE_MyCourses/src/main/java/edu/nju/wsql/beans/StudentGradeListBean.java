package edu.nju.wsql.beans;

import java.util.List;

public class StudentGradeListBean {
    private List<StudentGradeBean> grades;

    public StudentGradeListBean() {
    }

    public StudentGradeListBean(List<StudentGradeBean> grades) {
        this.grades = grades;
    }

    public List<StudentGradeBean> getGrades() {
        return grades;
    }

    public void setGrades(List<StudentGradeBean> grades) {
        this.grades = grades;
    }
}
