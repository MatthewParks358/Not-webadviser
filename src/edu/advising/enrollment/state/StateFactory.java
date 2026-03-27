package edu.advising.enrollment.state;

import edu.advising.enrollment.state.enroll.*;
import edu.advising.enrollment.state.transcript.TranscriptState;

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

    public static TranscriptState transcriptStateFor(String status) {
        if (status == null) {
            return PendingEnrollmentState.getInstance();
        }
        switch (status.toUpperCase()) {
            case "PENDING":
                return PendingEnrollmentState.getInstance();
            case "PROCESSING":
                return EnrolledState.getInstance();
            case "READY":
                return DroppedState.getInstance();
            case "SENT":
                return WithdrawnState.getInstance();
            case "CANCELLED":
                return CompletedState.getInstance();
            case "FAILED":
                return CompletedState.getInstance();
            default:
                throw new IllegalArgumentException("Unknown enrollment status: " + status);
        }
    }
}
