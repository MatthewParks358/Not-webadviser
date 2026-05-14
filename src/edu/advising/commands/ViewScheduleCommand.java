package edu.advising.commands;

import edu.advising.core.Table;
import edu.advising.users.Student;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

// View Schedule Command
// Shows the student their current class schedule

@Table(name = "command_history", isSubTable = true)
public class ViewScheduleCommand extends BaseCommand {

    private Student student;
    private String semester;
    private int year;

    public ViewScheduleCommand(Student student, String semester, int year) {
        super();
        this.commandType = "VIEW_SCHEDULE";
        this.student = student;
        this.semester = semester;
        this.year = year;
    }

    @Override
    public void execute() {
        executionTime = LocalDateTime.now();

        // print the header
        System.out.println("=== Schedule for " + student.getFirstName() + " " + student.getLastName() + " ===");
        System.out.println("Semester: " + semester + " " + year);
        System.out.println("-------------------------------------------");

        // get the student's sections
        List<Section> sections = null;
        try {
            sections = student.getSections();
        } catch (SQLException e) {
            System.out.println("Error loading schedule: " + e.getMessage());
            successful = false;
            return;
        }

        // check if they have any classes
        if (sections == null || sections.isEmpty()) {
            System.out.println("No classes found for this semester.");
            successful = true;
            return;
        }

        // loop through each section and print info
        double totalCredits = 0;
        for (Section s : sections) {

            // only show sections for the right semester and year
            if (!s.getSemester().equals(semester) || s.getYear() != year) {
                continue;
            }

            // get the course name
            String courseName = s.getCourseName();
            String courseCode = s.getCourseCode();

            // get meeting days and time, show TBA if not set
            String days = s.getMeetingDays();
            if (days == null) {
                days = "TBA";
            }

            String time = "TBA";
            if (s.getStartTime() != null && s.getEndTime() != null) {
                time = s.getStartTime() + " - " + s.getEndTime();
            }

            // get room, show TBA if not set
            String room = s.getRoom();
            if (room == null) {
                room = "TBA";
            }

            // print the section info
            System.out.println(courseCode + " - " + courseName);
            System.out.println("  Days: " + days + "  Time: " + time + "  Room: " + room);

            // add credits to total
            try {
                if (s.getCourse() != null) {
                    totalCredits += s.getCourse().getCredits();
                }
            } catch (SQLException e) {
                // just skip credits if we can't load the course
            }

            System.out.println();
        }

        System.out.println("-------------------------------------------");
        System.out.println("Total Credits: " + totalCredits);

        successful = true;
        executed = true;
    }

    @Override
    public void undo() {
        // you can't undo viewing a schedule, nothing changed
        System.out.println("Nothing to undo.");
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "View schedule for " + student.getFirstName() + " " + student.getLastName();
    }

    @Override
    protected String serializeCommandData() {
        return "{\"studentId\":" + student.getId() + ",\"semester\":\"" + semester + "\",\"year\":" + year + "}";
    }

    @Override
    protected void deserializeCommandData(String json) {
        // not needed for this command
    }
}
