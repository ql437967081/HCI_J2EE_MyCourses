package edu.nju.wsql.controller.vue.student;

import edu.nju.wsql.beans.StudentCourseClassBean;
import edu.nju.wsql.beans.StudentTermCourseBean;
import edu.nju.wsql.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/vue/student/courses")
public class VueStudentCoursesController {
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping
    @ResponseBody
    public List<StudentTermCourseBean> getCourses(HttpServletRequest request, HttpSession session) {
        request.setAttribute("info", studentCourseService.getAllCourses((String) session.getAttribute("login")));
        return studentCourseService.getAllCourses((String) session.getAttribute("login")).getTermCourseList();
    }
}
