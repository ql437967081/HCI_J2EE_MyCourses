package edu.nju.wsql.controller.vue;

import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.beans.RegisterBean;
import edu.nju.wsql.service.LoginService;
import edu.nju.wsql.service.results.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static edu.nju.wsql.service.results.ValidationResult.Result.SUCCESS;

@Controller
@RequestMapping("/vue/register")
public class VueRegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    @ResponseBody
    public InfoBean register(HttpServletRequest request) {
        RegisterBean bean = new RegisterBean();
        bean.setVue(true);
        String password = request.getParameter("password"); bean.setPassword(password);
        String confirmPassword = request.getParameter("confirmPassword"); bean.setConfirmPassword(confirmPassword);
        String name = request.getParameter("name"); bean.setName(name);
        String email = request.getParameter("email"); bean.setEmail(email);

        switch (loginService.checkEmail(email)) {
            case NOT_NJU_EMAIL:
                return new InfoBean(
                        "MyCourses: 注册失败",
                        "注册失败",
                        "不是NJU邮箱！",
                        "/login/register"
                );
            case ALREADY_EXIST:
                return new InfoBean(
                        "MyCourses: 注册失败",
                        "注册失败",
                        "邮箱已存在！",
                        "/login/register"
                );
            case NOT_EXIST:
                loginService.register(bean);
                break;
            case ALREADY_INVALID:
                loginService.registerAgain(bean);
                break;
        }

        return new InfoBean(
                "MyCourses: 注册成功",
                "注册成功",
                "邮件已发送至您的邮箱，请前往邮箱验证并登录！",
                "/login"
        );
    }

    @GetMapping("/validate")
    @ResponseBody
    public ValidationResult validate(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) {
        ValidationResult result = loginService.validate(token);
        if (result.getResult() == SUCCESS) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login", result.getId());
            session.setAttribute("type", result.getType());
        }
        return result;
    }
}
