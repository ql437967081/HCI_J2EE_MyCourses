package edu.nju.wsql.controller.vue.student;

import edu.nju.wsql.beans.StudentCourseClassBean;
import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/vue/student/select_course")
public class VueStudentSelectCourseController {
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    private TermCourseInfoBean getSelectCourseInfo(@RequestParam long publishCourseId) {
        return courseService.getTermCourseInfo(publishCourseId);
    }

    @PostMapping
    @ResponseBody
    private long selectCourse(HttpSession session, HttpServletRequest request) {
        String student = (String) session.getAttribute("login");
        long publishCourseId = Long.parseLong(request.getParameter("publishCourseId"));
        return studentCourseService.selectCourse(student, publishCourseId);
    }

    @PostMapping("/quit")
    @ResponseBody
    public StudentCourseClassBean quitCourse(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        long selectCourseId = Long.parseLong(request.getParameter("selectCourseId"));
        String student = (String) session.getAttribute("login");
        if (!studentCourseService.isTheCourseISelected(selectCourseId, student)) {
            response.setStatus(402);
            return null;
        }
        return studentCourseService.quitCourse(selectCourseId);
    }
}
