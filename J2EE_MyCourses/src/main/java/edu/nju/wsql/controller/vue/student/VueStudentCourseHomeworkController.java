package edu.nju.wsql.controller.vue.student;

import edu.nju.wsql.beans.HomeworkBean;
import edu.nju.wsql.service.FileService;
import edu.nju.wsql.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/vue/student/course_homework")
public class VueStudentCourseHomeworkController {
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private FileService fileService;

    @GetMapping
    @ResponseBody
    public HomeworkBean getHomework(@RequestParam long selectCourseId,
                                    @RequestParam long homeworkRequestId,
                                    HttpServletResponse response,
                                    HttpSession session) {
        String student = (String) session.getAttribute("login");
        if (!checkValidation(student, selectCourseId, homeworkRequestId, response))
            return null;
        return studentCourseService.getHomework(homeworkRequestId, selectCourseId);
    }

    @PostMapping
    @ResponseBody
    public boolean submitHomework(HttpServletRequest request, HttpServletResponse response, HttpSession session, MultipartFile file) {
        String student = (String) session.getAttribute("login");
        long selectCourseId = Long.parseLong(request.getParameter("selectCourseId"));
        long homeworkRequestId = Long.parseLong(request.getParameter("homeworkRequestId"));
        if (!checkValidation(student, selectCourseId, homeworkRequestId, response))
            return false;
        if (LocalDateTime.now().isAfter(studentCourseService.getDdlByHomeworkRequestId(homeworkRequestId)))
            return false;
        String submitName = file.getOriginalFilename();
        String filePath = session.getServletContext().getRealPath("/homework/" + homeworkRequestId);
        String location = fileService.fileOperate(file, filePath, student);
        studentCourseService.submitHomework(homeworkRequestId, selectCourseId, submitName, location);
        return true;
    }

    private boolean checkValidation(String student, long selectCourseId, long homeworkRequestId,
                                    HttpServletResponse response) {
        if (!studentCourseService.isTheCourseISelected(selectCourseId, student)) {
            response.setStatus(402);
            return false;
        }
        if (!studentCourseService.isTheHomeworkOfTheCourse(homeworkRequestId, selectCourseId)) {
            response.setStatus(403);
            return false;
        }
        return true;
    }
}
