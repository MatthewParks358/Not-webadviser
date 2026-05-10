package edu.advising.enrollment.state;

import edu.advising.commands.Enrollment;
import edu.advising.enrollment.EnrollmentContext;

/**
 * DroppedState - Student dropped from course, can reenroll if capacity allows
 */
public class DroppedState implements EnrollmentState {

    private static final DroppedState INSTANCE = new DroppedState();

    private DroppedState() {}

    public static DroppedState getInstance() {
        return INSTANCE;
    }

    @Override
    public void confirm(EnrollmentContext context) {
        // Invalid transition - no-op
    }

    @Override
    public void drop(EnrollmentContext context, String reason) {
        // Already dropped - no-op
    }

    @Override
    public void withdraw(EnrollmentContext context) {
        // Invalid transition - no-op
    }

    @Override
    public void complete(EnrollmentContext context, String finalGrade) {
        // Invalid transition - no-op
    }

    @Override
    public void reenroll(EnrollmentContext context) {
        // Capacity check is done via canReenroll() in EnrollmentContext
        Enrollment enrollment = context.getEnrollment();
        enrollment.setStatus("ENROLLED");
        context.setState(EnrolledState.getInstance());
        context.persist();
    }

    @Override
    public boolean canDrop() {
        return false;
    }

    @Override
    public boolean canWithdraw() {
        return false;
    }

    @Override
    public boolean canComplete() {
        return false;
    }

    @Override
    public boolean canReenroll() {
        return true;
    }
}
