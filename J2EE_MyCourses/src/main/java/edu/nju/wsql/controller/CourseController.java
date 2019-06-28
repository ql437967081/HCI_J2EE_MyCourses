package edu.nju.wsql.controller;

import edu.nju.wsql.beans.CourseBean;
import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.controller.utils.CoursewareUtil;
import edu.nju.wsql.model.Courseware;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/teacher/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public String getCourse(@PathVariable("id") long id, HttpServletRequest request) {
        CourseBean bean = courseService.getCourseInfo(id);
        switch (bean.getStatus()) {
            case SUBMIT:
                request.setAttribute("info", new InfoBean(
                        "创建课程",
                        "课程创建中",
                        "课程" + bean.getName() + "的创建请求已提交MyCourses主管审批，请耐心等待！",
                        "/teacher/new_course"));
                return "/info_page/info.jsp";
            case REJECTED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程",
                        "课程" + bean.getName() + "未通过MyCourses主管审批！",
                        "/teacher"));
                courseService.readRejectedCourse(id);
                return "/info_page/info.jsp";
            case REJECTED_READ:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程",
                        "课程不存在或未通过MyCourses主管审批！",
                        "/teacher"));
                return "/info_page/info.jsp";
        }
        request.setAttribute("course_home_active", true);
        request.setAttribute("course_id", id);
        request.setAttribute("info", bean);
        return "/teacher/course.jsp";
    }

    @PostMapping("/{id}/remove/{ware_id}")
    public String removeCourseware(@PathVariable("id") long id, @PathVariable("ware_id") long wareId) {
        courseService.removeCourseware(wareId);
        return "redirect:/teacher/course/" + id;
    }

    @PostMapping("/{id}/add_wares")
    public String addCourseware(@PathVariable("id") long id, HttpSession session, MultipartFile[] files) {
        String teacher = (String) session.getAttribute("login");
        String name = courseService.getCourseName(id);

        Set<Courseware> coursewares = CoursewareUtil.saveCoursewareFiles(session, name, files, fileService, teacher);
        courseService.addCoursewares(id, coursewares);
        return "redirect:/teacher/course/" + id;
    }

    @GetMapping("/{course_id}/term_course/{id}")
    public String getTermCourse(@PathVariable("id") long id, @PathVariable("course_id") long courseId, HttpServletRequest request) {
        TermCourseInfoBean bean = courseService.getTermCourseInfo(id);
        switch (bean.getStatus()) {
            case SUBMIT:
                request.setAttribute("info", new InfoBean(
                        "发布课程",
                        "课程发布中",
                        "课程" + bean.getName() + "的发布请求已提交MyCourses主管审批，请耐心等待！",
                        "/teacher/publish_course"));
                return "/info_page/info.jsp";
            case REJECTED:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程发布",
                        "课程" + bean.getName() + " " + bean.getTerm() + "的发布未通过MyCourses主管审批！",
                        "/teacher/publish_course"));
                courseService.readRejectedPublishCourse(id);
                return "/info_page/info.jsp";
            case REJECTED_READ:
                request.setAttribute("info", new InfoBean(
                        "错误信息",
                        "错误的课程发布",
                        "课程未发布或发布未通过MyCourses主管审批！",
                        "/teacher"));
                return "/info_page/info.jsp";
        }
        request.setAttribute("term_course_active", true);
        request.setAttribute("course_id", courseId);
        request.setAttribute("term_course_id", id);
        request.setAttribute("info", bean);
        return "/teacher/term_course.jsp";
    }

    @GetMapping("/{course_id}/term_course/{id}/{course_class_id}")
    public String getTermCourseClass(@PathVariable("id") long id,
                                     @PathVariable("course_id") long courseId,
                                     @PathVariable("course_class_id") long courseClassId,
                                     HttpServletRequest request) {
        request.setAttribute("course_id", courseId);
        request.setAttribute("term_course_id", id);
        request.setAttribute("course_class_id", courseClassId);
        request.setAttribute("info", courseService.getSelectInfoOfClass(courseClassId));
        return "/teacher/term_course_class.jsp";
    }


    @GetMapping("/{course_id}/publish_homework/{id}")
    public String getPublishHomework(@PathVariable("id") long id, @PathVariable("course_id") long courseId, HttpServletRequest request) {
        request.setAttribute("publish_homework_active", true);
        request.setAttribute("course_id", courseId);
        request.setAttribute("term_course_id", id);
        return "/teacher/publish_homework.jsp";
    }

    @PostMapping("/{course_id}/publish_homework/{id}")
    public String publishHomework(@PathVariable("id") long id, @PathVariable("course_id") long courseId, HttpServletRequest request, HttpSession session) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String ddl = request.getParameter("ddl");
        System.out.println(ddl);
        String fileMaxSize = request.getParameter("file_max_size");
        String fileType = request.getParameter("file_type");

        courseService.publishHomework(id, title, content, ddl, fileMaxSize, fileType);

        session.setAttribute("alert", "作业发布成功");
        return "redirect:/teacher/course/" + courseId + "/download_homework/" + id;
    }

    @GetMapping("/{course_id}/download_homework/{id}")
    public String getDownloadHomework(@PathVariable("id") long id, @PathVariable("course_id") long courseId, HttpServletRequest request) {
        request.setAttribute("download_homework_active", true);
        request.setAttribute("course_id", courseId);
        request.setAttribute("term_course_id", id);
        request.setAttribute("info", courseService.getHomeworkList(id, false));
        return "/teacher/download_homework.jsp";
    }

    @GetMapping("/{course_id}/download_homework/{id}/{request_id}")
    public void downloadHomework(@PathVariable("id") long id,
                                 @PathVariable("course_id") long courseId,
                                 @PathVariable("request_id") long requestId,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session) throws IOException {
        Map<String, String> locations = courseService.getHomeworkSubmitsByHomeworkRequestId(requestId);
        String filePath = session.getServletContext().getRealPath("/homework/" + requestId);
        String rootPath = session.getServletContext().getRealPath("/");
        File tmpDir = new File(rootPath, UUID.randomUUID().toString().replaceAll("-", ""));
        if(! tmpDir.exists())
            tmpDir.mkdirs();
        fileService.copyHomeworkSubmits(locations, filePath, tmpDir.getPath());
        response.setContentType("application/zip");
        String title = courseService.getHomeworkTitle(requestId);
        String zipName = URLEncoder.encode(title, "UTF-8");
        if (zipName.length() > 150) {
            String guessCharset = "gb2312";
            zipName = new String(title.getBytes(guessCharset), "ISO8859-1");
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName + ".zip");
        fileService.toZip(tmpDir.getPath(), response.getOutputStream(), false);
        for (File file: tmpDir.listFiles())
            file.delete();
        tmpDir.delete();
    }

    @GetMapping("/{course_id}/group_email/{id}")
    public String getGroupEmail(@PathVariable("id") long id, @PathVariable("course_id") long courseId, HttpServletRequest request) {
        request.setAttribute("group_email_active", true);
        request.setAttribute("course_id", courseId);
        request.setAttribute("term_course_id", id);
        return "/teacher/group_email.jsp";
    }

    @PostMapping("/{course_id}/group_email/{id}")
    public String groupEmail(@PathVariable("id") long id,
                             @PathVariable("course_id") long courseId,
                             HttpServletRequest request,
                             HttpSession session) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        courseService.groupEmail(id, title, content);
        session.setAttribute("alert", "邮件已经成功发送给了选择这门课的学生");
        return "redirect:/teacher/course/" + courseId + "/group_email/" + id;
    }

    @GetMapping("/{course_id}/publish_grade/{id}")
    public String getPublishGrade(@PathVariable("id") long id,
                                  @PathVariable("course_id") long courseId,
                                  HttpServletRequest request) {
        request.setAttribute("publish_grade_active", true);
        request.setAttribute("course_id", courseId);
        request.setAttribute("term_course_id", id);
        request.setAttribute("info", courseService.getHomeworkList(id, true));
        return "/teacher/publish_grade.jsp";
    }

    @PostMapping("/{course_id}/publish_grade/{id}")
    public String publishGrade(@PathVariable("id") long id,
                               @PathVariable("course_id") long courseId,
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
        return "redirect:/teacher/course/" + courseId + "/publish_grade/" + id;
    }

    @GetMapping("/{id}/forum")
    public String getForum(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("course_id", id);
        request.setAttribute("forum_active", true);
        request.setAttribute("info", courseService.getAllTopics(id));
        return "/teacher/forum.jsp";
    }

    @GetMapping("/{id}/forum/new_topic")
    public String getNewTopic(@PathVariable("id") long id, HttpServletRequest request) {
        request.setAttribute("course_id", id);
        return "/teacher/new_topic.jsp";
    }

    @PostMapping("/{id}/forum/new_topic")
    public String newTopic(@PathVariable("id") long id, HttpServletRequest request, HttpSession session) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        courseService.newTopic(id, (String) session.getAttribute("login"), title, content);
        session.setAttribute("alert", "发布成功");
        return "redirect:/teacher/course/" + id + "/forum";
    }

    @GetMapping("/{course_id}/forum/topic/{id}")
    public String getTopic(@PathVariable("id") long id,
                           @PathVariable("course_id") long courseId,
                           HttpServletRequest request) {
        request.setAttribute("course_id", id);
        request.setAttribute("topic_id", id);
        request.setAttribute("info", courseService.getTopicPosts(id));
        return "/teacher/topic.jsp";
    }

    @PostMapping("/{course_id}/forum/topic/{id}")
    public String sayOnTopic(@PathVariable("id") long id,
                             @PathVariable("course_id") long courseId,
                             HttpServletRequest request, HttpSession session) {
        String content = request.getParameter("content");
        courseService.sayOnTopic(id, (String) session.getAttribute("login"), content);
        session.setAttribute("alert", "发布成功");
        return "redirect:/teacher/course/" + courseId +
                "/forum/topic/" + id;
    }
}
