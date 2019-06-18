package edu.nju.wsql.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*
        String login = "";
        HttpSession session = request.getSession(false);
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("LoginCookie")) {
                    login=cookie.getValue();
                    break;
                }
            }
        }

        // Logout action removes session, but the cookie remains
        if (null != request.getParameter("Logout")) {
            if (null != session) {
                session.invalidate();
                session = null;
            }
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><title>登录</title><body><h1 align=center>客户登录</h1>");

        out.println(
                "<form method='POST' action='"
                        + response.encodeURL(request.getContextPath()+"/goodsList")
                        + "'>");
        out.println(
                "<table border='10' align=center>"
        );
        out.println(
                "<tr><td>客户id: </td><td><input type='text' name='login' value='" + login + "'></td></tr>");
        out.println(
                "<tr><td>密码: </td><td><input type='password' name='password' value=''></td></tr>");
        out.println("<tr><td colspan='2' align='center'><input type='submit' name='Submit' value='登录'></td></tr>");

        out.println("</table></form></body></html>");*/
    }
}
