package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.beans.UserInfoBean;
import edu.nju.wsql.controller.utils.UserInfoUtil;
import edu.nju.wsql.service.FileService;
import edu.nju.wsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/vue/teacher/info")
public class VueTeacherInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @GetMapping
    @ResponseBody
    public UserInfoBean getInfo(HttpSession session) {
        return userService.getUserInfo((String) session.getAttribute("login"));
    }

    @PostMapping
    @ResponseBody
    public String modifyInfo(HttpServletRequest request, HttpSession session, MultipartFile portrait) {
        UserInfoUtil.modify(request, session, portrait, userService, fileService);
        return null;
    }
}
