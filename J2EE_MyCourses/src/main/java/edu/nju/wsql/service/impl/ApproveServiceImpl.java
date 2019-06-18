package edu.nju.wsql.service.impl;

import edu.nju.wsql.beans.CourseBean;
import edu.nju.wsql.beans.CreateApprovalListBean;
import edu.nju.wsql.beans.PublishApprovalListBean;
import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.dao.CourseDao;
import edu.nju.wsql.dao.PublishCourseDao;
import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.service.ApproveService;
import edu.nju.wsql.service.utils.TermUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApproveServiceImpl implements ApproveService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private PublishCourseDao publishCourseDao;

    @Override
    @Transactional
    public CreateApprovalListBean getCreateApprovalInfo() {
        List<CourseBean> courseBeans = new ArrayList<>();
        for (Course course: courseDao.findAllSubmitCourses()) {
            CourseBean bean = new CourseBean();
            bean.setId(course.getId());
            bean.setName(course.getName());
            bean.setCreator(course.getCreator().getName());
            courseBeans.add(bean);
        }
        return new CreateApprovalListBean(courseBeans);
    }

    @Override
    @Transactional
    public CourseBean approveCourse(long id) {
        courseDao.approveCourse(id);
        Course course = courseDao.findOne(id);
        return wrapSimpleCourseInfo(course);
    }

    private CourseBean wrapSimpleCourseInfo(Course course) {
        CourseBean bean = new CourseBean();
        if (course != null) {
            bean.setName(course.getName());
            bean.setCreator(course.getCreator().getName());
        }
        return bean;
    }

    @Override
    @Transactional
    public CourseBean rejectCourse(long id, String realPath) {
        courseDao.rejectCourse(id);
        Course course = courseDao.findOne(id);
        course.deleteCoursewares(realPath);
        return wrapSimpleCourseInfo(course);
    }

    @Override
    @Transactional
    public PublishApprovalListBean getPublishApprovalInfo() {
        List<TermCourseInfoBean> beans = new ArrayList<>();
        for (PublishCourse publishCourse: publishCourseDao.findAllSubmitPublishCourses()) {
            TermCourseInfoBean bean = wrapSimpleTermCourseInfo(publishCourse);
            bean.setId(publishCourse.getId());
            beans.add(bean);
        }
        return new PublishApprovalListBean(beans);
    }

    private TermCourseInfoBean wrapSimpleTermCourseInfo(PublishCourse publishCourse) {
        return TermUtil.wrapSimpleTermCourseInfo(publishCourse);
    }

    @Override
    @Transactional
    public TermCourseInfoBean approvePublishCourse(long id) {
        publishCourseDao.approvePublishCourse(id);
        PublishCourse publishCourse = publishCourseDao.findOne(id);
        return wrapSimpleTermCourseInfo(publishCourse);
    }

    @Override
    @Transactional
    public TermCourseInfoBean rejectPublishCourse(long id) {
        publishCourseDao.rejectPublishCourse(id);
        PublishCourse publishCourse = publishCourseDao.findOne(id);
        return wrapSimpleTermCourseInfo(publishCourse);
    }
}
