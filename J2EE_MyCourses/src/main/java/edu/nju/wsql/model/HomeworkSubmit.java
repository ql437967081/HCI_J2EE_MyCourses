package edu.nju.wsql.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "homework_submit")
public class HomeworkSubmit implements Serializable {
    private long id;
    private HomeworkRequest homeworkRequest;
    private SelectCourse selectCourse;
    private String name;
    private String location;

    public HomeworkSubmit() {
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
    @JoinColumn(name = "hid")
    public HomeworkRequest getHomeworkRequest() {
        return homeworkRequest;
    }

    public void setHomeworkRequest(HomeworkRequest homeworkRequest) {
        this.homeworkRequest = homeworkRequest;
    }

    @ManyToOne
    @JoinColumn(name = "sid")
    public SelectCourse getSelectCourse() {
        return selectCourse;
    }

    public void setSelectCourse(SelectCourse selectCourse) {
        this.selectCourse = selectCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
