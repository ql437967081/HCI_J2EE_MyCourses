package edu.nju.wsql.controller;

import edu.nju.wsql.controller.utils.CoursewareUtil;
import edu.nju.wsql.controller.utils.UserInfoUtil;
import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.Courseware;
import edu.nju.wsql.model.enums.Season;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.FileService;
import edu.nju.wsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CourseService courseService;

    @GetMapping
    public String get(HttpServletRequest request, HttpSession session) {
        request.setAttribute("home_active", true);
        request.setAttribute("info", courseService.getMyPublishCourses((String) session.getAttribute("login")));
        return "/teacher/home.jsp";
    }

    @GetMapping("/select_term")
    public String getSelectTerm(HttpServletRequest request, HttpSession session) {
        String term = request.getParameter("term");
        if (term == null || term.equals("all"))
            return "redirect:/teacher";
        int year = Integer.parseInt(term.substring(0, 4));
        Season season = Season.chineseToSeason(term.substring(5, 7));
        request.setAttribute("home_active", true);
        request.setAttribute("info", courseService.getMyPublishCourses((String) session.getAttribute("login"), year, season));
        return "/teacher/home.jsp";
    }

    @GetMapping("/info")
    public String getInfo(HttpServletRequest request, HttpSession session) {
        UserInfoUtil.getInfo(request, session, userService);
        return "/teacher/info.jsp";
    }

    @PostMapping("/info")
    public String modifyInfo(HttpServletRequest request, HttpSession session, MultipartFile portrait) {
        UserInfoUtil.modify(request, session, portrait, userService, fileService);
        session.setAttribute("alert", "修改成功");
        return "redirect:/teacher/info";
    }

    @GetMapping("/new_course")
    public String toCreateCourse(HttpServletRequest request) {
        request.setAttribute("new_course_active", true);
        return "/teacher/new_course.jsp";
    }

    @PostMapping("/new_course")
    public String newCourse(HttpServletRequest request, HttpSession session, MultipartFile[] files) {
        String id = (String) session.getAttribute("login");
        String name = request.getParameter("name");
        if (courseService.existCourse(name, id)) {
            session.setAttribute("alert", "您已经创建过课程：" + name + "或正在审批中");
            return "redirect:/teacher/new_course";
        }
        Set<Courseware> coursewares = CoursewareUtil.saveCoursewareFiles(session, name, files, fileService, id);
        long courseId = courseService.createNewCourse(name, id, coursewares);
        return "redirect:/teacher/course/" + courseId;
    }

    @GetMapping("/publish_course")
    public String getPublish(HttpServletRequest request, HttpSession session) {
        request.setAttribute("publish_course_active", true);
        request.setAttribute("info", courseService.getPublishInfo((String) session.getAttribute("login")));
        return "/teacher/publish.jsp";
    }

    @PostMapping("/publish_course")
    public String publish(HttpServletRequest request, HttpSession session) {
        int year = Integer.parseInt(request.getParameter("year"));
        Season season = Season.chineseToSeason(request.getParameter("season"));
        long course = Long.parseLong(request.getParameter("course"));
        if (courseService.existPublish(year, season, course)) {
            session.setAttribute("alert", "您已经在这学期发布过课程：" + courseService.getCourseName(course) + "或正在审批中");
            return "redirect:/teacher/publish_course";
        }
        Set<CourseClass> classes = new HashSet<>();
        int classId = 0;
        while (true) {
            classId ++;
            String limit = request.getParameter("" + classId);
            if (limit == null)
                break;
            classes.add(new CourseClass(classId, Integer.parseInt(limit)));
        }
        long publishId = courseService.publishCourse(year, season, course, classes);
        return "redirect:/teacher/course/" + course + "/term_course/" + publishId;
    }

    @GetMapping("/my_courses")
    public String getMyCourses(HttpServletRequest request, HttpSession session) {
        request.setAttribute("my_courses_active", true);
        request.setAttribute("info", courseService.getCoursesICreated((String) session.getAttribute("login")));
        return "/teacher/my_courses.jsp";
    }
}
