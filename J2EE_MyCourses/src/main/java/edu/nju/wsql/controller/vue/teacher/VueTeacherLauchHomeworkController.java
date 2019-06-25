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
 * @date 2019/6/25 9:56
 */
@Controller
@RequestMapping("/vue/teacher/lauchhomework")
public class VueTeacherLauchHomeworkController {
    @Autowired
    private CourseService courseService;
    @PostMapping
    @ResponseBody
    public String publishHomework(HttpServletRequest request, HttpSession session) {
        System.out.println("start launch");
        Long courseId = Long.parseLong(request.getParameter("id"));
        System.out.println(courseId);
        String title = request.getParameter("title");
        System.out.println(title);
        String content = request.getParameter("content");
        System.out.println(content);
        String ddl = request.getParameter("ddl");
        System.out.println(ddl);
        String fileMaxSize = request.getParameter("file_max_size");
        System.out.println(fileMaxSize);
        String fileType = request.getParameter("file_type");
        System.out.println(fileType);

        courseService.publishHomework(courseId, title, content, ddl, fileMaxSize, fileType);
        System.out.println("success");
        session.setAttribute("alert", "作业发布成功");
        return "lauch success" ;
    }
}
