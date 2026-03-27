package edu.advising.enrollment;

import edu.advising.commands.Enrollment;
import edu.advising.commands.Section;
import edu.advising.enrollment.state.enroll.EnrollmentState;
import edu.advising.enrollment.state.transcript.TranscriptState;
import edu.advising.notifications.NotificationManager;

public class TranscriptContext {
    private Transcript transcript;
    private TranscriptState state;
    private NotificationManager notificationManager;


}
