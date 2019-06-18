package edu.nju.wsql.beans;

import java.util.List;

public class CreateApprovalListBean {
    private List<CourseBean> courseBeans;

    public CreateApprovalListBean() {
    }

    public CreateApprovalListBean(List<CourseBean> courseBeans) {
        this.courseBeans = courseBeans;
    }

    public List<CourseBean> getCourseBeans() {
        return courseBeans;
    }

    public void setCourseBeans(List<CourseBean> courseBeans) {
        this.courseBeans = courseBeans;
    }
}
