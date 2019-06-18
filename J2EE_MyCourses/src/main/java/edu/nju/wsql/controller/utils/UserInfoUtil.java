package edu.nju.wsql.controller.utils;

import edu.nju.wsql.service.FileService;
import edu.nju.wsql.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserInfoUtil {
    public static void getInfo(HttpServletRequest request, HttpSession session, UserService userService) {
        request.setAttribute("info_active", true);
        request.setAttribute("info", userService.getUserInfo((String) session.getAttribute("login")));
    }

    public static void modify(HttpServletRequest request, HttpSession session, MultipartFile portrait, UserService userService, FileService fileService) {
        String id = (String) session.getAttribute("login");
        String name = request.getParameter("name");
        String newPortrait = null;
        if (portrait != null) {
            String filePath = session.getServletContext().getRealPath("/img/portrait");
            newPortrait = fileService.fileOperate(portrait, filePath, id);
        }
        userService.modifyUserInfo(id, name, newPortrait);
    }
}
