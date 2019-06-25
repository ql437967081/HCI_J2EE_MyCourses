package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author gy
 * @date 2019/6/24 20:56
 */
@Controller
@RequestMapping("/vue/teacher/email")
public class VueTeacherGroupEmailController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public String groupEmail(HttpServletRequest request, HttpSession session) {
        System.out.println("调用成功");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        long id = Long.parseLong(request.getParameter("id"));
        System.out.println(id + " " + title + " " + content);
        courseService.groupEmail(id, title, content);
        return "Success";
    }
}
