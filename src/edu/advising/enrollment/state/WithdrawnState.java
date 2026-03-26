package edu.advising.enrollment.state;

import edu.advising.enrollment.EnrollmentContext;

/**
 * WithdrawnState - Terminal state, no further transitions allowed
 */
public class WithdrawnState implements EnrollmentState {

    private static final WithdrawnState INSTANCE = new WithdrawnState();

    private WithdrawnState() {}

    public static WithdrawnState getInstance() {
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
