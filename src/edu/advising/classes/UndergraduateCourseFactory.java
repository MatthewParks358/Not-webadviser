package edu.advising.classes;

import edu.advising.commands.Course;

/**
 * UndergraduateCourseFactory — Concrete Factory (Factory Method Pattern)
 *
 * Pattern Role: ConcreteCreator
 *   Implements createCourse() to build lower-division / undergraduate courses.
 *   Default credit load is 3.0; level is set to "UNDERGRADUATE".
 */
public class UndergraduateCourseFactory extends CourseFactory {

    // PSEUDO: Undergraduate courses default to 3 credits — most common community-college unit load
    private static final int    DEFAULT_CREDITS = 3;
    private static final String LEVEL           = "UNDERGRADUATE";

    // PSEUDO: code and name come from the administrator; credits and level are fixed by this factory
    // PSEUDO: Return the new Course object (not yet saved — saveCourse() in parent handles that)
    @Override
    public Course createCourse(String code, String name, String description, int departmentId) {
        return new Course(code, name, description, DEFAULT_CREDITS, departmentId, LEVEL);
    }
}
