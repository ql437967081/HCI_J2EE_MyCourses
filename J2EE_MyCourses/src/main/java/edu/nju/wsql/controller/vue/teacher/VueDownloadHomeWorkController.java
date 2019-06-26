package edu.nju.wsql.controller.vue.teacher;

import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * @author gy
 * @date 2019/6/25 21:39
 */

@Controller
@RequestMapping("/vue/teacher/downloadhomework")
public class VueDownloadHomeWorkController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;
    @GetMapping
    @ResponseBody
    public void downloadHomework(
                                 long requestId,
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
}
