package sba.sms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.management.RuntimeErrorException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.query.Query;

import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService implements StudentI {
	@Override 
    public List<Student> getAllStudents() {
		List<Student> result = new ArrayList<>();
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			String q = "from Student s";
			Query<Student> query = session.createQuery(q, Student.class);
			result = query.getResultList();
			
			// System.out.println(result);
			
			tx.commit();
			return result;
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			session.close();
		}
		
		return result;
	}
	
	@Override
    public void createStudent(Student student) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			session.persist(student);
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			session.close();
		}
	}

	@Override
    public Student getStudentByEmail(String email) {
		Student result = new Student();
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			result = session.get(Student.class, email);
			// might not need
			// System.out.println(result);
			if (result == null) {
				throw new NoSuchElementException("No such email exists in the system");
			}
			tx.commit();
			// might not need
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();
			// might not need
		} catch (NoSuchElementException ex) {
			System.out.println(ex.getMessage());
			tx.rollback();

		} finally {
			session.close();
		}
		
		return result;
	}

	@Override
    public boolean validateStudent(String email, String password) {
    	boolean result = false;
		Student student = new Student();
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			student = session.get(Student.class, email);
			if (student.getPassword().equals(password)) {
				result = true;
			}
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} catch (NullPointerException ex) {
			System.out.println("Wrong Credentials");
			tx.rollback();

		} finally {
			//HibernateUtil.shutdown();
			session.close();
		}
    	return result;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
	    	Student student = session.get(Student.class, email);
	    	Course course = session.get(Course.class, courseId);
			// student = session.get(Student.class, email);
			
			//System.out.println(student);
			
			List<Course> courses = student.getCourses();
			for (Course c : courses) {
				if (c.getId() == courseId) {
					// might change
					throw new RuntimeException("Student already registered to course with ID " + courseId);
				}
			}
			// might change
			// course = session.get(Course.class, courseId);
			
			//System.out.println(course);
			//System.out.println("courses size: " + courses.size());
			
			if (course == null) {
				throw new NoSuchElementException("Course does not exist");
			}
			courses.add(course);
			student.setCourses(courses);
			session.merge(student);
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();
		// might change
		} catch (NoSuchElementException ex) {
			System.out.println(ex.getMessage());
			tx.rollback();

		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
			tx.rollback();

		} finally {
			//HibernateUtil.shutdown();
			session.close();
		}
    }

    @Override
    public List<Course> getStudentCourses(String email) {
    	List<Course> result = new ArrayList<>();
    	
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			String q = "select * from student s where email = :email";
			Query query = session.createNativeQuery(q, Student.class);
					query.setParameter("email", email);
			List<Student> students = query.getResultList();
			Student student = students.get(0);
			result = student.getCourses();
			// System.out.println("getStudentCourses print student: " + student);
			// System.out.println("courses size: " + result.size());
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();

		} catch (NullPointerException ex) {
			System.out.println("Value is null");
			ex.printStackTrace();
			tx.rollback();

		} finally {
			//HibernateUtil.shutdown();
			session.close();
		}
    	
    	return result;
    }
}
