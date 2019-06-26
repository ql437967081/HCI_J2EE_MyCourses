package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gy
 * @date 2019/6/26 10:31
 */

@Controller
@RequestMapping("/vue/teacher/terminfo")
public class VueTeacherTermInfo {
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public TermCourseInfoBean getTermCourse(long id, HttpServletRequest request) {
        System.out.println("进来了");
        TermCourseInfoBean bean = courseService.getTermCourseInfo(id);
        System.out.println("成功");
        switch (bean.getStatus()) {
            case SUBMIT:
                request.setAttribute("info", new InfoBean(
                        "发布课程",
                        "课程发布中",
                        "课程" + bean.getName() + "的发布请求已提交MyCourses主管审批，请耐心等待！",
                        "/teacher/publish_course"));
            case REJECTED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程发布",
                        "课程" + bean.getName() + " " + bean.getTerm() + "的发布未通过MyCourses主管审批！",
                        "/teacher/publish_course"));
                courseService.readRejectedPublishCourse(id);
            case REJECTED_READ:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程发布",
                        "课程未发布或发布未通过MyCourses主管审批！",
                        "/teacher"));
        }
        request.setAttribute("term_course_active", true);
        request.setAttribute("term_course_id", id);
        request.setAttribute("info", bean);
        return bean;
    }
}
