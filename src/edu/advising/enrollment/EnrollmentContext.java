package edu.advising.enrollment;

import edu.advising.commands.Enrollment;
import edu.advising.commands.Section;
import edu.advising.core.DatabaseManager;
import edu.advising.enrollment.state.EnrollmentState;
import edu.advising.enrollment.state.StateFactory;
import edu.advising.notifications.NotificationManager;

import java.sql.SQLException;

/**
 * EnrollmentContext - Wraps Enrollment and manages state transitions
 */
public class EnrollmentContext {

    private Enrollment enrollment;
    private EnrollmentState state;
    private Section section;
    private NotificationManager notificationManager;

    private EnrollmentContext(Enrollment enrollment, Section section) {
        this.enrollment = enrollment;
        this.section = section;
        this.state = StateFactory.enrollmentStateFor(enrollment.getStatus());
        this.notificationManager = NotificationManager.getInstance();
    }

    // Factory methods
    public static EnrollmentContext create(int studentId, int sectionId, Section section) {
        Enrollment enrollment = new Enrollment(studentId, sectionId);
        enrollment.setStatus("PENDING");
        return new EnrollmentContext(enrollment, section);
    }

    public static EnrollmentContext load(Enrollment enrollment, Section section) {
        return new EnrollmentContext(enrollment, section);
    }

    public static EnrollmentContext loadByStudentAndSection(int studentId, Section section) throws SQLException {
        Enrollment enrollment = DatabaseManager.getInstance()
                .fetchOne(Enrollment.class, "student_id", studentId);
        if (enrollment == null || enrollment.getSectionId() != section.getId()) {
            return null;
        }
        return new EnrollmentContext(enrollment, section);
    }

    // State management
    public void setState(EnrollmentState newState) {
        this.state = newState;
    }

    public EnrollmentState getState() {
        return state;
    }

    // Delegated transitions
    public void confirm() {
        state.confirm(this);
    }

    public void drop(String reason) {
        state.drop(this, reason);
    }

    public void withdraw() {
        state.withdraw(this);
    }

    public void complete(String finalGrade) {
        state.complete(this, finalGrade);
    }

    public void reenroll() {
        state.reenroll(this);
    }

    // Delegated guards
    public boolean canDrop() {
        return state.canDrop();
    }

    public boolean canWithdraw() {
        return state.canWithdraw();
    }

    public boolean canComplete() {
        return state.canComplete();
    }

    public boolean canReenroll() {
        return state.canReenroll() && section.hasCapacity();
    }

    // Persistence
    public void persist() {
        try {
            DatabaseManager.getInstance().upsert(enrollment);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Failed to persist enrollment: " + e.getMessage());
        }
    }

    // Accessors
    public Enrollment getEnrollment() {
        return enrollment;
    }

    public Section getSection() {
        return section;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public int getStudentId() {
        return enrollment.getStudentId();
    }

    public String getStatus() {
        return enrollment.getStatus();
    }
}
