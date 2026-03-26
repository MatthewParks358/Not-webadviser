package edu.advising.enrollment.state;

import edu.advising.commands.Enrollment;
import edu.advising.enrollment.EnrollmentContext;

import java.time.LocalDateTime;

/**
 * PendingEnrollmentState - Initial state before enrollment is confirmed
 */
public class PendingEnrollmentState implements EnrollmentState {

    private static final PendingEnrollmentState INSTANCE = new PendingEnrollmentState();

    private PendingEnrollmentState() {}

    public static PendingEnrollmentState getInstance() {
        return INSTANCE;
    }

    @Override
    public void confirm(EnrollmentContext context) {
        Enrollment enrollment = context.getEnrollment();
        enrollment.setStatus("ENROLLED");
        enrollment.setEnrollmentDate(LocalDateTime.now());
        context.setState(EnrolledState.getInstance());
        context.persist();
    }

    @Override
    public void drop(EnrollmentContext context, String reason) {
        // Invalid transition - no-op
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
        // Invalid transition - no-op
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
        return false;
    }
}
