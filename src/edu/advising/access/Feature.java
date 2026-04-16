package edu.advising.access;

/**
 * Feature - Defines system features that can be access-controlled.
 *
 * Used with the Decorator pattern to check if an account has access
 * to specific functionality before allowing the action.
 */
public enum Feature {
    // Student features
    REGISTRATION("Course Registration"),
    VIEW_GRADES("View Grades"),
    VIEW_SCHEDULE("View Schedule"),
    FINANCIAL_AID("Financial Aid"),
    MAKE_PAYMENT("Make Payment"),
    LIBRARY("Library Access"),

    // Faculty features
    VIEW_ROSTER("View Class Roster"),
    ENTER_GRADES("Enter Grades"),
    DROP_STUDENTS("Drop Students"),

    // Admin features
    MANAGE_USERS("Manage Users"),
    SYSTEM_CONFIG("System Configuration"),
    VIEW_REPORTS("View Reports");

    private final String displayName;

    Feature(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
