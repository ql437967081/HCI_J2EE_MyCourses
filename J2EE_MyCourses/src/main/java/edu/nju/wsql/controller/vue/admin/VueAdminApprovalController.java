package edu.nju.wsql.controller.vue.admin;

import edu.nju.wsql.beans.CourseBean;
import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.service.ApproveService;
import edu.nju.wsql.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/vue/admin/approval")
public class VueAdminApprovalController {
    @Autowired
    private ApproveService approveService;
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> getCourseApprovals() {
        Map<String, Object> data = new HashMap<>();
        data.put("createApproval", approveService.getCreateApprovalInfo());
        data.put("publishApproval", approveService.getPublishApprovalInfo());
        return data;
    }

    @GetMapping("/create_detail")
    @ResponseBody
    public CourseBean getApproveCreation(@RequestParam long courseId) {
        return courseService.getCourseInfo(courseId);
    }

    @PostMapping("/approve_creation")
    @ResponseBody
    public CourseBean approveCreation(HttpServletRequest request) {
        long courseId = Long.parseLong(request.getParameter("courseId"));
        return approveService.approveCourse(courseId);
    }

    @PostMapping("/reject_creation")
    @ResponseBody
    public CourseBean rejectCreation(HttpServletRequest request, HttpSession session) {
        long courseId = Long.parseLong(request.getParameter("courseId"));
        return approveService.rejectCourse(courseId, session.getServletContext().getRealPath("/courseware"));
    }

    @GetMapping("/publish_detail")
    @ResponseBody
    public TermCourseInfoBean getApprovePublish(@RequestParam long publishCourseId) {
        return courseService.getTermCourseInfo(publishCourseId);
    }

    @PostMapping("/approve_publish")
    @ResponseBody
    public TermCourseInfoBean approvePublish(HttpServletRequest request) {
        long publishCourseId = Long.parseLong(request.getParameter("publishCourseId"));
        return approveService.approvePublishCourse(publishCourseId);
    }

    @PostMapping("/reject_publish")
    @ResponseBody
    public TermCourseInfoBean rejectCreation(HttpServletRequest request) {
        long publishCourseId = Long.parseLong(request.getParameter("publishCourseId"));
        return approveService.rejectPublishCourse(publishCourseId);
    }
}
