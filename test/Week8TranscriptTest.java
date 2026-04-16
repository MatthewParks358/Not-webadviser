import edu.advising.transcript.OfficialTranscriptTemplate;
import edu.advising.transcript.UnofficialTranscriptTemplate;
import edu.advising.transcript.UnofficialTranscriptTemplate.CourseRecord;
import edu.advising.transcript.UnofficialTranscriptTemplate.SemesterRecord;

import java.util.List;

// ============================================================================
// WEEK 8: TEMPLATE METHOD PATTERN — Transcript Test Application
// ============================================================================
//
// PURPOSE:
//   Tests the Template Method pattern for transcript generation.
//   Issue #38: Official Transcript
//   Issue #39: Unofficial Transcript
//
// TESTS COVERED:
//   1. Unofficial transcript generates with UNOFFICIAL watermark
//   2. Unofficial transcript contains semester organization
//   3. Unofficial transcript contains course codes, names, credits, grades
//   4. Unofficial transcript contains semester GPA and cumulative GPA
//   5. Official transcript generates without UNOFFICIAL watermark
//
// ============================================================================

public class Week8TranscriptTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("         WEEK 8: TEMPLATE METHOD PATTERN TESTS");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        testUnofficialTranscriptWatermark();
        testUnofficialTranscriptContent();
        testOfficialTranscriptNoWatermark();

        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    private static void testUnofficialTranscriptWatermark() {
        System.out.println("TEST 1: Unofficial transcript contains UNOFFICIAL watermark");

        List<CourseRecord> fall2024Courses = List.of(
                new CourseRecord("CS101", "Intro to Programming", 3, "A"),
                new CourseRecord("MATH201", "Calculus I", 4, "B+")
        );
        List<SemesterRecord> semesters = List.of(
                new SemesterRecord("Fall 2024", fall2024Courses, 3.65)
        );

        UnofficialTranscriptTemplate transcript = new UnofficialTranscriptTemplate(
                "Jane Doe", "STU001", semesters, 3.65
        );

        String output = transcript.generate();

        if (output.contains("UNOFFICIAL")) {
            System.out.println("  ✓ PASSED: Watermark found\n");
            passed++;
        } else {
            System.out.println("  ✗ FAILED: Watermark not found\n");
            failed++;
        }
    }

    private static void testUnofficialTranscriptContent() {
        System.out.println("TEST 2: Unofficial transcript contains required content");

        List<CourseRecord> fall2024Courses = List.of(
                new CourseRecord("CS101", "Intro to Programming", 3, "A"),
                new CourseRecord("MATH201", "Calculus I", 4, "B+")
        );
        List<CourseRecord> spring2025Courses = List.of(
                new CourseRecord("CS201", "Data Structures", 3, "A-"),
                new CourseRecord("PHYS101", "Physics I", 4, "B")
        );
        List<SemesterRecord> semesters = List.of(
                new SemesterRecord("Fall 2024", fall2024Courses, 3.65),
                new SemesterRecord("Spring 2025", spring2025Courses, 3.40)
        );

        UnofficialTranscriptTemplate transcript = new UnofficialTranscriptTemplate(
                "Jane Doe", "STU001", semesters, 3.52
        );

        String output = transcript.generate();
        boolean allPassed = true;

        // Check semester organization
        if (!output.contains("Fall 2024") || !output.contains("Spring 2025")) {
            System.out.println("  ✗ Missing semester organization");
            allPassed = false;
        }

        // Check course codes
        if (!output.contains("CS101") || !output.contains("CS201")) {
            System.out.println("  ✗ Missing course codes");
            allPassed = false;
        }

        // Check course names
        if (!output.contains("Intro to Programming") || !output.contains("Data Structures")) {
            System.out.println("  ✗ Missing course names");
            allPassed = false;
        }

        // Check credits (as part of formatted output)
        if (!output.contains("3") || !output.contains("4")) {
            System.out.println("  ✗ Missing credits");
            allPassed = false;
        }

        // Check grades
        if (!output.contains("A") || !output.contains("B+")) {
            System.out.println("  ✗ Missing grades");
            allPassed = false;
        }

        // Check semester GPA
        if (!output.contains("Semester GPA: 3.65") || !output.contains("Semester GPA: 3.40")) {
            System.out.println("  ✗ Missing semester GPA");
            allPassed = false;
        }

        // Check cumulative GPA
        if (!output.contains("CUMULATIVE GPA: 3.52")) {
            System.out.println("  ✗ Missing cumulative GPA");
            allPassed = false;
        }

        if (allPassed) {
            System.out.println("  ✓ PASSED: All content present\n");
            passed++;
        } else {
            System.out.println("  ✗ FAILED: Missing content\n");
            failed++;
        }

        // Print sample output for verification
        System.out.println("--- Sample Output ---");
        System.out.println(output);
        System.out.println("--- End Sample ---\n");
    }

    private static void testOfficialTranscriptNoWatermark() {
        System.out.println("TEST 3: Official transcript does NOT contain UNOFFICIAL watermark");

        OfficialTranscriptTemplate transcript = new OfficialTranscriptTemplate(
                "Jane Doe", "STU001", "Sample academic record", "electronic", "employer@company.com"
        );

        String output = transcript.generate();

        if (!output.contains("UNOFFICIAL")) {
            System.out.println("  ✓ PASSED: No watermark in official transcript\n");
            passed++;
        } else {
            System.out.println("  ✗ FAILED: Watermark incorrectly present\n");
            failed++;
        }
    }
}
