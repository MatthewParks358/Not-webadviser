package edu.advising.classes;

import edu.advising.commands.Course;

/**
 * GraduateCourseFactory — Concrete Factory (Factory Method Pattern)
 *
 * Pattern Role: ConcreteCreator
 *   Implements createCourse() to build upper-division / graduate courses.
 *   Default credit load is 4.0; level is set to "GRADUATE".
 */
public class GraduateCourseFactory extends CourseFactory {

    private static final int    DEFAULT_CREDITS = 4;
    private static final String LEVEL           = "GRADUATE";

    // PSEUDO: createCourse — build a Course with graduate defaults
    // PSEUDO: Same signature as parent's factory method, just different constants injected
    @Override
    public Course createCourse(String code, String name, String description, int departmentId) {
        return new Course(code, name, description, DEFAULT_CREDITS, departmentId, LEVEL);
    }
}
