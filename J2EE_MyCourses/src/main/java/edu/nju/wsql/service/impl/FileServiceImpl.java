package edu.nju.wsql.service.impl;

import edu.nju.wsql.service.FileService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String fileOperate(MultipartFile file, String filePath, String id) {
        String originalFileName = file.getOriginalFilename();
        System.out.println("文件原始名称：" + originalFileName);
        if (originalFileName.length() == 0)
            return null;
        String newFileName = id + "_" + UUID.randomUUID() + originalFileName;
        System.out.println("新的文件名称：" + newFileName);
        File dir = new File(filePath);
        if (! dir.exists())
            dir.mkdirs();
        File targetFile = new File(dir, newFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }

    @Override
    public void copyHomeworkSubmits(Map<String, String> locations, String filePath, String rootPath) {
        for (Map.Entry<String, String> entry: locations.entrySet()) {
            String id = entry.getKey();
            String sourceName = entry.getValue();
            File sourceFile = new File(filePath, sourceName);
            String[] tmps = sourceName.split("\\.");
            StringBuilder destName = new StringBuilder(id);
            int len = tmps.length;
            if (len > 1) {
                for (int i = 1; i < len; i ++)
                    destName.append(".").append(tmps[i]);
            }
            File destFile = new File(rootPath, destName.toString());
            try {
                Files.copy(sourceFile.toPath(), destFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void toZip(String srcDir, OutputStream out, boolean keepDirStructure) {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = new ZipOutputStream(out);
        File sourceFile = new File(srcDir);
        try {
            compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        try {
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Double> getGrades(File excel) {
        Map<String, Double> map = new HashMap<>();
        if (! excel.isFile() || ! excel.exists()) {
            System.out.println("找不到文件");
            return null;
        }
        String[] split = excel.getName().split("\\.");
        String postfix = split[split.length - 1];
        Workbook wb;
        FileInputStream is;
        try {
            is = new FileInputStream(excel);
            if ("xls".equals(postfix))
                wb = new HSSFWorkbook(is);
            else if ("xlsx".equals(postfix))
                wb = new XSSFWorkbook(is);
            else {
                System.out.println("文件类型错误");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Sheet sheet = wb.getSheetAt(0);
        int firstRowIndex = sheet.getFirstRowNum() + 1;
        int lastRowIndex = sheet.getLastRowNum();
        System.out.println("firstRowIndex: " + firstRowIndex);
        System.out.println("lastRowIndex: " + lastRowIndex);
        for (int i = firstRowIndex; i <= lastRowIndex; i ++) {
            System.out.println("行号：" + i);
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell idCell = row.getCell(row.getFirstCellNum());
                if (idCell == null) {
                    System.out.println(row.getFirstCellNum() + " id null");
                    return null;
                }
                Cell scoreCell = row.getCell(row.getLastCellNum() - 1);
                if (scoreCell == null) {
                    System.out.println(row.getLastCellNum() - 1 + " score null");
                    return null;
                }
                String id;
                switch (idCell.getCellType()) {
                    case NUMERIC:
                        id = "" + (long) idCell.getNumericCellValue();
                        break;
                    case STRING:
                        id = idCell.getStringCellValue();
                        break;
                    default:
                        return null;
                }
                double score = scoreCell.getNumericCellValue();
                System.out.println(id + ": " + score);
                map.put(id, score);
            }
        }
        return map;
    }

    private static final int  BUFFER_SIZE = 2 * 1024;

    private void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        System.out.println(name+" "+sourceFile.length()/1024.0);
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            FileInputStream in = new FileInputStream(sourceFile);
            int len;
            while ((len = in.read(buf)) != -1)
                zos.write(buf, 0, len);
            zos.closeEntry();
            in.close();
        }
        else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (keepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            }
            else {
                for (File file: listFiles) {
                    if (keepDirStructure)
                        compress(file, zos, name + "/" + file.getName(), keepDirStructure);
                    else
                        compress(file, zos, file.getName(), keepDirStructure);
                }
            }
        }
    }
}
