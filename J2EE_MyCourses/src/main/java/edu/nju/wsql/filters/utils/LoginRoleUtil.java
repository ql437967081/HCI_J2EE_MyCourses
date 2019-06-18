package edu.nju.wsql.filters.utils;

import edu.nju.wsql.model.enums.UserType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginRoleUtil {
    public static boolean filterLoginRole(ServletRequest req, ServletResponse resp, UserType userType) throws IOException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpServletResponse httpResp = (HttpServletResponse) resp;
            HttpSession session = httpReq.getSession(false);
            String login = null;
            UserType type = null;
            if (session != null) {
                login = (String) session.getAttribute("login");
                type = (UserType) session.getAttribute("type");
            }
            System.out.println(login);
            System.out.println(type);
            if (session == null || login == null || type != userType) {
                httpResp.sendRedirect("/login");
                return false;
            }
        }
        return true;
    }

    public static boolean filterVueLoginRole(ServletRequest req, ServletResponse resp, UserType userType) throws IOException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpServletResponse httpResp = (HttpServletResponse) resp;
            HttpSession session = httpReq.getSession(false);
            String login = null;
            UserType type = null;
            if (session != null) {
                login = (String) session.getAttribute("login");
                type = (UserType) session.getAttribute("type");
            }
            System.out.println(login);
            System.out.println(type);
            if (session == null || login == null || type != userType) {
                httpResp.setStatus(401);
                return false;
            }
        }
        return true;
    }
}
