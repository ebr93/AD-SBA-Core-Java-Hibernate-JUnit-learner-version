package sba.sms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

public class CourseService implements CourseI {
    @Override
	public void createCourse(Course course) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			session.persist(course);
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			session.close();
		}	
    }
    
    @Override
    public Course getCourseById(int courseId) {
    	Course result = new Course();
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			result = session.get(Course.class, courseId);
			if (result == null) {
				throw new NoSuchElementException("No such course exists");
			}
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();

		} catch (NoSuchElementException ex) {
			System.out.println(ex.getMessage());
			tx.rollback();

		} finally {
			//HibernateUtil.shutdown();
			session.close();
		}
		return result;
    }
    
    @Override
    public List<Course> getAllCourses() {
    	List<Course> result = new ArrayList<>();
    	
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			String q = "from Course";
			Query<Course> query = session.createQuery(q, Course.class);
			result = query.getResultList();
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			//HibernateUtil.shutdown();
			session.close();
		}	
    	
    	return result;
    }
}
