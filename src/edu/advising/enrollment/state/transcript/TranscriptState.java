package edu.advising.enrollment.state.transcript;

import edu.advising.enrollment.TranscriptContext;
// PENDING, PROCESSING, READY, SENT, CANCELLED, FAILED
public interface TranscriptState {

    // Transitions
    void beginProcessing(TranscriptContext context);
    void cancel(TranscriptContext context);
    void markAsReady(TranscriptContext context);
    void sendOff(TranscriptContext context);
    void markAsFailed(TranscriptContext context);

    // Guards
    boolean canProcess();
    boolean canCancel();
    boolean canSend();
}
