package edu.advising.transcript;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Unofficial transcript implementation for Issue #39.
 * Displays student academic history with an UNOFFICIAL watermark.
 * Uses the Template Method pattern from TranscriptTemplate.
 */
public class UnofficialTranscriptTemplate extends TranscriptTemplate {

    private final String studentName;
    private final String studentId;
    private final List<SemesterRecord> semesters;
    private final double cumulativeGPA;

    public UnofficialTranscriptTemplate(String studentName, String studentId,
                                         List<SemesterRecord> semesters, double cumulativeGPA) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.semesters = semesters;
        this.cumulativeGPA = cumulativeGPA;
    }

    @Override
    protected String generateHeader() {
        return "═══════════════════════════════════════════════════════════\n" +
               "                 UNIVERSITY OF ADVISING\n" +
               "                    STUDENT TRANSCRIPT\n" +
               "═══════════════════════════════════════════════════════════\n" +
               "\n" +
               "                       UNOFFICIAL\n";
    }

    @Override
    protected String generateStudentInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("STUDENT INFORMATION\n");
        sb.append("───────────────────────────────────────────────────────────\n");
        sb.append("Name: ").append(studentName).append("\n");
        sb.append("Student ID: ").append(studentId).append("\n\n");

        sb.append("ACADEMIC HISTORY\n");
        sb.append("───────────────────────────────────────────────────────────\n");

        for (SemesterRecord semester : semesters) {
            sb.append("\n").append(semester.semesterName()).append("\n");
            sb.append("-----------------------------------------------------------\n");
            sb.append(String.format("%-10s %-25s %7s %6s%n", "CODE", "COURSE NAME", "CREDITS", "GRADE"));
            sb.append("-----------------------------------------------------------\n");

            for (CourseRecord course : semester.courses()) {
                sb.append(String.format("%-10s %-25s %7d %6s%n",
                        course.code(),
                        course.name(),
                        course.credits(),
                        course.grade()));
            }

            sb.append(String.format("%nSemester GPA: %.2f%n", semester.semesterGPA()));
        }

        sb.append("\n───────────────────────────────────────────────────────────\n");
        sb.append(String.format("CUMULATIVE GPA: %.2f%n", cumulativeGPA));

        return sb.toString();
    }

    @Override
    protected String generateCertification() {
        return "───────────────────────────────────────────────────────────\n" +
               "This is an unofficial transcript for student reference only.\n" +
               "Not valid for official use.";
    }

    @Override
    protected String generateIssueDate() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        return "───────────────────────────────────────────────────────────\n" +
               "Generated: " + date;
    }

    @Override
    protected String generateDeliveryInfo() {
        return "═══════════════════════════════════════════════════════════";
    }

    /**
     * Record representing a single course entry.
     */
    public record CourseRecord(String code, String name, int credits, String grade) {}

    /**
     * Record representing a semester with its courses and GPA.
     */
    public record SemesterRecord(String semesterName, List<CourseRecord> courses, double semesterGPA) {}
}
