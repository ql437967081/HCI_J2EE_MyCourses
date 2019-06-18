package edu.nju.wsql.controller;

import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.beans.RegisterBean;
import edu.nju.wsql.model.enums.UserType;
import edu.nju.wsql.service.LoginService;
import edu.nju.wsql.service.results.LoginResult;
import edu.nju.wsql.service.results.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        // Logout action removes session, but the cookie remains
        String alert = (String) session.getAttribute("alert");
        session.invalidate();
        session = request.getSession(true);
        session.setAttribute("alert", alert);
        return "redirect:/login";
    }

    @GetMapping
    public String getLogin(HttpServletRequest request) {
        String login = "";
        Cookie cookie;
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (Cookie c : cookies) {
                cookie = c;
                if (cookie.getName().equals("LoginCookie")) {
                    login = cookie.getValue();
                    break;
                }
            }
        }
        request.setAttribute("login", login);
        return "/login/login.jsp";
    }

    @PostMapping
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String loginValue = request.getParameter("login");
        addLoginCookie(request, response, loginValue);
        if (loginValue != null){
            LoginResult result = loginService.login(loginValue, request.getParameter("password"));
            switch (result.getResult()) {
                case SUCCESS:
                    HttpSession session = request.getSession(true);
                    session.setAttribute("login", result.getId());
                    session.setAttribute("type", result.getType());
                    return "redirect:/login/permission";
                case FAILURE:
                    request.setAttribute("info", new InfoBean(
                            "登录失败",
                            "登录失败",
                            "用户名不存在或密码错误！",
                            "/login"));
                    return "/info_page/info.jsp";
                case INVALID:
                    request.setAttribute("info", new InfoBean(
                            "登录失败",
                            "登录失败",
                            "未进行邮箱验证或已经注销！",
                            "/login"));
                    return "/info_page/info.jsp";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegister(HttpSession session) {
        if (session.getAttribute("register") == null)
            session.setAttribute("register", new RegisterBean());
        return "/login/register.jsp";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, HttpSession session) {
        RegisterBean bean = (RegisterBean) session.getAttribute("register");
        String password = request.getParameter("password"); bean.setPassword(password);
        String confirmPassword = request.getParameter("confirmPassword"); bean.setConfirmPassword(confirmPassword);
        String name = request.getParameter("name"); bean.setName(name);
        String email = request.getParameter("email"); bean.setEmail(email);

        if (password.length() < 3) {
            request.setAttribute("info", new InfoBean(
                    "MyCourses: 注册失败",
                    "注册失败",
                    "密码不能少于3位！",
                    "/login/register"
            ));
            return "/info_page/info.jsp";
        }

        if (! password.equals(confirmPassword)) {
            request.setAttribute("info", new InfoBean(
                    "MyCourses: 注册失败",
                    "注册失败",
                    "确认密码不一致！",
                    "/login/register"
            ));
            return "/info_page/info.jsp";
        }

        switch (loginService.checkEmail(email)) {
            case NOT_NJU_EMAIL:
                request.setAttribute("info", new InfoBean(
                        "MyCourses: 注册失败",
                        "注册失败",
                        "不是NJU邮箱！",
                        "/login/register"
                ));
                return "/info_page/info.jsp";
            case ALREADY_EXIST:
                request.setAttribute("info", new InfoBean(
                        "MyCourses: 注册失败",
                        "注册失败",
                        "邮箱已存在！",
                        "/login/register"
                ));
                return "/info_page/info.jsp";
            case NOT_EXIST:
                loginService.register(bean);
                break;
            case ALREADY_INVALID:
                loginService.registerAgain(bean);
                break;
        }

        request.setAttribute("info", new InfoBean(
                "MyCourses: 注册成功",
                "注册成功",
                "邮件已发送至您的邮箱，请前往邮箱验证并登录！",
                "/login"
        ));
        return "/info_page/info.jsp";
    }

    @GetMapping("/validate")
    public String validate(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) {
        ValidationResult result = loginService.validate(token);
        switch (result.getResult()) {
            case ILLEGAL:
                request.setAttribute("info", new InfoBean(
                        "MyCourses: 验证失败",
                        "验证失败",
                        "非法的邮箱验证！",
                        "/login"
                ));
                return "/info_page/info.jsp";
            case EXPIRED:
                request.setAttribute("info", new InfoBean(
                        "MyCourses: 验证失败",
                        "验证失败",
                        "邮箱验证已过期，请重新注册！",
                        "/login"
                ));
                return "/info_page/info.jsp";
            case SUCCESS:
                String id = result.getId();
                addLoginCookie(request, response, id);
                HttpSession session = request.getSession(true);
                session.setAttribute("login", id);
                session.setAttribute("type", result.getType());
                return "redirect:/login/permission";
        }
        return "redirect:/login";
    }

    @GetMapping("/permission")
    public String getPermission(HttpSession session) {
        switch ((UserType) session.getAttribute("type")) {
            case STUDENT:
                return "redirect:/student";
            case TEACHER:
                return "redirect:/teacher";
            case ADMINISTRATOR:
                return "redirect:/admin";
        }
        return null;
    }

    private void addLoginCookie(HttpServletRequest request, HttpServletResponse response, String loginValue) {
        boolean cookieFound = false;
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (Cookie c : cookies) {
                cookie = c;
                if (cookie.getName().equals("LoginCookie")) {
                    cookieFound = true;
                    break;
                }
            }
        }
        if (loginValue != null) {
            //修改cookie
            if (cookieFound) { // If the cookie exists update the value only
                // if changed
                if (!loginValue.equals(cookie.getValue())) {
                    cookie.setValue(loginValue);
                    response.addCookie(cookie);
                }
            } else {
                // If the cookie does not exist, create it and set value
                cookie = new Cookie("LoginCookie", loginValue);
                cookie.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);
            }
        }
    }
}
