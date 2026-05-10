package edu.advising.enrollment.state;

/**
 * StateFactory - Maps status strings to EnrollmentState instances
 */
public class StateFactory {

    public static EnrollmentState enrollmentStateFor(String status) {
        if (status == null) {
            return PendingEnrollmentState.getInstance();
        }
        switch (status.toUpperCase()) {
            case "PENDING":
                return PendingEnrollmentState.getInstance();
            case "ENROLLED":
                return EnrolledState.getInstance();
            case "DROPPED":
                return DroppedState.getInstance();
            case "WITHDRAWN":
                return WithdrawnState.getInstance();
            case "COMPLETED":
                return CompletedState.getInstance();
            default:
                throw new IllegalArgumentException("Unknown enrollment status: " + status);
        }
    }
}
