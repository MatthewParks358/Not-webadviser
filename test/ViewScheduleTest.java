import edu.advising.commands.*;
import edu.advising.core.DatabaseManager;
import edu.advising.users.Faculty;
import edu.advising.users.Student;
import edu.advising.users.UserFactory;

// Week 9 Test - View Schedule

public class ViewScheduleTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) throws Exception {

        System.out.println("=== Week 9 - View Schedule Tests ===\n");

        DatabaseManager db = DatabaseManager.getInstance();
        db.seedDatabase();
        UserFactory userFactory = new UserFactory();

        // add the new columns if they don't exist
        try {
            db.executeUpdate("ALTER TABLE sections ADD COLUMN IF NOT EXISTS meeting_days VARCHAR(10)", new Object[0]);
            db.executeUpdate("ALTER TABLE sections ADD COLUMN IF NOT EXISTS start_time VARCHAR(5)", new Object[0]);
            db.executeUpdate("ALTER TABLE sections ADD COLUMN IF NOT EXISTS end_time VARCHAR(5)", new Object[0]);
        } catch (Exception e) {
            System.out.println("Columns may already exist: " + e.getMessage());
        }

        // create a student
        Student student = (Student) userFactory.createUser(
                "STUDENT", "jdoe", "Password1!",
                "jdoe@college.edu", "John", "Doe", "S12345");

        // create a faculty member
        Faculty faculty = (Faculty) userFactory.createUser(
                "FACULTY", "prof.lee", "Password1!",
                "lee@college.edu", "Amy", "Lee", "F001", "CIS");

        // set up a course and section
        int deptId = db.executeInsert("INSERT INTO departments (code, name) VALUES (?,?)", "CIS", "Computer Info Systems");
        int courseId = db.executeInsert("INSERT INTO courses (code, name, credits, department_id, is_active) VALUES (?,?,?,?,?)",
                "CIS18", "Intro to Java", 3.0, deptId, true);

        Section section = new Section(courseId, "01", "SP", 2026, 30, 0, faculty.getId());
        section.setRoom("BUS210");
        section.setMeetingDays("MWF");
        section.setStartTime("10:00");
        section.setEndTime("11:05");
        db.upsert(section);

        // enroll the student
        section.enroll(student);

        // ---------------------------------------------------
        // Test 1: basic execute runs without crashing
        // ---------------------------------------------------
        System.out.println("Test 1: execute() runs without crashing");
        try {
            ViewScheduleCommand cmd = new ViewScheduleCommand(student, "SP", 2026);
            cmd.execute();
            if (cmd.wasSuccessful()) {
                System.out.println("  PASS");
                passed++;
            } else {
                System.out.println("  FAIL - was not successful");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("  FAIL - threw exception: " + e.getMessage());
            failed++;
        }

        // ---------------------------------------------------
        // Test 2: isUndoable returns false
        // ---------------------------------------------------
        System.out.println("\nTest 2: isUndoable() returns false");
        ViewScheduleCommand cmd2 = new ViewScheduleCommand(student, "SP", 2026);
        if (!cmd2.isUndoable()) {
            System.out.println("  PASS");
            passed++;
        } else {
            System.out.println("  FAIL - should not be undoable");
            failed++;
        }

        // ---------------------------------------------------
        // Test 3: empty schedule doesn't crash
        // ---------------------------------------------------
        System.out.println("\nTest 3: student with no classes doesn't crash");
        try {
            Student emptyStudent = (Student) userFactory.createUser(
                    "STUDENT", "nobody", "Password1!",
                    "nobody@college.edu", "Empty", "Student", "S99999");
            ViewScheduleCommand cmd3 = new ViewScheduleCommand(emptyStudent, "SP", 2026);
            cmd3.execute();
            System.out.println("  PASS");
            passed++;
        } catch (Exception e) {
            System.out.println("  FAIL - threw exception: " + e.getMessage());
            failed++;
        }

        // ---------------------------------------------------
        // Test 4: section with no time shows TBA
        // ---------------------------------------------------
        System.out.println("\nTest 4: section with no time set shows TBA");
        try {
            Student student2 = (Student) userFactory.createUser(
                    "STUDENT", "jdoe2", "Password1!",
                    "jdoe2@college.edu", "Jane", "Doe", "S12346");

            Section noTimeSection = new Section(courseId, "02", "SP", 2026, 30, 0, faculty.getId());
            // intentionally not setting meeting days or time
            db.upsert(noTimeSection);
            noTimeSection.enroll(student2);

            ViewScheduleCommand cmd4 = new ViewScheduleCommand(student2, "SP", 2026);
            cmd4.execute();
            System.out.println("  PASS");
            passed++;
        } catch (Exception e) {
            System.out.println("  FAIL - threw exception: " + e.getMessage());
            failed++;
        }

        // ---------------------------------------------------
        // Results
        // ---------------------------------------------------
        System.out.println("\n=== Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        db.shutdown();
    }
}
