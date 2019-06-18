package edu.nju.wsql.beans;

import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.enums.ApprovalStatus;

import java.util.ArrayList;
import java.util.List;

public class TermCourseInfoBean {
    private long courseId;
    private String name;
    private long id;
    private int year;
    private String season;
    private String publisher;
    private List<CourseClassBean> classes;
    private ApprovalStatus status;
    private int numOfUndergraduate;
    private int numOfGraduate;
    private int numOfDoctor;
    private int limit;
    private int numOfHomework;
    private String selectTime;
    private int notSubmitHomeworkNum;

    public TermCourseInfoBean() {
    }

    public TermCourseInfoBean(PublishCourse publishCourse) {
        status = publishCourse == null ? ApprovalStatus.REJECTED_READ : publishCourse.getStatus();
        if (status == ApprovalStatus.REJECTED_READ)
            return;
        setCourse(publishCourse.getCourse());
        setId(publishCourse.getId());
        setYear(publishCourse.getYear());
        setSeason(publishCourse.getSeason().toChinese());
        classes = new ArrayList<>();
        for (CourseClass courseClass: publishCourse.getClasses())
            classes.add(new CourseClassBean(courseClass));
        setNums(publishCourse);
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<CourseClassBean> getClasses() {
        return classes;
    }

    public void setClasses(List<CourseClassBean> classes) {
        this.classes = classes;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public int getNumOfUndergraduate() {
        return numOfUndergraduate;
    }

    public void setNumOfUndergraduate(int numOfUndergraduate) {
        this.numOfUndergraduate = numOfUndergraduate;
    }

    public int getNumOfGraduate() {
        return numOfGraduate;
    }

    public void setNumOfGraduate(int numOfGraduate) {
        this.numOfGraduate = numOfGraduate;
    }

    public int getNumOfDoctor() {
        return numOfDoctor;
    }

    public void setNumOfDoctor(int numOfDoctor) {
        this.numOfDoctor = numOfDoctor;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getNumOfHomework() {
        return numOfHomework;
    }

    public void setNumOfHomework(int numOfHomework) {
        this.numOfHomework = numOfHomework;
    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

    public int getNotSubmitHomeworkNum() {
        return notSubmitHomeworkNum;
    }

    public void setNotSubmitHomeworkNum(int notSubmitHomeworkNum) {
        this.notSubmitHomeworkNum = notSubmitHomeworkNum;
    }

    public String getTerm() {
        return year + "年" + season + "学期";
    }

    public void setCourse(Course course) {
        setCourseId(course.getId());
        setName(course.getName());
        setPublisher(course.getCreator().getName());
    }

    public void setNums(PublishCourse publishCourse) {
        setLimit(publishCourse.calcLimit());
        setNumOfUndergraduate(publishCourse.calcStudentNumOfOneType("本科生"));
        setNumOfGraduate(publishCourse.calcStudentNumOfOneType("研究生"));
        setNumOfDoctor(publishCourse.calcStudentNumOfOneType("博士生"));
        setNumOfHomework(publishCourse.getHomeworkRequests().size());
    }
}
