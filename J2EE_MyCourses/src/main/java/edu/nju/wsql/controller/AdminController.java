package edu.nju.wsql.controller;

import edu.nju.wsql.beans.CourseBean;
import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.service.ApproveService;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ApproveService approveService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String get(HttpServletRequest request) {
        request.setAttribute("home_active", true);
        request.setAttribute("info", userService.getUserStatics());
        return "/admin/home.jsp";
    }

    @GetMapping("/course_create_approval")
    public String getCourseCreationApproval(HttpServletRequest request) {
        request.setAttribute("course_create_approval_active", true);
        request.setAttribute("info", approveService.getCreateApprovalInfo());
        return "/admin/create_approval.jsp";
    }

    @GetMapping("/approve_creation/{id}")
    public String getApproveCreation(@PathVariable("id") long id, HttpServletRequest request) {
        CourseBean bean = courseService.getCourseInfo(id);
        switch (bean.getStatus()) {
            case REJECTED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程",
                        "课程不存在或未通过MyCourses主管审批！",
                        "/admin/course_create_approval"));
                return "/info_page/info.jsp";
            case APPROVED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的审批",
                        "课程已通过MyCourses主管审批！",
                        "/admin/course_create_approval"));
                return "/info_page/info.jsp";
        }
        request.setAttribute("id", id);
        request.setAttribute("info", bean);
        return "/admin/approve_creation.jsp";
    }

    @PostMapping("/approve_creation/{id}")
    public String approveCreation(@PathVariable("id") long id, HttpSession session) {
        CourseBean bean = approveService.approveCourse(id);
        session.setAttribute("alert", bean.getCreator() + "创建的课程" + bean.getName() + "已被审核通过");
        return "redirect:/admin/course_create_approval";
    }

    @PostMapping("/reject_creation/{id}")
    public String rejectCreation(@PathVariable("id") long id, HttpSession session) {
        CourseBean bean = approveService.rejectCourse(id, session.getServletContext().getRealPath("/courseware"));
        session.setAttribute("alert", bean.getCreator() + "创建的课程" + bean.getName() + "已被拒绝通过");
        return "redirect:/admin/course_create_approval";
    }

    @GetMapping("/course_publish_approval")
    public String getCoursePublishApproval(HttpServletRequest request) {
        request.setAttribute("course_publish_approval_active", true);
        request.setAttribute("info", approveService.getPublishApprovalInfo());
        return "/admin/publish_approval.jsp";
    }

    @GetMapping("/approve_publish/{id}")
    public String getApprovePublish(@PathVariable("id") long id, HttpServletRequest request) {
        TermCourseInfoBean bean = courseService.getTermCourseInfo(id);
        switch (bean.getStatus()) {
            case REJECTED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程发布",
                        "课程未发布或发布未通过MyCourses主管审批！",
                        "/admin/course_publish_approval"));
                return "/info_page/info.jsp";
            case APPROVED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的审批",
                        "课程发布已通过MyCourses主管审批！",
                        "/admin/course_publish_approval"));
                return "/info_page/info.jsp";
        }
        request.setAttribute("id", id);
        request.setAttribute("info", bean);
        return "/admin/approve_publish.jsp";
    }

    @PostMapping("/approve_publish/{id}")
    public String approvePublish(@PathVariable("id") long id, HttpSession session) {
        TermCourseInfoBean bean = approveService.approvePublishCourse(id);
        session.setAttribute("alert", bean.getPublisher() + "发布的课程" + bean.getName() + " " + bean.getTerm() + "已被审核通过");
        return "redirect:/admin/course_publish_approval";
    }

    @PostMapping("/reject_publish/{id}")
    public String rejectPublish(@PathVariable("id") long id, HttpSession session) {
        TermCourseInfoBean bean = approveService.rejectPublishCourse(id);
        session.setAttribute("alert", bean.getPublisher() + "发布的课程" + bean.getName() + " " + bean.getTerm() + "已被拒绝通过");
        return "redirect:/admin/course_publish_approval";
    }

    @GetMapping("/teacher_info")
    public String getTeacherInfo(HttpServletRequest request) {
        request.setAttribute("teacher_info_active", true);
        request.setAttribute("info", courseService.getAllTeacherStatics());
        return "/admin/teacher_info.jsp";
    }
    @GetMapping("/student_info")
    public String getStudentInfo(HttpServletRequest request) {
        request.setAttribute("student_info_active", true);
        request.setAttribute("info", courseService.getStudentStatics());
        return "/admin/student_info.jsp";
    }
}
