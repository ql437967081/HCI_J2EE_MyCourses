package edu.nju.wsql.service;

import edu.nju.wsql.beans.CourseBean;
import edu.nju.wsql.beans.CreateApprovalListBean;
import edu.nju.wsql.beans.PublishApprovalListBean;
import edu.nju.wsql.beans.TermCourseInfoBean;

public interface ApproveService {
    CreateApprovalListBean getCreateApprovalInfo();

    CourseBean approveCourse(long id);

    CourseBean rejectCourse(long id, String realPath);

    PublishApprovalListBean getPublishApprovalInfo();

    TermCourseInfoBean approvePublishCourse(long id);

    TermCourseInfoBean rejectPublishCourse(long id);
}
