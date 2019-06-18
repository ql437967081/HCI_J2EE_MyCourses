package edu.nju.wsql.dao;

import edu.nju.wsql.model.CourseClass;

public interface CourseClassDao {
    CourseClass findOne(long id);
}
