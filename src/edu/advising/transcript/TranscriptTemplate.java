package edu.advising.transcript;

/**
 * Abstract template for generating transcripts.
 * Uses the Template Method pattern to define the algorithm structure
 * while allowing subclasses to customize individual steps.
 */
public abstract class TranscriptTemplate {

    /**
     * Template method that defines the algorithm for generating a transcript.
     * This method is final to prevent subclasses from changing the algorithm structure.
     *
     * @return the complete transcript as a String
     */
    public final String generate() {
        StringBuilder transcript = new StringBuilder();

        transcript.append(generateHeader());
        transcript.append("\n");
        transcript.append(generateStudentInfo());
        transcript.append("\n");
        transcript.append(generateCertification());
        transcript.append("\n");
        transcript.append(generateIssueDate());
        transcript.append("\n");
        transcript.append(generateDeliveryInfo());

        return transcript.toString();
    }

    /**
     * Generates the header section of the transcript.
     * @return header content
     */
    protected abstract String generateHeader();

    /**
     * Generates the student information and academic record section.
     * @return student info content
     */
    protected abstract String generateStudentInfo();

    /**
     * Generates the certification section.
     * @return certification content
     */
    protected abstract String generateCertification();

    /**
     * Generates the issue date section.
     * @return issue date content
     */
    protected abstract String generateIssueDate();

    /**
     * Generates delivery information based on the delivery method.
     * @return delivery info content
     */
    protected abstract String generateDeliveryInfo();
}
