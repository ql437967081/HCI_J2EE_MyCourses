package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.beans.CourseBean;
import edu.nju.wsql.beans.StudentTermCourseBean;
import edu.nju.wsql.controller.utils.CoursewareUtil;
import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.Courseware;
import edu.nju.wsql.model.enums.Season;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/vue/teacher/course")
public class VueTeacherCourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    private FileService fileService;

    @GetMapping
    @ResponseBody
    public Map<Long, String> getCourses(HttpServletRequest request, HttpSession session) {
        return courseService.getCoursesICreated((String) session.getAttribute("login")).getCourses();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CourseBean getCourse(@PathVariable("id") long id, HttpServletRequest request) {
        return courseService.getCourseInfo(id);
    }

    @PostMapping("/{id}/add_wares")
    @ResponseBody
    public void addCourseware(@PathVariable("id") long id, HttpSession session, MultipartFile[] files) {
        String teacher = (String) session.getAttribute("login");
        String name = courseService.getCourseName(id);
        Set<Courseware> coursewares = CoursewareUtil.saveCoursewareFiles(session, name, files, fileService, teacher);
        courseService.addCoursewares(id, coursewares);
    }

    @PostMapping("/remove")
    @ResponseBody
    public void removeCourseware(HttpServletRequest request) {
        long wareId = Long.parseLong(request.getParameter("wareId"));
        System.out.println("remove courseware: " + wareId);
        courseService.removeCourseware(wareId);
    }

    @PostMapping("/new_course")
    @ResponseBody
    public long newCourse(HttpServletRequest request, HttpSession session, MultipartFile[] files) {
        String id = (String) session.getAttribute("login");
        String name = request.getParameter("name");
        if (courseService.existCourse(name, id)) {
            session.setAttribute("alert", "您已经创建过课程：" + name + "或正在审批中");
            return -1;
        }
        Set<Courseware> coursewares = CoursewareUtil.saveCoursewareFiles(session, name, files, fileService, id);
        long courseId = courseService.createNewCourse(name, id, coursewares);
        return courseId;
    }

    @PostMapping("/publish_course")
    @ResponseBody
    public long publish(HttpServletRequest request, HttpSession session) {
        int year = Integer.parseInt(request.getParameter("year"));
        Season season = Season.chineseToSeason(request.getParameter("season"));
        long course = Long.parseLong(request.getParameter("course"));
        if (courseService.existPublish(year, season, course)) {
            session.setAttribute("alert", "您已经在这学期发布过课程：" + courseService.getCourseName(course) + "或正在审批中");
            return -1;
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
        return publishId;
    }

}
