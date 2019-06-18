package edu.nju.wsql.controller.vue;

import edu.nju.wsql.service.LoginService;
import edu.nju.wsql.service.results.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static edu.nju.wsql.service.results.LoginResult.Result.SUCCESS;

@Controller
@RequestMapping("/vue/login")
public class VueLoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    @ResponseBody
    public LoginResult login(HttpServletRequest request) {
        LoginResult result = loginService.login(request.getParameter("login"), request.getParameter("password"));
        if (result.getResult() == SUCCESS) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login", result.getId());
            session.setAttribute("type", result.getType());
        }
        return result;
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpSession session) {
        System.out.println(session.getAttribute("login") + " logout!");
        session.invalidate();
        request.getSession(true);
        return null;
    }
}
