package edu.nju.wsql.beans;

import java.util.List;

public class CourseHomeworkListBean {
    private List<HomeworkBean> homeworkBeans;
    private List<CourseGradeBean> gradeBeans;

    public CourseHomeworkListBean() {
    }

    public CourseHomeworkListBean(List<HomeworkBean> homeworkBeans) {
        this.homeworkBeans = homeworkBeans;
    }

    public List<HomeworkBean> getHomeworkBeans() {
        return homeworkBeans;
    }

    public void setHomeworkBeans(List<HomeworkBean> homeworkBeans) {
        this.homeworkBeans = homeworkBeans;
    }

    public List<CourseGradeBean> getGradeBeans() {
        return gradeBeans;
    }

    public void setGradeBeans(List<CourseGradeBean> gradeBeans) {
        this.gradeBeans = gradeBeans;
    }
}
