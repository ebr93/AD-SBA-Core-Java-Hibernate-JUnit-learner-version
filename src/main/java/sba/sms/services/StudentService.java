package sba.sms.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
			tx.commit();

		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			HibernateUtil.shutdown();
		}
		
		return result;
	}
	
	@Override
    public void createStudent(Student student) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			session.merge(student);
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			HibernateUtil.shutdown();
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
			tx.commit();

		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			HibernateUtil.shutdown();
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

		} finally {
			HibernateUtil.shutdown();
		}
    	return result;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
    	Student student = new Student();
    	Course course = new Course();
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			student = session.get(Student.class, email);
			List<Course> courses = student.getCourses();
			for (Course c : courses) {
				if (c.getId() == courseId) {
					System.out.println("Student is already enrolled in class");
					break;
				}
			}
			course = session.get(Course.class, courseId);
			courses.add(course);
			student.setCourses(courses);
			session.merge(student);
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			HibernateUtil.shutdown();
		}
    }

    @Override
    public List<Course> getStudentCourses(String email) {
    	List<Course> result = new ArrayList<>();
    	
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			tx = session.beginTransaction();
			String q = "select s.courses from student s where email = :email";
			Query query = session.createNativeQuery(q, Student.class);
			result = query.setParameter("email", email).getResultList();
			tx.commit();
		} catch (HibernateException ex) {

			ex.printStackTrace();
			tx.rollback();

		} finally {
			HibernateUtil.shutdown();
		}
    	
    	return result;
    }
}
