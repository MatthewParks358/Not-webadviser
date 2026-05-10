package edu.advising.enrollment.state;

import edu.advising.enrollment.EnrollmentContext;

/**
 * EnrollmentState - State interface for Enrollment lifecycle
 */
public interface EnrollmentState {

    // Transitions
    void confirm(EnrollmentContext context);
    void drop(EnrollmentContext context, String reason);
    void withdraw(EnrollmentContext context);
    void complete(EnrollmentContext context, String finalGrade);
    void reenroll(EnrollmentContext context);

    // Guards
    boolean canDrop();
    boolean canWithdraw();
    boolean canComplete();
    boolean canReenroll();
}
