import edu.advising.classes.CourseFactory;
import edu.advising.classes.GraduateCourseFactory;
import edu.advising.classes.UndergraduateCourseFactory;
import edu.advising.commands.Course;

/**
 * ClassManagementDemo, demonstrates class usage
 *
 * Simulates an administrator creating new classes to offer to students.
 * No database is required for the demo saveCourse() is overridden
 * by anonymous subclasses that skip persistence, keeping this runnable standalone.
 */
public class ClassManagementDemo {

    public static void main(String[] args) {

        System.out.println("=== Class Management — Factory Method Demo ===\n");

        CourseFactory undergradFactory = new UndergraduateCourseFactory() {
            @Override
            protected void saveCourse(Course course) {
                // Skipping DB for demo; in production this would call DatabaseManager.upsert()
                System.out.println("  [DEMO] Skipping DB save for: " + course.getCode());
            }
        };

        CourseFactory gradFactory = new GraduateCourseFactory() {
            @Override
            protected void saveCourse(Course course) {
                System.out.println("  [DEMO] Skipping DB save for: " + course.getCode());
            }
        };

        System.out.println("Admin action: Create undergraduate course");
        Course cs101 = undergradFactory.offerCourse(
                "CS101",
                "Introduction to Computer Science",
                "Fundamental concepts of programming and computational thinking.",
                1  // departmentId — Computer Science dept
        );
        printCourse(cs101);


        System.out.println("\nAdmin action: Create another undergraduate course");
        Course math201 = undergradFactory.offerCourse(
                "MATH201",
                "Calculus I",
                "Limits, derivatives, and integrals of single-variable functions.",
                2  // departmentId — Mathematics dept
        );
        printCourse(math201);

        // ── Administrator creates a graduate course ─────────────────────────────
        System.out.println("\nAdmin action: Create graduate course");
        Course cs501 = gradFactory.offerCourse(
                "CS501",
                "Advanced Algorithms",
                "Analysis and design of algorithms: complexity, graph theory, NP-completeness.",
                1  // departmentId — Computer Science dept
        );
        printCourse(cs501);

        System.out.println("\n=== Demo Complete ===");
        System.out.println("Pattern summary:");
        System.out.println("  CourseFactory (abstract)         → declares createCourse()");
        System.out.println("  UndergraduateCourseFactory       → creates 3-credit UNDERGRADUATE courses");
        System.out.println("  GraduateCourseFactory            → creates 4-credit GRADUATE courses");
        System.out.println("  offerCourse() (template method)  → create → save → return, same for all factories");
    }

    private static void printCourse(Course course) {
        System.out.printf("  Created: [%s] %s | Level: %s | Credits: %.1f | Active: %s%n",
                course.getCode(),
                course.getName(),
                course.getLevel(),
                course.getCredits(),
                course.isActive());
    }
}
