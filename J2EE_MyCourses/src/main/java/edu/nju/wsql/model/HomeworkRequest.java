package edu.nju.wsql.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "homework_request")
public class HomeworkRequest implements Serializable {
    private long id;
    private PublishCourse publishCourse;
    private String title;
    private String content;
    private LocalDateTime ddl;
    private int fileMaxSize;  //  MB
    private String fileType;
    private Set<HomeworkSubmit> submits;

    public HomeworkRequest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public PublishCourse getPublishCourse() {
        return publishCourse;
    }

    public void setPublishCourse(PublishCourse publishCourse) {
        this.publishCourse = publishCourse;
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

    public LocalDateTime getDdl() {
        return ddl;
    }

    public void setDdl(LocalDateTime ddl) {
        this.ddl = ddl;
    }

    @Column(name = "file_max_size")
    public int getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(int fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @OneToMany(mappedBy = "homeworkRequest")
    public Set<HomeworkSubmit> getSubmits() {
        return submits;
    }

    public void setSubmits(Set<HomeworkSubmit> submits) {
        this.submits = submits;
    }

    public int calcSubmitNum() {
        int sum = 0;
        for (HomeworkSubmit homeworkSubmit: submits) {
            if (homeworkSubmit.getSelectCourse().getQuitTime() == null)
                sum ++;
        }
        return sum;
    }
}
