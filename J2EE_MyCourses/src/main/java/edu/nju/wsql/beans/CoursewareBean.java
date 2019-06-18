package edu.nju.wsql.beans;

import edu.nju.wsql.model.Courseware;

public class CoursewareBean {
    private long id;
    private String name;
    private String location;

    public CoursewareBean() {
    }

    public CoursewareBean(Courseware courseware, String teacher) {
        setId(courseware.getId());
        setName(courseware.getName());
        setLocation("/courseware/" + teacher + "/" + courseware.getCourse().getName() + "/" + courseware.getLocation());
    }

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
}
