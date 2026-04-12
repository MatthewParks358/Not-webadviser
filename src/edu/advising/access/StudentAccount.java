package edu.advising.access;

import edu.advising.users.User;

import java.util.EnumSet;

/**
 * StudentAccount - Concrete account for students.
 *
 * Provides default access to student-specific features like registration,
 * viewing grades, financial aid, and library access.
 */
public class StudentAccount extends BaseAccount {

    private static final EnumSet<Feature> STUDENT_FEATURES = EnumSet.of(
            Feature.REGISTRATION,
            Feature.VIEW_GRADES,
            Feature.VIEW_SCHEDULE,
            Feature.FINANCIAL_AID,
            Feature.MAKE_PAYMENT,
            Feature.LIBRARY
    );

    public StudentAccount(User user) {
        super(user, STUDENT_FEATURES);
    }

    @Override
    protected String getAccountType() {
        return "Student";
    }
}
