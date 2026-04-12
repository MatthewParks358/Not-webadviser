package edu.advising.transcript;

/**
 * Represents a student's request for an official transcript.
 * Encapsulates all information needed to generate and deliver a transcript.
 */
public class TranscriptRequest {

    private final String studentName;
    private final String studentId;
    private final String academicRecord;
    private final String deliveryMethod;
    private final String recipient;

    public TranscriptRequest(String studentName, String studentId,
                             String academicRecord, String deliveryMethod,
                             String recipient) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.academicRecord = academicRecord;
        this.deliveryMethod = deliveryMethod;
        this.recipient = recipient;
    }

    /**
     * Generates the official transcript using the Template Method pattern.
     *
     * @return the complete transcript as a String
     */
    public String generateTranscript() {
        TranscriptTemplate template = new OfficialTranscriptTemplate(
                studentName,
                studentId,
                academicRecord,
                deliveryMethod,
                recipient
        );
        return template.generate();
    }

    // Getters

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAcademicRecord() {
        return academicRecord;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getRecipient() {
        return recipient;
    }
}
