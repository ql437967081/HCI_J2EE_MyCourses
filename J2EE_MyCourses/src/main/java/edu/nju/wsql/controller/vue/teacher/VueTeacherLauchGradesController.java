package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;

/**
 * @author gy
 * @date 2019/6/25 16:50
 */
@Controller
@RequestMapping("/vue/teacher/lauchgrades")
public class VueTeacherLauchGradesController {
    @Autowired
    private FileService fileService;
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public String publishGrade( long id,
                               long courseId,
                               HttpServletRequest request,
                               HttpSession session,
                               MultipartFile sheet) {
        long project = Long.parseLong(request.getParameter("project"));
        String remark = request.getParameter("remark");
        boolean open = Boolean.parseBoolean(request.getParameter("open"));
        String filePath = session.getServletContext().getRealPath("/grade/" + id);
        String teacher = (String) session.getAttribute("login");
        String location = fileService.fileOperate(sheet, filePath, teacher);
        Map<String, Double> grades = fileService.getGrades(new File(filePath, location));
        if (grades == null) {
            session.setAttribute("alert", "上传的成绩文件格式有误");
            return "redirect:/teacher/course/" + courseId + "/publish_grade/" + id;
        }
        courseService.publishGrade(id, project, remark, open, location, grades);
        session.setAttribute("alert", "成绩上传成功");
        return "成绩上传成功";
    }
}
