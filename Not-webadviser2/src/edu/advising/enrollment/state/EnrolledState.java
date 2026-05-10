package edu.advising.enrollment.state;

import edu.advising.commands.Enrollment;
import edu.advising.enrollment.EnrollmentContext;

import java.time.LocalDateTime;

/**
 * EnrolledState - Active enrollment state
 */
public class EnrolledState implements EnrollmentState {

    private static final EnrolledState INSTANCE = new EnrolledState();

    private EnrolledState() {}

    public static EnrolledState getInstance() {
        return INSTANCE;
    }

    @Override
    public void confirm(EnrollmentContext context) {
        // Already enrolled - no-op
    }

    @Override
    public void drop(EnrollmentContext context, String reason) {
        Enrollment enrollment = context.getEnrollment();
        enrollment.setStatus("DROPPED");
        enrollment.setDropReason(reason);
        enrollment.setDroppedAt(LocalDateTime.now());
        context.setState(DroppedState.getInstance());
        context.persist();
        // NOTE: No existing notification method for drop. Add if needed.
    }

    @Override
    public void withdraw(EnrollmentContext context) {
        Enrollment enrollment = context.getEnrollment();
        enrollment.setStatus("WITHDRAWN");
        context.setState(WithdrawnState.getInstance());
        context.persist();
        // NOTE: No existing notification method for withdraw. Add if needed.
    }

    @Override
    public void complete(EnrollmentContext context, String finalGrade) {
        Enrollment enrollment = context.getEnrollment();
        enrollment.setStatus("COMPLETED");
        enrollment.setFinalGrade(finalGrade);
        enrollment.setGradedAt(LocalDateTime.now());
        context.setState(CompletedState.getInstance());
        context.persist();
    }

    @Override
    public void reenroll(EnrollmentContext context) {
        // Already enrolled - no-op
    }

    @Override
    public boolean canDrop() {
        return true;
    }

    @Override
    public boolean canWithdraw() {
        return true;
    }

    @Override
    public boolean canComplete() {
        return true;
    }

    @Override
    public boolean canReenroll() {
        return false;
    }
}
