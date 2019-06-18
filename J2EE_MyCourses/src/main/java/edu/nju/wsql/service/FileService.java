package edu.nju.wsql.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

public interface FileService {
    String fileOperate(MultipartFile file, String filePath, String id);

    void copyHomeworkSubmits(Map<String, String> locations, String filePath, String rootPath);

    void toZip(String srcDir, OutputStream out, boolean KeepDirStructure);

    Map<String, Double> getGrades(File excel);
}
