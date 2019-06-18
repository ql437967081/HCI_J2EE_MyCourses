package edu.nju.wsql.beans;

import edu.nju.wsql.model.HomeworkRequest;
import edu.nju.wsql.model.HomeworkSubmit;

import java.time.format.DateTimeFormatter;

public class HomeworkBean {
    private long id;
    private String title;
    private String content;
    private String ddl;
    private int fileMaxSize;
    private String fileType;
    private long submitId;
    private String submitName;
    private String location;
    private int submitNum;
    private int selectCourseNum;

    public HomeworkBean() {
    }

    public HomeworkBean(HomeworkRequest homeworkRequest) {
        setId(homeworkRequest.getId());
        setTitle(homeworkRequest.getTitle());
        setContent(homeworkRequest.getContent());
        setDdl(homeworkRequest.getDdl().format(DateTimeFormatter.ofPattern("yyyy/MM/dd E HH:mm")));
        setFileMaxSize(homeworkRequest.getFileMaxSize());
        setFileType(homeworkRequest.getFileType());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public int getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(int fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSubmitId() {
        return submitId;
    }

    public void setSubmitId(long submitId) {
        this.submitId = submitId;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSubmitNum() {
        return submitNum;
    }

    public void setSubmitNum(int submitNum) {
        this.submitNum = submitNum;
    }

    public int getSelectCourseNum() {
        return selectCourseNum;
    }

    public void setSelectCourseNum(int selectCourseNum) {
        this.selectCourseNum = selectCourseNum;
    }

    public void setSubmit(HomeworkSubmit submit) {
        if (submit == null)
            return;
        setSubmitId(submit.getId());
        setSubmitName(submit.getName());
        setLocation("/homework/" + id + "/" + submit.getLocation());
    }
}
