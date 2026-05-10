package edu.advising.enrollment.state;

import edu.advising.enrollment.EnrollmentContext;

/**
 * CompletedState - Terminal state, course completed with final grade
 */
public class CompletedState implements EnrollmentState {

    private static final CompletedState INSTANCE = new CompletedState();

    private CompletedState() {}

    public static CompletedState getInstance() {
        return INSTANCE;
    }

    @Override
    public void confirm(EnrollmentContext context) {
        // Terminal state - no-op
    }

    @Override
    public void drop(EnrollmentContext context, String reason) {
        // Terminal state - no-op
    }

    @Override
    public void withdraw(EnrollmentContext context) {
        // Terminal state - no-op
    }

    @Override
    public void complete(EnrollmentContext context, String finalGrade) {
        // Terminal state - no-op
    }

    @Override
    public void reenroll(EnrollmentContext context) {
        // Terminal state - no-op
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
