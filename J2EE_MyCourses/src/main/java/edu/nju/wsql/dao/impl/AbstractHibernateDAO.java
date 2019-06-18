package edu.nju.wsql.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDAO<T extends Serializable> {
    private Class< T > clazz;

    @Autowired
    private SessionFactory sessionFactory;

    void setClazz(final Class<T> clazzToSet){
        clazz = clazzToSet;
    }

    <R> R getOneAttribute(final Serializable id, final String attribute, final Class<R> attributeClass) {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<R> criteriaQuery = criteriaBuilder.createQuery(attributeClass);
        Root<T> root = criteriaQuery.from(clazz);
        Path path = root;
        for (String attr: attribute.split("\\.")) {
            path = path.get(attr);
        }
        criteriaQuery.select(path);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        Query<R> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        List<R> list = query.getResultList();
        return list.get(0);
    }

    public T findOne( final Serializable id ){
        return (T) getCurrentSession().get( clazz, id );
    }
    public List< T > findAll(){
        Session session = getCurrentSession();
        CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);
        return session.createQuery(criteriaQuery).getResultList();
    }

    public void save( final T entity ){
        getCurrentSession().persist( entity );
    }

    public T update( final T entity ){
        return (T) getCurrentSession().merge( entity );
    }

    public void delete( final T entity ){
        getCurrentSession().delete( entity );
    }
    public void deleteById( final Serializable id ){
        final T entity = findOne( id);
        delete( entity );
    }

    public long getCount() {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(criteriaBuilder.count(root));
        Query<Long> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        List<Long> list = query.getResultList();
        return list.get(0);
    }

    final Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    final UpdateHelper getUpdateHelper() {
        return new UpdateHelper();
    }

    final class UpdateHelper {
        Session session;
        CriteriaBuilder criteriaBuilder;
        CriteriaUpdate<T> criteriaUpdate;
        Root<T> root;
        private UpdateHelper() {
            session = getCurrentSession();
            criteriaBuilder = session.getCriteriaBuilder();
            criteriaUpdate = criteriaBuilder.createCriteriaUpdate(clazz);
            root = criteriaUpdate.from(clazz);
        }
        int execute() {
            Query query = session.createQuery(criteriaUpdate);
            return query.executeUpdate();
        }
    }

    final SelectHelper getSelectHelper() {
        return new SelectHelper();
    }

    final class SelectHelper {
        Session session;
        CriteriaBuilder criteriaBuilder;
        CriteriaQuery<T> criteriaQuery;
        Root<T> root;
        private SelectHelper() {
            session = getCurrentSession();
            criteriaBuilder = session.getCriteriaBuilder();
            criteriaQuery = criteriaBuilder.createQuery(clazz);
            root = criteriaQuery.from(clazz);
            criteriaQuery.select(root);
        }

        T findOne() {
            Query<T> query = session.createQuery(criteriaQuery);
            query.setMaxResults(1);
            List<T> list = query.getResultList();
            return list.size() == 1 ? list.get(0) : null;
        }

        List<T> findAll() {
            Query<T> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    final MultiselectHelper getMultiselectHelper() {
        return new MultiselectHelper();
    }

    final class MultiselectHelper {
        Session session;
        CriteriaBuilder criteriaBuilder;
        CriteriaQuery criteriaQuery;
        Root<T> root;
        private MultiselectHelper() {
            session = getCurrentSession();
            criteriaBuilder = session.getCriteriaBuilder();
            criteriaQuery = criteriaBuilder.createQuery();
            root = criteriaQuery.from(clazz);
        }
        List<Object[]> findAll() {
            Query<Object[]> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }
}
