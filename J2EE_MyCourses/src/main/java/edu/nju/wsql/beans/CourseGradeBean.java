package edu.nju.wsql.beans;

import edu.nju.wsql.model.CourseGrade;
import edu.nju.wsql.model.HomeworkRequest;

public class CourseGradeBean {
    private String name;
    private String location;
    private String remark;
    private String open;

    public CourseGradeBean() {
    }

    public CourseGradeBean(CourseGrade grade, boolean student) {
        boolean isOpen = grade.isOpen();
        String location = grade.getLocation();
        String[] split = location.split("\\.");
        String postfix = split[split.length - 1];
        HomeworkRequest homeworkRequest = grade.getHomeworkRequest();
        String name = homeworkRequest == null ? "考试" : homeworkRequest.getTitle();
        if (! student || isOpen) {
            setName(name + "成绩." + postfix);
            setLocation("/grade/" + grade.getPublishCourse().getId() + "/" + location);
        }
        else
            setName(name + "成绩（不公开）");
        setRemark(grade.getRemark());
        setOpen(isOpen ? "是" : "否");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
