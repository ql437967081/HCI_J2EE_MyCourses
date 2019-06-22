package edu.nju.wsql.controller.vue.student;

import edu.nju.wsql.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/vue/student/course_detail")
public class VueStudentCourseDetailController {
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> getCourseDetail(@RequestParam long selectCourseId, HttpServletResponse response, HttpSession session) {
        String student = (String) session.getAttribute("login");
        if (!studentCourseService.isTheCourseISelected(selectCourseId, student)) {
            response.setStatus(402);
            return null;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("course", studentCourseService.getMyCourse(selectCourseId));
        data.put("coursewares", studentCourseService.getCoursewaresBySelectCourseId(selectCourseId));
        data.put("homework_list", studentCourseService.getHomeworkListBySelectCourseId(selectCourseId));
        data.put("grade", studentCourseService.getAllGrades(selectCourseId));
        return data;
    }
}
