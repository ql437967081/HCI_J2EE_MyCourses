package edu.nju.wsql.controller;

import edu.nju.wsql.beans.InfoBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hello_world")
public class HelloController {

    @GetMapping
    @ResponseBody
    public InfoBean get(HttpServletRequest request) {
        InfoBean info = new InfoBean(
                "错误信息",
                "错误的审批",
                "课程已通过MyCourses主管审批！",
                "/admin/course_create_approval");
        return info;
    }
}
