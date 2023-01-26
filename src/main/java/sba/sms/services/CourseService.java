package sba.sms.services;

import java.util.ArrayList;
import java.util.List;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;

public class CourseService implements CourseI {
    @Override
	public void createCourse(Course course) {
    	
    }
    
    @Override
    public Course getCourseById(int courseId) {
    	Course result = new Course();
    	
    	return result;
    }
    
    @Override
    public List<Course> getAllCourses() {
    	List<Course> result = new ArrayList<>();
    	
    	return null;
    }
}
