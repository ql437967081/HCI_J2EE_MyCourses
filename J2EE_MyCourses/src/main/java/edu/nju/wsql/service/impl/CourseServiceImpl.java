package edu.nju.wsql.service.impl;

import edu.nju.wsql.beans.*;
import edu.nju.wsql.dao.*;
import edu.nju.wsql.model.*;
import edu.nju.wsql.model.enums.ApprovalStatus;
import edu.nju.wsql.model.enums.Season;
import edu.nju.wsql.service.CourseService;
import edu.nju.wsql.service.utils.EmailUtil;
import edu.nju.wsql.service.utils.ForumUtil;
import edu.nju.wsql.service.utils.TermUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CoursewareDao coursewareDao;
    @Autowired
    private PublishCourseDao publishCourseDao;
    @Autowired
    private HomeworkRequestDao homeworkRequestDao;
    @Autowired
    private CourseGradeDao courseGradeDao;
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private CourseClassDao courseClassDao;

    @Override
    @Transactional
    public long createNewCourse(String name, String teacher, Set<Courseware> coursewares) {
        Course course = new Course();
        course.setName(name);
        course.setCreator(userDao.findOne(teacher));
        for (Courseware courseware: coursewares)
            courseware.setCourse(course);
        course.setCoursewares(coursewares);
        course.setStatus(ApprovalStatus.SUBMIT);
        courseDao.save(course);
        return course.getId();
    }

    @Override
    @Transactional
    public CourseBean getCourseInfo(long id) {
        return new CourseBean(courseDao.findOne(id));
    }

    @Override
    @Transactional
    public String getCourseName(long id) {
        return courseDao.getName(id);
    }

    @Override
    @Transactional
    public boolean existCourse(String name, String teacher) {
        return courseDao.findOneByNameAndTeacher(name, teacher) != null;
    }

    @Override
    @Transactional
    public void removeCourseware(long wareId) {
        coursewareDao.deleteById(wareId);
    }

    @Override
    @Transactional
    public void addCoursewares(long id, Set<Courseware> coursewares) {
        Course course = courseDao.findOne(id);
        Set<Courseware> previousCoursewares = course.getCoursewares();
        for (Courseware courseware: coursewares) {
            courseware.setCourse(course);
            previousCoursewares.add(courseware);
        }
    }

    @Override
    @Transactional
    public PublishCourseBean getPublishInfo(String creator) {
        PublishCourseBean bean = new PublishCourseBean();
        LocalDate now = LocalDate.now();
        bean.setYear(now.getYear());
        bean.setSeason(Season.getSeason(now.getMonthValue()).toChinese());
        List<CourseBean> courseList = new ArrayList<>();
        for (Course course: courseDao.findCoursesByCreator(creator)) {
            if (course.getStatus() == ApprovalStatus.APPROVED)
                courseList.add(new CourseBean(course));
        }
        bean.setCourseList(courseList);
        return bean;
    }

    @Override
    @Transactional
    public long publishCourse(int year, Season season, long course, Set<CourseClass> classes) {
        PublishCourse publishCourse = new PublishCourse();
        publishCourse.setYear(year);
        publishCourse.setSeason(season);
        publishCourse.setCourse(courseDao.findOne(course));
        for (CourseClass courseClass: classes)
            courseClass.setPublishCourse(publishCourse);
        publishCourse.setClasses(classes);
        publishCourse.setStatus(ApprovalStatus.SUBMIT);
        publishCourseDao.save(publishCourse);
        return publishCourse.getId();
    }

    @Override
    @Transactional
    public boolean existPublish(int year, Season season, long course) {
        return publishCourseDao.findOneByYearAndSeasonAndCourse(year, season, course) != null;
    }

    @Override
    @Transactional
    public TermCourseInfoBean getTermCourseInfo(long id) {
        return new TermCourseInfoBean(publishCourseDao.findOne(id));
    }

    @Override
    @Transactional
    public CourseListBean getCoursesICreated(String teacher) {
        HashMap<Long, String> courses = new HashMap<>();
        for (Course course: courseDao.findCoursesByCreator(teacher))
            courses.put(course.getId(), course.getName());
        return new CourseListBean(courses);
    }

    @Override
    @Transactional
    public void readRejectedCourse(long id) {
        courseDao.readRejectedCourse(id);
    }

    @Override
    @Transactional
    public void readRejectedPublishCourse(long id) {
        publishCourseDao.readRejectedPublishCourse(id);
    }

    @Override
    @Transactional
    public void publishHomework(long id, String title, String content, String ddl, String fileMaxSize, String fileType) {
        HomeworkRequest homeworkRequest = new HomeworkRequest();
        homeworkRequest.setPublishCourse(publishCourseDao.findOne(id));
        homeworkRequest.setTitle(title);
        homeworkRequest.setContent(content);
        homeworkRequest.setDdl(LocalDateTime.parse(ddl));
        homeworkRequest.setFileMaxSize(Integer.parseInt(fileMaxSize));
        homeworkRequest.setFileType(fileType);
        homeworkRequestDao.save(homeworkRequest);
    }

    @Override
    @Transactional
    public CourseHomeworkListBean getHomeworkList(long id, boolean gradeNeed) {
        List<HomeworkBean> list = new ArrayList<>();
        PublishCourse publishCourse = publishCourseDao.findOne(id);
        for (HomeworkRequest homeworkRequest: publishCourse.getHomeworkRequests()) {
            HomeworkBean bean = new HomeworkBean(homeworkRequest);
            bean.setSubmitNum(homeworkRequest.calcSubmitNum());
            bean.setSelectCourseNum(homeworkRequest.getPublishCourse().calcSelectedNum());
            list.add(bean);
        }
        CourseHomeworkListBean courseHomeworkListBean = new CourseHomeworkListBean(list);
        if (gradeNeed) {
            List<CourseGradeBean> gradeList = new ArrayList<>();
            for (CourseGrade grade: publishCourse.getCourseGrades())
                gradeList.add(new CourseGradeBean(grade, false));
            courseHomeworkListBean.setGradeBeans(gradeList);
        }
        return courseHomeworkListBean;
    }

    @Override
    @Transactional
    public Map<String, String> getHomeworkSubmitsByHomeworkRequestId(long id) {
        Map<String, String> map = new HashMap<>();
        for (HomeworkSubmit submit: homeworkRequestDao.findOne(id).getSubmits()) {
            SelectCourse selectCourse = submit.getSelectCourse();
            if (selectCourse.getQuitTime() == null)
                map.put(selectCourse.getStudent().getId(), submit.getLocation());
        }
        return map;
    }

    @Override
    @Transactional
    public String getHomeworkTitle(long id) {
        return homeworkRequestDao.findTitle(id);
    }

    @Override
    @Transactional
    public void groupEmail(long publishCourseId, String title, String content) {
        PublishCourse publishCourse = publishCourseDao.findOne(publishCourseId);
        Course course = publishCourse.getCourse();
        String teacher = course.getCreator().getName();
        String name = course.getName();
        String term = publishCourse.calcTerm();
        for (CourseClass courseClass: publishCourse.getClasses()) {
            for (SelectCourse selectCourse: courseClass.getSelectCourses()) {
                EmailUtil.sendEmail(
                        selectCourse.getStudent().getEmail(),
                        title,
                        name + "（" + term + "）" + System.lineSeparator() +
                        "教师：" + teacher + System.lineSeparator() +
                        content);
            }
        }
    }

    @Override
    @Transactional
    public void publishGrade(long id, long project, String remark, boolean open, String location, Map<String, Double> grades) {
        PublishCourse publishCourse = publishCourseDao.findOne(id);
        HomeworkRequest homeworkRequest = null;
        if (project != -1)
            homeworkRequest = homeworkRequestDao.findOne(project);
        CourseGrade courseGrade = new CourseGrade();
        courseGrade.setPublishCourse(publishCourse);
        courseGrade.setHomeworkRequest(homeworkRequest);
        courseGrade.setRemark(remark);
        courseGrade.setOpen(open);
        courseGrade.setLocation(location);
        Set<Grade> set = new HashSet<>();
        for (CourseClass courseClass: publishCourse.getClasses()) {
            for (SelectCourse selectCourse: courseClass.getSelectCourses()) {
                if (selectCourse.getQuitTime() == null) {
                    String student = selectCourse.getStudent().getId();
                    double score = 0;
                    if (grades.containsKey(student))
                        score = grades.get(student);
                    set.add(new Grade(courseGrade, selectCourse, score));
                }
            }
        }
        courseGrade.setGrades(set);
        courseGradeDao.save(courseGrade);
    }

    @Override
    @Transactional
    public TopicListBean getAllTopics(long id) {
        return ForumUtil.getAllTopics(courseDao.findOne(id));
    }

    @Override
    @Transactional
    public void newTopic(long courseId, String teacher, String title, String content) {
        topicDao.save(ForumUtil.newTopic(
                courseDao.findOne(courseId),
                userDao.findOne(teacher),
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
    public void sayOnTopic(long id, String teacher, String content) {
        ForumUtil.sayOnTopic(topicDao.findOne(id), userDao.findOne(teacher), content);
    }

    @Override
    @Transactional
    public PublishApprovalListBean getMyPublishCourses(String teacher) {
        List<TermCourseInfoBean> beans = new ArrayList<>();
        for (PublishCourse publishCourse: publishCourseDao.findAllApprovedPublishCoursesByTeacher(teacher)) {
            TermCourseInfoBean bean = wrapSimpleTermCourseInfo(publishCourse);
            bean.setId(publishCourse.getId());
            bean.setNums(publishCourse);
            beans.add(bean);
        }
        return new PublishApprovalListBean(beans, getAllTerms());
    }

    @Override
    @Transactional
    public PublishApprovalListBean getMyPublishCourses(String teacher, int year, Season season) {
        List<TermCourseInfoBean> beans = new ArrayList<>();
        for (PublishCourse publishCourse: publishCourseDao.findAllApprovedPublishCoursesByTeacherAndYearAndSeason(teacher, year, season)) {
            TermCourseInfoBean bean = wrapSimpleTermCourseInfo(publishCourse);
            bean.setId(publishCourse.getId());
            bean.setNums(publishCourse);
            beans.add(bean);
        }
        return new PublishApprovalListBean(beans, getAllTerms(), year, season);
    }

    @Override
    @Transactional
    public SelectCourseListBean getSelectInfoOfClass(long id) {
        SelectCourseListBean selectCourseListBean = new SelectCourseListBean();
        CourseClass courseClass = courseClassDao.findOne(id);
        selectCourseListBean.setCourseClass(courseClass);
        return selectCourseListBean;
    }

    @Override
    @Transactional
    public TeacherStaticsListBean getAllTeacherStatics() {
        List<TeacherStaticsBean> list = new ArrayList<>();
        for (User teacher: userDao.getAllTeachers())
            list.add(new TeacherStaticsBean(teacher));
        return new TeacherStaticsListBean(list);
    }

    @Override
    @Transactional
    public StudentStaticsBean getStudentStatics() {
        return new StudentStaticsBean(userDao.getAllStudents());
    }

    private Map<Integer, List<Season>> getAllTerms() {
        return TermUtil.getAllTerms(publishCourseDao.findAllTerms());
    }

    private TermCourseInfoBean wrapSimpleTermCourseInfo(PublishCourse publishCourse) {
        return TermUtil.wrapSimpleTermCourseInfo(publishCourse);
    }
}
