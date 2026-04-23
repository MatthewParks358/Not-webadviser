package edu.advising.enrollment.state.transcript;

import edu.advising.commands.Enrollment;
import edu.advising.enrollment.TranscriptContext;
import edu.advising.enrollment.state.enroll.EnrolledState;
import edu.advising.enrollment.state.enroll.PendingEnrollmentState;

import java.time.LocalDateTime;

public class PendingTranscriptState implements TranscriptState {

    private static final PendingTranscriptState INSTANCE = new PendingTranscriptState();

    private PendingTranscriptState() {}

    public static PendingTranscriptState getInstance() {
        return INSTANCE;
    }

    @Override
    public void beginProcessing(TranscriptContext context) {
        Enrollment enrollment = context.getEnrollment();
        enrollment.setStatus("ENROLLED");
        enrollment.setEnrollmentDate(LocalDateTime.now());
        context.setState(EnrolledState.getInstance());
        context.persist();
    }

    public void cancel(TranscriptContext context)
    {
        // Invalid transition - no-op
    }

    public void markAsReady(TranscriptContext context)
    {
        // Invalid transition - no-op
    }

    public void sendOff(TranscriptContext context)
    {
        // Invalid transition - no-op
    }

    public void markAsFailed(TranscriptContext context)
    {
        // Invalid transition - no-op
    }

    @Override
    public boolean canProcess() {
        return false;
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    @Override
    public boolean canSend() {
        return false;
    }
}
