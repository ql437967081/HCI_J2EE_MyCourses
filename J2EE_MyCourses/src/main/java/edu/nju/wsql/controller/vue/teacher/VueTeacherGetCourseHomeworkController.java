package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.beans.CourseHomeworkListBean;
import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author gy
 * @date 2019/6/25 21:46
 */
@Controller
@RequestMapping("/vue/teacher/coursehomework")
public class VueTeacherGetCourseHomeworkController {
    @Autowired
    private CourseService courseService;
    @GetMapping
    @ResponseBody
    public CourseHomeworkListBean receiveHomework(HttpServletRequest request, HttpSession session) {
        System.out.println("recieveHomework");
        long id = Long.parseLong(request.getParameter("id"));
        return courseService.getHomeworkList(id, false);
    }
}
