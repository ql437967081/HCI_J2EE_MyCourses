package edu.nju.wsql.controller.utils;

import edu.nju.wsql.model.Courseware;
import edu.nju.wsql.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class CoursewareUtil {
    public static Set<Courseware> saveCoursewareFiles(HttpSession session, String name, MultipartFile[] files, FileService fileService, String teacher) {
        Set<Courseware> coursewares = new HashSet<>();
        String filePath = session.getServletContext().getRealPath("/courseware/" + teacher + "/" + name);
        for (MultipartFile file: files) {
            String location = fileService.fileOperate(file, filePath, teacher);
            if (location != null)
                coursewares.add(new Courseware(file.getOriginalFilename(), location));
        }
        return coursewares;
    }
}
