package edu.nju.wsql.controller;

import edu.nju.wsql.beans.StudentCourseClassBean;
import edu.nju.wsql.controller.utils.UserInfoUtil;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.FileService;
import edu.nju.wsql.service.StudentCourseService;
import edu.nju.wsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private CourseService courseService;

    @GetMapping
    public String get(HttpServletRequest request, HttpSession session) {
        request.setAttribute("home_active", true);
        request.setAttribute("info", studentCourseService.getMyCourses((String) session.getAttribute("login")));
        return "/student/home.jsp";
    }

    @GetMapping("/select")
    public String selectTermAndCourseAndTeacher(HttpServletRequest request, HttpSession session) {
        String term = request.getParameter("term");
        long course = Long.parseLong(request.getParameter("course"));
        String teacher = request.getParameter("teacher");
        if (term.equals("all"))
            if (course == -1)
                if (teacher.equals("all"))
                    return "redirect:/student";
        request.setAttribute("home_active", true);
        request.setAttribute("info", studentCourseService.getMyCoursesByTermAndCourseAndTeacher(
                (String) session.getAttribute("login"),
                term, course, teacher));
        return "/student/home.jsp";
    }

    @GetMapping("/info")
    public String getInfo(HttpServletRequest request, HttpSession session) {
        UserInfoUtil.getInfo(request, session, userService);
        return "/student/info.jsp";
    }

    @PostMapping("/info")
    public String modifyInfo(HttpServletRequest request, HttpSession session, MultipartFile portrait) {
        UserInfoUtil.modify(request, session, portrait, userService, fileService);
        session.setAttribute("alert", "修改成功");
        return "redirect:/student/info";
    }

    @PostMapping("/unregister")
    public String unregister(HttpSession session) {
        userService.unregister((String) session.getAttribute("login"));
        session.setAttribute("alert", "注销成功，（可用同一邮箱再次注册，数据不删除）");
        return "redirect:/login/logout";
    }

    @GetMapping("/courses")
    public String getCourses(HttpServletRequest request, HttpSession session) {
        request.setAttribute("courses_active", true);
        request.setAttribute("info", studentCourseService.getAllCourses((String) session.getAttribute("login")));
        return "/student/courses.jsp";
    }

    @GetMapping("/select_course/{id}")
    public String getSelectCourse(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("id", id);
        request.setAttribute("info", courseService.getTermCourseInfo(id));
        return "/student/select_course.jsp";
    }

    @PostMapping("/select_course/{id}")
    public String selectCourse(@PathVariable("id") long id, HttpSession session) {
        long selectCourseId = studentCourseService.selectCourse((String) session.getAttribute("login"), id);
        if (selectCourseId > 0) {
            session.setAttribute("alert", "选课成功");
            return "redirect:/student/course/" + selectCourseId;
        }
        else if (selectCourseId == -1){
            session.setAttribute("alert", "您选的课程人数已满");
            return "redirect:/student/select_course/" + id;
        }
        else {
            session.setAttribute("alert", "您已经加入该课程");
            return "redirect:/student/select_course/" + id;
        }
    }

    @GetMapping("/course/{select_course_id}")
    public String getPublishCourse(@PathVariable("select_course_id") long id, HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        request.setAttribute("course_home_active", true);
        request.setAttribute("info", studentCourseService.getMyCourse(id));
        return "/student/course.jsp";
    }

    @PostMapping("/quit_course/{id}")
    public String quitCourse(@PathVariable("id") long id, HttpSession session) {
        StudentCourseClassBean bean =  studentCourseService.quitCourse(id);
        session.setAttribute("alert", "从" + bean.getName() + "  " + bean.getTerm() +
                "退课成功");
        return "redirect:/student/courses";
    }

    @GetMapping("/coursewares/{id}")
    public String getCoursewares(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        request.setAttribute("courseware_active", true);
        request.setAttribute("info", studentCourseService.getCoursewaresBySelectCourseId(id));
        return "/student/coursewares.jsp";
    }

    @GetMapping("/homework_list/{id}")
    public String getHomeworkList(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        request.setAttribute("homework_list_active", true);
        request.setAttribute("info", studentCourseService.getHomeworkListBySelectCourseId(id));
        return "/student/homework_list.jsp";
    }

    @GetMapping("/homework/{select_course_id}/{id}")
    public String getHomework(@PathVariable("id") long id, @PathVariable("select_course_id") long selectCourseId, HttpServletRequest request) {
        request.setAttribute("select_course_id", selectCourseId);
        request.setAttribute("id", id);
        request.setAttribute("info", studentCourseService.getHomework(id, selectCourseId));
        return "/student/homework.jsp";
    }

    @PostMapping("/homework/{select_course_id}/{id}")
    public String submitHomework(@PathVariable("id") long id, @PathVariable("select_course_id") long selectCourseId,
                                 HttpServletRequest request, HttpSession session, MultipartFile file) {
        if (LocalDateTime.now().isAfter(studentCourseService.getDdlByHomeworkRequestId(id))) {
            session.setAttribute("alert", "您已错过截止的提交时间");
            return "redirect:/student/homework/" + selectCourseId + "/" + id;
        }
        String student = (String) session.getAttribute("login");
        String submitName = file.getOriginalFilename();
        String filePath = session.getServletContext().getRealPath("/homework/" + id);
        String location = fileService.fileOperate(file, filePath, student);
        studentCourseService.submitHomework(id, selectCourseId, submitName, location);
        session.setAttribute("alert", "提交成功");
        return "redirect:/student/homework/" + selectCourseId + "/" + id;
    }

    @GetMapping("/grade/{id}")
    public String getGrades(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        request.setAttribute("grade_active", true);
        request.setAttribute("info", studentCourseService.getAllGrades(id));
        return "/student/grade.jsp";
    }

    @GetMapping("/forum/{id}")
    public String getForum(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        request.setAttribute("forum_active", true);
        request.setAttribute("info", studentCourseService.getAllTopics(id));
        return "/student/forum.jsp";
    }

    @GetMapping("/forum/{id}/new_topic")
    public String getNewTopic(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        return "/student/new_topic.jsp";
    }

    @PostMapping("/forum/{id}/new_topic")
    public String newTopic(@PathVariable("id") long id, HttpServletRequest request, HttpSession session) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        studentCourseService.newTopic(id, (String) session.getAttribute("login"), title, content);
        session.setAttribute("alert", "发布成功");
        return "redirect:/student/forum/" + id;
    }

    @GetMapping("/forum/{select_course_id}/topic/{id}")
    public String getTopic(@PathVariable("id") long id,
                           @PathVariable("select_course_id") long selectCourseId,
                           HttpServletRequest request) {
        request.setAttribute("select_course_id", id);
        request.setAttribute("topic_id", id);
        request.setAttribute("info", studentCourseService.getTopicPosts(id));
        return "/student/topic.jsp";
    }

    @PostMapping("/forum/{select_course_id}/topic/{id}")
    public String sayOnTopic(@PathVariable("id") long id,
                             @PathVariable("select_course_id") long selectCourseId,
                             HttpServletRequest request, HttpSession session) {
        String content = request.getParameter("content");
        studentCourseService.sayOnTopic(id, (String) session.getAttribute("login"), content);
        session.setAttribute("alert", "发布成功");
        return "redirect:/student/forum/" + selectCourseId +
                "/topic/" + id;
    }
}
