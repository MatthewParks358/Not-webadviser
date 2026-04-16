package edu.advising.access;

import edu.advising.users.User;

import java.util.EnumSet;

/**
 * FacultyAccount - Concrete account for faculty members.
 *
 * Provides default access to faculty-specific features like viewing rosters,
 * entering grades, and dropping students from courses.
 */
public class FacultyAccount extends BaseAccount {

    private static final EnumSet<Feature> FACULTY_FEATURES = EnumSet.of(
            Feature.VIEW_ROSTER,
            Feature.ENTER_GRADES,
            Feature.VIEW_SCHEDULE,
            Feature.DROP_STUDENTS,
            Feature.LIBRARY
    );

    public FacultyAccount(User user) {
        super(user, FACULTY_FEATURES);
    }

    @Override
    protected String getAccountType() {
        return "Faculty";
    }
}
