package edu.nju.wsql.beans;

public class StudentTermCourseBean {
    private TermCourseInfoBean termCourseInfo;
    private boolean selected;
    private long selectCourseId;

    public StudentTermCourseBean() {
    }

    public StudentTermCourseBean(TermCourseInfoBean termCourseInfo, boolean selected, Long selectCourseId) {
        this.termCourseInfo = termCourseInfo;
        this.selected = selected;
        if (selected)
            setSelectCourseId(selectCourseId);
    }

    public TermCourseInfoBean getTermCourseInfo() {
        return termCourseInfo;
    }

    public void setTermCourseInfo(TermCourseInfoBean termCourseInfo) {
        this.termCourseInfo = termCourseInfo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getSelectCourseId() {
        return selectCourseId;
    }

    public void setSelectCourseId(long selectCourseId) {
        this.selectCourseId = selectCourseId;
    }
}
