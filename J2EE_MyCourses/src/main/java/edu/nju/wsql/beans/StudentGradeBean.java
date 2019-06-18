package edu.nju.wsql.beans;

import edu.nju.wsql.model.Grade;

public class StudentGradeBean {
    private CourseGradeBean bean;
    private double score;

    public StudentGradeBean() {
    }

    public StudentGradeBean(Grade grade) {
        setBean(new CourseGradeBean(grade.getCourseGrade(), true));
        setScore(grade.getScore());
    }

    public CourseGradeBean getBean() {
        return bean;
    }

    public void setBean(CourseGradeBean bean) {
        this.bean = bean;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
