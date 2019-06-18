package edu.nju.wsql.beans;

import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.Courseware;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.enums.ApprovalStatus;

import java.util.ArrayList;
import java.util.List;

public class CourseBean {
    private long id;
    private String name;
    private String creator;
    private List<CoursewareBean> coursewares;
    private List<CourseTermBean> terms;
    private ApprovalStatus status;

    public CourseBean() {
    }

    public CourseBean(Course course) {
        status = course == null ? ApprovalStatus.REJECTED_READ : course.getStatus();
        if (status == ApprovalStatus.REJECTED_READ)
            return;
        setId(course.getId());
        setName(course.getName());
        setCreator(course.getCreator().getName());
        coursewares = new ArrayList<>();
        for (Courseware courseware: course.getCoursewares())
            coursewares.add(new CoursewareBean(courseware, course.getCreator().getId()));
        terms = new ArrayList<>();
        for (PublishCourse publishCourse: course.getPublishCourses()) {
            if (publishCourse.getStatus() != ApprovalStatus.REJECTED_READ)
                terms.add(new CourseTermBean(publishCourse));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<CoursewareBean> getCoursewares() {
        return coursewares;
    }

    public void setCoursewares(List<CoursewareBean> coursewares) {
        this.coursewares = coursewares;
    }

    public List<CourseTermBean> getTerms() {
        return terms;
    }

    public void setTerms(List<CourseTermBean> terms) {
        this.terms = terms;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }
}
