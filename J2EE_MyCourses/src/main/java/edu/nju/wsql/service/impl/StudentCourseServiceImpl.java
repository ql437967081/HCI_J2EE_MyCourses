package edu.nju.wsql.service.impl;

import edu.nju.wsql.beans.*;
import edu.nju.wsql.dao.*;
import edu.nju.wsql.model.*;
import edu.nju.wsql.model.enums.Season;
import edu.nju.wsql.service.StudentCourseService;
import edu.nju.wsql.service.utils.ForumUtil;
import edu.nju.wsql.service.utils.TermUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    @Autowired
    private PublishCourseDao publishCourseDao;
    @Autowired
    private SelectCourseDao selectCourseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HomeworkRequestDao homeworkRequestDao;
    @Autowired
    private HomeworkSubmitDao homeworkSubmitDao;
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional
    public StudentPublishCourseListBean getAllCourses(String student) {
        List<StudentTermCourseBean> termCourseList = new ArrayList<>();
        Map<Long, Long> selectedPublishCourse = new HashMap<>();
        for (SelectCourse selectCourse: selectCourseDao.findNonExitByStudentId(student))
            selectedPublishCourse.put(selectCourse.getCourseClass().getPublishCourse().getId(), selectCourse.getId());
        for (PublishCourse publishCourse: publishCourseDao.findAllApprovedPublishCourses()) {
            long id = publishCourse.getId();
            TermCourseInfoBean termCourseInfoBean = new TermCourseInfoBean();
            termCourseInfoBean.setCourse(publishCourse.getCourse());
            termCourseInfoBean.setId(id);
            termCourseInfoBean.setYear(publishCourse.getYear());
            termCourseInfoBean.setSeason(publishCourse.getSeason().toChinese());
            termCourseList.add(new StudentTermCourseBean(
                    termCourseInfoBean,
                    selectedPublishCourse.containsKey(id),
                    selectedPublishCourse.get(id)
                    ));
        }
        return new StudentPublishCourseListBean(termCourseList);
    }

    @Override
    @Transactional
    public long selectCourse(String student, long publishCourseId) {
        if (selectCourseDao.findNonExitByStudentIdAndPublishCourseId(student, publishCourseId) != null) {
            return -2;
        }
        CourseClass courseClass = null;
        double minSelectedRate = 1;
        for (CourseClass cc: publishCourseDao.findOne(publishCourseId).getClasses()) {
            double selectedRate = cc.calcSelectedRate();
            if (selectedRate < minSelectedRate) {
                courseClass = cc;
                minSelectedRate = selectedRate;
            }
        }
        if (courseClass == null) {
            return -1;
        }
        SelectCourse selectCourse = new SelectCourse();
        selectCourse.setStudent(userDao.findOne(student));
        selectCourse.setCourseClass(courseClass);
        selectCourse.setSelectTime(LocalDateTime.now());
        selectCourseDao.save(selectCourse);
        return selectCourse.getId();
    }

    @Override
    @Transactional
    public StudentCourseClassBean getMyCourse(long id) {
        return new StudentCourseClassBean(selectCourseDao.findOne(id));
    }

    @Override
    @Transactional
    public StudentCourseClassBean quitCourse(long id) {
        selectCourseDao.quitCourse(id, LocalDateTime.now());
        SelectCourse selectCourse = selectCourseDao.findOne(id);
        PublishCourse publishCourse = selectCourse.getCourseClass().getPublishCourse();
        StudentCourseClassBean bean = new StudentCourseClassBean();
        bean.setName(publishCourse.getCourse().getName());
        bean.setYear(publishCourse.getYear());
        bean.setSeason(publishCourse.getSeason().toChinese());
        return bean;
    }

    @Override
    @Transactional
    public CourseBean getCoursewaresBySelectCourseId(long id) {
        CourseBean bean = new CourseBean();
        Course course = selectCourseDao.findOne(id).getCourseClass().getPublishCourse().getCourse();
        List<CoursewareBean> coursewares = new ArrayList<>();
        for (Courseware courseware: course.getCoursewares())
            coursewares.add(new CoursewareBean(courseware, course.getCreator().getId()));
        bean.setCoursewares(coursewares);
        return bean;
    }

    @Override
    @Transactional
    public CourseHomeworkListBean getHomeworkListBySelectCourseId(long id) {
        List<HomeworkBean> homeworkBeans = new ArrayList<>();
        for (HomeworkRequest request: selectCourseDao.findOne(id).getCourseClass().getPublishCourse().getHomeworkRequests()) {
            HomeworkBean bean = new HomeworkBean(request);
            if (homeworkSubmitDao.findOneByHomeworkRequestIdAndSelectCourseId(request.getId(), id) == null)
                bean.setSubmitId(-1);
            homeworkBeans.add(bean);
        }
        return new CourseHomeworkListBean(homeworkBeans);
    }

    @Override
    @Transactional
    public HomeworkBean getHomework(long id, long selectCourseId) {
        HomeworkBean bean = new HomeworkBean(homeworkRequestDao.findOne(id));
        HomeworkSubmit submit = homeworkSubmitDao.findOneByHomeworkRequestIdAndSelectCourseId(id, selectCourseId);
        bean.setSubmit(submit);
        return bean;
    }

    @Override
    @Transactional
    public void submitHomework(long id, long selectCourseId, String submitName, String location) {
        HomeworkSubmit homeworkSubmit = homeworkSubmitDao.findOneByHomeworkRequestIdAndSelectCourseId(id, selectCourseId);
        boolean save = false;
        if (homeworkSubmit == null) {
            save = true;
            homeworkSubmit = new HomeworkSubmit();
            homeworkSubmit.setHomeworkRequest(homeworkRequestDao.findOne(id));
            homeworkSubmit.setSelectCourse(selectCourseDao.findOne(selectCourseId));
        }
        homeworkSubmit.setName(submitName);
        homeworkSubmit.setLocation(location);
        if (save)
            homeworkSubmitDao.save(homeworkSubmit);
    }

    @Override
    @Transactional
    public LocalDateTime getDdlByHomeworkRequestId(long id) {
        return homeworkRequestDao.findDdl(id);
    }

    @Override
    @Transactional
    public StudentGradeListBean getAllGrades(long id) {
        List<StudentGradeBean> list = new ArrayList<>();
        for (Grade grade: selectCourseDao.findOne(id).getGrades())
            list.add(new StudentGradeBean(grade));
        return new StudentGradeListBean(list);
    }

    @Override
    @Transactional
    public TopicListBean getAllTopics(long id) {
        return ForumUtil.getAllTopics(selectCourseDao.findOne(id).getCourseClass().getPublishCourse().getCourse());
    }

    @Override
    @Transactional
    public void newTopic(long selectCourseId, String student, String title, String content) {
        topicDao.save(ForumUtil.newTopic(
                selectCourseDao.findOne(selectCourseId).getCourseClass().getPublishCourse().getCourse(),
                userDao.findOne(student),
                title,
                content
                ));
    }

    @Override
    @Transactional
    public TopicBean getTopicPosts(long id) {
        return ForumUtil.getTopicPosts(topicDao.findOne(id));
    }

    @Override
    @Transactional
    public void sayOnTopic(long id, String student, String content) {
        ForumUtil.sayOnTopic(topicDao.findOne(id), userDao.findOne(student), content);
    }

    @Override
    @Transactional
    public StudentPublishCourseListBean getMyCourses(String student) {
        return new StudentPublishCourseListBean(
                wrapTermCourseList(selectCourseDao.findNonExitByStudentId(student)),
                wrapQuitCourseList(selectCourseDao.findExitByStudentId(student)),
                TermUtil.getAllTerms(publishCourseDao.findAllTerms()),
                TermUtil.getCourses(courseDao.findAllIdAndNames()),
                TermUtil.getTeachers(userDao.findAllTeachers())
        );
    }

    @Override
    @Transactional
    public StudentPublishCourseListBean getMyCoursesByTermAndCourseAndTeacher(String student, String term, long course, String teacher) {
        if (term.equals("all")) {
            if (course == -1) {
                List<StudentTermCourseBean> termCourseList = wrapTermCourseList(
                        selectCourseDao.findNonExitByStudentIdAndTeacherId(student, teacher)
                );
                List<StudentQuitCourseBean> quitCourseList = wrapQuitCourseList(
                        selectCourseDao.findExitByStudentIdAndTeacherId(student, teacher)
                );
                Map<Integer, List<Season>> terms = TermUtil.getAllTerms(publishCourseDao.findAllTermsByTeacher(teacher));
                Map<Long, String> courses = TermUtil.getCourses(courseDao.findAllIdAndNamesByTeacher(teacher));
                Map<String, String> teachers = TermUtil.getTeachers(userDao.findAllTeachers());
                return new StudentPublishCourseListBean(
                        termCourseList,
                        quitCourseList,
                        terms, courses, teachers,
                        "all", -1, teacher);
            }
            List<StudentTermCourseBean> termCourseList = wrapTermCourseList(
                    selectCourseDao.findNonExitByStudentIdAndCourseId(student, course)
            );
            List<StudentQuitCourseBean> quitCourseList = wrapQuitCourseList(
                    selectCourseDao.findExitByStudentIdAndCourseId(student, course)
            );
            Map<Integer, List<Season>> terms = TermUtil.getAllTerms(publishCourseDao.findAllTermsByCourseId(course));
            Map<Long, String> courses = TermUtil.getCourses(
                    teacher.equals("all") ? courseDao.findAllIdAndNames() : courseDao.findAllIdAndNamesByTeacher(teacher));
            Map<String, String> teachers = new HashMap<>();
            String courseTeacher = courseDao.findTeacherId(course);
            teachers.put(courseTeacher, userDao.findName(courseTeacher));
            return new StudentPublishCourseListBean(
                    termCourseList,
                    quitCourseList,
                    terms, courses, teachers,
                    "all", course, teacher);
        }
        int year = Integer.parseInt(term.substring(0, 4));
        Season season = Season.chineseToSeason(term.substring(5, 7));
        if (course == -1) {
            if (teacher.equals("all")) {
                List<StudentTermCourseBean> termCourseList = wrapTermCourseList(
                        selectCourseDao.findNonExitByStudentIdAndYearAndSeason(student, year, season)
                );
                List<StudentQuitCourseBean> quitCourseList = wrapQuitCourseList(
                        selectCourseDao.findExitByStudentIdAndYearAndSeason(student, year, season)
                );
                Map<Integer, List<Season>> terms = TermUtil.getAllTerms(publishCourseDao.findAllTerms());
                Map<Long, String> courses = TermUtil.getCourses(publishCourseDao.findAllCourseIdAndCourseNamesByYearAndSeason(year, season));
                Map<String, String> teachers = TermUtil.getTeachers(publishCourseDao.findAllTeacherIdAndTeacherNamesByYearAndSeason(year, season));
                return new StudentPublishCourseListBean(
                        termCourseList,
                        quitCourseList,
                        terms, courses, teachers,
                        term, -1, "all");
            }
            List<StudentTermCourseBean> termCourseList = wrapTermCourseList(
                    selectCourseDao.findNonExitByStudentIdAndYearAndSeasonAndTeacher(student, year, season, teacher)
            );
            List<StudentQuitCourseBean> quitCourseList = wrapQuitCourseList(
                    selectCourseDao.findExitByStudentIdAndYearAndSeasonAndTeacher(student, year, season, teacher)
            );
            Map<Integer, List<Season>> terms = TermUtil.getAllTerms(publishCourseDao.findAllTermsByTeacher(teacher));
            Map<Long, String> courses = TermUtil.getCourses(publishCourseDao.findAllCourseIdAndCourseNamesByYearAndSeasonAndTeacher(year, season, teacher));
            Map<String, String> teachers = TermUtil.getTeachers(publishCourseDao.findAllTeacherIdAndTeacherNamesByYearAndSeason(year, season));
            return new StudentPublishCourseListBean(
                    termCourseList,
                    quitCourseList,
                    terms, courses, teachers,
                    term, -1, teacher);
        }
        List<StudentTermCourseBean> termCourseList = wrapTermCourseList(
                selectCourseDao.findNonExitByStudentIdAndYearAndSeasonAndCourse(student, year, season, course)
        );
        List<StudentQuitCourseBean> quitCourseList = wrapQuitCourseList(
                selectCourseDao.findExitByStudentIdAndYearAndSeasonAndCourse(student, year, season, course)
        );
        Map<Integer, List<Season>> terms = TermUtil.getAllTerms(publishCourseDao.findAllTermsByCourseId(course));
        Map<Long, String> courses = TermUtil.getCourses(
                teacher.equals("all") ?
                        publishCourseDao.findAllCourseIdAndCourseNamesByYearAndSeason(year, season) :
                publishCourseDao.findAllCourseIdAndCourseNamesByYearAndSeasonAndTeacher(year, season, teacher));
        Map<String, String> teachers = new HashMap<>();
        String courseTeacher = courseDao.findTeacherId(course);
        teachers.put(courseTeacher, userDao.findName(courseTeacher));
        return new StudentPublishCourseListBean(
                termCourseList,
                quitCourseList,
                terms, courses, teachers,
                term, course, teacher);
    }

    @Override
    @Transactional
    public boolean isTheCourseISelected(long selectCourseId, String student) {
        SelectCourse selectCourse = selectCourseDao.findOne(selectCourseId);
        if (selectCourse == null)
            return false;
        return selectCourse.getStudent().getId().equals(student);
    }

    @Override
    @Transactional
    public boolean isTheHomeworkOfTheCourse(long homeworkRequestId, long selectCourseId) {
        HomeworkRequest request = homeworkRequestDao.findOne(homeworkRequestId);
        SelectCourse selectCourse = selectCourseDao.findOne(selectCourseId);
        if (request == null || selectCourse == null)
            return false;
        return request.getPublishCourse().getId() == selectCourse.getCourseClass().getPublishCourse().getId();
    }

    // TODO 未提交的作业
    private List<StudentTermCourseBean> wrapTermCourseList(List<SelectCourse> selectCourseList) {
        List<StudentTermCourseBean> termCourseList = new ArrayList<>();
        for (SelectCourse selectCourse: selectCourseList) {
            TermCourseInfoBean termCourseInfoBean = new TermCourseInfoBean();
            PublishCourse publishCourse = selectCourse.getCourseClass().getPublishCourse();
            termCourseInfoBean.setId(publishCourse.getId());
            termCourseInfoBean.setCourse(publishCourse.getCourse());
            termCourseInfoBean.setYear(publishCourse.getYear());
            termCourseInfoBean.setSeason(publishCourse.getSeason().toChinese());
            termCourseInfoBean.setSelectTime(selectCourse.getSelectTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
            termCourseInfoBean.setNotSubmitHomeworkNum(publishCourse.getHomeworkRequests().size() - selectCourse.getSubmits().size());
            termCourseList.add(new StudentTermCourseBean(
                    termCourseInfoBean,
                    true,
                    selectCourse.getId()));
        }
        return termCourseList;
    }

    private List<StudentQuitCourseBean> wrapQuitCourseList(List<SelectCourse> selectCourseList) {
        List<StudentQuitCourseBean> list = new ArrayList<>();
        for (SelectCourse selectCourse: selectCourseList)
            list.add(new StudentQuitCourseBean(selectCourse));
        return list;
    }
}
