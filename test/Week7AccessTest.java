import edu.advising.access.*;
import edu.advising.users.User;

// ============================================================================
// WEEK 7: DECORATOR PATTERN — System Access Control Test
// ============================================================================
//
// PURPOSE:
//   Tests the Decorator pattern implementation for system access control.
//   Verifies that holds can be applied/removed and access is controlled appropriately.
//
// TESTS COVERED:
//   GROUP 1  — Default access for each account type
//   GROUP 2  — Financial hold decorator
//   GROUP 3  — Academic hold decorator
//   GROUP 4  — Library hold decorator
//   GROUP 5  — Stacking multiple holds in different orders
//   GROUP 6  — Removing holds restores access
//   GROUP 7  — Admin accounts have full access
//
// RUN WITH:
//   mvn exec:java@run-week7-test
//
// ============================================================================

public class Week7AccessTest {

    private static int passed = 0;
    private static int failed = 0;

    // Test fixtures (using base User for portability - no DB dependencies)
    private static User studentUser;
    private static User facultyUser;
    private static User adminUser;

    public static void main(String[] args) {
        banner("WEEK 7 — DECORATOR PATTERN  |  System Access Control");

        setUp();

        testDefaultStudentAccess();
        testDefaultFacultyAccess();
        testDefaultAdminAccess();
        testFinancialHoldDecorator();
        testAcademicHoldDecorator();
        testLibraryHoldDecorator();
        testStackedHolds();
        testRemovingHolds();
        testHoldOrderIndependence();

        // Final report
        banner("RESULTS");
        System.out.printf("  Total  : %d%n", passed + failed);
        System.out.printf("  Passed : %d%n", passed);
        System.out.printf("  Failed : %d%n", failed);
        System.out.println(failed == 0 ? "\n  ALL TESTS PASSED\n" : "\n  SOME TESTS FAILED\n");
    }

    private static void setUp() {
        header("SET UP");

        // Create test users (using base User class - no DB dependencies needed)
        studentUser = new User("jsmith", "password", "jsmith@college.edu", "Jane", "Smith");
        note("Student user: " + studentUser.getFullName());

        facultyUser = new User("prof.jones", "password", "jones@college.edu", "Marcus", "Jones");
        note("Faculty user: " + facultyUser.getFullName());

        adminUser = new User("admin", "password", "admin@college.edu", "System", "Admin");
        note("Admin user: " + adminUser.getFullName());

        System.out.println();
    }

    // =========================================================================
    // GROUP 1 — Default Student Access
    // =========================================================================

    private static void testDefaultStudentAccess() {
        header("GROUP 1 — Default Student Access");

        Account student = new StudentAccount(studentUser);

        // Students should have access to student features
        check("1.1  Student has REGISTRATION access",
                student.hasAccess(Feature.REGISTRATION));
        check("1.2  Student has VIEW_GRADES access",
                student.hasAccess(Feature.VIEW_GRADES));
        check("1.3  Student has VIEW_SCHEDULE access",
                student.hasAccess(Feature.VIEW_SCHEDULE));
        check("1.4  Student has FINANCIAL_AID access",
                student.hasAccess(Feature.FINANCIAL_AID));
        check("1.5  Student has MAKE_PAYMENT access",
                student.hasAccess(Feature.MAKE_PAYMENT));
        check("1.6  Student has LIBRARY access",
                student.hasAccess(Feature.LIBRARY));

        // Students should NOT have access to faculty/admin features
        check("1.7  Student lacks VIEW_ROSTER access",
                !student.hasAccess(Feature.VIEW_ROSTER));
        check("1.8  Student lacks ENTER_GRADES access",
                !student.hasAccess(Feature.ENTER_GRADES));
        check("1.9  Student lacks MANAGE_USERS access",
                !student.hasAccess(Feature.MANAGE_USERS));
    }

    // =========================================================================
    // GROUP 2 — Default Faculty Access
    // =========================================================================

    private static void testDefaultFacultyAccess() {
        header("GROUP 2 — Default Faculty Access");

        Account faculty = new FacultyAccount(facultyUser);

        // Faculty should have access to faculty features
        check("2.1  Faculty has VIEW_ROSTER access",
                faculty.hasAccess(Feature.VIEW_ROSTER));
        check("2.2  Faculty has ENTER_GRADES access",
                faculty.hasAccess(Feature.ENTER_GRADES));
        check("2.3  Faculty has VIEW_SCHEDULE access",
                faculty.hasAccess(Feature.VIEW_SCHEDULE));
        check("2.4  Faculty has DROP_STUDENTS access",
                faculty.hasAccess(Feature.DROP_STUDENTS));
        check("2.5  Faculty has LIBRARY access",
                faculty.hasAccess(Feature.LIBRARY));

        // Faculty should NOT have access to student/admin features
        check("2.6  Faculty lacks REGISTRATION access",
                !faculty.hasAccess(Feature.REGISTRATION));
        check("2.7  Faculty lacks FINANCIAL_AID access",
                !faculty.hasAccess(Feature.FINANCIAL_AID));
        check("2.8  Faculty lacks MANAGE_USERS access",
                !faculty.hasAccess(Feature.MANAGE_USERS));
    }

    // =========================================================================
    // GROUP 3 — Default Admin Access
    // =========================================================================

    private static void testDefaultAdminAccess() {
        header("GROUP 3 — Default Admin Access (Full Access)");

        Account admin = new AdminAccount(adminUser);

        // Admin should have access to ALL features
        check("3.1  Admin has REGISTRATION access",
                admin.hasAccess(Feature.REGISTRATION));
        check("3.2  Admin has VIEW_GRADES access",
                admin.hasAccess(Feature.VIEW_GRADES));
        check("3.3  Admin has VIEW_ROSTER access",
                admin.hasAccess(Feature.VIEW_ROSTER));
        check("3.4  Admin has ENTER_GRADES access",
                admin.hasAccess(Feature.ENTER_GRADES));
        check("3.5  Admin has MANAGE_USERS access",
                admin.hasAccess(Feature.MANAGE_USERS));
        check("3.6  Admin has SYSTEM_CONFIG access",
                admin.hasAccess(Feature.SYSTEM_CONFIG));
        check("3.7  Admin has VIEW_REPORTS access",
                admin.hasAccess(Feature.VIEW_REPORTS));
        check("3.8  Admin has LIBRARY access",
                admin.hasAccess(Feature.LIBRARY));
    }

    // =========================================================================
    // GROUP 4 — Financial Hold Decorator
    // =========================================================================

    private static void testFinancialHoldDecorator() {
        header("GROUP 4 — Financial Hold Decorator");

        Account student = new StudentAccount(studentUser);
        Account withHold = new FinancialHoldDecorator(student);

        System.out.println("  (Financial hold applied to student account)");
        System.out.println();

        // Financial hold blocks these features
        check("4.1  Financial hold blocks REGISTRATION",
                !withHold.hasAccess(Feature.REGISTRATION));
        check("4.2  Financial hold blocks FINANCIAL_AID",
                !withHold.hasAccess(Feature.FINANCIAL_AID));
        check("4.3  Financial hold blocks MAKE_PAYMENT",
                !withHold.hasAccess(Feature.MAKE_PAYMENT));
        check("4.4  Financial hold blocks VIEW_GRADES",
                !withHold.hasAccess(Feature.VIEW_GRADES));

        // These should still be accessible
        check("4.5  VIEW_SCHEDULE still accessible",
                withHold.hasAccess(Feature.VIEW_SCHEDULE));
        check("4.6  LIBRARY still accessible",
                withHold.hasAccess(Feature.LIBRARY));

        // Verify hold type
        check("4.7  Hold type is 'Financial'",
                "Financial".equals(((AccountDecorator) withHold).getHoldType()));
    }

    // =========================================================================
    // GROUP 5 — Academic Hold Decorator
    // =========================================================================

    private static void testAcademicHoldDecorator() {
        header("GROUP 5 — Academic Hold Decorator");

        Account student = new StudentAccount(studentUser);
        Account withHold = new AcademicHoldDecorator(student);

        System.out.println("  (Academic hold applied to student account)");
        System.out.println();

        // Academic hold blocks these features
        check("5.1  Academic hold blocks REGISTRATION",
                !withHold.hasAccess(Feature.REGISTRATION));
        check("5.2  Academic hold blocks VIEW_GRADES",
                !withHold.hasAccess(Feature.VIEW_GRADES));

        // These should still be accessible
        check("5.3  FINANCIAL_AID still accessible",
                withHold.hasAccess(Feature.FINANCIAL_AID));
        check("5.4  MAKE_PAYMENT still accessible",
                withHold.hasAccess(Feature.MAKE_PAYMENT));
        check("5.5  LIBRARY still accessible",
                withHold.hasAccess(Feature.LIBRARY));

        // Verify hold type
        check("5.6  Hold type is 'Academic'",
                "Academic".equals(((AccountDecorator) withHold).getHoldType()));
    }

    // =========================================================================
    // GROUP 6 — Library Hold Decorator
    // =========================================================================

    private static void testLibraryHoldDecorator() {
        header("GROUP 6 — Library Hold Decorator");

        Account student = new StudentAccount(studentUser);
        Account withHold = new LibraryHoldDecorator(student);

        System.out.println("  (Library hold applied to student account)");
        System.out.println();

        // Library hold blocks library access
        check("6.1  Library hold blocks LIBRARY",
                !withHold.hasAccess(Feature.LIBRARY));

        // Other features should still be accessible
        check("6.2  REGISTRATION still accessible",
                withHold.hasAccess(Feature.REGISTRATION));
        check("6.3  VIEW_GRADES still accessible",
                withHold.hasAccess(Feature.VIEW_GRADES));
        check("6.4  FINANCIAL_AID still accessible",
                withHold.hasAccess(Feature.FINANCIAL_AID));

        // Verify hold type
        check("6.5  Hold type is 'Library'",
                "Library".equals(((AccountDecorator) withHold).getHoldType()));
    }

    // =========================================================================
    // GROUP 7 — Stacked Holds
    // =========================================================================

    private static void testStackedHolds() {
        header("GROUP 7 — Stacked Holds (Multiple Decorators)");

        Account student = new StudentAccount(studentUser);

        // Apply multiple holds
        Account withFinancial = new FinancialHoldDecorator(student);
        Account withBoth = new LibraryHoldDecorator(withFinancial);

        System.out.println("  (Financial + Library holds applied)");
        System.out.println();

        // Both holds should be active
        check("7.1  Stacked: REGISTRATION blocked (financial)",
                !withBoth.hasAccess(Feature.REGISTRATION));
        check("7.2  Stacked: LIBRARY blocked (library)",
                !withBoth.hasAccess(Feature.LIBRARY));
        check("7.3  Stacked: VIEW_GRADES blocked (financial)",
                !withBoth.hasAccess(Feature.VIEW_GRADES));

        // VIEW_SCHEDULE is not blocked by either
        check("7.4  Stacked: VIEW_SCHEDULE still accessible",
                withBoth.hasAccess(Feature.VIEW_SCHEDULE));

        // Add academic hold too
        Account withAll = new AcademicHoldDecorator(withBoth);
        System.out.println("  (Added Academic hold - now all three)");

        check("7.5  Triple stacked: still blocks REGISTRATION",
                !withAll.hasAccess(Feature.REGISTRATION));
        check("7.6  Triple stacked: still blocks LIBRARY",
                !withAll.hasAccess(Feature.LIBRARY));
    }

    // =========================================================================
    // GROUP 8 — Removing Holds Restores Access
    // =========================================================================

    private static void testRemovingHolds() {
        header("GROUP 8 — Removing Holds Restores Access");

        Account student = new StudentAccount(studentUser);
        Account withFinancial = new FinancialHoldDecorator(student);

        System.out.println("  (Financial hold applied)");
        check("8.1  Before removal: REGISTRATION blocked",
                !withFinancial.hasAccess(Feature.REGISTRATION));

        // Remove the financial hold
        Account afterRemoval = AccessChecker.removeHold(withFinancial, FinancialHoldDecorator.class);

        System.out.println("  (Financial hold removed)");
        check("8.2  After removal: REGISTRATION accessible",
                afterRemoval.hasAccess(Feature.REGISTRATION));
        check("8.3  After removal: VIEW_GRADES accessible",
                afterRemoval.hasAccess(Feature.VIEW_GRADES));

        // Test removing from stacked holds
        Account withBoth = new LibraryHoldDecorator(new FinancialHoldDecorator(student));
        System.out.println("  (Financial + Library holds applied)");

        check("8.4  Stacked: LIBRARY blocked",
                !withBoth.hasAccess(Feature.LIBRARY));
        check("8.5  Stacked: REGISTRATION blocked",
                !withBoth.hasAccess(Feature.REGISTRATION));

        // Remove just the library hold
        Account afterLibraryRemoval = AccessChecker.removeHold(withBoth, LibraryHoldDecorator.class);
        System.out.println("  (Library hold removed, financial remains)");

        check("8.6  After library removal: LIBRARY accessible",
                afterLibraryRemoval.hasAccess(Feature.LIBRARY));
        check("8.7  After library removal: REGISTRATION still blocked",
                !afterLibraryRemoval.hasAccess(Feature.REGISTRATION));
    }

    // =========================================================================
    // GROUP 9 — Hold Order Independence
    // =========================================================================

    private static void testHoldOrderIndependence() {
        header("GROUP 9 — Hold Order Independence");

        Account student = new StudentAccount(studentUser);

        // Order 1: Financial -> Library
        Account order1 = new LibraryHoldDecorator(new FinancialHoldDecorator(student));

        // Order 2: Library -> Financial
        Account order2 = new FinancialHoldDecorator(new LibraryHoldDecorator(student));

        System.out.println("  (Testing that hold order doesn't affect blocking)");

        // Both orders should block the same features
        check("9.1  Order 1: REGISTRATION blocked",
                !order1.hasAccess(Feature.REGISTRATION));
        check("9.2  Order 2: REGISTRATION blocked",
                !order2.hasAccess(Feature.REGISTRATION));
        check("9.3  Order 1: LIBRARY blocked",
                !order1.hasAccess(Feature.LIBRARY));
        check("9.4  Order 2: LIBRARY blocked",
                !order2.hasAccess(Feature.LIBRARY));
        check("9.5  Order 1: VIEW_SCHEDULE accessible",
                order1.hasAccess(Feature.VIEW_SCHEDULE));
        check("9.6  Order 2: VIEW_SCHEDULE accessible",
                order2.hasAccess(Feature.VIEW_SCHEDULE));
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private static void check(String label, boolean condition) {
        if (condition) {
            System.out.printf("  [PASS]  %s%n", label);
            passed++;
        } else {
            System.out.printf("  [FAIL]  %s%n", label);
            failed++;
        }
    }

    private static void banner(String text) {
        String line = "=".repeat(62);
        System.out.printf("%n%s%n  %s%n%s%n", line, text, line);
    }

    private static void header(String text) {
        System.out.printf("%n  -- %s --%n", text);
    }

    private static void note(String text) {
        System.out.printf("  > %s%n", text);
    }
}
