package edu.advising.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.advising.core.DatabaseManager;
import edu.advising.core.Table;
import edu.advising.enrollment.EnrollmentContext;
import edu.advising.notifications.ObservableStudent;
import edu.advising.users.Student;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * DropCommand - Drop a course section
 */
@Table(name = "command_history", isSubTable = true)
public class DropCommand extends BaseCommand {
    private ObservableStudent student;
    private Section section;
    private int previousEnrollmentId;
    private DatabaseManager dbManager;

    // Adding No argument constructor needed for fromSuperType() and ORM autoMapper()
    public DropCommand() {
        this(null, null);
    }

    public DropCommand(ObservableStudent student, Section section) {
        super();
        this.commandType = "DROP";
        this.student = student;
        this.section = section;
        this.dbManager = DatabaseManager.getInstance();
    }

    public static DropCommand fromSuperType(BaseCommand base) {
        DropCommand cmd = new DropCommand();
        BaseCommand.copyBaseFields(base, cmd);
        cmd.initAfterLoad();
        return cmd;
    }

    @Override
    public void execute() {
        executionTime = LocalDateTime.now();

        if (section.drop(student)) {
            // State Pattern: load enrollment and transition to DROPPED
            try {
                EnrollmentContext context = EnrollmentContext.loadByStudentAndSection(
                        student.getId(), section);
                if (context != null && context.canDrop()) {
                    context.drop("Dropped by student request");
                }
            } catch (SQLException e) {
                System.err.println("DropCommand: failed to load enrollment — " + e.getMessage());
            }

            executed = true;
            successful = true;

            System.out.printf("✓ Student %s dropped %s%n",
                    student.getStudentId(), section.getCourseCode());

            // Check waitlist and promote next student
            try {
                promoteFromWaitlist();
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("Failed to promote from waitlist.");
            }
        } else {
            successful   = false;
            errorMessage = String.format("Drop failed — student not enrolled in %s",
                    section.getCourseCode());
            System.out.println("✗ " + errorMessage);
        }
    }

    @Override
    public void undo() {
        if (!executed || !successful) {
            System.out.println("Cannot undo - command not executed or failed");
            return;
        }

        // Re-enroll using State Pattern
        try {
            EnrollmentContext context = EnrollmentContext.loadByStudentAndSection(
                    student.getId(), section);
            if (context != null && context.canReenroll()) {
                context.reenroll();
                System.out.printf("↶ Undone: Drop of %s - student re-enrolled%n",
                        section.getCourseCode());
                this.undoneAt = LocalDateTime.now();
                this.isUndone = true;
            }
        } catch (SQLException e) {
            System.err.println("DropCommand undo: failed to load enrollment — " + e.getMessage());
        }
    }

    @Override
    public boolean isUndoable() {
        return executed && successful && section.hasCapacity();
    }

    @Override
    public String getDescription() {
        return String.format("Drop %s (%s)", section.getCourseCode(), section.getCourseName());
    }

    private void promoteFromWaitlist() throws SQLException, IllegalAccessException {
        if (!section.getWaitlist().isEmpty() && section.hasCapacity()) {
            // Get the next waitlist entry
            WaitlistEntry nextWaitlistEntry = section.getWaitlist().get(0);
            // Lookup the student for this entry
            Student student = nextWaitlistEntry.getStudent();
            // Remove that student from the waitlist
            section.removeFromWaitlist(student);

            // Use State Pattern for enrollment
            EnrollmentContext context = EnrollmentContext.create(
                    student.getId(), section.getId(), section);
            context.confirm();

            System.out.printf("↑ Student ID %s promoted from waitlist%n", student.getStudentId());
        }
    }

    @Override
    protected String serializeCommandData() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("studentPk",            student.getId());   // int PK
        data.put("studentId", student.getStudentId());
        data.put("sectionId", section.getId()); // Assuming Section has an id
        data.put("previousEnrollmentId", previousEnrollmentId);
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize DropCommand data", e);
        }
    }

    @Override
    protected void deserializeCommandData(String json) {
        if (json == null || json.isBlank()) return;
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> data = mapper.readValue(json, Map.class);
            int studentPk = (int) data.get("studentPk");
            int sectionId = (int) data.get("sectionId");
            this.previousEnrollmentId = (int) data.get("previousEnrollmentId");

            Student raw  = DatabaseManager.getInstance().fetchOne(Student.class, "id", studentPk);
            this.student = ObservableStudent.fromSuperType(raw);
            this.section = DatabaseManager.getInstance().fetchOne(Section.class, "id", sectionId);
        } catch (JsonProcessingException | SQLException e) {
            throw new RuntimeException("Failed to deserialize DropCommand data", e);
        }
    }
}