package edu.nju.wsql.beans;

import edu.nju.wsql.model.enums.Season;
import edu.nju.wsql.service.utils.SortUtil;

import java.util.*;

public class PublishApprovalListBean {
    private List<TermCourseInfoBean> termCourses;
    private List<String> terms;
    private String selection;

    public PublishApprovalListBean() {
    }

    public PublishApprovalListBean(List<TermCourseInfoBean> termCourses) {
        this.termCourses = termCourses;
    }

    public PublishApprovalListBean(List<TermCourseInfoBean> beans, Map<Integer, List<Season>> allTerms) {
        this(beans);
        setTerms(SortUtil.getSortedTerms(allTerms));
        setSelection("全部");
    }

    public PublishApprovalListBean(List<TermCourseInfoBean> beans, Map<Integer, List<Season>> allTerms, int year, Season season) {
        this(beans, allTerms);
        setSelection(year + "年" + season.toChinese() + "学期");
    }

    public List<TermCourseInfoBean> getTermCourses() {
        return termCourses;
    }

    public void setTermCourses(List<TermCourseInfoBean> termCourses) {
        this.termCourses = termCourses;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
