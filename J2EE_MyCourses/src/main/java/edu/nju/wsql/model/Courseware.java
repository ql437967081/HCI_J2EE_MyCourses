package edu.nju.wsql.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "courseware")
public class Courseware implements Serializable {
    private long id;
    private String name;
    private String location;
    private Course course;

    public Courseware() {
    }

    public Courseware(String name, String location) {
        setName(name);
        setLocation(location);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @ManyToOne
    @JoinColumn(name = "cid")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
