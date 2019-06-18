package edu.nju.wsql.dao;

import edu.nju.wsql.model.HomeworkSubmit;

public interface HomeworkSubmitDao {
    HomeworkSubmit findOneByHomeworkRequestIdAndSelectCourseId(long homeworkRequestId, long selectCourseId);

    void save(HomeworkSubmit homeworkSubmit);
}
