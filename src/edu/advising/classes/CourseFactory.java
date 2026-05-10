package edu.advising.classes;

import edu.advising.commands.Course;
import edu.advising.core.DatabaseManager;

import java.sql.SQLException;

 /**
 *   - declares the factory method createCourse(), which subclasses must implement.
 *   - offerCourse() is the template method, it calls createCourse() then uses result.
 *   - subclasses decide what kind of course to use (undergrad vs. graduate),
 *     but the save/announce logic lives here once.
 */
public abstract class CourseFactory {

    public abstract Course createCourse(String code, String name, String description, int departmentId);

    /**
     * Template method called by an administrator to create and publish a new class.
     * Uses the factory method internally; callers never need to know which subtype is used.
     */
    public Course offerCourse(String code, String name, String description, int departmentId) {
        Course course = createCourse(code, name, description, departmentId);

        saveCourse(course);

        System.out.printf("[CourseFactory] New %s course offered: %s — %s%n",
                course.getLevel(), course.getCode(), course.getName());

        return course;
    }


    protected void saveCourse(Course course) {
        try {
            DatabaseManager.getInstance().upsert(course);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("[CourseFactory] Failed to save course '" + course.getCode() + "': " + e.getMessage());
            throw new RuntimeException("Course creation failed", e);
        }
    }
}
