package edu.advising.access;

import java.util.EnumSet;
import java.util.Set;

/**
 * AcademicHoldDecorator - Blocks access to registration and grade features.
 *
 * Applied when a student has academic issues (probation, missing requirements, etc.)
 * Blocks: REGISTRATION, VIEW_GRADES
 */
public class AcademicHoldDecorator extends AccountDecorator {

    private static final Set<Feature> BLOCKED_FEATURES = EnumSet.of(
            Feature.REGISTRATION,
            Feature.VIEW_GRADES
    );

    public AcademicHoldDecorator(Account wrappedAccount) {
        super(wrappedAccount);
    }

    @Override
    public boolean hasAccess(Feature feature) {
        if (BLOCKED_FEATURES.contains(feature)) {
            printDeniedMessage(feature);
            return false;
        }
        return wrappedAccount.hasAccess(feature);
    }

    @Override
    public String getHoldType() {
        return "Academic";
    }
}
