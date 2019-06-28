package edu.nju.wsql.controller.vue.admin;

import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/vue/admin/statics")
public class VueAdminStaticsController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> getCourseApprovals() {
        Map<String, Object> data = new HashMap<>();
        data.put("userStatics", userService.getUserStatics());
        data.put("teacherStatics", courseService.getAllTeacherStatics());
        data.put("studentStatics", courseService.getStudentStatics());
        return data;
    }
}
