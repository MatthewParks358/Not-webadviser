package edu.advising.transcript;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Official transcript implementation.
 * Includes institutional header, registrar certification, and issue date.
 * Does NOT include any unofficial watermark.
 */
public class OfficialTranscriptTemplate extends TranscriptTemplate {

    private final String studentName;
    private final String studentId;
    private final String academicRecord;
    private final String deliveryMethod;
    private final String recipient;

    public OfficialTranscriptTemplate(String studentName, String studentId,
                                       String academicRecord, String deliveryMethod,
                                       String recipient) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.academicRecord = academicRecord;
        this.deliveryMethod = deliveryMethod;
        this.recipient = recipient;
    }

    @Override
    protected String generateHeader() {
        return "═══════════════════════════════════════════════════════════\n" +
               "                 UNIVERSITY OF ADVISING\n" +
               "                  OFFICIAL TRANSCRIPT\n" +
               "═══════════════════════════════════════════════════════════";
    }

    @Override
    protected String generateStudentInfo() {
        return "STUDENT INFORMATION\n" +
               "───────────────────────────────────────────────────────────\n" +
               "Name: " + studentName + "\n" +
               "Student ID: " + studentId + "\n" +
               "\n" +
               "ACADEMIC RECORD\n" +
               "───────────────────────────────────────────────────────────\n" +
               academicRecord;
    }

    @Override
    protected String generateCertification() {
        return "───────────────────────────────────────────────────────────\n" +
               "REGISTRAR CERTIFICATION\n" +
               "I certify that this is an official transcript of the academic\n" +
               "record of the student named above.\n" +
               "\n" +
               "Office of the Registrar\n" +
               "University of Advising";
    }

    @Override
    protected String generateIssueDate() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        return "───────────────────────────────────────────────────────────\n" +
               "Issue Date: " + date;
    }

    @Override
    protected String generateDeliveryInfo() {
        String method = deliveryMethod.equalsIgnoreCase("electronic")
                ? "Electronic Delivery"
                : "Physical Mail";

        return "───────────────────────────────────────────────────────────\n" +
               "DELIVERY INFORMATION\n" +
               "Method: " + method + "\n" +
               "Recipient: " + recipient + "\n" +
               "═══════════════════════════════════════════════════════════";
    }
}
