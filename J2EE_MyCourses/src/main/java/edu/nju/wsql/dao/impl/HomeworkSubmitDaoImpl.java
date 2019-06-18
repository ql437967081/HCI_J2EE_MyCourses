package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.HomeworkSubmitDao;
import edu.nju.wsql.model.HomeworkSubmit;
import org.springframework.stereotype.Repository;

@Repository
public class HomeworkSubmitDaoImpl extends AbstractHibernateDAO<HomeworkSubmit> implements HomeworkSubmitDao {
    public HomeworkSubmitDaoImpl() {
        setClazz(HomeworkSubmit.class);
    }

    @Override
    public HomeworkSubmit findOneByHomeworkRequestIdAndSelectCourseId(long homeworkRequestId, long selectCourseId) {
        SelectHelper helper = getSelectHelper();
        helper.criteriaQuery.where(
                helper.criteriaBuilder.and(
                        helper.criteriaBuilder.equal(
                                helper.root.get("homeworkRequest").get("id"),
                                homeworkRequestId
                        ),
                        helper.criteriaBuilder.equal(
                                helper.root.get("selectCourse").get("id"),
                                selectCourseId
                        )
                ));
        return helper.findOne();
    }
}
