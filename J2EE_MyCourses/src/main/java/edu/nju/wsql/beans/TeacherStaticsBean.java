package edu.nju.wsql.beans;

import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.User;
import edu.nju.wsql.model.enums.ApprovalStatus;

import java.util.Set;

public class TeacherStaticsBean {
    private String name;
    private int createNum;
    private int publishNum;
    private int homeworkNum;
    private int selectNum;

    public TeacherStaticsBean() {
    }

    public TeacherStaticsBean(User teacher) {
        setName(teacher.getName());
        Set<Course> courseSet = teacher.getCourses();
        createNum = 0;
        publishNum = 0;
        homeworkNum = 0;
        selectNum = 0;
        for (Course course: courseSet) {
            if (course.getStatus() == ApprovalStatus.APPROVED) {
                createNum++;
                for (PublishCourse publishCourse: course.getPublishCourses()) {
                    if (publishCourse.getStatus() == ApprovalStatus.APPROVED) {
                        publishNum++;
                        homeworkNum += publishCourse.getHomeworkRequests().size();
                        selectNum += publishCourse.calcSelectedNum();
                    }
                }
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreateNum() {
        return createNum;
    }

    public void setCreateNum(int createNum) {
        this.createNum = createNum;
    }

    public int getPublishNum() {
        return publishNum;
    }

    public void setPublishNum(int publishNum) {
        publishNum = publishNum;
    }

    public int getHomeworkNum() {
        return homeworkNum;
    }

    public void setHomeworkNum(int homeworkNum) {
        this.homeworkNum = homeworkNum;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }
}
